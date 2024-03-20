package xyz.tangledwires.poweritems.events;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import xyz.tangledwires.poweritems.PowerItems;
import xyz.tangledwires.poweritems.utils.PersistantDataContainerUtils;

public class onItemUse implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        Configuration config = PowerItems.getPlugin(PowerItems.class).getConfig();
        if (config.getString("config.commandTriggersAllowed") == "true") {
            if (config.getString("config.permissionRequiredForTriggers") == "true") {
                if (event.getPlayer().hasPermission("poweritems.usecommandtriggers") == false) {
                    event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to do that.");
                    return;
                }
            }
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Gson gson = new Gson();
                    ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
                    Type type = new TypeToken<Map<String, String>>(){}.getType();
                    Map<String, String> commandTriggers = new HashMap<String, String>();
                    if (heldItem != null && heldItem.getItemMeta() != null) {
                        if (PersistantDataContainerUtils.getAsString(heldItem) != null) {
                            commandTriggers = gson.fromJson(PersistantDataContainerUtils.getAsString(heldItem), type);
                            if (commandTriggers != null) {
                                for (String key : commandTriggers.keySet()) {
                                    if (key.equalsIgnoreCase("chat")) {
                                        event.getPlayer().chat(commandTriggers.get(key));
                                    }
                                    else if (key.equalsIgnoreCase("command")) {
                                        event.getPlayer().performCommand(commandTriggers.get(key));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            event.getPlayer().sendMessage(ChatColor.RED + "Commmand triggers are disabled on this server.");
        }
    }
}