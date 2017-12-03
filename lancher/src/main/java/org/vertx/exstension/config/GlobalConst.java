package org.vertx.exstension.config;

/**
 * const for global.
 * Created by kam on 2017/12/3.
 */
public enum GlobalConst {
    /**
     * default path for core property.
     */
    DEFAULT_PROPERTIES_PATH("core.propertis"),
    CURR_PROPERTIES_PATH("curr.propertis");
    private String key;

    GlobalConst(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
