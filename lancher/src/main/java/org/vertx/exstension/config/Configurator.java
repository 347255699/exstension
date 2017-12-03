package org.vertx.exstension.config;

import io.vertx.core.json.JsonObject;
import org.vertx.exstension.utils.PropertiesLoader;

/**
 * configuration for current system.
 * Created by kam on 2017/12/3.
 */
public class Configurator {
    private static int DEFAULT_WEB_PORT = 80;
    private static int HTTP_PORT = DEFAULT_WEB_PORT;

    public static void init() {
        JsonObject properties = new PropertiesLoader(SysConst.DEFAULT_PROPERTIES_PATH.getKey()).asJson();
        JsonObject subProperties = new JsonObject();
        if (properties.containsKey(SysConst.CURR_PROPERTIES_PATH.getKey()))
            subProperties = new PropertiesLoader(properties.getString(SysConst.CURR_PROPERTIES_PATH.getKey()))
                    .asJson();
        initWebConfigurator(subProperties);
    }

    public static void initWebConfigurator(JsonObject subProperties) {
        HTTP_PORT = subProperties.size() > 0 && subProperties.containsKey(WebConst.HTTP_PORT.getKey()) ?
                Integer.parseInt(subProperties.getString(WebConst.HTTP_PORT.getKey())) : DEFAULT_WEB_PORT;
    }

    public static int getHttpPort() {
        return HTTP_PORT;
    }
}