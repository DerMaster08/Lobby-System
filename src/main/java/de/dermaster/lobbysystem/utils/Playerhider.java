package de.dermaster.lobbysystem.utils;

import de.dermaster.lobbysystem.MySQL.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Playerhider {

    public static void getPlayerHider(Player p) {
        try {
        switch (MySQL.getState(String.valueOf(p.getUniqueId()))){
            case 1:
                p.getInventory().setItem(1, new ItemBuilder(Material.BLAZE_ROD).setName("§a§oPlayerhider").build());
                for (Player all : Bukkit.getOnlinePlayers()){
                    p.showPlayer(all);
                }
                break;
            case 2:
                p.getInventory().setItem(1, new ItemBuilder(Material.STICK).setName("§c§oPlayerhider").build());
                for (Player all : Bukkit.getOnlinePlayers()){
                        p.hidePlayer(all);
                }
                break;
        }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public static void setPlayerHider(Player p)  {
        try {
            switch (MySQL.getState(String.valueOf(p.getUniqueId()))) {
                case 1:
                    MySQL.setStatePH(2, String.valueOf(p.getUniqueId()));
                    break;
                case 2:
                    MySQL.setStatePH(1, String.valueOf(p.getUniqueId()));
                    break;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
}
