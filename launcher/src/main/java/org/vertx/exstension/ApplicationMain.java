package org.vertx.exstension;


import org.vertx.exstension.service.LaunchService;

/**
 * Created by kam on 2017/12/3.
 */
public class ApplicationMain {
    public static void main(String[] args) {
        for (String str : args) {
            System.out.println(str);
        }

        LaunchService.launchLocal();
    }
}
