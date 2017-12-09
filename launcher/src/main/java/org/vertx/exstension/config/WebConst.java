package org.vertx.exstension.config;

/**
 * const collection for web container.
 * Created by kam on 2017/12/3.
 */
public enum WebConst {
    HTTP_PORT("web.listen.port"),
    ROUTE_PACKAGE("web.route.package");

    private String key;

    WebConst(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
