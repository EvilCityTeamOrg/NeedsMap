package com.evilcity.needsmap;

import com.evilcity.needsmap.entity.MapObject;
import com.evilcity.needsmap.entity.User;
import com.mongodb.client.model.Filters;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import static com.evilcity.needsmap.Database.getDatabase;
import static spark.Spark.*;

public class WebRouting {
    /**
     * Creates sparkJava(Jetty) webserver with all routing ready
     */
    public static void onServerStart() {
        port(80);

        //staticFiles.externalLocation(ServerStart.getStringArgument("sparkPath"));

        get("/snack/js-snackbar.min.js", f("js-snackbar.min.js", "application/javascript"));
        get("/snack/js-snackbar.min.css", f("js-snackbar.min.css", "text/css"));
        get("style.css", f("style.css", "text/css"));
        get("/", r("/app", "/promo"));
        get("/promo", f("promo.html"));
        get("/login", f("login.html", false));
        get("/register", f("reg.html", false));
        get("/app.webmanifest", f("app.webmanifest"));
        get("/favicon-512x512.png", png("favicon-512x512.png"));
        get("/favicon-192x192.png", png("favicon-192x192.png"));
        get("/favicon.ico", f("favicon.ico", "image/x-icon"));

        path("/app", () -> {
            get("/home", f("app.html", true));
            get("/add", f("add.html", true));
        });

        path("/api", () -> {
            path("/system", () -> {
                path("/accounts", () -> {
                    post("/reg", createUser);
                    post("/login", login);
                });
                path("/objects", () -> {
                    post("/create", createObj);
                    get("/get", getObj);
                    delete("/:id", deleteObj);
                });
            });
        });

        init();
    }
    private static final Route getObj = (request, response) -> {
        if (!authCheck(request, response)) {halt(401, "DO NOT USE THIS AS API! UNAUTHORIZED");}
        MapObject[] objects = MapObject.fetch();
        String str = "{\"result\":[";

        for (MapObject object : objects) {
            str += "{\"lon\":\"" + object.getLon() + "\",\"lat\":\"" + object.getLat() + "\", \"name\":\"" + object.getName() + "\",\"id\":\"" + object.getID() + "\"},";
        }

        str += "]}";

        response.type("application/json");
        return str.replace("},]}", "}]}");
    };
    private static final Route deleteObj = (request, response) -> {
        if (!authCheck(request, response)) {halt(401, "DO NOT USE THIS AS API! UNAUTHORIZED");}
        getDatabase().getCollection("objects").findOneAndDelete(Filters.eq("ID", request.params(":id")));
        return "fine";
    };
    private static final Route createObj = (request, response) -> {
        if (!authCheck(request, response)) {halt(401, "DO NOT USE THIS AS API! UNAUTHORIZED");}
        String lon = request.queryParams("lon");
        String lat = request.queryParams("lat");
        String name = request.queryParams("name");
        if (lon == null) {halt(400, "DO NOT USE THIS AS API! 819245346");}
        if (lat == null) {halt(400, "DO NOT USE THIS AS API! 618774483");}
        if (name == null) {halt(400, "DO NOT USE THIS AS API! 534565345");}
        MapObject.register(lon, lat, name);
        return "DO NOT USE THIS AS API! 584395849";
    };
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
    private static Route png(String path) {
        return (request, response) -> {
            BufferedImage ans = ImageIO.read(new File(ServerStart.path + "/" + path));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(ans, "png", baos);
            response.type("image/png");
            return baos.toByteArray();
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
