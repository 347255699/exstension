package org.exstension.web;

import io.vertx.ext.web.Router;

import java.util.Set;

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

    /**
     * Get the uri set from route.
     *
     * @return
     */
    Set<String> uriSet();
}
