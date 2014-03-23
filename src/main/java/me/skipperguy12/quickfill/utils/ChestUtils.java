package me.skipperguy12.quickfill.utils;

import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

/**
 * Utils to make managing chests easier
 */
public class ChestUtils {
    /**
     * Checks if the specified chest is empty
     * @param c Chest
     * @return whether the chest is empty or not
     */
    public static boolean isEmpty(Chest c) {
        for(ItemStack item : c.getInventory().getContents())
        {
            if(item != null)
                return false;
        }
        return true;
    }
}
