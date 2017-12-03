package org.vertx.exstension.config;

import io.vertx.core.json.JsonObject;

/**
 * configuration for web container.
 * Created by kam on 2017/12/3.
 */
public class WebConfigurator {
    private static int DEFAULT_WEB_PORT = 80;
    private static int HTTP_PORT = DEFAULT_WEB_PORT;

    public static void init(JsonObject subProperties) {
        HTTP_PORT = subProperties.size() > 0 && subProperties.containsKey(WebConst.HTTP_PORT.getKey()) ?
                Integer.parseInt(subProperties.getString(WebConst.HTTP_PORT.getKey())) : DEFAULT_WEB_PORT;
    }

    public static int getHttpPort() {
        return HTTP_PORT;
    }
}
