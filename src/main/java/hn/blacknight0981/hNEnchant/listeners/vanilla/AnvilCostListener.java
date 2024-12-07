package hn.blacknight0981.hNEnchant.listeners.vanilla;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;

@SuppressWarnings("UnstableApiUsage")
public class AnvilCostListener implements Listener {

    private static final int MAX_COST = 1024;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilView anvilView = event.getView();
        ItemStack result = event.getResult();

        if (result == null || result.getType() == Material.AIR) return;

        anvilView.setMaximumRepairCost(MAX_COST);
        anvilView.getPlayer().sendMessage(Component.text("當前附魔花費: " + anvilView.getRepairCost()));
    }
}
