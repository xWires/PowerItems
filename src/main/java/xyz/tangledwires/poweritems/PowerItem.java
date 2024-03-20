package xyz.tangledwires.poweritems;

import java.util.ArrayList;
import java.util.Map;

//import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import xyz.tangledwires.poweritems.utils.AttributeUtils;
/**
 * This class represents a PowerItem that can be given to a player.
 * You can give a PowerItem to a player by using the {@link xyz.tangledwires.poweritems.PowerItem#giveTo(Player)} function.
 */
public class PowerItem {
	private int damage;
	private String rarity;
	private String internalName;
	private String itemName;
	private Material itemMaterial;
	private ItemStack is;
	/**
	 * This is the constructor for the {@link xyz.tangledwires.poweritems.PowerItem} class.
	 * When creating an instance of the PowerItem class, you must pass the following parameters:
	 * 
	 * @param internalName The ID of the PowerItem, it should be unique as it is used in the config of PowerItems to seperate different PowerItems from each other.
	 * @param itemMaterial The material that is used for the ItemStack inside the PowerItem instance.
	 * @param damage How much damage the PowerItem should deal when attacking.
	 * @param rarity The rarity text displayed in the item's lore.
	 * @param itemName The name of the item.
	 */
	PowerItem(String internalName, Material itemMaterial, int damage, String rarity, String itemName) {
		ItemStack is = new ItemStack(itemMaterial);
		setItemStack(is);
		setInternalName(internalName);
		setDamage(damage);
		setRarity(rarity);
		setItemMaterial(itemMaterial);
		setName(itemName);
		restoreDefaultAttributes();
		setCustomLore();
	}
	/**
	 * Used to set the name of the ItemStack inside the PowerItem instance.
	 * 
	 * @param name The new name of the item.
	 * @return The ItemStack with the new name.
	 */
	public ItemStack setName(String name) {
		this.itemName = name;
        ItemMeta m = is.getItemMeta();
        m.setDisplayName(name);
        is.setItemMeta(m);
        return is;
    }
	/**
	 * Sets the lore for the PowerItem, including the amount of damage that the item deals and the rarity.
	 * Any existing lore will be cleared before the new lore is added.
	 * 
	 * @return The updated ItemStack with the lore added
	 */
	public ItemStack setCustomLore() {
		ItemMeta m = is.getItemMeta();
		ArrayList<String> lore = new ArrayList<String>();
		String damage = ChatColor.GRAY + "Damage: ";
		damage = damage + ChatColor.RED;
		damage = damage + "+" + getDamage();
		lore.add(damage);
		lore.add("");
		switch (rarity) {
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
				rarity = ChatColor.translateAlternateColorCodes('&', rarity);
				lore.add(rarity);
				break;
		}
		m.setLore(lore);
		is.setItemMeta(m);
		return is;
	}
	/**
	 * This method restores the original attack speed attributes to the item.
	 * When the attack damage attribute is added, it clears the default attack speed attribute, which means that this method is required to restore it.
	 * <p>
	 * The default attack speed is obtained from {@link xyz.tangledwires.poweritems.utils.AttributeUtils#getDefaultAttackSpeeds()}.
	 * 
	 * @return The ItemStack with its default attack speed restored.
	 */
	public ItemStack restoreDefaultAttributes() {
		ItemMeta meta = is.getItemMeta();
		Material material = is.getType();
		Map<Material, AttributeModifier> attributeMap = AttributeUtils.getDefaultAttackSpeeds();
		if (attributeMap.containsKey(material)) {
			meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attributeMap.get(material));
			is.setItemMeta(meta);
			return is;
		}
		return is;
	}
	/**
	 * Updates the amount of damage the PowerItem deals when attacking.
	 * <p>
	 * After this method is called, you should also call {@link xyz.tangledwires.poweritems.PowerItem#setCustomLore()} to update the lore of the item.
	 * 
	 * @param damage The amount of damage the item should do
	 * @return The ItemStack with the updated attack damage attribute.
	 */
	public ItemStack setDamage(int damage) {
		this.damage = damage;
		ItemMeta m = is.getItemMeta();
		m.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier("generic.attackDamage", damage, Operation.ADD_NUMBER));
		m.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		is.setItemMeta(m);
		return is;
	}
	/**
	 * Returns the amount of damage the PowerItem deals when attacking.
	 */
	public int getDamage() {
		return damage;
	}
	/**
	 * Returns the ID of the PowerItem instance.
	 */
	public String getInternalName() {
		return internalName;
	}
	/**
	 * Returns the rarity of the PowerItem.
	 */
	public String getRarity() {
		return rarity;
	}
	/**
	 * Updates the rarity of the PowerItem.
	 * <p>
	 * After this method is called, you should also call {@link xyz.tangledwires.poweritems.PowerItem#setCustomLore()} to update the lore of the item.
	 * 
	 * @param rarity The new rarity of the item.
	 */
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	/**
	 * Returns the name of the PowerItem.
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * Returns the material of the PowerItem.
	 */
	public Material getItemMaterial() {
		return itemMaterial;
	}
	/**
	 * Returns the ItemStack that represents the PowerItem.
	 */
	public ItemStack getItemStack() {
		return is;
	}
	/**
	 * Updates the ItemStack inside the PowerItem instance
	 * 
	 * @param is The new ItemStack
	 */
	public void setItemStack(ItemStack is) {
		this.is = is;
	}
	/**
	 * Gives the PowerItem to the specified player.
	 * 
	 * @param player The player to give the PowerItem to.
	 */
	public void giveTo(Player player) {
		player.getInventory().addItem(getItemStack());
	}
	private void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	private void setItemMaterial(Material itemMaterial) {
		this.itemMaterial = itemMaterial;
	}
}
