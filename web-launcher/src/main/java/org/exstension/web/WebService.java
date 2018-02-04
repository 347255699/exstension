package org.exstension.web;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import org.apache.commons.lang3.StringUtils;
import org.exstension.base.scanner.PackageSimpleScanner;

import java.io.IOException;

/**
 * Launch web server.
 * Created by kam on 2017/12/10.
 */
public class WebService {
    private static Logger logger;

    /**
     * ready for launch web service.
     *
     * @param propPath
     * @param vertxOptions
     * @param isCluster
     */
    private static void launchPre(String propPath, VertxOptions vertxOptions, Boolean isCluster) {
        try {
            if (propPath != null)
                SysConfig.init(propPath);
            else SysConfig.init(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNoneEmpty(SysConfig.getSysLoggingFactory()))
            System.setProperty("vertx.logger-delegate-factory-class-name", SysConfig.getSysLoggingFactory());
        if (isCluster)
            System.setProperty("hazelcast.logging.type", SysConfig.getHazelcastLoggingType());
        // This step must after setting logger factory system property.
        logger = LoggerFactory.getLogger(WebService.class);
        if (isCluster) {
            Vertx.clusteredVertx(vertxOptions == null ? new VertxOptions() : vertxOptions, res -> {
                if (res.succeeded()) {
                    VertxUtils.setVertx(res.result());
                    logger.info("Web-Launch ->> launch app with cluster success.");
                    deployVerticleSet();
                    launchWebServer();
                } else {
                    logger.error("Web-Launch ->> launch app with cluster failure.");
                }
            });
        } else {
            VertxUtils.setVertx(vertxOptions == null ? Vertx.vertx() : Vertx.vertx(vertxOptions));
            logger.info("Web-Launch ->> Vertx Component already.");
        }
    }

    /**
     * Using default config.
     */
    public static void launch() {
        launchPre(null, null, false);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Using customize properties path.
     *
     * @param propPath
     */
    public static void launch(String propPath) {
        launchPre(propPath, null, false);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Using customize properties path and VertxOptions.
     *
     * @param propPath
     * @param vertxOptions
     */
    public static void launch(String propPath, VertxOptions vertxOptions) {
        launchPre(propPath, vertxOptions, false);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Use customize VertxOptions.
     *
     * @param vertxOptions
     */
    public static void launch(VertxOptions vertxOptions) {
        launchPre(null, vertxOptions, false);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Using default config and launch with cluster.
     */
    public static void launchCluster() {
        launchPre(null, null, true);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Using customize properties path and launch with cluster.
     *
     * @param propPath
     */
    public static void launchCluster(String propPath) {
        launchPre(propPath, null, true);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Using customize properties path, VertxOptions and launch with cluster.
     *
     * @param propPath
     */
    public static void launchCluster(String propPath, VertxOptions vertxOptions) {
        launchPre(propPath, vertxOptions, true);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Using customize VertxOptions and launch with cluster.
     *
     * @param vertxOptions
     */
    public static void launchCluster(VertxOptions vertxOptions) {
        launchPre(null, vertxOptions, true);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Deploy a set of Verticle instant.
     *
     * @throws IOException
     */
    private static void deployVerticleSet() {
        if (StringUtils.isNoneEmpty(SysConfig.getSysVerticlePackage())) {
            logger.info("Web-Launch ->> All the verticle deploy now.");
            try {
                new PackageSimpleScanner<SimpleAbstractVerticle>().scan(SysConfig.getSysVerticlePackage(), SimpleAbstractVerticle.class)
                        .forEach(v -> {
                            logger.info("Web-Launch ->> Deploy verticle:" + v.getClass().getSimpleName());
                            VertxUtils.vertx().deployVerticle(v, v.getDeploymentOptions());
                        });
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
            logger.info("Web-Launch ->> All the verticle deploy over.");
        }
    }

    /**
     * Launch Web Server
     */
    private static void launchWebServer() {
        logger.info("Web-Launch ->> Web server launch now.");
        Router router = Router.router(VertxUtils.vertx());
        if (!StringUtils.isEmpty(SysConfig.getSysVerticlePackage())) {
            try {
                new PackageSimpleScanner<Route>().scan(SysConfig.getWebRoutePackage(), Route.class)
                        .forEach(route -> {
                            logger.info("Web-Launch ->> Deploy route:" + route.getClass().getSimpleName() + ".");
                            route.route(router);
                        });
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        router.getRoutes().forEach(r -> {
            String httpUri = r.getPath();
            logger.info("Web-Launch ->> Loading http path:" + httpUri + ".");
            SysConfig.collectHttpUri(httpUri);
        });
        int httpPort = StringUtils.isEmpty(SysConfig.getWebListenPort()) ?
                80 : Integer.parseInt(SysConfig.getWebListenPort());
        VertxUtils.vertx().createHttpServer().requestHandler(router::accept)
                .listen(httpPort);
        logger.info("Web-Launch ->> Web server launch over on port " + httpPort + ".");
    }


}
