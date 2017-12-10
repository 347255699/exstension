package org.vertx.exstension.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.vertx.exstension.config.Configurator;
import org.vertx.exstension.config.SysConst;
import org.vertx.exstension.holder.VertxHolder;
import org.vertx.exstension.utils.PackageScanner;

/**
 * The launch actor, to week up other actor. It is application entrance.
 * Created by kam on 2017/11/30.
 */
public class LauncherVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(LauncherVerticle.class);

    public void start() {
        VertxHolder.vertx().deployVerticle(WebVerticle.class.getName());
        logger.info("scanning verticle package now.");
        new PackageScanner<AbstractVerticle>()
                .scan(Configurator.properties().getString(SysConst.SYS_VERTICLE_PACKAGE.getKey()), AbstractVerticle.class)
                .forEach(VertxHolder.vertx() :: deployVerticle);
        logger.info("scanning verticle package over.");
    }
}
