package xyz.tangledwires.poweritems.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import xyz.tangledwires.poweritems.PowerItems;

public class PersistantDataContainerUtils {
    static NamespacedKey namespace = new NamespacedKey(PowerItems.getPlugin(PowerItems.class), "poweritems_commandTriggers");

    private PersistantDataContainerUtils() {}

    public static String getAsString(ItemStack i) {
        ItemMeta m = i.getItemMeta();
        PersistentDataContainer pdc = m.getPersistentDataContainer();
        return pdc.get(namespace, PersistentDataType.STRING);
    }
    public static void setAsString(ItemStack i, String s) {
        ItemMeta m = i.getItemMeta();
        PersistentDataContainer pdc = m.getPersistentDataContainer();
        pdc.set(namespace, PersistentDataType.STRING, s);
    }
}