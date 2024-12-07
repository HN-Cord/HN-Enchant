package hn.blacknight0981.hNEnchant.utils.material;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class SmashingMaterial {
    private static final Map<Material, Material> MATERIALS = new HashMap<>();

    static {
        MATERIALS.put(Material.COBBLESTONE, Material.GRAVEL); // 鵝卵石 礫石
        MATERIALS.put(Material.GRAVEL, Material.SAND); // 礫石 沙
    }

    public static Material getMaterial(Material material) {
        return MATERIALS.get(material);
    }
}
