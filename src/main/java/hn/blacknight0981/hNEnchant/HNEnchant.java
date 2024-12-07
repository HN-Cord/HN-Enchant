package hn.blacknight0981.hNEnchant;

import hn.blacknight0981.hNEnchant.config.Config;
import hn.blacknight0981.hNEnchant.data.enchants.*;
import hn.blacknight0981.hNEnchant.listeners.enchants.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class HNEnchant extends JavaPlugin {

    @Override
    public void onEnable() {
        initEnchantListeners();
    }

    @Override
    public void onDisable() {

    }

    public void initEnchantListeners() {
        getLogger().info("註冊 附魔 監聽器...");
        if (Config.ENCHANTS.containsKey(ExcavatingEnchant.KEY)) {
            getServer().getPluginManager().registerEvents(new ExcavatingListener(), this);
        }
        if (Config.ENCHANTS.containsKey(SmashingEnchant.KEY)) {
            getServer().getPluginManager().registerEvents(new SmashingListener(), this);
        }
        if (Config.ENCHANTS.containsKey(SmeltingEnchant.KEY)) {
            getServer().getPluginManager().registerEvents(new SmeltingListener(), this);
        }
        if (Config.ENCHANTS.containsKey(VeinmineEnchant.KEY)) {
            getServer().getPluginManager().registerEvents(new VeinmineListener(), this);
        }
        if (Config.ENCHANTS.containsKey(SoulboundEnchant.KEY)) {
            getServer().getPluginManager().registerEvents(new SoulboundListener(), this);
        }
    }
}
