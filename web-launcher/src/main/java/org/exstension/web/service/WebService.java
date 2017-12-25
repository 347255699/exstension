package org.exstension.web.service;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import org.apache.commons.lang3.StringUtils;
import org.exstension.base.PackageScanner;
import org.exstension.web.Holder.SysHolder;
import org.exstension.web.config.SysConfigHolder;
import org.exstension.web.route.Route;

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
    private static void launchPre(String propPath, VertxOptions vertxOptions, Boolean isCluster) {
        if (propPath != null)
            SysConfigHolder.init(propPath);
        else SysConfigHolder.init(null);
        if (StringUtils.isNoneEmpty(SysConfigHolder.sysLoggingFactory()))
            System.setProperty("vertx.logger-delegate-factory-class-name", SysConfigHolder.sysLoggingFactory());
        if (isCluster)
            System.setProperty("hazelcast.logging.type", SysConfigHolder.hazelcastLoggingType());
        // this step must after setting logger factory system property.
        logger = LoggerFactory.getLogger(WebService.class);
        if (isCluster) {
            Vertx.clusteredVertx(vertxOptions == null ? new VertxOptions() : vertxOptions, res -> {
                if (res.succeeded()) {
                    SysHolder.setVertx(res.result());
                    logger.info("launch vertx with cluster success.");
                    launchVerticle();
                    launchWebServer();
                } else {
                    logger.info("launch vertx with cluster fail.");
                }
            });
        } else {
            SysHolder.setVertx(vertxOptions == null ? Vertx.vertx() : Vertx.vertx(vertxOptions));
            logger.info("launch local vertx success.");
        }
    }

    /**
     * use default config.
     */
    public static void launch() {
        launchPre(null, null, false);
        launchVerticle();
        launchWebServer();
    }

    /**
     * use customize properties path.
     *
     * @param propPath
     */
    public static void launch(String propPath) {
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
    public static void launch(String propPath, VertxOptions vertxOptions) {
        launchPre(propPath, vertxOptions, false);
        launchVerticle();
        launchWebServer();
    }

    /**
     * use customize vertx options.
     *
     * @param vertxOptions
     */
    public static void launch(VertxOptions vertxOptions) {
        launchPre(null, vertxOptions, false);
        launchVerticle();
        launchWebServer();
    }

    /**
     * use default config and launch with cluster.
     */
    public static void launchCluster() {
        launchPre(null, null, true);
    }

    /**
     * use customize propPath and launch with cluster.
     *
     * @param propPath
     */
    public static void launchCluster(String propPath) {
        launchPre(propPath, null, true);
    }

    /**
     * use customize propPath and vertx options launch with cluster.
     *
     * @param propPath
     */
    public static void launchCluster(String propPath, VertxOptions vertxOptions) {
        launchPre(propPath, vertxOptions, true);
    }

    /**
     * use customize vertx options and launch with cluster.
     *
     * @param vertxOptions
     */
    public static void launchCluster(VertxOptions vertxOptions) {
        launchPre(null, vertxOptions, true);
    }

    private static void launchVerticle() {
        if (!StringUtils.isEmpty(SysConfigHolder.sysVerticlePackage())) {
            logger.info("all the verticle launch now.");
            new PackageScanner<Verticle>().scan(SysConfigHolder.sysVerticlePackage(), Verticle.class)
                    .forEach(verticle -> {
                        logger.info("deploy verticle -> ".concat(verticle.getClass().getName()));
                        SysHolder.vertx().deployVerticle(verticle);
                    });
            logger.info("all the verticle launch over.");
        }
    }

    private static void launchWebServer() {
        logger.info("the web server launch now.");
        Router router = Router.router(SysHolder.vertx());
        if (!StringUtils.isEmpty(SysConfigHolder.sysVerticlePackage())) {
            new PackageScanner<Route>().scan(SysConfigHolder.webRoutePackage(), Route.class)
                    .forEach(route -> {
                        logger.info("deploy route -> ".concat(route.getClass().getName()));
                        route.route(router);
                    });
        }
        int httpPort = StringUtils.isEmpty(SysConfigHolder.webListenPort()) ?
                80 : Integer.parseInt(SysConfigHolder.webListenPort());
        SysHolder.vertx().createHttpServer().requestHandler(router::accept)
                .listen(httpPort);
        logger.info("the web server launch over.");
        logger.info("the web server launch on ".concat(String.valueOf(httpPort)).concat(" port"));
    }

}
