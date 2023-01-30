package xyz.tangledwires.poweritems;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class PowerItems extends JavaPlugin {
	@Override
    public void onEnable() {
		Bukkit.getServer().getLogger().info("Loaded PowerItems by xWires.");
    }
    
    @Override
    public void onDisable() {
        Bukkit.getServer().getLogger().info("PowerItems Disabled, bye!");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (cmd.getName().equalsIgnoreCase("testitem")) { 
    		@SuppressWarnings("unused")
			PowerItem testItem = new PowerItem("testItem", "DIAMOND_SWORD", "50", "common", "Test Item");
    		@SuppressWarnings("unused")
			PowerItem masterStaff = new PowerItem("masterStaff", "BLAZE_ROD", "500", "rare", "§r§9Master Staff");
    		return true;
    	}
    	else if (cmd.getName().equalsIgnoreCase("createitem")) {
			StringBuilder itemNameBuilder = new StringBuilder(args[4]);
            for (int arg = 5; arg < args.length; arg++) {
              itemNameBuilder.append(" ").append(args[arg]);
            }
			String builtItemName = itemNameBuilder.toString();
    		PowerItem commandItem = new PowerItem(args[0], args[1], args[2], args[3], builtItemName);
			saveItemData(commandItem);
    		return true;
    	}
		else if (cmd.getName().equalsIgnoreCase("getitem")) {
			getItemData(args[0]);
		}
    	return false; 
    }
	public void saveItemData(PowerItem createdItem) {
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
		PowerItem getItem = new PowerItem(internalName, itemName, itemMaterial, damageValue, itemRarity);
	}
}