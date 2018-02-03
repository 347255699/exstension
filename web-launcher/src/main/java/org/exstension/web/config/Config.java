package org.exstension.web.config;

import io.vertx.core.json.JsonObject;
import org.exstension.base.ConfigHolder;
import org.exstension.web.Util.Constant;

import java.io.IOException;

/**
 * config for this web system.
 * Created by kam on 2017/12/22.
 */
public class Config {

    /**
     * initialization system config.
     *
     * @param propPath
     */
    public static void init(String propPath) throws IOException {
        if (propPath == null)
            ConfigHolder.init();
        else ConfigHolder.init(propPath);
    }

    public static JsonObject asJson() {
        return new JsonObject(ConfigHolder.properties());
    }

    public static String sysLoggingFactory() {
        return ConfigHolder.property(Constant.SYS_LOGGING_FACTORY).toString();
    }

    public static String sysVerticlePackage() {
        return ConfigHolder.property(Constant.SYS_VERTICLE_PACKAGE).toString();
    }

    public static String webListenPort() {
        return ConfigHolder.property(Constant.WEB_LISTEN_PORT).toString();
    }

    public static String webRoutePackage() {
        return ConfigHolder.property(Constant.WEB_ROUTE_PACKAGE).toString();
    }

    public static String hazelcastLoggingType() {
        return ConfigHolder.property(Constant.HAZELCAST_LOGGING_TYPE).toString();
    }
}
