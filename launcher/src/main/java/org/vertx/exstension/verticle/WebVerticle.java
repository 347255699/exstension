package org.vertx.exstension.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.vertx.exstension.config.Configurator;
import org.vertx.exstension.config.WebConst;
import org.vertx.exstension.holder.VertxHolder;
import org.vertx.exstension.route.Route;
import org.vertx.exstension.utils.PackageScanner;

/**
 * The actor to hold web container.
 * Created by kam on 2017/11/30.
 */
public class WebVerticle extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(WebVerticle.class);

    @Override
    public void start() throws Exception {
        logger.info("scanning route package now.");
        new PackageScanner<Route>()
                .scan(Configurator.properties().getString(WebConst.ROUTE_PACKAGE.getKey()), Route.class);
        logger.info("scanning route package over.");
        VertxHolder.vertx().createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello World!");
        }).listen(Configurator.getHttpPort());
        logger.info("the web server are ready.");
    }
}
