package com.bybit.api.client.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bybit.api.client.config.BybitApiConfig;
import com.bybit.api.client.security.HmacSHA256Signer;
import lombok.Getter;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.bybit.api.client.constant.Util.generateTransferID;

@Getter
public class WebsocketClientImpl implements WebsocketClient {
    private static final String THREAD_PUBLIC_PING = "thread-public-ping";
    private static final String THREAD_PRIVATE_AUTH = "thread-private-auth";
    private static final String THREAD_PRIVATE_PING = "thread-private-ping";
    private static final String PING_DATA = "{\"op\":\"ping\"}";
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketClientImpl.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private WebsocketMessageHandler messageHandler;
    private final WebSocketHttpClientSingleton webSocketHttpClientSingleton;

    private final String apikey;
    private final String secret;
    private final String baseUrl;
    private final Boolean debugMode;
    private final String logOption;
    private final Integer pingInterval;
    private final String maxAliveTime;
    private List<String> argNames;
    private String path;

    public WebsocketClientImpl(String apikey, String secret, String baseUrl, Integer pingInterval, String maxAliveTime, Boolean debugMode, String logOption, WebsocketMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
        this.apikey = apikey;
        this.secret = secret;
        this.baseUrl = baseUrl;
        this.pingInterval = pingInterval;
        this.debugMode = debugMode;
        this.logOption = logOption;
        this.maxAliveTime = maxAliveTime;
        webSocketHttpClientSingleton = WebSocketHttpClientSingleton.createInstance(this.debugMode, this.logOption);
    }

    private void setupPublicChannelStream(List<String> argNames, String path) {
        this.argNames = new ArrayList<>(argNames);
        this.path = path;
    }

    private void sendJsonMessage(WebSocket ws, Object messageObject, String messageType) {
        try {
            String json = objectMapper.writeValueAsString(messageObject);
            ws.send(json);
            LOGGER.info("Sent {}: {}", messageType, json);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error serializing {} message: ", messageType, e);
        }
    }

    private void sendSubscribeMessage(WebSocket ws) {
        Map<String, Object> subscribeMsg = createSubscribeMessage();
        sendJsonMessage(ws, subscribeMsg, "Subscribe");
    }

    @NotNull
    private Map<String, Object> createSubscribeMessage() {
        Map<String, Object> subscribeMsg = new LinkedHashMap<>();
        subscribeMsg.put("op", "subscribe");
        subscribeMsg.put("req_id", generateTransferID());
        subscribeMsg.put("args", argNames);
        return subscribeMsg;
    }

    private boolean requiresAuthentication(String path) {
        return BybitApiConfig.V5_PRIVATE.equals(path) ||
                BybitApiConfig.V3_CONTRACT_PRIVATE.equals(path) ||
                BybitApiConfig.V3_UNIFIED_PRIVATE.equals(path) ||
                BybitApiConfig.V3_SPOT_PRIVATE.equals(path);
    }

    @NotNull
    private Thread createPingThread(WebSocket ws) {
        return new Thread(() -> {
            try {
                while (true) {
                    if (ws != null) { // check if the WebSocket is still valid
                        ws.send(PING_DATA);
                        LOGGER.info(PING_DATA);
                        TimeUnit.SECONDS.sleep(pingInterval); // waits for 10 seconds before the next iteration
                    } else {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                LOGGER.error("Ping thread was interrupted", e);
                Thread.currentThread().interrupt();
            }
        });
    }

    @NotNull
    private Map<String, Object> createAuthMessage() {
        long expires = Instant.now().toEpochMilli() + 10000;
        String val = "GET/realtime" + expires;
        String signature = HmacSHA256Signer.auth(val, secret);

        var args = List.of(apikey, expires, signature);
        return Map.of("req_id", generateTransferID(), "op", "auth", "args", args);
    }

    private void sendAuthMessage(WebSocket ws) {
        var authMessage = createAuthMessage();
        sendJsonMessage(ws, authMessage, "Auth");
    }

    @NotNull
    private Thread createAuthThread(WebSocket ws, Runnable afterAuth) {
        return new Thread(() -> {
            try {
                sendAuthMessage(ws);
                if (afterAuth != null) {
                    afterAuth.run();
                }
            } catch (Exception e) {
                LOGGER.error("Error during authentication: ", e);
            }
        });
    }

    @NotNull
    private String getWssUrl() {
        Pattern pattern = Pattern.compile("(\\d+)([sm])");
        Matcher matcher = pattern.matcher(maxAliveTime);
        String wssUrl;
        if (matcher.matches()) {
            int timeValue = Integer.parseInt(matcher.group(1));
            String timeUnit = matcher.group(2);
            boolean isTimeValid = isTimeValid(timeUnit, timeValue);

            wssUrl = isTimeValid && requiresAuthentication(path) ? baseUrl + path + "?max_alive_time=" + maxAliveTime : baseUrl + path;
        } else {
            wssUrl = baseUrl + path;
        }
        return wssUrl;
    }

    private boolean isTimeValid(String timeUnit, int timeValue) {
        int minValue = "s".equals(timeUnit) ? 30 : 1;
        int maxValue = "s".equals(timeUnit) ? 600 : 10;
        return timeValue >= minValue && timeValue <= maxValue;
    }

    @NotNull
    private WebSocketListener createWebSocketListener() {
        return new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                WebsocketClientImpl.this.onClose(code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                WebsocketClientImpl.this.onError(t);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                try {
                    WebsocketClientImpl.this.onMessage(text);
                } catch (Exception e) {
                    WebsocketClientImpl.this.onError(e);
                }
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                WebsocketClientImpl.this.onOpen(webSocket);
            }
        };
    }

    public void setMessageHandler(WebsocketMessageHandler handler) {
        this.messageHandler = handler;
    }

    @Override
    public void onMessage(String msg) throws JsonProcessingException {
        if (messageHandler != null) {
            messageHandler.handleMessage(msg);
        } else {
            LOGGER.info(msg);
        }
    }

    @Override
    public void onError(Throwable t) {
        LOGGER.error(t.getMessage());
    }

    @Override
    public void onClose(int code, String reason) {
        LOGGER.warn("websocket connection is about to close: " + reason);
    }

    @Override
    public void onOpen(WebSocket ws) {
        // Start the ping thread immediately.
        Thread pingThread = createPingThread(ws);
        pingThread.setName(THREAD_PUBLIC_PING); // Default to public ping name
        pingThread.start();

        // If it requires authentication, authenticate first, then subscribe.
        if (requiresAuthentication(path)) {
            Thread authThread = createAuthThread(ws, () -> {
                // After auth, send a subscribed message.
                sendSubscribeMessage(ws);
            });
            authThread.start();
            pingThread.setName(THREAD_PRIVATE_PING);
        } else {
            // If no authentication is needed, just send the subscribed message.
            sendSubscribeMessage(ws);
        }
    }

    @Override
    public void connect() {
        String wssUrl = getWssUrl();
        LOGGER.info(wssUrl);
        webSocketHttpClientSingleton.createWebSocket(wssUrl, createWebSocketListener());
    }

    @Override
    public void getPublicChannelStream(List<String> argNames, String path) {
        setupPublicChannelStream(argNames, path);
        connect();
    }

    @Override
    public void getPrivateChannelStream(List<String> argNames, String path) {
        setupPublicChannelStream(argNames, path);
        connect();
    }
}
