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

        get("style.css", f("style.css", "text/css"));
        get("/", r("/app", "/promo"));
        get("/promo", f("promo.html"));
        get("/login", f("login.html", false));
        get("/register", f("reg.html", false));

        path("/app", () -> {
            get("/home", f("app.html", true));
        });

        path("/api", () -> {
            path("/system", () -> {
                path("/accounts", () -> {
                    post("/reg", createUser);
                    post("/login", login);
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
        User user = User.register(username, password);
        request.session().attribute("user", user.getID());
        return "DO NOT USE THIS AS API! 626911954";
    };
    private static final Route login = (request, response) -> {
        String username = request.queryParams("u");
        String password = request.queryParams("p");
        if (username == null) {halt(400, "DO NOT USE THIS AS API! 651098510");}
        if (password == null) {halt(400, "DO NOT USE THIS AS API! 706044366");}
        User user = User.fetchByUsername(username);
        if (user == null) {halt(400, "DO NOT USE THIS AS API! 419069176");}
        if (!user.comparePassword(password)) {halt(400, "DO NOT USE THIS AS API! 419069176");}
        request.session().attribute("user", user.getID());
        return "DO NOT USE THIS AS API! 563453465";
    };


    private static Route f(String path) {
        return (request, response) -> {
            putFile(path, response);
            // Return empty string for Spark (it just appends returned value, so it should be fine)
            return "";
        };
    }
    private static Route f(String path, String mime) {
        return (request, response) -> {
            response.type(mime);
            putFile(path, response);
            // Return empty string for Spark (it just appends returned value, so it should be fine)
            return "";
        };
    }
    private static void putFile(String path, Response response) throws IOException {
        try (FileReader fr = new FileReader(ServerStart.path + "/" + path); Scanner sc = new Scanner(fr)) {
            // Transfer file content to response
            while (sc.hasNextLine()) {
                response.raw().getOutputStream().write((sc.nextLine() + "\n").getBytes());
            }
        }
    }
    private static boolean authCheck(Request request, Response response) {
        // For anyone reading this:
        //   Session attributes located on server side unlike cookies
        //   It is impossible to change session attributes
        return request.session().attribute("user") != null;
    }
    private static Route f(String path, boolean authRequired) {
        if (authRequired) {
            return (request, response) -> {
                if (authCheck(request, response)) {
                    putFile(path, response);
                } else {
                    response.redirect("/login");
                }
                return "";
            };
        }
        return (request, response) -> {
            if (!authCheck(request, response)) {
                putFile(path, response);
            } else {
                response.redirect("/app/home");
            }
            return "";
        };
    }
    private static Route r(String authPath, String path) {
        return (request, response) -> {
            if (authCheck(request, response)) {
                response.redirect(authPath);
            } else {
                response.redirect(path);
            }
            return "";
        };
    }
}
