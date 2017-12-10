package org.vertx.exstension.service;

import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.vertx.exstension.config.Configurator;
import org.vertx.exstension.holder.VertxHolder;
import org.vertx.exstension.verticle.LauncherVerticle;

/**
 * service for lanch
 * Created by kam on 2017/12/3.
 */
public class LaunchService {
    private static final Logger logger = LoggerFactory.getLogger(LaunchService.class);
    /**
     * lanch local vertx with a properties path.
     */
    public static void launchLocal(String propPath) {
        logger.info("the system is launching now.");
        Configurator.init(propPath);
        VertxHolder.setVertx(Vertx.vertx());
        VertxHolder.vertx().deployVerticle(LauncherVerticle.class.getName());
        logger.info("the system launch over.");
    }
    /**
     * lanch local vertx use default properties path.
     */
    public static void launchLocal() {
        logger.info("the system is launching now.");
        Configurator.init();
        VertxHolder.setVertx(Vertx.vertx());
        VertxHolder.vertx().deployVerticle(LauncherVerticle.class.getName());
        logger.info("the system launch over.");
    }

}
