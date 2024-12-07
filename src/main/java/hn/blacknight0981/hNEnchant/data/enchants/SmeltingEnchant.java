package hn.blacknight0981.hNEnchant.data.enchants;

import hn.blacknight0981.hNEnchant.config.Config;
import hn.blacknight0981.hNEnchant.utils.EnchantioEnchant;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
import io.papermc.paper.registry.set.RegistryKeySet;
import io.papermc.paper.registry.set.RegistrySet;
import io.papermc.paper.registry.tag.TagKey;
import io.papermc.paper.tag.TagEntry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class SmeltingEnchant implements EnchantioEnchant {

    public static final Key KEY = Key.key("craft:smelting");

    private final int anvilCost, weight, maxLevel;
    private final EnchantmentRegistryEntry.EnchantmentCost minimumCost;
    private final EnchantmentRegistryEntry.EnchantmentCost maximumCost;
    private final Set<TagEntry<ItemType>> supportedItemTags;
    private final Set<TagKey<Enchantment>> enchantTagKeys = new HashSet<>();

    public SmeltingEnchant(
            int anvilCost,
            int weight,
            int maxLevel,
            EnchantmentRegistryEntry.EnchantmentCost minimumCost,
            EnchantmentRegistryEntry.EnchantmentCost maximumCost,
            boolean canGetFromEnchantingTable,
            Set<TagEntry<ItemType>> supportedItemTags
    ) {
        this.anvilCost = anvilCost;
        this.weight = weight;
        this.maxLevel = maxLevel;
        this.minimumCost = minimumCost;
        this.maximumCost = maximumCost;
        this.supportedItemTags = supportedItemTags;
        if (canGetFromEnchantingTable) {
            enchantTagKeys.add(EnchantmentTagKeys.IN_ENCHANTING_TABLE);
        }
    }

    public static SmeltingEnchant create(ConfigurationSection smelting) {
        SmeltingEnchant smeltingEnchant = new SmeltingEnchant(
                Config.getInt(smelting, "anvilCost", 5),
                Config.getInt(smelting, "weight", 2),
                Config.getInt(smelting, "maxLevel", 1),
                EnchantmentRegistryEntry.EnchantmentCost.of(
                        Config.getInt(smelting, "minimumCost.base", 25),
                        Config.getInt(smelting, "maximumCost.additionalPerLevel", 3)
                ),
                EnchantmentRegistryEntry.EnchantmentCost.of(
                        Config.getInt(smelting, "maximumCost.base", 65),
                        Config.getInt(smelting, "maximumCost.additionalPerLevel", 1)
                ),
                Config.getBoolean(smelting, "canGetFromEnchantingTable", true),
                Config.getTagsFromList(Config.getStringList(
                        smelting,
                        "supportedItemTags",
                        List.of(
                                "#pickaxes",
                                "#shovels"
                        )
                ))
        );


        if (Config.getBoolean(smelting, "enabled", true)) {
            Config.ENCHANTS.put(SmeltingEnchant.KEY, smeltingEnchant);
        }
        return smeltingEnchant;
    }

    @Override
    public @NotNull Key getKey() {
        return KEY;
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.translatable("hn.craft.enchant.smelting", "冶煉");
    }

    @Override
    public int getAnvilCost() {
        return anvilCost;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public @NotNull EnchantmentRegistryEntry.EnchantmentCost getMinimumCost() {
        return minimumCost;
    }

    @Override
    public @NotNull EnchantmentRegistryEntry.EnchantmentCost getMaximumCost() {
        return maximumCost;
    }

    @Override
    public @NotNull Iterable<EquipmentSlotGroup> getActiveSlots() {
        return Set.of(EquipmentSlotGroup.MAINHAND);
    }

    @Override
    public @NotNull Set<TagEntry<ItemType>> getSupportedItems() {
        return supportedItemTags;
    }

    @Override
    public @NotNull Set<TagKey<Enchantment>> getEnchantTagKeys() {
        return Collections.unmodifiableSet(enchantTagKeys);
    }

    @Override
    public RegistryKeySet<@NotNull Enchantment> getExclusiveWith() {
        return RegistrySet.keySet(RegistryKey.ENCHANTMENT,
                TypedKey.create(RegistryKey.ENCHANTMENT, Key.key("craft:smashing"))
        );
    }
}
