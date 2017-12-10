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
    private static JsonObject PROPERTIES;

    public static void init(String propPath) {
        logger.info("the system configurator init start.");
        PROPERTIES = new PropertiesLoader(StringUtils.isNoneEmpty(propPath) ?
                propPath : SysConst.DEFAULT_PROPERTIES_PATH.getKey()).asJson();
        initSysConfigurator();
    }
    public static void init() {
        logger.info("the system configurator init start.");
        PROPERTIES = new PropertiesLoader(SysConst.DEFAULT_PROPERTIES_PATH.getKey()).asJson();
        initSysConfigurator();
    }

    private static void initSysConfigurator() {
        System.setProperty("vertx.logger-delegate-factory-class-name",
                PROPERTIES.getString(SysConst.SYS_LOGGING_FACTORY.getKey()));
        logger.info("the system configurator init over.");
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