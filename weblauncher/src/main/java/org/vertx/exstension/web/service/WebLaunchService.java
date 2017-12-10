package org.vertx.exstension.web.service;

import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import org.apache.commons.lang3.StringUtils;
import org.vertx.exstension.config.Configurator;
import org.vertx.exstension.holder.VertxHolder;
import org.vertx.exstension.service.LaunchService;
import org.vertx.exstension.utils.PackageScanner;
import org.vertx.exstension.web.config.WebConst;
import org.vertx.exstension.web.route.Route;

/**
 * launch service for web server.
 * Created by kam on 2017/12/10.
 */
public class WebLaunchService {
    private static final Logger logger = LoggerFactory.getLogger(WebLaunchService.class);

    public void launchLocal(String propPath) {
        new LaunchService().launchLocal(propPath);
        launchWebServer();
    }

    public void launchLocal() {
        new LaunchService().launchLocal();
        launchWebServer();
    }

    private void launchWebServer() {
        JsonObject config = Configurator.properties();
        logger.info("the web server launch now.");
        Router router = Router.router(VertxHolder.vertx());
        new PackageScanner<Route>().scan(config.getString(WebConst.WEB_ROUTE_PACKAGE.getKey()), Route.class)
                .forEach(route -> route.route(router));
        int httpPort = StringUtils.isEmpty(config.getString(WebConst.WEB_LISTEN_PORT.getKey()))?
                80 : Integer.parseInt(config.getString(WebConst.WEB_LISTEN_PORT.getKey()));
        VertxHolder.vertx().createHttpServer().requestHandler(router::accept)
                .listen(httpPort);
        logger.info("the web server launch over.");
        logger.info("the web server launch port is {}.", String.valueOf(httpPort));
    }

}
