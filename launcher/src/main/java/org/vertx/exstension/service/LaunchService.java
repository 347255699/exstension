package org.vertx.exstension.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import org.vertx.exstension.config.Configurator;
import org.vertx.exstension.config.SysConst;
import org.vertx.exstension.holder.VertxHolder;
import org.vertx.exstension.utils.PackageScanner;

/**
 * service for lanch
 * Created by kam on 2017/12/3.
 */
public class LaunchService {
    private static final Logger logger = LoggerFactory.getLogger(LaunchService.class);
    /**
     * lanch local vertx with a properties path.
     */
    public void launchLocal(String propPath) {
        Configurator.init(propPath);
        launch();
    }
    /**
     * lanch local vertx use default properties path.
     */
    public void launchLocal() {
        Configurator.init();
        launch();
    }

    private void launch(){
        logger.info("the system is launching now.");
        VertxHolder.setVertx(Vertx.vertx());
        logger.info("scanning verticle package now.");
        new PackageScanner<AbstractVerticle>()
                .scan(Configurator.properties().getString(SysConst.SYS_VERTICLE_PACKAGE.getKey()), AbstractVerticle.class)
                .forEach(VertxHolder.vertx() :: deployVerticle);
        logger.info("scanning verticle package over.");
        logger.info("the system launch over.");
    }


}
