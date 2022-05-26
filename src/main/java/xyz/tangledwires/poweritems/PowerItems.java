package xyz.tangledwires.poweritems;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tangledwires.poweritems.ItemDefinitions;
import org.bukkit.configuration.*;

import java.io.File;
import java.io.IOException;

public final class PowerItems extends JavaPlugin {
	@Override
    public void onEnable() {
		Bukkit.getServer().getLogger().info("Loaded PowerItems. Enjoy!");
    }
    
    @Override
    public void onDisable() {
        Bukkit.getServer().getLogger().info("PowerItems Disabled, bye! Have a nice day.");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("testitem")) { 
    		@SuppressWarnings("unused")
			ItemDefinitions testItem = new ItemDefinitions("testItem", "Test Item", "DIAMOND_SWORD", "50", "common");
    		@SuppressWarnings("unused")
			ItemDefinitions masterStaff = new ItemDefinitions("masterStaff", "§r§9Master Staff", "BLAZE_ROD", "500", "rare");
    		return true;
    	}
    	else if (cmd.getName().equalsIgnoreCase("createitem")) {
    		ItemDefinitions commandItem = new ItemDefinitions(args[0], args[1], args[2], args[3], args[4]);
			saveItemData(commandItem);
    		return true;
    	}
		else if (cmd.getName().equalsIgnoreCase("getitem")) {
			getItemData(args[0]);
		}
    	return false; 
    }
	public void saveItemData(ItemDefinitions createdItem) {
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemName", createdItem.getItemName());
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemMaterial", createdItem.getItemMaterial());
		this.getConfig().set("items." + createdItem.getInternalName() + ".damageValue", createdItem.getHitDamageValue());
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemRarity", createdItem.getItemRarityType());
		this.saveConfig();
	}
	public void getItemData(String internalName) {
		String itemName = this.getConfig().getString("items." + internalName + ".itemName");
		String itemMaterial = this.getConfig().getString("items." + internalName + ".itemMaterial");
		String damageValue = this.getConfig().getString("items." + internalName + ".damageValue");
		String itemRarity = this.getConfig().getString("items." + internalName + ".itemRarity");
		ItemDefinitions getItem = new ItemDefinitions(internalName, itemName, itemMaterial, damageValue, itemRarity);
	}
}