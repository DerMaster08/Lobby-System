package de.dermaster.lobbysystem.listeners;

import de.dermaster.lobbysystem.utils.GadgetsClass;
import de.dermaster.lobbysystem.utils.GrapplingHookColldown;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class GadgetsEvent implements Listener {
    @EventHandler
    public void onFish(PlayerFishEvent event){
        Player p = event.getPlayer();
        ItemStack itemStack = p.getInventory().getItemInMainHand();
        ItemMeta meta = itemStack.getItemMeta();
        String name = meta.getDisplayName();
        if(name.equals("§6Grapling Hook")) {
            if (event.getState().equals(PlayerFishEvent.State.REEL_IN) || event.getState().equals(PlayerFishEvent.State.IN_GROUND)) {
                if (GrapplingHookColldown.checkCooldown(event.getPlayer())) {
                    Location loc = p.getLocation();
                    Location hook = event.getHook().getLocation();
                    Location change = hook.subtract(loc);
                    p.teleport(p.getLocation().add(0, 0.5, 0));
                    Vector v = getVectorForPoints(loc, hook);
                    p.setVelocity(v);
                    GrapplingHookColldown.setCooldowns(event.getPlayer(), (int)2.5);
                } else {
                    event.getHook().remove();
                }
            }
        }
    }
    private Vector getVectorForPoints(Location loc1, Location loc2){
        double g = -0.08;
        double d = loc2.distance(loc1);
        double t = d;
        double vX = (1.0+0.07*t) * (loc2.getX() - loc1.getX())/t;
        double vY = (1.0+0.03*t) * (loc2.getY() - loc1.getY())/t - 0.5*g*t;
        double vZ = (1.0+0.07*t) * (loc2.getY() - loc1.getY())/t;
        return new Vector(vX, vY, vZ);
    }
    @EventHandler
    public void onLaunch(ProjectileLaunchEvent event){
        Projectile proj = event.getEntity();
        if(proj instanceof EnderPearl){
            new GadgetsClass((Player)proj.getShooter());
        }/*else if(proj instanceof FishHook){
            Player p = (Player) event.getEntity().getShooter();
            p.teleport(p.getLocation().add(0, 0.5, 0));
            Vector v = getVectorForPoints(p.getLocation(), proj.getLocation());
            proj.setVelocity(v);
            p.setVelocity(v);
        }*/
    }
}
