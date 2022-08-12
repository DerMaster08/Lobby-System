package de.dermaster.lobbysystem.utils;

import de.dermaster.lobbysystem.LobbySystem;
import java.io.*;

import org.bukkit.*;
import java.util.*;

public class Config
{
    private static List<String> identifies;
    private static List<String> adds;
    
    public static void configureConfig() {
        final File f = new File(LobbySystem.getInstance().getDataFolder().getPath(), "config.yml");
        if (!f.exists()) {
            f.mkdir();
            try {
                f.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            FileHelper.saveLocation(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", "spawnLoc", Bukkit.getWorlds().get(0).getSpawnLocation());
        }
        for (int i = 0; i < Config.identifies.size(); ++i) {
            if (!FileHelper.contains(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", Config.identifies.get(i))) {
                FileHelper.saveString(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", Config.identifies.get(i), Config.adds.get(i));
            }
        }
        LobbySystem.PREFIX = getString("prefix");
    }
    
    public static String getString(final String identify) {
        return ChatColor.translateAlternateColorCodes('&', FileHelper.getString(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", identify));
    }
    
    public static Location getSpawnLocation() {
        return FileHelper.getLocation(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", "spawnLoc");
    }
    
    static {
        Config.identifies = Arrays.asList("prefix", "joinMsg", "flyPerm", "noperm", "flyActivated", "flyDeactivated");
        Config.adds = Arrays.asList("&c&lLobby&4&lSystem &8&l| &7 ", "&bWillkommen auf &cLbGameMC.de!", "LbGameMC.fly", "&cDazu hast du keine Berechtigung!", "&bDu kannst nun fliegen!", "&bDu kannst jetzt nicht mehr fliegen!");
    }
}
