package xyz.tangledwires.poweritems;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import xyz.tangledwires.poweritems.events.CommandTriggerRunner;
import xyz.tangledwires.poweritems.events.UpdateNotifier;
import xyz.tangledwires.poweritems.utils.PersistantDataContainerUtils;

public final class PowerItems extends JavaPlugin {
	public String version = getDescription().getVersion();
	public String latestVersion;
	public boolean isOutdated = false;
	@Override
    public void onEnable() {
		// Setup metrics
		int pluginId = 21046;
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this, pluginId);
		// Register events
		getServer().getPluginManager().registerEvents(new CommandTriggerRunner(), this);
		getServer().getPluginManager().registerEvents(new UpdateNotifier(), this);
		// Set default config options
		Configuration config = getConfig();
		if (config.get("config.commandTriggersAllowed") == null) {
			config.set("config.commandTriggersAllowed", true);
			saveConfig();
		}
		if (config.get("config.permissionRequiredForTriggers") == null) {
			config.set("config.permissionRequiredForTriggers", false);
			saveConfig();
		}
		/*
		 * This checks whether the plugin is up to date.
		 * The URL below returns the latest build number from Jenkins.
		 * 
		 * It gets the latest build number and compares it with the version string of this instance of PowerItems.
		 */
		HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://ci.tangledwires.xyz/job/PowerItems/lastSuccessfulBuild/buildNumber"))
                .GET()
                .build();
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			int newestVersion = Integer.parseInt(response.body());
			latestVersion = Integer.toString(newestVersion);
			if (newestVersion > Integer.parseInt(getDescription().getVersion())) {
				isOutdated = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		registerDefaultRarities();
		Bukkit.getServer().getLogger().info("Loaded PowerItems by xWires.");
    }
    
    @Override
    public void onDisable() {
		unregisterDefaultRarities();
        Bukkit.getServer().getLogger().info("PowerItems Disabled, bye!");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// The "/testitem" command gives the player two items to test whether PowerItems is working properly.
    	if (cmd.getName().equalsIgnoreCase("testitem")) { 
    		if(sender instanceof Player) {
            	Player p = (Player) sender;
				PowerItem testItem = new PowerItem("testItem", Material.DIAMOND_SWORD, 50, "common", "Test Item");
				testItem.giveTo(p);
				PowerItem ultraStaff = new PowerItem("ultraStaff", Material.BLAZE_ROD, 500, "rare", "§r§9Ultra Staff");
				ultraStaff.giveTo(p);
			}
			
    		return true;
    	}
		/*
		 * Creates a new instance of the PowerItem class, then gives it to the player that ran the command.
		 * 
		 * The first argument is the ID of the PowerItem
		 * The second argument is the material of the item
		 * The third argument is the amount of damage the item should do
		 * The fourth argument is the rarity of the item, and everything after that is the name of the item
		 */
    	else if (cmd.getName().equalsIgnoreCase("createitem")) {
			if (args.length >= 5) {
				StringBuilder itemNameBuilder = new StringBuilder(args[4]);
            	for (int arg = 5; arg < args.length; arg++) {
              		itemNameBuilder.append(" ").append(args[arg]);
           		}
				String builtItemName = itemNameBuilder.toString();
				builtItemName = ChatColor.translateAlternateColorCodes('&', builtItemName);
				if(sender instanceof Player) {
            		Player p = (Player) sender;
					Material material = Material.matchMaterial(args[1]);
					int damage;
					if (material == null) {
						sender.sendMessage(ChatColor.RED + "Couldn't recognise the material: " + args[1]);
						return true;
					}
					try {
						damage = Integer.parseInt(args[2]);
					} catch (Exception e) {
						sender.sendMessage(ChatColor.RED + "\"" + args[2] + "\" is not a valid number");
						return true;
					}
					if (material.isItem()) {
						PowerItem commandItem = new PowerItem(args[0], material, damage, args[3], builtItemName);
						commandItem.giveTo(p);
						saveItemData(commandItem);
						return true;
					}
					else {
						sender.sendMessage(ChatColor.RED + material.toString() + " is not an item.");
					}
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
		/*
		 * Gets a PowerItem from the PowerItems config file and gives it to the player who ran the command.
		 * 
		 * The first arguement is the ID of the PowerItem.
		 */
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
						Material material = Material.matchMaterial(itemMaterial);
						int intDamage;
						if (material == null) {
							sender.sendMessage(ChatColor.RED + "Couldn't recognise the material: " + args[1]);
							return true;
						}
						try {
							intDamage = Integer.parseInt(damage);
						} catch (Exception e) {
							sender.sendMessage(ChatColor.RED + "\"" + args[2] + "\" is not a valid number");
							return true;
						}
						itemName = ChatColor.translateAlternateColorCodes('&', itemName);
						if (material.isItem()) {
							PowerItem getItem = new PowerItem(args[0], material, intDamage, itemRarity, itemName);
							getItem.giveTo(p);
						}
						else {
							sender.sendMessage(ChatColor.RED + material.toString() + " is not an item.");
							return true;
						}
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
		/*
		 * Manages command triggers on an item (it doesn't have to be a PowerItem).
		 */
		else if (cmd.getName().equalsIgnoreCase("commandtrigger")) {
			if (args.length > 0) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (args[0].equalsIgnoreCase("add")) {
						if (args.length < 3) {
							return false;
						}
						if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
							sender.sendMessage(ChatColor.RED + "You are not holding an item!");
							return true;
						}
						else {
							if (!args[1].equalsIgnoreCase("chat") && !args[1].equalsIgnoreCase("command")) {
								return false;
							}
							// The first string should either be "chat" or "command", player.chat() will force the player to send a chat message. player.performCommand() will force the player to run a command, do not include the forward slash.
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
						if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
							sender.sendMessage(ChatColor.RED + "You are not holding an item!");
							return true;
						}
						PersistantDataContainerUtils.setAsString(p.getInventory().getItemInMainHand(), "");
						return true;
					}
					else if (args[0].equalsIgnoreCase("list")) {
						if (p.getInventory().getItemInMainHand() == null || p.getInventory().getItemInMainHand().getType() == Material.AIR) {
							sender.sendMessage(ChatColor.RED + "You are not holding an item!");
							return true;
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
				// Reloads the PowerItems config file.
				if (args[0].equalsIgnoreCase("reload")) {
					this.reloadConfig();
					sender.sendMessage(ChatColor.GREEN + "Config reloaded!");
					return true;
				}
				// Gets some information about the currently installed version of PowerItems.
				else if (args[0].equalsIgnoreCase("version")) {
					ChatColor colour = isOutdated ? ChatColor.RED : ChatColor.GREEN;
					sender.sendMessage(ChatColor.GRAY + "--------------------------------------------");
					sender.sendMessage("PowerItems Version: " + getDescription().getVersion());
					sender.sendMessage("Latest PowerItems Version: " + latestVersion);
					sender.sendMessage("");
					sender.sendMessage("Server Version: " + getServer().getVersion());
					sender.sendMessage("");
					sender.sendMessage("PowerItems Outdated: " + colour + ChatColor.BOLD + String.valueOf(isOutdated).toUpperCase());
					sender.sendMessage(ChatColor.GRAY + "--------------------------------------------");
					return true;
				}
			}
			else {
				return false;
			}
		}
    	return false; 
    }
	// This gets the information about the PowerItem and saves it to the plugin's config file
	public void saveItemData(PowerItem createdItem) {
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemName", createdItem.getItemName().replace('§', '&'));
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemMaterial", createdItem.getItemMaterial().toString());
		this.getConfig().set("items." + createdItem.getInternalName() + ".damage", createdItem.getDamage());
		this.getConfig().set("items." + createdItem.getInternalName() + ".itemRarity", createdItem.getRarity().replace('§', '&'));
		this.saveConfig();
	}
	public void registerDefaultRarities() {
		String common = ChatColor.BOLD + "COMMON";
		common = ChatColor.WHITE + common;
		RarityManager.registerRarity("common", common);

		String uncommon = ChatColor.BOLD + "UNCOMMON";
		uncommon = ChatColor.GREEN + uncommon;
		RarityManager.registerRarity("uncommon", uncommon);

		String rare = ChatColor.BOLD + "RARE";
		rare = ChatColor.BLUE + rare;
		RarityManager.registerRarity("rare", rare);

		String epic = ChatColor.BOLD + "EPIC";
		epic = ChatColor.DARK_PURPLE + epic;
		RarityManager.registerRarity("epic", epic);

		String legendary = ChatColor.BOLD + "LEGENDARY";
		legendary = ChatColor.GOLD + legendary;
		RarityManager.registerRarity("legendary", legendary);

		String mythic = ChatColor.BOLD + "MYTHIC";
		mythic = ChatColor.LIGHT_PURPLE + mythic;
		RarityManager.registerRarity("mythic", mythic);
	}
	public void unregisterDefaultRarities() {
		RarityManager.unregisterRarity("common");
		RarityManager.unregisterRarity("uncommon");
		RarityManager.unregisterRarity("rare");
		RarityManager.unregisterRarity("epic");
		RarityManager.unregisterRarity("legendary");
		RarityManager.unregisterRarity("mythic");
	}
}
