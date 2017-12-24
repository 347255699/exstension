package org.exstension.web.service;

import io.vertx.core.Verticle;
import io.vertx.core.Vertx;
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

    private static void launchPre(String propPath) {
        if (propPath != null)
            SysConfigHolder.init(propPath);
        else SysConfigHolder.init(null);
        if (!StringUtils.isEmpty(SysConfigHolder.sysLoggingFactory()))
            System.setProperty("vertx.logger-delegate-factory-class-name", SysConfigHolder.sysLoggingFactory());
        // this step must after setting logger factory system property.
        logger = LoggerFactory.getLogger(WebService.class);
        SysHolder.setVertx(Vertx.vertx());
    }

    /**
     * use default properties path.
     */
    public static void launch() {
        launchPre(null);
        launchVerticle();
        launchWebServer();
    }

    /**
     * use customize properties path.
     *
     * @param propPath
     */
    public static void launch(String propPath) {
        launchPre(propPath);
        launchVerticle();
        launchWebServer();
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
