package org.vertx.exstension.config;

import io.vertx.core.json.JsonObject;
import org.vertx.exstension.utils.PropertiesLoader;

/**
 * configuration for current system.
 * Created by kam on 2017/12/3.
 */
public class GlobalConfigurator {
    public static void init() {
        JsonObject properties = new PropertiesLoader(GlobalConst.DEFAULT_PROPERTIES_PATH.getKey()).asJson();
        JsonObject subProperties = new JsonObject();
        if (properties.containsKey(GlobalConst.CURR_PROPERTIES_PATH.getKey()))
            subProperties = new PropertiesLoader(properties.getString(GlobalConst.CURR_PROPERTIES_PATH.getKey()))
                    .asJson();
        WebConfigurator.init(subProperties);
    }
}
