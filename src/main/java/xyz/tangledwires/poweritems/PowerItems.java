package xyz.tangledwires.poweritems;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import xyz.tangledwires.poweritems.events.onItemUse;
import xyz.tangledwires.poweritems.utils.PersistantDataContainerUtils;

public final class PowerItems extends JavaPlugin {
	@Override
    public void onEnable() {
		int pluginId = 21046;
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, pluginId);
		getServer().getPluginManager().registerEvents(new onItemUse(), this);
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
			if (args.length == 1) {
				if (sender instanceof Player) {
					FileConfiguration config = this.getConfig();
					if (config.get("items." + args[0]) != null) {
						String itemName = config.getString("items." + args[0] + ".itemName");
						String itemMaterial = config.getString("items." + args[0] + ".itemMaterial");
						String damage = config.getString("items." + args[0] + ".damage");
						String itemRarity = config.getString("items." + args[0] + ".itemRarity");
						Player p = (Player) sender;
						PowerItem getItem = new PowerItem(args[0], itemMaterial, damage, itemRarity, itemName);
						getItem.giveItem(p);
						return true;
					}
					else {
						sender.sendMessage("That item does not exist!");
					}
				}
				else {
					sender.sendMessage("[PowerItems] This command must be run as a player!");
				}
			}
			else {
				return false;
			}
		}
		else if (cmd.getName().equalsIgnoreCase("commandtrigger")) {
			if (args.length > 0) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (args[0].equalsIgnoreCase("add")) {
						if (args.length < 3) {
							return false;
						}
						if (p.getInventory().getItemInMainHand() == null) {
							sender.sendMessage(ChatColor.RED + "You are not holding an item!");
							return false;
						}
						else {
							if (!args[1].equalsIgnoreCase("chat") && !args[1].equalsIgnoreCase("command")) {
								return false;
							}
							// The first string should either be "chat" or "command", player.chat() will force the player to send a chat message. player.performCommand() will force the player to run a command, do not include the slash.
							Gson gson = new Gson();
							ItemStack heldItem = p.getInventory().getItemInMainHand();
							Type type = new TypeToken<Map<String, String>>(){}.getType();
							Map<String, String> commandTriggers = new HashMap<String, String>();
							StringBuilder commandBuilder = new StringBuilder(args[2]);
							for (int arg = 3; arg < args.length; arg++) {
								commandBuilder.append(" ").append(args[arg]);
							}
							String builtCommand = commandBuilder.toString();
							if (PersistantDataContainerUtils.getAsString(heldItem) != null) {
								commandTriggers = gson.fromJson(PersistantDataContainerUtils.getAsString(heldItem), type);
							}
							if (commandTriggers == null) {
								commandTriggers = new HashMap<String, String>();
							}
							commandTriggers.put(args[1], builtCommand);
							PersistantDataContainerUtils.setAsString(heldItem, gson.toJson(commandTriggers));
							sender.sendMessage(ChatColor.GREEN + "Added trigger to the item!");
							return true;
						}
					}
					else if (args[0].equalsIgnoreCase("clear")) {
						PersistantDataContainerUtils.setAsString(p.getInventory().getItemInMainHand(), "");
						return true;
					}
					else if (args[0].equalsIgnoreCase("list")) {
						if (p.getInventory().getItemInMainHand() == null) {
							sender.sendMessage(ChatColor.RED + "You are not holding an item!");
						}
						Gson gson = new Gson();
						ItemStack heldItem = p.getInventory().getItemInMainHand();
						Type type = new TypeToken<Map<String, String>>(){}.getType();
						Map<String, String> commandTriggers = new HashMap<String, String>();
						if (PersistantDataContainerUtils.getAsString(heldItem) != null) {
							commandTriggers = gson.fromJson(PersistantDataContainerUtils.getAsString(heldItem), type);
							if (commandTriggers != null) {
								for (String key : commandTriggers.keySet()) {
									if (key.equalsIgnoreCase("chat")) {
										sender.sendMessage("Chat: " + commandTriggers.get(key));
									}
									else if (key.equalsIgnoreCase("command")) {
										sender.sendMessage("Command: " + commandTriggers.get(key));
									}
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + "This item does not have any triggers on it!");
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "This item does not have any triggers on it!");
						}
						return true;
					}
					else {
						return false;
					}
				}
				else {
					sender.sendMessage("[PowerItems] This command must be run as a player");
				}
			}
		}
		else if (cmd.getName().equalsIgnoreCase("poweritems")) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					this.reloadConfig();
				}
			}
			else {
				return false;
			}
		}
    	return false; 
    }
	public void saveItemData(PowerItem createdItem) {
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemName", createdItem.getItemName());
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemMaterial", createdItem.getItemMaterial());
		this.getConfig().set("items." + createdItem.getInternalName() + ".damage", createdItem.getHitDamageValue());
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemRarity", createdItem.getItemRarityType());
		this.saveConfig();
	}
}
