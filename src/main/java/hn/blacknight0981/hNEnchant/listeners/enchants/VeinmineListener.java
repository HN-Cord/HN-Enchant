package hn.blacknight0981.hNEnchant.listeners.enchants;

import hn.blacknight0981.hNEnchant.data.enchants.VeinmineEnchant;
import hn.blacknight0981.hNEnchant.utils.material.VeinmineMaterial;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class VeinmineListener implements Listener {

    private final Registry<Enchantment> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);
    private final Enchantment veinmine = registry.get(VeinmineEnchant.KEY);

    private final Set<UUID> currentlyBreaking = new HashSet<>(); // 標記

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onBreak(BlockBreakEvent event) {
        if (veinmine == null) return;
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (!tool.containsEnchantment(veinmine)) return;
        if (currentlyBreaking.contains(uuid)) return;

        if (player.isSneaking()) return; // 蹲下破壞 則取消事件

        Block initialBlock = event.getBlock(); // 被破坏的初始方块
        Material targetType = initialBlock.getType(); // 获取初始方块的类型（如矿石类型）
        if (!VeinmineMaterial.isMaterial(targetType)) return;
        currentlyBreaking.add(uuid);

        try {
            Set<Block> blocksToBreak = getVeinBlocks(initialBlock, targetType, 50);
            for (Block block: blocksToBreak) {
                player.breakBlock(block);
            }
        } finally {
            currentlyBreaking.remove(uuid);
        }
    }

    private Set<Block> getVeinBlocks(Block startBlock, Material targetType, int limit) {
        Set<Block> result = new HashSet<>();
        Queue<Block> toCheck = new LinkedList<>();
        toCheck.add(startBlock);

        while (!toCheck.isEmpty() && result.size() < limit) {
            Block current = toCheck.poll();
            if (current == null || result.contains(current)) continue;
            if (current.getType() != targetType) continue;

            result.add(current);

            // 添加相鄰方塊
            for (BlockFace face : BlockFace.values()) {
                Block neighbor = current.getRelative(face);
                if (!result.contains(neighbor)) {
                    toCheck.add(neighbor);
                }
            }
        }
        return result;
    }
}
