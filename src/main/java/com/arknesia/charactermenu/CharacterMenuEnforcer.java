package com.arknesia.charactermenu;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CharacterMenuEnforcer extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    private boolean shouldForceMenu(Player player) {
        String result = PlaceholderAPI.setPlaceholders(player, "%servervariables_value_hascharacter%");
        return result.equalsIgnoreCase("none");
    }

    private void openCharacterMenu(Player player) {
        String command = "dm open characters " + player.getName();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (shouldForceMenu(player)) {
                openCharacterMenu(player);
            }
        }, 20L); // delay 1 detik
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Bukkit.getScheduler().runTaskLater(this, () -> {
            if (shouldForceMenu(player)) {
                openCharacterMenu(player);
            }
        }, 10L); // delay sedikit setelah menutup
    }

}
