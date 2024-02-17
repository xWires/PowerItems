package xyz.tangledwires.poweritems;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
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
    		if(sender instanceof Player) {
            	Player p = (Player) sender;
				PowerItem testItem = new PowerItem("testItem", "DIAMOND_SWORD", "50", "common", "Test Item");
				testItem.giveItem(p);
				PowerItem ultraStaff = new PowerItem("ultraStaff", "BLAZE_ROD", "500", "rare", "§r§9Ultra Staff");
				ultraStaff.giveItem(p);
			}
			
    		return true;
    	}
    	else if (cmd.getName().equalsIgnoreCase("createitem")) {
			if (args.length >= 5) {
				StringBuilder itemNameBuilder = new StringBuilder(args[4]);
            	for (int arg = 5; arg < args.length; arg++) {
              		itemNameBuilder.append(" ").append(args[arg]);
           		}
				String builtItemName = itemNameBuilder.toString();
				builtItemName = ChatColor.translateAlternateColorCodes("&".charAt(0), builtItemName);
				if(sender instanceof Player) {
            		Player p = (Player) sender;
					PowerItem commandItem = new PowerItem(args[0], args[1], args[2], args[3], builtItemName);
					commandItem.giveItem(p);
					saveItemData(commandItem);
					return true;
				}
				else {
					Bukkit.getServer().getLogger().severe("[PowerItems] This command must be run as a player!");
					return true;
				}
			}
			else {
				return false;
			}
			
    	}
		else if (cmd.getName().equalsIgnoreCase("getitem")) {
			FileConfiguration config = this.getConfig();
			if (config.get("items." + args[0]) != null) {
				String itemName = config.getString("items." + args[0] + ".itemName");
				String itemMaterial = config.getString("items." + args[0] + ".itemMaterial");
				String damageValue = config.getString("items." + args[0] + ".damageValue");
				String itemRarity = config.getString("items." + args[0] + ".itemRarity");
				Bukkit.getServer().getLogger().info(itemName + itemMaterial + damageValue + itemRarity);
				if(sender instanceof Player) {
					Player p = (Player) sender;
					PowerItem getItem = new PowerItem(args[0], itemName, itemMaterial, damageValue, itemRarity);
					getItem.giveItem(p);
				}
			}
			else {
				sender.sendMessage("That item does not exist!");
			}
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

	}
}
