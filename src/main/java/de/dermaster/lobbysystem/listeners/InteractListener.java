package de.dermaster.lobbysystem.listeners;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.MySQL.MySQLf;
import de.dermaster.lobbysystem.utils.Config;
import de.dermaster.lobbysystem.utils.GadgetsClass;
import de.dermaster.lobbysystem.utils.Hotbar;
import de.dermaster.lobbysystem.utils.Playerhider;
import org.bukkit.Sound;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InteractListener implements Listener
{
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        final Player p = e.getPlayer();

        if(e.getItem() == null){e.setCancelled(true); return;}
        if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§oNavigator")) {
            Hotbar.openNavigator(p);
            e.setCancelled(true);
        }else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§oTeamServer")){
            Hotbar.openTeam(p);
            e.setCancelled(true);
        } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§oFly")) {
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
            if(!LobbySystem.PHCooldown.contains(p.getUniqueId())) {
                GadgetsClass.StartPhCooldown(p);
                LobbySystem.PHCooldown.add(p.getUniqueId());
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 50));
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 100, 0);
                Playerhider.setPlayerHider(e.getPlayer());
                Playerhider.getPlayerHider(e.getPlayer());
            }else {
                if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    p.sendMessage(LobbySystem.PREFIX + "Bitte warte eine Sekunde");
                }
            }
        } else if (e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("§oCosmetic")) {
            Hotbar.openCosmetik(p);
            e.setCancelled(true);
        } else if (e.getItem().getItemMeta().getDisplayName().contains("LobbySwitcher")) {
            Hotbar.openLobbyswitcher(p);
            e.setCancelled(true);
        }else if (e.getItem().getItemMeta().getDisplayName().contains("Profil")){
            MySQLf.sendFriendList(e.getPlayer(), 1);
            e.setCancelled(true);
        }
    }
}
