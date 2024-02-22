package xyz.tangledwires.poweritems.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import xyz.tangledwires.poweritems.PowerItems;

public class PersistantDataContainerUtils {
    static NamespacedKey namespace = new NamespacedKey(PowerItems.getPlugin(PowerItems.class), "triggers");

    private PersistantDataContainerUtils() {}

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
    public static void setAsString(ItemStack i, String s) {
        ItemMeta m = i.getItemMeta();
        if (m != null) {
            PersistentDataContainer pdc = m.getPersistentDataContainer();
            pdc.set(namespace, PersistentDataType.STRING, s);
            i.setItemMeta(m);
        }
    }
}