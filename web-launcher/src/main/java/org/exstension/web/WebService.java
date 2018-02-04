package org.exstension.web;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.exstension.base.scanner.PackageSimpleScanner;
import org.exstension.web.verticle.WebVerticle;

import java.io.IOException;

/**
 * Launch web server.
 * Created by kam on 2017/12/10.
 */
public class WebService {
    private static Logger logger;
    private static DeploymentOptions options;

    /**
     * ready for launch web service.
     *
     * @param propPath
     * @param vertxOptions
     * @param isCluster
     */
    private static void launchPre(String propPath, VertxOptions vertxOptions, Boolean isCluster) throws IOException {
        if (propPath != null)
            SysConfig.init(propPath);
        else SysConfig.init(null);
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
    public static void launch() throws IOException {
        launchPre(null, null, false);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Using customize properties path.
     *
     * @param propPath
     */
    public static void launch(String propPath) throws IOException {
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
    public static void launch(String propPath, VertxOptions vertxOptions) throws IOException {
        launchPre(propPath, vertxOptions, false);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Use customize VertxOptions.
     *
     * @param vertxOptions
     */
    public static void launch(VertxOptions vertxOptions) throws IOException {
        launchPre(null, vertxOptions, false);
        deployVerticleSet();
        launchWebServer();
    }

    /**
     * Using default config and launch with cluster.
     */
    public static void launchCluster() throws IOException {
        launchPre(null, null, true);
    }

    /**
     * Using customize properties path and launch with cluster.
     *
     * @param propPath
     */
    public static void launchCluster(String propPath) throws IOException {
        launchPre(propPath, null, true);
    }

    /**
     * Using customize properties path, VertxOptions and launch with cluster.
     *
     * @param propPath
     */
    public static void launchCluster(String propPath, VertxOptions vertxOptions) throws IOException {
        launchPre(propPath, vertxOptions, true);
    }

    /**
     * Using customize VertxOptions and launch with cluster.
     *
     * @param vertxOptions
     */
    public static void launchCluster(VertxOptions vertxOptions) throws IOException {
        launchPre(null, vertxOptions, true);
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
                logger.error(e.getCause().getMessage());
            }
            logger.info("Web-Launch ->> All the verticle deploy over.");
        }
    }

    /**
     * Launch Web Server
     */
    private static void launchWebServer() {
        WebVerticle webVerticle = new WebVerticle();
        if (options != null)
            logger.info("Web-Launch ->> Using your options：" + options.getConfig().toString());

        VertxUtils.vertx().deployVerticle(webVerticle, (options == null) ? webVerticle.getDeploymentOptions() : options);
    }

    /**
     * Set your web server options.
     *
     * @param _options
     */
    public static void setWebServerOptions(DeploymentOptions _options) {
        options = _options;
    }

}
