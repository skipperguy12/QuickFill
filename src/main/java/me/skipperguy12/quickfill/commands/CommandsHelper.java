package me.skipperguy12.quickfill.commands;

import com.sk89q.minecraft.util.commands.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class to store static utils that can be used in commands
 */
public class CommandsHelper {
    /**
     * Gets a player from sender
     * @param sender CommandSender
     * @return Player
     * @throws CommandException thrown if the sender is not a player
     */
    public static Player getPlayer(CommandSender sender) throws CommandException {
        // If the sender is an in game player then return a casted sender
        if ((sender instanceof Player))
            return (Player) sender;
        // otherwise stop the rest of the command from executing and provide a helpful message to the player
        throw new CommandException("You must be a player to execute this command!");
    }
}
