package org.vertx.exstension.config;

/**
 * const for global.
 * Created by kam on 2017/12/3.
 */
public enum SysConst {
    /**
     * default path for core property.
     */
    DEFAULT_PROPERTIES_PATH("core.properties"),
    CURR_PROPERTIES_PATH("curr.properties"),
    CURR_LOGGING_FACTORY("curr.logging.factory");
    private String key;

    SysConst(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
