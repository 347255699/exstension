package org.vertx.exstension.config;

import io.vertx.core.json.JsonObject;
import org.vertx.exstension.utils.PropertiesLoader;

/**
 * configuration for current system.
 * Created by kam on 2017/12/3.
 */
public class Configurator {
    private static final int DEFAULT_WEB_PORT = 80;
    private static int HTTP_PORT = DEFAULT_WEB_PORT;
    private static JsonObject SUB_PROPERTIES;

    public static void init() {
        JsonObject properties = new PropertiesLoader(SysConst.DEFAULT_PROPERTIES_PATH.getKey()).asJson();
        if (properties.containsKey(SysConst.CURR_PROPERTIES_PATH.getKey()))
            SUB_PROPERTIES = new PropertiesLoader(properties.getString(SysConst.CURR_PROPERTIES_PATH.getKey()))
                    .asJson();
        initSysConfigurator();
        initWebConfigurator(SUB_PROPERTIES);
    }

    private static void initSysConfigurator(){
        System.setProperty("vertx.logger-delegate-factory-class-name",
                "io.vertx.core.logging.SLF4JLogDelegateFactory");
    }

    private static void initWebConfigurator(JsonObject subProperties) {
        HTTP_PORT = subProperties.size() > 0 && subProperties.containsKey(WebConst.HTTP_PORT.getKey()) ?
                Integer.parseInt(subProperties.getString(WebConst.HTTP_PORT.getKey())) : DEFAULT_WEB_PORT;
    }

    public static int getHttpPort() {
        return HTTP_PORT;
    }

    /**
     * get properties of current system.
     *
     * @return
     */
    public static JsonObject properties() {
        return SUB_PROPERTIES;
    }
}