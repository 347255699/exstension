package org.vertx.exstension.utils;

import io.vertx.core.json.JsonObject;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The property file load utils.
 * Created by kam on 2017/12/3.
 */
public class PropertiesLoader {
    private Properties prop;
    private String propPath;

    /**
     * the propPath must blow classpath path.
     *
     * @param propPath
     * @throws Throwable
     */
    public PropertiesLoader(String propPath) {
        this.prop = new Properties();
        this.propPath = propPath;
        try {
            prop.load(getClass().getClassLoader().getResourceAsStream(propPath));
        } catch (Exception e) {
            e.getCause().printStackTrace();
        }
    }

    /**
     * get current property file path or name.
     *
     * @return
     */
    public String path() {
        return propPath;
    }

    /**
     * get properties object.
     *
     * @return
     */
    public Properties properties() {
        return prop;
    }

    /**
     * get property with a key.
     *
     * @param k
     * @return
     */
    public String value(String k) {
        return prop.getProperty(k);
    }

    /**
     * get property map from properties.
     *
     * @return
     */
    public Map<String, Object> map() {
        Map<String, Object> propertiesMap = new HashMap<>(prop.size(), 1f);
        Enumeration enumeration = prop.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            propertiesMap.put(key, prop.getProperty(key));
        }
        return propertiesMap;
    }

    /**
     * get property json object from properties.
     *
     * @return
     */
    public JsonObject json() {
        return new JsonObject(map());
    }
}
