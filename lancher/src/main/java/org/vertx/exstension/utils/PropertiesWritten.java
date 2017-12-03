package org.vertx.exstension.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;

/**
 * The property file write utils.
 * Created by kam on 2017/12/3.
 */
public class PropertiesWritten {
    private Properties prop;

    public PropertiesWritten(Properties prop) {
        this.prop = prop;
    }

    /**
     * write key/value to property file.
     *
     * @param propPath
     * @param k
     * @param v
     * @throws Throwable
     */
    public void write(String propPath, String k, String v) throws Throwable {
        try {
            OutputStream out = new FileOutputStream(propPath);
            prop.put(k, v);
            prop.store(out, "update".concat(k).concat("property."));
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    /**
     * write key/value to property file with a comment.
     *
     * @param propPath
     * @param k
     * @param v
     * @param comment
     * @throws Throwable
     */
    public void write(String propPath, String k, String v, String comment) throws Throwable {
        try {
            OutputStream out = new FileOutputStream(propPath);
            prop.put(k, v);
            prop.store(out, comment);
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    /**
     * write key/value collection to property file.
     * @param propPath
     * @param propertiesMap
     * @throws Throwable
     */
    public void write(String propPath, Map<String, String> propertiesMap) throws Throwable {
        try {
            OutputStream out = new FileOutputStream(propPath);
            propertiesMap.forEach((k, v) -> {
                prop.put(k, v);
            });
            prop.store(out, "update more than one property.");
        } catch (Exception e) {
            throw e.getCause();
        }
    }

    /**
     * write key/value collection to property file with a comment.
     * @param propPath
     * @param propertiesMap
     * @throws Throwable
     */
    public void write(String propPath, Map<String, String> propertiesMap, String comment) throws Throwable {
        try {
            OutputStream out = new FileOutputStream(propPath);
            propertiesMap.forEach((k, v) -> {
                prop.put(k, v);
            });
            prop.store(out, comment);
        } catch (Exception e) {
            throw e.getCause();
        }
    }
}
