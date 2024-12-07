package hn.blacknight0981.hNEnchant.config;

import hn.blacknight0981.hNEnchant.data.enchants.*;
import hn.blacknight0981.hNEnchant.utils.EnchantioEnchant;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.keys.tags.ItemTypeTagKeys;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Config {

    public static final Map<Key, EnchantioEnchant> ENCHANTS = new HashMap<>();

    public Config(Path filePath) throws IOException {
        File file = filePath.toFile();
        if (!file.exists()) {
            file.mkdirs();
        }

        File configFile = new File(filePath.toFile(), "enchant_config.yml");
        configFile.createNewFile();

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);

        ConfigurationSection enchantsSection = getConfigSection(configuration, "enchants");

        // 粉碎
        ConfigurationSection smashingSection = getConfigSection(enchantsSection,"smashing");
        SmashingEnchant.create(smashingSection);

        // 挖掘
        ConfigurationSection excavating = getConfigSection(enchantsSection,"excavating");
        ExcavatingEnchant.create(excavating);

        // 冶煉
        ConfigurationSection smeltingSection = getConfigSection(enchantsSection,"smelting");
        SmeltingEnchant.create(smeltingSection);

        // 連鎖挖礦
        ConfigurationSection veinmineSection = getConfigSection(enchantsSection,"veinmine");
        VeinmineEnchant.create(veinmineSection);

        // 靈魂綁定
        ConfigurationSection soulSection = getConfigSection(enchantsSection,"soulbound");
        SoulboundEnchant.create(soulSection);

        configuration.save(configFile);
    }

    public static List<String> getStringList(ConfigurationSection section, String key, List<String> defaultValue) {
        List<String> list = section.contains(key) ? section.getStringList(key) : null;
        if (list == null) {
            section.set(key, defaultValue);
            return defaultValue;
        }
        return list;
    }

    public static int getInt(ConfigurationSection section, String key, int defaultValue) {
        int value = section.contains(key) ? section.getInt(key) : -1;
        if (value == -1) {
            section.set(key, defaultValue);
            return defaultValue;
        }
        return value;
    }

    public static double getDouble(ConfigurationSection section, String key, double defaultValue) {
        double value = section.contains(key) ? section.getDouble(key) : -1;
        if (value == -1) {
            section.set(key, defaultValue);
            return defaultValue;
        }
        return value;
    }

    public static boolean getBoolean(ConfigurationSection section, String key, boolean defaultValue) {
        boolean value = section.contains(key) && section.getBoolean(key);
        if (!value) {
            section.set(key, defaultValue);
            return defaultValue;
        }
        return value;
    }

    public static Set<TagEntry<ItemType>> getTagsFromList(@NotNull List<String> tags) {
        Set<TagEntry<ItemType>> supportedItemTags = new HashSet<>();
        for (String itemTag : tags) {
            if (itemTag == null) continue;
            if (itemTag.startsWith("#")) {
                itemTag = itemTag.substring(1);
                try {
                    Key key = Key.key(itemTag);
                    TagKey<ItemType> tagKey = ItemTypeTagKeys.create(key);
                    TagEntry<ItemType> tagEntry = TagEntry.tagEntry(tagKey);
                    supportedItemTags.add(tagEntry);
                } catch (IllegalArgumentException e) {
                }
                continue;
            }
            try {
                Key key = Key.key(itemTag);
                TypedKey<ItemType> typedKey = TypedKey.create(RegistryKey.ITEM, key);
                TagEntry<ItemType> tagEntry = TagEntry.valueEntry(typedKey);
                supportedItemTags.add(tagEntry);
            } catch (IllegalArgumentException | NullPointerException e) {
            }
        }
        return supportedItemTags;
    }

    public static ConfigurationSection getConfigSection(ConfigurationSection section, String key) {
        ConfigurationSection value = section.getConfigurationSection(key);
        if (value == null) {
            value = section.createSection(key);
        }
        return value;
    }
}
