package org.exstension.web;

import io.vertx.core.json.JsonObject;
import org.exstension.base.Properties.ConfigHolder;

import java.io.IOException;

/**
 * config for this web system.
 * Created by kam on 2017/12/22.
 */
public class SysConfig {

    /**
     * Initialization system config.
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

    public static String getSysLoggingFactory() {
        return ConfigHolder.property(SysConst.SYS_LOGGING_FACTORY).toString();
    }

    public static String getSysVerticlePackage() {
        return ConfigHolder.property(SysConst.SYS_VERTICLE_PACKAGE).toString();
    }

    public static String getWebListenPort() {
        return ConfigHolder.property(SysConst.WEB_LISTEN_PORT).toString();
    }

    public static String getWebRoutePackage() {
        return ConfigHolder.property(SysConst.WEB_ROUTE_PACKAGE).toString();
    }

    public static String getHazelcastLoggingType() {
        return ConfigHolder.property(SysConst.HAZELCAST_LOGGING_TYPE).toString();
    }

    public static String getWebServerInstantNumber() {
        return ConfigHolder.property(SysConst.WEB_SERVER_INSTANT_NUMBER).toString();
    }
}
