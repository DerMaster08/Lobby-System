package de.dermaster.lobbysystem.listeners;


import de.dermaster.lobbysystem.utils.Hotbar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.sql.SQLException;

public class SneakEvent implements Listener {
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(event.isSneaking()){
            Hotbar.setItemsTeam(event.getPlayer());
        }else{
            try {
                Hotbar.setItems(event.getPlayer());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
