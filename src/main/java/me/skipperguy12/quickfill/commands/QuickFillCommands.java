package me.skipperguy12.quickfill.commands;

import com.sk89q.minecraft.util.commands.ChatColor;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import me.skipperguy12.quickfill.Log;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Class to store the nested commands of the quickfill command
 */
public class QuickFillCommands {
    /**
     * Fills chest with a Players hotbar
     *
     * @param args
     * @param sender CommandSender player who sends the command
     * @throws CommandException thrown if sender is not a Player, or if the chest is not empty and override is disabled
     */
    @Command(aliases = {"hotbar", "hb", "row"}, desc = "Fills the chest the sender is looking at with the senders hot bar", flags = "oa", min = 0, max = 0)
    public static void hotbar(final CommandContext args, CommandSender sender) throws CommandException {
        Player player = CommandsHelper.getPlayer(sender);

        // Gets the players hotbar and stores in List
        PlayerInventory inventory = player.getInventory();
        ItemStack[] playerHotBar = new ItemStack[9];
        Log.debug(player.getName() + "'s hotbar:");
        for (int i = 0; i < 9; i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null)
                item = new ItemStack(Material.AIR);

            playerHotBar[i] = item;
            Log.debug(i + ": " + item.toString());
        }

        // Gets chest and replaces if empty (or override enabled)
        Block b = player.getTargetBlock(null, 100);
        if (b.getType() == Material.CHEST) {
            Chest chest = (Chest) b.getState();
            Log.debug("Chest contents length: " + chest.getBlockInventory().getContents().length);
            Log.debug("Is chest empty? " + (chest.getBlockInventory().getContents().length == 0 ? "yes" : "no"));
            if (chest.getBlockInventory().getContents().length != 0 && args.hasFlag('o') == false) {
                throw new CommandException("This chest is not empty! If you wish to override the chest (the chest WILL be CLEARED), execute this command with the -o flag.");
            }

            chest.getBlockInventory().clear();
            if(args.hasFlag('a'))
                for (int hor = 0; hor < chest.getBlockInventory().getSize()/9; hor++) {
                for (int ver = 0; ver < 9; ver++) {
                    chest.getBlockInventory().setItem(hor * 9 + ver, playerHotBar[ver]);
                }
            }
            else
                chest.getBlockInventory().setContents(playerHotBar);

            player.sendMessage(ChatColor.GREEN + "Successfully replaced chests contents with your hotbar!");
        } else {
            Log.debug("Unable to perform command, " + player.getName() + " was looking at " + b.getType());
            throw new CommandException("You must be looking at a chest! Currently you're looking at " + b.getType());
        }
    }

    /**
     * Fills chest with a Players inventory
     *
     * @param args
     * @param sender CommandSender player who sends the command
     * @throws CommandException thrown if sender is not a Player, or if the chest is not empty and override is disabled
     */
    @Command(aliases = {"inventory", "inv"}, desc = "Fills the chest the sender is looking at with the senders inventory", flags = "o", min = 0, max = 0)
    public static void inventory(final CommandContext args, CommandSender sender) throws CommandException {
        Player player = CommandsHelper.getPlayer(sender);

        PlayerInventory inventory = player.getInventory();

        // Gets chest and replaces if empty (or override enabled)
        Block b = player.getTargetBlock(null, 100);
        if (b.getType() == Material.CHEST) {
            Chest chest = (Chest) b.getState();
            Log.debug("Chest contents length: " + chest.getBlockInventory().getContents().length);
            Log.debug("Is chest empty? " + (chest.getBlockInventory().getContents().length == 0 ? "yes" : "no"));
            if (chest.getBlockInventory().getContents().length != 0 && args.hasFlag('o') == false) {
                throw new CommandException("This chest is not empty! If you wish to override the chest (the chest WILL be CLEARED), execute this command with the -o flag.");
            }

            chest.getBlockInventory().clear();

            for (int i = 0; i < chest.getBlockInventory().getSize(); i++) {
                Log.debug("Setting slot " + i + " in chest to " + inventory.getContents()[i]);
                chest.getBlockInventory().setItem(i, inventory.getContents()[i]);
            }
            player.sendMessage(ChatColor.GREEN + "Successfully replaced chests contents with your inventories contents!");
        } else {
            Log.debug("Unable to perform command, " + player.getName() + " was looking at " + b.getType());
            throw new CommandException("You must be looking at a chest! Currently you're looking at " + b.getType());
        }
    }
}
