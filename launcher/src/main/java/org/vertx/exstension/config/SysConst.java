package org.vertx.exstension.config;

/**
 * const for global.
 * Created by kam on 2017/12/3.
 */
public enum SysConst {
    /**
     * default path for core property.
     */
    DEFAULT_PROPERTIES_PATH("config.properties"),
    SYS_LOGGING_FACTORY("sys.logging.factory"),
    SYS_VERTICLE_PACKAGE("sys.verticle.package");
    private String key;

    SysConst(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
