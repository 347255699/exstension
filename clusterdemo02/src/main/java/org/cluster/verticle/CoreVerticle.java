package org.cluster.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import org.cluster.Holder.SysHolder;

/**
 * Created by kam on 2017/12/25.
 */
public class CoreVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        SysHolder.eventBus().<JsonObject>consumer("cluster.demo02", msg -> {
            System.out.println(msg.body().toString());
        });
    }
}
