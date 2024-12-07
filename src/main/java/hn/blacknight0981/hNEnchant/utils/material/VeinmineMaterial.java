package hn.blacknight0981.hNEnchant.utils.material;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public class VeinmineMaterial {
    private static final Set<Material> MATERIALS = new HashSet<>();

    static {
        MATERIALS.add(Material.IRON_ORE);
        MATERIALS.add(Material.DEEPSLATE_IRON_ORE);
        MATERIALS.add(Material.COAL_ORE);
        MATERIALS.add(Material.DEEPSLATE_COAL_ORE);
        MATERIALS.add(Material.DIAMOND_ORE);
        MATERIALS.add(Material.DEEPSLATE_DIAMOND_ORE);
        MATERIALS.add(Material.EMERALD_ORE);
        MATERIALS.add(Material.DEEPSLATE_EMERALD_ORE);
        MATERIALS.add(Material.REDSTONE_ORE);
        MATERIALS.add(Material.DEEPSLATE_REDSTONE_ORE);
        MATERIALS.add(Material.LAPIS_ORE);
        MATERIALS.add(Material.DEEPSLATE_LAPIS_ORE);
        MATERIALS.add(Material.COPPER_ORE);
        MATERIALS.add(Material.DEEPSLATE_COPPER_ORE);
        MATERIALS.add(Material.GOLD_ORE);
        MATERIALS.add(Material.DEEPSLATE_GOLD_ORE);
        MATERIALS.add(Material.NETHER_GOLD_ORE);
        MATERIALS.add(Material.NETHER_QUARTZ_ORE);
        MATERIALS.add(Material.ANCIENT_DEBRIS);
    }

    public static boolean isMaterial(Material material) {
        return MATERIALS.contains(material);
    }
}
