package xyz.tangledwires.poweritems.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import xyz.tangledwires.poweritems.PowerItems;

public class PersistantDataContainerUtils {
    // Setup the namespace key "poweritems:triggers"
    static NamespacedKey namespace = new NamespacedKey(PowerItems.getPlugin(PowerItems.class), "triggers");

    private PersistantDataContainerUtils() {}

    /**
     * Returns the list of command triggers on an item.
     * 
     * @param i The ItemStack to get the triggers from.
     * @return A JSON formatted list of command triggers
     */
    public static String getAsString(ItemStack i) {
        ItemMeta m = i.getItemMeta();
        if (m != null) {
            PersistentDataContainer pdc = m.getPersistentDataContainer();
            return pdc.get(namespace, PersistentDataType.STRING);
        }
        else {
            return null;
        }
    }
    /**
     * Sets the list of command triggers on an item.
     * 
     * @param i The ItemStack to set the triggers on
     * @param s A JSON formatted string to set as the list of command triggers
     */
    public static void setAsString(ItemStack i, String s) {
        ItemMeta m = i.getItemMeta();
        if (m != null) {
            PersistentDataContainer pdc = m.getPersistentDataContainer();
            pdc.set(namespace, PersistentDataType.STRING, s);
            i.setItemMeta(m);
        }
    }
}