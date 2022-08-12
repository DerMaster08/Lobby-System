package de.dermaster.lobbysystem.utils;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.HashMap;
import java.util.UUID;

public class GrapplingHookColldown {
    public static HashMap<UUID, Double> cooldowns;
    public static void init(){
        cooldowns = new HashMap<>();
    }
    public static void setCooldowns(Player p, int seconds){
        double delay = System.currentTimeMillis()+(seconds*1000);
        cooldowns.put(p.getUniqueId(), delay);
    }
    public static boolean checkCooldown(Player p){
        if(!cooldowns.containsKey(p.getUniqueId()) || cooldowns.get(p.getUniqueId()) <= System.currentTimeMillis()){
            return true;
        }else{
            return false;
        }
    }
}
