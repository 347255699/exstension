package org.vertx.exstension;


import org.vertx.exstension.service.LaunchService;

/**
 * Created by kam on 2017/12/3.
 */
public class ApplicationMain {
    public static void main(String[] args) {
        LaunchService.launchLocal(args.length > 0 ? args[0] : null);
    }
}
