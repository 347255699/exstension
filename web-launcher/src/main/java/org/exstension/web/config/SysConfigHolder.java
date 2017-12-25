package org.exstension.web.config;

import io.vertx.core.json.JsonObject;
import org.exstension.base.ConfigHolder;

/**
 * config for this web system.
 * Created by kam on 2017/12/22.
 */
public class SysConfigHolder {
    private static String WEB_LISTEN_PORT = "web.listen.port";
    private static String WEB_ROUTE_PACKAGE = "web.route.package";
    private static String SYS_LOGGING_FACTORY = "sys.logging.factory";
    private static String SYS_VERTICLE_PACKAGE = "sys.verticle.package";
    private static String HAZELCAST_LOGGING_TYPE = "hazelcast.logging.type";

    /**
     * y
     * initialization system config.
     *
     * @param propPath
     */
    public static void init(String propPath) {
        if (propPath == null)
            ConfigHolder.init();
        else ConfigHolder.init(propPath);
    }

    public static JsonObject asJson() {
        return new JsonObject(ConfigHolder.properties());
    }

    public static String sysLoggingFactory() {
        return String.valueOf(ConfigHolder.val(SYS_LOGGING_FACTORY));
    }

    public static String sysVerticlePackage() {
        return String.valueOf(ConfigHolder.val(SYS_VERTICLE_PACKAGE));
    }

    public static String webListenPort() {
        return String.valueOf(ConfigHolder.val(WEB_LISTEN_PORT));
    }

    public static String webRoutePackage() {
        return String.valueOf(ConfigHolder.val(WEB_ROUTE_PACKAGE));
    }

    public static String hazelcastLoggingType() {
        return String.valueOf(ConfigHolder.val(HAZELCAST_LOGGING_TYPE));
    }
}
