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

/**
 * This class handles activating command triggers when right clicking.
 * <p>
 * See {@link xyz.tangledwires.poweritems.PowerItems#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, String, String[])} for how they are added to items.
 */
public class CommandTriggerRunner implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        Configuration config = PowerItems.getPlugin(PowerItems.class).getConfig();
        // Checks if command triggers are enabled on the server.
        if (config.getString("config.commandTriggersAllowed") == "true") {
            // If permissions are required to use command triggers, check if the player has permission.
            if (config.getString("config.permissionRequiredForTriggers") == "true") {
                if (event.getPlayer().hasPermission("poweritems.usecommandtriggers") == false) {
                    event.getPlayer().sendMessage(ChatColor.RED + "You don't have permission to do that.");
                    return;
                }
            }
            // Check if the item that was right clicked is in the player's main hand
            if (event.getHand() == EquipmentSlot.HAND) {
                // Was it a right click?
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    // Get the list of command triggers
                    Gson gson = new Gson();
                    ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
                    Type type = new TypeToken<Map<String, String>>(){}.getType();
                    Map<String, String> commandTriggers = new HashMap<String, String>();
                    // Is there a valid item in the player's hand?
                    if (heldItem != null && heldItem.getItemMeta() != null) {
                        if (PersistantDataContainerUtils.getAsString(heldItem) != null) {
                            // Get the command triggers, then execute them.
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