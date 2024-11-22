package com.evilcity.needsmap;

import spark.Request;
import spark.Response;
import spark.Route;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static spark.Spark.*;

public class WebRouting {
    /**
     * Creates sparkJava(Jetty) webserver with all routing ready
     */
    public static void onServerStart() {
        port(80);

        //staticFiles.externalLocation(ServerStart.getStringArgument("sparkPath"));

        path("/assets", () -> {
            get("/css", f("style.css"));
        });
        get("/", f("index.html"));

        init();
    }


    private static Route f(String path) {
        return (request, response) -> {
            putFile(path, response);
            // Return empty string for Spark (it just appends returned value, so it should be fine)
            return "";
        };
    }
    private static void putFile(String path, Response response) throws IOException {
        try (FileReader fr = new FileReader(ServerStart.path + "/" + path); Scanner sc = new Scanner(fr)) {
            // Transfer file content to response
            while (sc.hasNextLine()) {
                response.raw().getOutputStream().write(sc.nextLine().getBytes());
            }
        }
    }
    private static boolean authCheck(Request request, Response response) {
        // For anyone reading this:
        //   Session attributes located on server side unlike cookies
        //   It is impossible to change session attributes
        return request.session().attribute("user") != null;
    }
}
