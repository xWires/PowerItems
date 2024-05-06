package xyz.tangledwires.poweritems;

import java.util.HashMap;
import java.util.Map;

/**
 * Used for managing the list of rarities that can be set on a PowerItem.
 */
public class RarityManager {
    private Map<String, String> rarities = new HashMap<String,String>();

    RarityManager() {}
    /**
     * Returns a Map of available rarities.
     * 
     * @return The available rarities.
     */
    public Map<String, String> getRaritiesMap() {
        return rarities;
    }
    /**
     * Gets a rarity from its key.
     * 
     * @param key The key of the rarity to retrieve.
     * @return The text that is shown on a PowerItem when the specified rarity is applied to it.
     */
    public String getRarity(String key) {
        return rarities.get(key);
    }
    /**
     * Registers a rarity so that it can be used on a PowerItem.
     * 
     * @param key The key of the new rarity.
     * @param value The text that should be shown on a PowerItem when the rarity is applied to it.
     * @throws IllegalArgumentException If the key contains spaces, is empty, or if the rarity is already registered.
     */
    public void registerRarity(String key, String value) {
        if (key.contains(" ")) {
            throw new IllegalArgumentException("Rarity key cannot contain spaces.");
        }
        if (key.isBlank()) {
            throw new IllegalArgumentException("Rarity key cannot be empty.");
        }
        if (rarities.containsKey(key)) {
            throw new IllegalArgumentException(key + " is already a registered rarity, if you want to overwrite it, unregister it first.");
        }
        rarities.put(key, value);
    }
    /**
     * Unregisters a rarity so that it is no longer usable on PowerItems.
     * 
     * @param key The key of the rarity to unregister.
     * @throws IllegalArgumentException If the key contains spaces, is empty, or if the rarity is already unregistered.
     */
    public void unregisterRarity(String key) {
        if (key.contains(" ")) {
            throw new IllegalArgumentException("Rarity key cannot contain spaces.");
        }
        if (key.isBlank()) {
            throw new IllegalArgumentException("Rarity key cannot be empty.");
        }
        if (!rarities.containsKey(key)) {
            throw new IllegalArgumentException(key + " is not a registered rarity.");
        }
        rarities.remove(key);
    }
    /**
     * Checks if the given key is a registered rarity.
     * 
     * @param key The key of the rarity to check.
     * @return true if the key is a registered rarity, false if it is not.
     */
    public boolean isRegistered(String key) {
        return rarities.containsKey(key);
    }
}
