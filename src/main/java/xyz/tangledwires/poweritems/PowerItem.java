package xyz.tangledwires.poweritems;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PowerItem {
	private String hitDamageValue;
	private String itemRarityType;
	private String internalName;
	private String itemName;
	private String itemMaterial;
	private ItemStack is;
	PowerItem(String internalName, String itemMaterial, String damageValue, String rarity, String itemName) {
		setInternalName(internalName);
		setHitDamageValue(damageValue);
		setItemRarityType(rarity);
		setItemMaterial(itemMaterial);
		setItemName(itemName);
		Material material = Material.matchMaterial(itemMaterial);
		if (material == null) {
			Bukkit.getServer().getLogger().severe("[PowerItems] Tried to create an item with an unknown material: " + itemMaterial);
		}
		else {
			ItemStack is = new ItemStack(material);
			setName(is, itemName);
			setCustomLore(is);
			setDamageValue(is, damageValue);
			setItemStack(is);
		}
	}
	public ItemStack setName(ItemStack is, String name){
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
        return is;
    }
	public ItemStack setCustomLore(ItemStack is) {
		ItemMeta m = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		String damage = ChatColor.GRAY + "Damage: ";
		damage = damage + ChatColor.RED;
		damage = damage + "+" + getHitDamageValue();
		lore.add(damage);
		lore.add("");
		String rarity;
		switch (itemRarityType) {
			case "common" :
				rarity = ChatColor.BOLD + "COMMON";
				rarity = ChatColor.WHITE + rarity;
				lore.add(rarity);
				break;
			case "uncommon" :
				rarity = ChatColor.BOLD + "UNCOMMON";
				rarity = ChatColor.GREEN + rarity;
				lore.add(rarity);
				break;
			case "rare" :
				rarity = ChatColor.BOLD + "RARE";
				rarity = ChatColor.BLUE + rarity;
				lore.add(rarity);
				break;
			case "epic" :
				rarity = ChatColor.BOLD + "EPIC";
				rarity = ChatColor.DARK_PURPLE + rarity;
				lore.add(rarity);
				break;
			case "legendary" :
				rarity = ChatColor.BOLD + "LEGENDARY";
				rarity = ChatColor.GOLD + rarity;
				lore.add(rarity);
				break;
			case "mythic" :
				rarity = ChatColor.BOLD + "MYTHIC";
				rarity = ChatColor.LIGHT_PURPLE + rarity;
				lore.add(rarity);
				break;
			default:
				rarity = ChatColor.translateAlternateColorCodes("&".charAt(0), itemRarityType);
				lore.add(rarity);
				break;
		}
		m.setLore(lore);
		is.setItemMeta(m);
		return is;
	}
	public ItemStack setDamageValue(ItemStack is, String damageValue) {
		int intDamageValue = Integer.parseInt(damageValue);
		ItemMeta m = is.getItemMeta();
		m.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", intDamageValue, Operation.ADD_NUMBER));
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is.setItemMeta(m);
		return is;
	}
	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	public String getItemRarityType() {
		return itemRarityType;
	}
	public void setItemRarityType(String itemRarityType) {
		this.itemRarityType = itemRarityType;
	}
	public String getHitDamageValue() {
		return hitDamageValue;
	}
	public void setHitDamageValue(String hitDamageValue) {
		this.hitDamageValue = hitDamageValue;
	}
	public String toString(String internalName, String name, String material, int damage, String rarity) {
		return "\nInternalName: " + internalName + "\nMaterial: " + material + "\nDamage: " + damage + "\nRarity: " + rarity + "\n" + "\nName: " + name;
	}
	public String getItemName() { return itemName; }
	public void setItemName(String itemName) {this.itemName = itemName;}
	public String getItemMaterial() { return itemMaterial; }
	public void setItemMaterial(String itemMaterial) {this.itemMaterial = itemMaterial;}
	public ItemStack getItemStack() { return is; }
	public void setItemStack(ItemStack is) {this.is = is; }

	public void giveItem(Player player) {
		player.getInventory().addItem(getItemStack());
	}
}
