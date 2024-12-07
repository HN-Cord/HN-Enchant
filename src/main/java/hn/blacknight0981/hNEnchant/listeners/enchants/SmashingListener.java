package hn.blacknight0981.hNEnchant.listeners.enchants;

import hn.blacknight0981.hNEnchant.data.enchants.SmashingEnchant;
import hn.blacknight0981.hNEnchant.utils.material.SmashingMaterial;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class SmashingListener implements Listener {

    private final Registry<Enchantment> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);
    private final Enchantment smashing = registry.get(SmashingEnchant.KEY);

    private final Map<Location, Material> blockTypeMap = new HashMap<>();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (smashing == null) return;
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (!tool.containsEnchantment(smashing)) return;

        Block block = event.getBlock();
        blockTypeMap.put(block.getLocation(), block.getType());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onDrop(BlockDropItemEvent event) {
        if (smashing == null) return;
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (!tool.containsEnchantment(smashing)) return;

        Block block = event.getBlock();
        BlockState blockState = event.getBlockState();
        if (blockState instanceof BlockInventoryHolder) return; // 略過容器

        Material smashedMaterial = SmashingMaterial.getMaterial(blockTypeMap.get(block.getLocation()));
        if (smashedMaterial == null) return; // 轉換檢查


        for (Item item : event.getItems()) {
            int amount = item.getItemStack().getAmount();
            ItemStack smashedItem = new ItemStack(smashedMaterial, amount);
            item.setItemStack(smashedItem);
        }

        blockTypeMap.remove(block.getLocation());
    }
}
