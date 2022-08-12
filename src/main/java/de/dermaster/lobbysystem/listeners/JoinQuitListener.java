package de.dermaster.lobbysystem.listeners;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.MySQL.MySQL;
import de.dermaster.lobbysystem.utils.*;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class JoinQuitListener implements Listener
{
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        e.setJoinMessage(null);
        final Player p = e.getPlayer();
        p.teleport(Config.getSpawnLocation());
        p.setGameMode(GameMode.SURVIVAL);
        p.setLevel(2022);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.0f);
        p.sendMessage(LobbySystem.PREFIX + Config.getString("joinMsg"));
        p.setAllowFlight(true);
        p.setMaxHealth(6);
        p.setHealth(6);
        p.setSaturation(1);
        p.getInventory().clear();
        ScoreboardManager.setScoreboard();
        giveItems(p);
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        LobbySystem.flyPlayers.remove(e.getPlayer().getUniqueId());
        LobbySystem.buildPlayers.remove(e.getPlayer().getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                ScoreboardManager.setScoreboard();
            }
        }.runTaskLater(LobbySystem.getInstance(), 10);
        e.setQuitMessage(null);
    }
    private void giveItems(Player p){
        try {
            if(MySQL.getStateS(p.getName(), "Animation") == 1){
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§6Navigator").build());
                    }
                }, 1*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Playerhider.getPlayerHider(p);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }, 2*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(3,  new ItemBuilder(Material.CHEST).setName("§6Cosmetic").build());
                    }
                }, 3*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(5, new ItemBuilder(Material.NETHER_STAR).setName("§6LobbySwitcher").build());
                    }
                }, 4*20L);

                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        GadgetsClass.getGadget(p);
                    }
                }, 5*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setName("§6Profil").setOwner(p.getName()).build());
                    }
                }, 6*20L);
            }else {
                p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§6Navigator").build());
                Playerhider.getPlayerHider(p);
                p.getInventory().setItem(3,  new ItemBuilder(Material.CHEST).setName("§6Cosmetic").build());
                p.getInventory().setItem(5, new ItemBuilder(Material.NETHER_STAR).setName("§6LobbySwitcher").build());
                GadgetsClass.getGadget(p);
                p.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setName("§6Profil").setOwner(p.getName()).build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
