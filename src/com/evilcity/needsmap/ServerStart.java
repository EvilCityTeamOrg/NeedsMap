package com.evilcity.needsmap;

import com.evilcity.needsmap.log.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class ServerStart {
    public static final HashMap<String, Object> cli = new HashMap<>();
    private static final org.slf4j.Logger log = LoggerFactory.getLogger("STARTER");
    public static void main(String[] args) {
        commandLineArgs(args);

        Logger.onServerStart();
        WebRouting.onServerStart();

        log.info("Started!");
    }

    private static void commandLineArgs(String[] args) {
        String lastArgumentName = null;
        String last = "";
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                if (args.length - 1 == i || args[i + 1].startsWith("--")) {
                    cli.put(arg, true);
                }
                lastArgumentName = arg;
            } else {
                if (lastArgumentName == null) {
                    System.out.println(" ".repeat(15) + last + args[i]);
                    System.out.println("               ^ ");
                    System.out.println("Excepted \"--\" after value of command line argument.\nContact developers!");
                    System.exit(32);
                }
                if (!cli.containsKey(lastArgumentName)) cli.put(lastArgumentName, arg);
                else cli.put(lastArgumentName, cli.get(lastArgumentName) + " " + arg);
            }
            last += args[i] + " ";
        }
    }
    public static String getStringArgument(String name) {
        if (!cli.containsKey("--" + name)) {
            log.error("Required string command line argument \"" + name + "\" doesn't exists");
            System.exit(1);
        }
        if (cli.get("--" + name) instanceof String s) {
            return s;
        } else {
            log.error("Required string command line argument \"" + name + "\" isn't actual string");
            System.exit(1);

            return null; // Java compiler just wants this line for no reason ¯\_(ツ)_/¯ Execution should be stopped by now
        }
    }
}
