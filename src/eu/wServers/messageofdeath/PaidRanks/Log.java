package eu.wServers.messageofdeath.PaidRanks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * A class used for accessing the Bukkit logger.
 *
 * @author Sushi
 */
public class Log {
	
    private  Logger log = Bukkit.getLogger();
    private  String pluginName;
    
    public Log(JavaPlugin plugin) {
    	pluginName = plugin.getDescription().getName();
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "severe" log
     * level.
     *
     * @param message The message to log.
     */
    public void severe(String message) {
        log.severe("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "warning" log
     * level.
     *
     * @param message The message to log.
     */
    public  void warning(String message) {
        log.warning("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "info" log
     * level.
     *
     * @param message The message to log.
     */
    public  void info(String message) {
        log.info("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "config" log
     * level.
     *
     * @param message The message to log.
     */
    public  void config(String message) {
        log.config("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "fine" log
     * level.
     *
     * @param message The message to log.
     */
    public  void fine(String message) {
        log.fine("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "finer" log
     * level.
     *
     * @param message The message to log.
     */
    public  void finer(String message) {
        log.finer("[" + pluginName + "] " + message);
    }

    /**
     * This logs a message to the Bukkit console. It
     * will log the message using the "finest" log
     * level.
     *
     * @param message The message to log.
     */
    public  void finest(String message) {
        log.finest("[" + pluginName + "] " + message);
    }
}
