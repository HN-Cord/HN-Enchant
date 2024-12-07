package hn.blacknight0981.hNEnchant.listeners.enchants;

import hn.blacknight0981.hNEnchant.data.enchants.SmeltingEnchant;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SmeltingListener implements Listener {

    private final Registry<Enchantment> registry = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT);
    private final Enchantment smelting = registry.get(SmeltingEnchant.KEY);
    private final Map<ItemStack, ItemStack> smeltingCache = new HashMap<>();

    //TODO: 後續為了 冶煉成功添加 火焰粒子特效
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onDrop(BlockDropItemEvent event) {
        if (smelting == null) return;
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (!tool.containsEnchantment(smelting)) return;

        BlockState blockState = event.getBlock().getState();
        if (blockState instanceof BlockInventoryHolder) return;

        for (Item item : event.getItems()) {
            int amount = item.getItemStack().getAmount();
            ItemStack smeltedItem = getSmeltedItem(item.getItemStack());
            if (smeltedItem == null) continue;
            item.setItemStack(smeltedItem.asQuantity(amount));

            Block block = event.getBlock();
            World world = block.getWorld();
            Location location = block.getLocation();

            world.spawnParticle(
                    Particle.FLAME,
                    location.add(0.5, 0.5, 0.5),
                    3,
                    0.2, 0.5, 0.2,
                    0.01
            );
        }
    }

    private ItemStack getSmeltedItem(@NotNull ItemStack itemStack) {
        ItemStack singleItem = itemStack.asOne();
        if (smeltingCache.containsKey(singleItem)) return smeltingCache.get(singleItem);

        for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext();) {
            Recipe recipe = it.next();
            if (!(recipe instanceof FurnaceRecipe furnaceRecipe)) continue;
            if (!furnaceRecipe.getInputChoice().test(singleItem)) continue;
            ItemStack result = furnaceRecipe.getResult();
            smeltingCache.put(singleItem, result);
            return result;
        }
        return null;
    }
}
