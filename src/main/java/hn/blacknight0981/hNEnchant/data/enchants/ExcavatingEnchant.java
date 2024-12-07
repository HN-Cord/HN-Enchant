package hn.blacknight0981.hNEnchant.data.enchants;

import hn.blacknight0981.hNEnchant.config.Config;
import hn.blacknight0981.hNEnchant.utils.EnchantioEnchant;
import io.papermc.paper.registry.data.EnchantmentRegistryEntry;
import io.papermc.paper.registry.keys.tags.EnchantmentTagKeys;
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
public class ExcavatingEnchant implements EnchantioEnchant {

    public static final Key KEY = Key.key("craft:excavating");

    private final int anvilCost, weight, maxLevel;
    private final EnchantmentRegistryEntry.EnchantmentCost minimumCost;
    private final EnchantmentRegistryEntry.EnchantmentCost maximumCost;
    private final Set<TagEntry<ItemType>> supportedItemTags;
    private final Set<TagKey<Enchantment>> enchantTagKeys = new HashSet<>();

    public ExcavatingEnchant(
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

    public static ExcavatingEnchant create(ConfigurationSection excavating) {
        ExcavatingEnchant excavatingEnchant = new ExcavatingEnchant(
                Config.getInt(excavating, "anvilCost", 9),
                Config.getInt(excavating, "weight", 1),
                Config.getInt(excavating, "maxLevel", 1),
                EnchantmentRegistryEntry.EnchantmentCost.of(
                        Config.getInt(excavating, "minimumCost.base", 25),
                        Config.getInt(excavating, "maximumCost.additionalPerLevel", 3)
                ),
                EnchantmentRegistryEntry.EnchantmentCost.of(
                        Config.getInt(excavating, "maximumCost.base", 65),
                        Config.getInt(excavating, "maximumCost.additionalPerLevel", 1)
                ),
                Config.getBoolean(excavating, "canGetFromEnchantingTable", true),
                Config.getTagsFromList(Config.getStringList(
                        excavating,
                        "supportedItemTags",
                        List.of(
                                "#pickaxes",
                                "#shovels"
                        )
                ))
        );

        if (Config.getBoolean(excavating, "enabled", true)) {
            Config.ENCHANTS.put(ExcavatingEnchant.KEY, excavatingEnchant);
        }

        return excavatingEnchant;
    }

    @Override
    public @NotNull Key getKey() {
        return KEY;
    }

    @Override
    public @NotNull Component getDescription() {
        return Component.translatable("hn.craft.enchant.excavating", "挖掘");
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
}
