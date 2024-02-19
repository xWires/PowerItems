package xyz.tangledwires.poweritems.events;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import xyz.tangledwires.poweritems.utils.PersistantDataContainerUtils;

public class onItemUse implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Gson gson = new Gson();
                ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
                Type type = new TypeToken<Map<String, String>>(){}.getType();
                Map<String, String> commandTriggers = new HashMap<String, String>();
                if (PersistantDataContainerUtils.getAsString(heldItem) != null) {
					commandTriggers = gson.fromJson(PersistantDataContainerUtils.getAsString(heldItem), type);
                }
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