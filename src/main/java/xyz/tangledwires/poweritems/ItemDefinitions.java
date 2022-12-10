package xyz.tangledwires.poweritems;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemDefinitions {
	private String hitDamageValue;
	private String itemRarityType;
	private String internalName;
	private String itemName;
	private String itemMaterial;
	ItemDefinitions(String internalName, String itemMaterial, String damageValue, String rarity, String itemName) {
		setInternalName(internalName);
		setHitDamageValue(damageValue);
		setItemRarityType(rarity);
		setItemMaterial(itemMaterial);
		setItemName(itemName);
		Material material = Material.matchMaterial(itemMaterial);
		if (material == null) {
			Bukkit.getServer().getLogger().severe("Tried to create an item with an unknown material!");
		}
		else {
			ItemStack is = new ItemStack(material);
			setName(is, itemName);
			setCustomLore(is);
			setDamageValue(is, damageValue);
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.getInventory().addItem(is);
			}
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
		lore.add("§r§7Damage: " + "§c+" + getHitDamageValue());
		lore.add("");
		switch (itemRarityType) {
			case "common" :
				lore.add("§r§f§lCOMMON");
				break;
			case "uncommon" :
				lore.add("§r§a§lUNCOMMON");
				break;
			case "rare" :
				lore.add("§r§9§lRARE");
				break;
			case "epic" :
				lore.add("§r§5§lEPIC");
				break;
			case "legendary" :
				lore.add("§r§6§lLEGENDARY");
				break;
			case "mythic" :
				lore.add("§r§d§lMYTHIC");
				break;
			default:
				lore.add("UNKNOWN");
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
}
