package me.skipperguy12.quickfill;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * Class to handle configuration settings
 */
public class Config {

    // the config File
    protected static File configFile;

    // the YamlConfiguration of configFile
    protected static FileConfiguration config;


    /**
     * Gets an Object from a specified path
     * @param path path to object
     * @param <T> Object type
     * @return casted Object from path
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String path) {
        return (T) config.get(path);
    }

    /**
     * Gets an Object from a specified path
     * @param path path to object
     * @param def default object to return in the event that the Object at path does not exist
     * @param <T> Object type
     * @return casted Object from path (or def if the Object is non-existant)
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String path, Object def) {
        return (T) config.get(path, def);
    }


    /**
     * Sets a value at a specified path
     * @param path path to object
     * @param value value of path
     */
    public static void set(String path, Object value) {
        config.set(path, value);
        try {
            config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * General section of config
     */
    public static class General {
        /**
         * Sets debugging status
         */
        public static boolean debugging = get("general.debugging", false);
    }


} 