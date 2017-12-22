package org.exstension.base.web.config;

/**
 * Created by kam on 2017/12/10.
 */
public enum WebConst {
    WEB_LISTEN_PORT("web.listen.port"),
    WEB_ROUTE_PACKAGE("web.route.package");
    private String key;

    WebConst(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
