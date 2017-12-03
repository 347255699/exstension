package org.vertx.exstension.holder;

import io.vertx.core.Vertx;

/**
 * The global vertx object holder.
 * Created by kam on 2017/11/30.
 */
public class VertxHolder {
    private static Vertx vertx;

    public static Vertx vertx() {
        return vertx;
    }

    public static void setVertx(Vertx _vertx) {
        vertx = _vertx;
    }
}
