package com.evilcity.needsmap.log;

import com.evilcity.needsmap.ServerStart;
import org.slf4j.Marker;

import static com.evilcity.needsmap.ServerStart.cli;

/**
 * I have probably done something wrong here. I'm using SLF4J first time, but I don't want to hava billion libraries installed, so I will stick with this garbage
 */
public class Logger implements org.slf4j.Logger {

    private static boolean debug = true;
    private static boolean trace = true;
    private static boolean info = true;
    private static boolean warn = true;
    private static boolean error = true;


    public static void onServerStart() {
        debug = !cli.containsKey("--noDebug");
        trace = !cli.containsKey("--noTrace");
        info = !cli.containsKey("--noInfo");
        warn = !cli.containsKey("--noWarn");
        error = !cli.containsKey("--noError");
    }

    private final String name;

    public Logger(String name) {
        this.name = "] [" + name + "] ";
    }

    private void Log(String level, String text) {
        if (level.equals("DEBUG") && !debug) return;
        if (level.equals("TRACE") && !trace) return;
        if (level.equals("INFO ") && !info) return;
        if (level.equals("WARN ") && !warn) return;
        if (level.equals("ERROR") && !error) return;
        System.out.println("[" + level + name + text);
    }
    private void Log(String level, String text, Object o) {
        Log(level, text + " " + o);
    }
    private void Log(String level, String text, Object o, Object o1) {
        Log(level, text + " " + o + " " + o1);
    }
    private void Log(String level, String s, Object... objects) {
        String str = s + " | ";
        for (Object o : objects) {
            if (o == null) {
                str += "null ";
                continue;
            }
            str += o + " ";
        }
        Log(level, str);
    }
    private void Log(String level, String s, Throwable t) {
        Log(level, s + t.toString());
        t.printStackTrace();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void trace(String s) {
        Log("TRACE", s);
    }

    @Override
    public void trace(String s, Object o) {
        Log("TRACE", s, o);
    }

    @Override
    public void trace(String s, Object o, Object o1) {
        Log("TRACE", s, o, o1);
    }

    @Override
    public void trace(String s, Object... objects) {
        Log("TRACE", s, objects);
    }

    @Override
    public void trace(String s, Throwable throwable) {
        Log("TRACE", s, throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return true;
    }

    
    @Override
    public void trace(Marker marker, String s) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void trace(Marker marker, String s, Object o) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void trace(Marker marker, String s, Object o, Object o1) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void trace(Marker marker, String s, Object... objects) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public void debug(String s) {
        Log("DEBUG", s);
    }

    @Override
    public void debug(String s, Object o) {
        Log("DEBUG", s, o);
    }

    @Override
    public void debug(String s, Object o, Object o1) {
        Log("DEBUG", s, o, o1);
    }

    @Override
    public void debug(String s, Object... objects) {
        Log("DEBUG", s, objects);
    }

    @Override
    public void debug(String s, Throwable throwable) {
        Log("DEBUG", s, throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return true;
    }

    
    @Override
    public void debug(Marker marker, String s) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void debug(Marker marker, String s, Object o) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void debug(Marker marker, String s, Object o, Object o1) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void debug(Marker marker, String s, Object... objects) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public void info(String s) {
        Log("INFO ", s);
    }

    @Override
    public void info(String s, Object o) {
        Log("INFO ", s, o);
    }

    @Override
    public void info(String s, Object o, Object o1) {
        Log("INFO ", s, o, o1);
    }

    @Override
    public void info(String s, Object... objects) {
        Log("INFO ", s, objects);
    }

    @Override
    public void info(String s, Throwable throwable) {
        Log("INFO ", s, throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return true;
    }

    
    @Override
    public void info(Marker marker, String s) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void info(Marker marker, String s, Object o) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void info(Marker marker, String s, Object o, Object o1) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void info(Marker marker, String s, Object... objects) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void warn(String s) {
        Log("WARN ", s);
    }

    @Override
    public void warn(String s, Object o) {
        Log("WARN ", s, o);
    }

    @Override
    public void warn(String s, Object... objects) {
        Log("WARN ", s, objects);
    }

    @Override
    public void warn(String s, Object o, Object o1) {
        Log("WARN ", s, o, o1);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        Log("WARN ", s, throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return true;
    }

    
    @Override
    public void warn(Marker marker, String s) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void warn(Marker marker, String s, Object o) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void warn(Marker marker, String s, Object o, Object o1) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void warn(Marker marker, String s, Object... objects) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public void error(String s) {
        Log("ERROR", s);
    }

    @Override
    public void error(String s, Object o) {
        Log("ERROR", s, o);
    }

    @Override
    public void error(String s, Object o, Object o1) {
        Log("ERROR", s, o, o1);
    }

    @Override
    public void error(String s, Object... objects) {
        Log("ERROR", s, objects);
    }

    @Override
    public void error(String s, Throwable throwable) {
        Log("ERROR", s, throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return true;
    }

    
    @Override
    public void error(Marker marker, String s) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void error(Marker marker, String s, Object o) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void error(Marker marker, String s, Object o, Object o1) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void error(Marker marker, String s, Object... objects) {
        throw new UnsupportedOperationException();
    }

    
    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        throw new UnsupportedOperationException();
    }
}
