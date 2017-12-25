package org.cluster.Holder;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

/**
 * Created by kam on 2017/12/25.
 */
public class SysHolder {
    private static Vertx vertx;

    public static Vertx vertx() {
        return vertx;
    }

    public static void setVertx(Vertx vertx) {
        SysHolder.vertx = vertx;
    }

    public static EventBus eventBus() {
        return vertx().eventBus();
    }

}
