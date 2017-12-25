package org.cluster.service;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.cluster.Holder.SysHolder;
import org.exstension.base.ConfigHolder;
import org.exstension.base.PackageScanner;

/**
 * Created by kam on 2017/12/25.
 */
public class LaunchService {
    private static Logger logger;

    public static void launch() {
        ConfigHolder.init();
        System.setProperty("vertx.logger-delegate-factory-class-name", String.valueOf(ConfigHolder.val("sys.logging.factory")));
        System.setProperty("hazelcast.logging.type", String.valueOf(ConfigHolder.val("hazelcast.logging.type")));
        logger = LoggerFactory.getLogger(LaunchService.class);
        launchVertx();
        logger.info("service launch over.");
    }

    private static void launchVertx() {
        Vertx.clusteredVertx(new VertxOptions(), res -> {
            if (res.succeeded()) {
                SysHolder.setVertx(res.result());
                logger.info("launch vertx with cluster success.");
                launchVerticle();
            } else {
                logger.info("launch vertx with cluster fail.");
            }
        });
    }

    private static void launchVerticle() {
        new PackageScanner<Verticle>().scan(String.valueOf(ConfigHolder.val("sys.verticle.package")), Verticle.class)
                .forEach(v -> {
                    SysHolder.vertx().deployVerticle(v);
                    logger.info(v.getClass().getName().concat("->deploy over."));
                });
    }
}
