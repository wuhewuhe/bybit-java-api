package com.bybit.api.client.constant;

import java.util.HashMap;
import java.util.List;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Utility class
 */
public final class Util {

    public static long generateTimestamp() {
        return Instant.now().toEpochMilli();
    }

    private Util() {

    }

    public static Map<String, Object> convertQueryToMap(String query) {
        Map<String, Object> result = new HashMap<>();
        if (query == null || query.isEmpty()) {
            return result;
        }

        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }
        }

        return result;
    }

    public static String generateTransferID()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String listToString(List<String> items) {
        return String.join(",", items);
    }
}