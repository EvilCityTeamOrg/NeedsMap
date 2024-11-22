package com.evilcity.needsmap;

import static spark.Spark.*;

public class WebRouting {
    /**
     * Creates sparkJava(Jetty) webserver with all routing ready
     */
    public static void onServerStart() {
        port(80);

        staticFiles.externalLocation(ServerStart.getStringArgument("sparkPath"));

        init();
    }
}
