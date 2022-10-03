package de.dermaster.lobbysystem.listeners;

import de.dermaster.lobbysystem.LobbySystem;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

public class DoubleJumpListener implements Listener
{
    @EventHandler
    public void onDoubleJump(final PlayerToggleFlightEvent e) {
        final Player p = e.getPlayer();
        if (LobbySystem.buildPlayers.contains(p.getUniqueId())) {
            return;
        }
        if (LobbySystem.flyPlayers.contains(p.getUniqueId())) {
            return;
        }
        e.setCancelled(true);
        p.setAllowFlight(false);
        p.setVelocity(p.getLocation().getDirection().multiply(2.0D).setY(0.9D));
        //p.setVelocity(p.getLocation().getDirection().clone().normalize().multiply(0.8).setY(1.0).setY(p.getLocation().getPitch()));
        p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 0.5f, 1.0f);
    }
    
    @EventHandler
    public void onMove(final PlayerMoveEvent e) {
        final Player p = e.getPlayer();
        if (p.isOnGround()) {
            p.setAllowFlight(true);
        }
    }
}
