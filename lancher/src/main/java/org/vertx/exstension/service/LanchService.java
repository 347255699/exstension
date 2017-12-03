package org.vertx.exstension.service;

import io.vertx.core.Vertx;
import org.vertx.exstension.config.Configurator;
import org.vertx.exstension.holder.VertxHolder;
import org.vertx.exstension.verticle.LancherVerticle;

/**
 * service for lanch
 * Created by kam on 2017/12/3.
 */
public class LanchService {

    /**
     * lanch local vertx.
     */
    public static void lanchLocal() {
        Configurator.init();
        VertxHolder.setVertx(Vertx.vertx());
        VertxHolder.vertx().deployVerticle(LancherVerticle.class.getName());
    }

}
