package com.evilcity.needsmap;

import com.evilcity.needsmap.entity.User;
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

        get("style.css", f("style.css"));
        get("/", f("index.html"));

        path("/api", () -> {
            path("/system", () -> {
                path("/accounts", () -> {
                    post("/reg", createUser);
                });
            });
        });

        init();
    }

    private static final Route createUser = (request, response) -> {
        String username = request.queryParams("u");
        String password = request.queryParams("p");
        if (username == null) {halt(400, "DO NOT USE THIS AS API! 651098510");}
        if (password == null) {halt(400, "DO NOT USE THIS AS API! 706044366");}
        User.register(username, password);
        return "DO NOT USE THIS AS API! 626911954";
    };


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
