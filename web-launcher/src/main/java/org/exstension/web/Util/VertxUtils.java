package org.exstension.web.Util;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.shareddata.SharedData;

/**
 * hold some object for web service.
 * Created by kam on 2017/12/22.
 */
public class VertxUtils {
    private static Vertx vertx;

    public static void setVertx(Vertx _vertx) {
        vertx = _vertx;
    }

    public static Vertx vertx() {
        return vertx;
    }

    public static EventBus eventBus() {
        return vertx.eventBus();
    }

    public static SharedData sharedData(){
        return vertx.sharedData();
    }
}
