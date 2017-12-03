package org.vertx.exstension.config;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.vertx.exstension.utils.PropertiesLoader;

/**
 * configuration for current system.
 * Created by kam on 2017/12/3.
 */
public class Configurator {
    private static final Logger logger = LoggerFactory.getLogger(Configurator.class);
    private static final int DEFAULT_WEB_PORT = 80;
    private static int HTTP_PORT = DEFAULT_WEB_PORT;
    private static JsonObject SUB_PROPERTIES;
    private static JsonObject PROPERTIES;

    public static void init() {
        logger.info("the configurator is Initialization.");
        PROPERTIES = new PropertiesLoader(SysConst.DEFAULT_PROPERTIES_PATH.getKey()).asJson();
        if (PROPERTIES.containsKey(SysConst.CURR_PROPERTIES_PATH.getKey()))
            SUB_PROPERTIES = new PropertiesLoader(PROPERTIES.getString(SysConst.CURR_PROPERTIES_PATH.getKey()))
                    .asJson();
        initSysConfigurator();
        initWebConfigurator(SUB_PROPERTIES);
        logger.info("the configurator init over.");
    }

    private static void initSysConfigurator() {
        System.setProperty("vertx.logger-delegate-factory-class-name",
                PROPERTIES.getString(SysConst.CURR_LOGGING_FACTORY.getKey()));
        logger.info("the system configurator init over.");
    }

    private static void initWebConfigurator(JsonObject subProperties) {
        HTTP_PORT = subProperties.size() > 0 && subProperties.containsKey(WebConst.HTTP_PORT.getKey()) ?
                Integer.parseInt(subProperties.getString(WebConst.HTTP_PORT.getKey())) : DEFAULT_WEB_PORT;
        logger.info("the web configurator init over.");
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