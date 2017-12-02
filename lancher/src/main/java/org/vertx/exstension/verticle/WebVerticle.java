package org.vertx.exstension.verticle;

import io.vertx.core.AbstractVerticle;
import org.vertx.exstension.config.WebConfigurator;
import org.vertx.exstension.holder.VertxHolder;

/**
 * The actor to hold web container.
 * Created by kam on 2017/11/30.
 */
public class WebVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        VertxHolder.vertx().createHttpServer().requestHandler(req -> {
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello World!");
        }).listen(WebConfigurator.getPort());
    }
}
