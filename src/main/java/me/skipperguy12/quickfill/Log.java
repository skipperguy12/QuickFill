package me.skipperguy12.quickfill;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class to handle logging/debugging
 */
public class Log {
    // Java Logger in use
    private static Logger log;

    // Should Log print out debugging information to the console?
    private static boolean debug = false;

    // Sets the Logger in use
    static {
        Log.log = QuickFill.getInstance().getLogger();
    }

    /**
     * Logs a message with a specified priority Level
     *
     * @param lvl priority
     * @param msg message
     */
    public static void log(Level lvl, String msg) {
        log.log(lvl, msg);
    }

    /**
     * Logs a message with priority defaulted to INFO
     *
     * @param msg
     */
    public static void log(String msg) {
        log(Level.INFO, msg);
    }

    /**
     * Logs an exception
     *
     * @param e exception
     */
    public static void log(Exception e) {
        e.printStackTrace();
    }

    /**
     * Logs a message if debugging is enabled
     *
     * @param msg message
     */
    public static void debug(String msg) {
        if (debug) log("[DEBUG] " + msg);
    }

    /**
     * Sets debugging status
     *
     * @param debug should plugin use debugging
     */
    public static void setDebugging(boolean debug) {
        Log.debug = debug;
        if (debug) log("Debugging on.");
    }

}
