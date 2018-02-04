package org.exstension.web.verticle;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import org.apache.commons.lang3.StringUtils;
import org.exstension.base.scanner.PackageSimpleScanner;
import org.exstension.web.Route;
import org.exstension.web.SimpleAbstractVerticle;
import org.exstension.web.SysConfig;
import org.exstension.web.VertxUtils;

import java.io.IOException;

/**
 * Created by kam on 2018/2/4.
 */
public class WebVerticle extends SimpleAbstractVerticle {
    private static Logger logger = LoggerFactory.getLogger(WebVerticle.class);

    @Override
    public DeploymentOptions getDeploymentOptions() {
        int num = StringUtils.isEmpty(SysConfig.getWebServerInstantNumber()) ?
                1 : Integer.parseInt(SysConfig.getWebServerInstantNumber());
        DeploymentOptions options = new DeploymentOptions()
                .setInstances(num);
        return options;
    }

    @Override
    public void start() throws Exception {
        logger.info("Web-Launch ->> Web server launch now.");
        Router router = Router.router(VertxUtils.vertx());
        if (!StringUtils.isEmpty(SysConfig.getSysVerticlePackage())) {
            try {
                new PackageSimpleScanner<Route>().scan(SysConfig.getWebRoutePackage(), Route.class)
                        .forEach(route -> {
                            logger.info("Web-Launch ->> Deploy route:" + route.getClass().getSimpleName());
                            route.route(router);
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int httpPort = StringUtils.isEmpty(SysConfig.getWebListenPort()) ?
                80 : Integer.parseInt(SysConfig.getWebListenPort());
        VertxUtils.vertx().createHttpServer().requestHandler(router::accept)
                .listen(httpPort);
        logger.info("Web-Launch ->> Web server launch over on port " + httpPort + ".");
    }
}
