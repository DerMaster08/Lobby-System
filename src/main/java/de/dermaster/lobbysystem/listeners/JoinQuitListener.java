package de.dermaster.lobbysystem.listeners;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.MySQL.MySQL;
import de.dermaster.lobbysystem.utils.Config;
import de.dermaster.lobbysystem.utils.Hotbar;
import de.dermaster.lobbysystem.utils.ScoreboardManager;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class JoinQuitListener implements Listener
{
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        e.setJoinMessage(null);
        final Player p = e.getPlayer();
        try {
            if(MySQL.getStateS(p.getName(), "SpawnJoin") == 1) {
                p.teleport(Config.getSpawnLocation());
            }else{
                p.teleport(MySQL.getLocS(p.getName()));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        p.setGameMode(GameMode.SURVIVAL);
        p.setLevel(2022);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 1.0f);
        p.setAllowFlight(true);
        p.setMaxHealth(6);
        p.setHealth(6);
        p.setSaturation(1);
        p.getInventory().clear();
        try {
            if(!MySQL.hasaccount(p.getUniqueId().toString())){
                MySQL.register(p.getUniqueId().toString());
            }
            Hotbar.setItems(p);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                ScoreboardManager.setScoreboard();
            }
        }.runTaskLater(LobbySystem.getInstance(), 1);
        try {
            if (MySQL.getStateS(p.getName(), "JoinFly") == 1) {
                LobbySystem.flyPlayers.add(p.getUniqueId());
                p.setAllowFlight(true);
                p.setFlying(true);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent e) {
        try {
            MySQL.setLocS(e.getPlayer().getName(), e.getPlayer().getLocation());
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
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

}
