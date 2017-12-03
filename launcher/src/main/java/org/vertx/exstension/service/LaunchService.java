package org.vertx.exstension.service;

import io.vertx.core.Vertx;
import org.vertx.exstension.config.Configurator;
import org.vertx.exstension.holder.VertxHolder;
import org.vertx.exstension.verticle.LauncherVerticle;

/**
 * service for lanch
 * Created by kam on 2017/12/3.
 */
public class LaunchService {

    /**
     * lanch local vertx.
     */
    public static void lanchLocal() {
        Configurator.init();
        VertxHolder.setVertx(Vertx.vertx());
        VertxHolder.vertx().deployVerticle(LauncherVerticle.class.getName());
    }

}
