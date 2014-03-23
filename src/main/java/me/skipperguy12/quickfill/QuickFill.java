package me.skipperguy12.quickfill;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import me.skipperguy12.quickfill.commands.QuickFillParentCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Main class (Bukkit JavaPlugin)
 */
public class QuickFill extends JavaPlugin {
    /**
     * Holds instance for plugin
     */
    private static QuickFill instance;
    /**
     * sk89q's command framework CommandsManager
     */
    private CommandsManager<CommandSender> commands;

    /**
     * Called when the plugin gets enabled
     */
    @Override
    public void onEnable() {
        instance = this;

        // setup the sk89q command framework
        this.setupCommands();

        // save the default config file and set variables to Config.java
        saveDefaultConfig();

        Config.configFile = new File(getDataFolder(), "config.yml");
        Config.config = YamlConfiguration.loadConfiguration(Config.configFile);

        // set debugging to the value in the config
        Log.setDebugging(Config.General.debugging);
    }

    /**
     * Called when the plugin gets disabled
     */
    public void onDisable() {
        instance = null;
    }

    /**
     * sk89q's command framework method to setup commands from onEnable
     */
    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        cmdRegister.register(QuickFillParentCommand.class);
    }

    /**
     * Gets instance of plugin
     *
     * @return main plugin instance
     */
    public static QuickFill getInstance() {
        return instance;
    }

    // Passes commands from Bukkit to sk89q
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }
}
