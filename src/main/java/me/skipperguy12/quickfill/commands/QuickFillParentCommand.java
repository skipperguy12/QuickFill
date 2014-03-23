package me.skipperguy12.quickfill.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.NestedCommand;
import org.bukkit.command.CommandSender;

/**
 * Parent command for QuickFill commands
 */
public class QuickFillParentCommand {
    /**
     * Parent command for all nested QuickFill commands
     * @param args
     * @param sender Player who sends the command
     * @throws CommandException thrown if a nested command throws a CommandException
     */
    @Command(aliases = { "quickfill", "qf" }, desc = "Quickly fills a chest with items from hot bar or inventory", min = 0, max = -1)
    @NestedCommand(QuickFillCommands.class) //All commands will get passed on to QuickFillCommands.class
    public static void quickfill(final CommandContext args, CommandSender sender) throws CommandException {
    }
}
