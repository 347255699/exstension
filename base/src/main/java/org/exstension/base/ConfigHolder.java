package org.exstension.base;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Configuration for app.
 * Created by kam on 2017/12/3.
 */
public class ConfigHolder {
    // Hold properties body as mao format.
    private static Map<String, Object> PROPERTIES;

    public static void init(String propPath) throws IOException {
        if (StringUtils.isNoneEmpty(propPath)) {
            PROPERTIES = new PropertiesLoader(propPath).asMap();
        } else {
            PROPERTIES = new PropertiesLoader().asMap();
        }
    }

    public static void init() throws IOException {
        PROPERTIES = new PropertiesLoader().asMap();
    }

    /**
     * Get properties body.
     *
     * @return
     */
    public static Map<String, Object> properties() {
        return PROPERTIES;
    }

    /**
     * Get single property from properties body.
     *
     * @param k
     * @return
     */
    public static Object property(String k) {
        if (PROPERTIES != null && PROPERTIES.size() > 0)
            return PROPERTIES.get(k);
        else throw new RuntimeException("The properties body have not any element.");
    }
}