package de.dermaster.lobbysystem.utils;

import com.google.common.collect.ImmutableList;
import de.dermaster.lobbysystem.LobbySystem;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.module.permission.PermissionPool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class ScoreboardManager {
    public static void setScoreboard(){
        for (Player player : Bukkit.getOnlinePlayers()) {
            String[] args = player.getDisplayName().split(" ");
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
    }
    private static ICloudPlayer getCloudPlayer(Player p) {
        try {
            return (ICloudPlayer) CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(p.getUniqueId()).get();
        } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}