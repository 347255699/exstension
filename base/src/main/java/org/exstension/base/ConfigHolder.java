package org.exstension.base;


import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * configuration for current system.
 * Created by kam on 2017/12/3.
 */
public class ConfigHolder {
    private static Map<String, String> PROPERTIES;
    private static String DEFAULT_PROPERTIES_PATH = "config.properties";

    /**
     * initialization with customize properties path.
     * @param propPath
     */
    public static void init(String propPath) {
        PROPERTIES = new PropertiesLoader(StringUtils.isNoneEmpty(propPath) ?
                propPath : DEFAULT_PROPERTIES_PATH).asMap();
    }

    /**
     * initialization with default properties path.
     */
    public static void init() {
        PROPERTIES = new PropertiesLoader(DEFAULT_PROPERTIES_PATH).asMap();
    }

    /**
     * get properties of current system.
     *
     * @return
     */
    public static Map<String, String> properties() {
        return PROPERTIES;
    }

    /**
     * get properties single property.
     * @param k
     * @return
     */
    public static String val(String k){
        if(PROPERTIES != null && PROPERTIES.size() > 0)
            return PROPERTIES.get(k);
        else throw new RuntimeException("The properties not initialization or empty.");
    }
}