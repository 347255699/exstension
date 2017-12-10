package org.vertx.exstension.config;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.vertx.exstension.utils.PropertiesLoader;

/**
 * configuration for current system.
 * Created by kam on 2017/12/3.
 */
public class Configurator {
    private static final Logger logger = LoggerFactory.getLogger(Configurator.class);
    private static final int DEFAULT_WEB_PORT = 80;
    private static int HTTP_PORT = DEFAULT_WEB_PORT;
    private static JsonObject PROPERTIES;

    public static void init(String propPath) {
        PROPERTIES = new PropertiesLoader(StringUtils.isNoneEmpty(propPath) ?
                propPath : SysConst.DEFAULT_PROPERTIES_PATH.getKey()).asJson();
        common();
    }
    public static void init() {
        PROPERTIES = new PropertiesLoader(SysConst.DEFAULT_PROPERTIES_PATH.getKey()).asJson();
        common();
    }

    private static void common(){
        logger.info("the configurator is Initialization.");
        initSysConfigurator();
        initWebConfigurator(PROPERTIES);
        logger.info("the configurator init over.");
    }

    private static void initSysConfigurator() {
        System.setProperty("vertx.logger-delegate-factory-class-name",
                PROPERTIES.getString(SysConst.SYS_LOGGING_FACTORY.getKey()));
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
        return PROPERTIES;
    }
}