package org.exstension.web;

import io.vertx.ext.web.Router;

/**
 * Created by kam on 2017/12/10.
 */
public interface Route {
    /**
     * Pass router instant.
     *
     * @param router
     */
    void route(Router router);
}
