package de.dermaster.lobbysystem.listeners;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.MySQL.MySQLf;
import de.dermaster.lobbysystem.utils.Config;
import de.dermaster.lobbysystem.utils.Hotbar;
import de.dermaster.lobbysystem.utils.Playerhider;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.sql.SQLException;

public class InteractListener implements Listener
{
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        final Player p = e.getPlayer();

        if(e.getItem() == null){e.setCancelled(true); return;}
        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Navigator")) {
            Hotbar.openNavigator(p);
            e.setCancelled(true);
        }else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6TeamServer")){
            Hotbar.openTeam(p);
            e.setCancelled(true);
        } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Fly")) {
            if (LobbySystem.flyPlayers.contains(p.getUniqueId())) {
                LobbySystem.flyPlayers.remove(p.getUniqueId());
                p.sendMessage(LobbySystem.PREFIX + Config.getString("flyDeactivated"));
                p.setFlying(false);
                p.setAllowFlight(false);
                e.setCancelled(true);
            }
            else {
                LobbySystem.flyPlayers.add(p.getUniqueId());
                p.sendMessage(LobbySystem.PREFIX + Config.getString("flyActivated"));
                p.setAllowFlight(true);
                e.setCancelled(true);
            }
        }else if (e.getItem().getItemMeta().getDisplayName().contains("Playerhider")){
            try {
                Playerhider.setPlayerHider(e.getPlayer());
                Playerhider.getPlayerHider(e.getPlayer());
                e.setCancelled(true);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Cosmetic")) {
            Hotbar.openCosmetik(p);
            e.setCancelled(true);
        } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6LobbySwitcher")) {
            Hotbar.openLobbyswitcher(p);
            e.setCancelled(true);
        }else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Profil")){
            MySQLf.sendFriendList(e.getPlayer(), 1);
            e.setCancelled(true);
        }
    }
}
