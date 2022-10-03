package de.dermaster.lobbysystem.utils;

import com.google.common.collect.ImmutableList;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardManager {
    public static void setScoreboard(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            Sidebar sidebar = new Sidebar("§c§lLbGame§4§lMC", ImmutableList.<String>builder()
                    .add("§0")
                    .add("§aOnline§8:")
                    .add("§7"+Bukkit.getOnlinePlayers().size()+"§8/§7"+Bukkit.getMaxPlayers())
                    .add("§1")
                    .add("§aServer§8:")
                    .add("§7"+ getCloudPlayer(player).getConnectedServer().getName())
                    .add("§3")
                    .add("§aWebsite§8:")
                    .add("§7https://lbgamemc.de/")
                    .add("§4")
                    .add("§aDiscord§8:")
                    .add("§7https://dc.lbgamemc.de/")
                    .add("§5")
                    .build()).build().send(player);
        }
    }//getCloudPlayer(player).getConnectedServer().getName()
    private static ICloudPlayer getCloudPlayer(Player p) {
        try {
            return (ICloudPlayer) CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(p.getUniqueId()).get();
        } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}