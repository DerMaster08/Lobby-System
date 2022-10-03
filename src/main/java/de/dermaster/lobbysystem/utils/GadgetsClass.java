package de.dermaster.lobbysystem.utils;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.MySQL.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class GadgetsClass {
    Player player;
    public GadgetsClass(Player player){
        this.player = player;
        toWait();
    }
    public void toWait(){
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                LobbySystem.ECooldown.add(player.getUniqueId());
                toEnderPearl();
                if(player.getInventory().getItem(1).getType().equals(Material.BLAZE_ROD)) {
                    player.getInventory().setItem(7, new ItemBuilder(Material.FIREWORK_STAR).setName("§oEinen Moment bitte").setLore("§7Du musst 2 Sekunden warten...").build());
                }
            }
        }, 1L);
    }
    public void toEnderPearl(){
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                LobbySystem.ECooldown.remove(player.getUniqueId());
                if(player.getInventory().getItem(7).getType().equals(Material.FIREWORK_STAR)) {
                    player.getInventory().setItem(7, new ItemBuilder(Material.ENDER_PEARL).setName("§oEnderperle").build());
                }
            }
        }, 5*20L);
    }
    public static void StartPhCooldown(Player p){
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                LobbySystem.PHCooldown.remove(p.getUniqueId());
            }
        }, 1*20L);
    }
    public static void getGadget(Player p){
        try {
            if(MySQL.getStateG(p.getUniqueId().toString()) == 0){
                p.getInventory().setItem(7, new ItemBuilder(Material.FIREWORK_STAR).setName("§7Kein Gadget ausgewählt").build());
            }else if(MySQL.getStateG(p.getUniqueId().toString()) == 1){
                p.getInventory().setItem(7, ItemAPI.GrapplingHook);
            }else if(MySQL.getStateG(p.getUniqueId().toString()) == 2){
                if(!LobbySystem.ECooldown.contains(p.getUniqueId())) {
                    p.getInventory().setItem(7, new ItemBuilder(Material.ENDER_PEARL).setName("§oEnderperle").build());
                }else {
                    p.getInventory().setItem(7, new ItemBuilder(Material.FIREWORK_STAR).setName("§oEinen Moment bitte").setLore("§7Du musst 5 Sekunden warten...").build());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
