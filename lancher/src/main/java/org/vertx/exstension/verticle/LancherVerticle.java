package org.vertx.exstension.verticle;

import io.vertx.core.AbstractVerticle;
import org.vertx.exstension.holder.VertxHolder;

/**
 * The lancher actor, to week up other actor. It is application entrance.
 * Created by kam on 2017/11/30.
 */
public class LancherVerticle extends AbstractVerticle {
    public void start() {
        VertxHolder.setVertx(vertx);
        VertxHolder.vertx().deployVerticle(WebVerticle.class.getName());
    }
}
