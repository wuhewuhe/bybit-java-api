package com.bybit.api.client.constant;

import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Constants used throughout bybit's API.
 */
public class BybitApiConstants {
    /**
     *  HTTP Header to be used for content type.
     */
    public static final String API_CONTENT_TYPE = "Content-Type";
    /**
     * HTTP Header to be used for API-KEY authentication.
     */
    public static final String API_KEY_HEADER = "X-BAPI-API-KEY";

    /**
     * HTTP Header to be used for signature.
     */
    public static final String SIGN_HEADER = "X-BAPI-SIGN";
    /**
     * HTTP Header to be used for signature type.
     */
    public static final String SIGN_TYPE_HEADER = "X-BAPI-SIGN-TYPE";

    /**
     * HTTP Header to be used for timestamp.
     */
    public static final String TIMESTAMP_HEADER = "X-BAPI-TIMESTAMP";

    /**
     * HTTP Header to be used for receiving window.
     */
    public static final String RECV_WINDOW_HEADER = "X-BAPI-RECV-WINDOW";
    /**
     * Default receiving window.
     */
    public static final long DEFAULT_RECEIVING_WINDOW = 5000;
    /**
     * Default Ping Pong Heart Beat Request Interval
     */
    public static final int DEFAULT_PING_INTERVAL = 20;
    /**
     * Default Maximum Alive Time
     */
    public static final String DEFAULT_MAX_ALIVE_TIME = "-1";
    /**
     * Default content type.
     */
    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    /**
     * Decorator to indicate that an endpoint requires a signature.
     */
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED_HEADER = SIGN_TYPE_HEADER + ": 2";
    /**
     * Default ToStringStyle used by toString methods.
     * Override this to change the output format of the overridden toString methods.
     *  - Example ToStringStyle.JSON_STYLE
     */
    public static ToStringStyle TO_STRING_BUILDER_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;
}
