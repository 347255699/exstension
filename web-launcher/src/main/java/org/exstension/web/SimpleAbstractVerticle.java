package org.exstension.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

/**
 * Created by kam on 2018/2/4.
 */
public abstract class SimpleAbstractVerticle extends AbstractVerticle {
    DeploymentOptions getDeploymentOptions() {
        return new DeploymentOptions();
    }
}
