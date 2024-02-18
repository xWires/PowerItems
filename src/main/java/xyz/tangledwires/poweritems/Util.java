package xyz.tangledwires.poweritems;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;

public class Util {
    private static HashMap<Material, AttributeModifier> defaultAttackSpeeds = new HashMap<Material, AttributeModifier>();
    
    static {
        // To get the amount, take the default modifier, make it negative and remove 0.8
        defaultAttackSpeeds.put(Material.WOODEN_SWORD, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.6), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.STONE_SWORD, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.6), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.IRON_SWORD, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.6), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.GOLDEN_SWORD, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.6), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.DIAMOND_SWORD, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.6), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.NETHERITE_SWORD, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.6), Operation.ADD_NUMBER));

        defaultAttackSpeeds.put(Material.WOODEN_AXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(0.8), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.STONE_AXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(0.8), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.IRON_AXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(0.9), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.GOLDEN_AXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.DIAMOND_AXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.NETHERITE_AXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));

        defaultAttackSpeeds.put(Material.WOODEN_PICKAXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.2), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.STONE_PICKAXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.2), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.IRON_PICKAXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.2), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.GOLDEN_PICKAXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.2), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.DIAMOND_PICKAXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.2), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.NETHERITE_PICKAXE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.2), Operation.ADD_NUMBER));

        defaultAttackSpeeds.put(Material.WOODEN_SHOVEL, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.STONE_SHOVEL, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.IRON_SHOVEL, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.GOLDEN_SHOVEL, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.DIAMOND_SHOVEL, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.NETHERITE_SHOVEL, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));

        defaultAttackSpeeds.put(Material.WOODEN_HOE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.STONE_HOE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(2), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.IRON_HOE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(3), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.GOLDEN_HOE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.DIAMOND_HOE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(4), Operation.ADD_NUMBER));
        defaultAttackSpeeds.put(Material.NETHERITE_HOE, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(4), Operation.ADD_NUMBER));

        defaultAttackSpeeds.put(Material.TRIDENT, new AttributeModifier("generic.attackSpeed", convertAttributeModifier(1.1), Operation.ADD_NUMBER));
    }

    private Util() {}

    public static Map<Material, AttributeModifier> getDefaultAttackSpeeds() {
        return defaultAttackSpeeds;
    }

    public static double convertAttributeModifier(double m) {
        m *= -1;
        m -= 0.8;
        return m;
    }
}
