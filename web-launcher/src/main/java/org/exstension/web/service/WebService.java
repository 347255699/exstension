package org.exstension.web.service;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import org.apache.commons.lang3.StringUtils;
import org.exstension.base.PackageScanner;
import org.exstension.web.Util.VertxUtils;
import org.exstension.web.config.Config;
import org.exstension.web.route.Route;

import java.io.IOException;

/**
 * launch service for web server.
 * Created by kam on 2017/12/10.
 */
public class WebService {
    private static Logger logger;

    /**
     * ready for launch web service.
     * @param propPath
     * @param vertxOptions
     * @param isCluster
     */
    private static void launchPre(String propPath, VertxOptions vertxOptions, Boolean isCluster) throws IOException {
        if (propPath != null)
            Config.init(propPath);
        else Config.init(null);
        if (StringUtils.isNoneEmpty(Config.sysLoggingFactory()))
            System.setProperty("vertx.logger-delegate-factory-class-name", Config.sysLoggingFactory());
        if (isCluster)
            System.setProperty("hazelcast.logging.type", Config.hazelcastLoggingType());
        // this step must after setting logger factory system property.
        logger = LoggerFactory.getLogger(WebService.class);
        if (isCluster) {
            Vertx.clusteredVertx(vertxOptions == null ? new VertxOptions() : vertxOptions, res -> {
                if (res.succeeded()) {
                    VertxUtils.setVertx(res.result());
                    logger.info("launch vertx with cluster success.");
                    launchVerticle();
                    launchWebServer();
                } else {
                    logger.info("launch vertx with cluster fail.");
                }
            });
        } else {
            VertxUtils.setVertx(vertxOptions == null ? Vertx.vertx() : Vertx.vertx(vertxOptions));
            logger.info("launch local vertx success.");
        }
    }

    /**
     * use default config.
     */
    public static void launch() throws IOException {
        launchPre(null, null, false);
        launchVerticle();
        launchWebServer();
    }

    /**
     * use customize properties path.
     *
     * @param propPath
     */
    public static void launch(String propPath) throws IOException {
        launchPre(propPath, null, false);
        launchVerticle();
        launchWebServer();
    }

    /**
     * use customize properties path and vertx options.
     *
     * @param propPath
     * @param vertxOptions
     */
    public static void launch(String propPath, VertxOptions vertxOptions) throws IOException {
        launchPre(propPath, vertxOptions, false);
        launchVerticle();
        launchWebServer();
    }

    /**
     * use customize vertx options.
     *
     * @param vertxOptions
     */
    public static void launch(VertxOptions vertxOptions) throws IOException {
        launchPre(null, vertxOptions, false);
        launchVerticle();
        launchWebServer();
    }

    /**
     * use default config and launch with cluster.
     */
    public static void launchCluster() throws IOException {
        launchPre(null, null, true);
    }

    /**
     * use customize propPath and launch with cluster.
     *
     * @param propPath
     */
    public static void launchCluster(String propPath) throws IOException {
        launchPre(propPath, null, true);
    }

    /**
     * use customize propPath and vertx options launch with cluster.
     *
     * @param propPath
     */
    public static void launchCluster(String propPath, VertxOptions vertxOptions) throws IOException {
        launchPre(propPath, vertxOptions, true);
    }

    /**
     * use customize vertx options and launch with cluster.
     *
     * @param vertxOptions
     */
    public static void launchCluster(VertxOptions vertxOptions) throws IOException {
        launchPre(null, vertxOptions, true);
    }

    private static void launchVerticle() {
        if (!StringUtils.isEmpty(Config.sysVerticlePackage())) {
            logger.info("all the verticle launch now.");
            new PackageScanner<Verticle>().scan(Config.sysVerticlePackage(), Verticle.class)
                    .forEach(verticle -> {
                        logger.info("deploy verticle -> ".concat(verticle.getClass().getName()));
                        VertxUtils.vertx().deployVerticle(verticle);
                    });
            logger.info("all the verticle launch over.");
        }
    }

    private static void launchWebServer() {
        logger.info("the web server launch now.");
        Router router = Router.router(VertxUtils.vertx());
        if (!StringUtils.isEmpty(Config.sysVerticlePackage())) {
            new PackageScanner<Route>().scan(Config.webRoutePackage(), Route.class)
                    .forEach(route -> {
                        logger.info("deploy route -> ".concat(route.getClass().getName()));
                        route.route(router);
                    });
        }
        int httpPort = StringUtils.isEmpty(Config.webListenPort()) ?
                80 : Integer.parseInt(Config.webListenPort());
        VertxUtils.vertx().createHttpServer().requestHandler(router::accept)
                .listen(httpPort);
        logger.info("the web server launch over.");
        logger.info("the web server launch on ".concat(String.valueOf(httpPort)).concat(" port"));
    }

}
