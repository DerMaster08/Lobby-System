package de.dermaster.lobbysystem.utils;

import java.io.*;
import org.bukkit.configuration.file.*;
import org.bukkit.*;

public class FileHelper
{
    public static boolean contains(final String filePath, final String identify) {
        final File file = new File(filePath);
        final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.contains(identify);
    }
    
    public static void saveString(final String filePath, final String identify, final String add) {
        final File file = new File(filePath);
        final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(identify, add);
        try {
            cfg.save(file);
        }
        catch (Exception ex) {}
    }
    

    
    public static void saveInteger(final String filePath, final String identify, final int add) {
        final File file = new File(filePath);
        final FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        cfg.set(identify, (Object)add);
        try {
            cfg.save(file);
        }
        catch (Exception ex) {}
    }
    
    public static void saveDouble(final String filePath, final String identify, final double add) {
        final File file = new File(filePath);
        final FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        cfg.set(identify, (Object)add);
        try {
            cfg.save(file);
        }
        catch (Exception ex) {}
    }
    
    public static void saveLocation(final String filePath, final String identify, final Location loc) {
        final File file = new File(filePath);
        final FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        cfg.set(identify + ".World", (Object)loc.getWorld().getName());
        cfg.set(identify + ".X", (Object)loc.getX());
        cfg.set(identify + ".Y", (Object)loc.getY());
        cfg.set(identify + ".Z", (Object)loc.getZ());
        cfg.set(identify + ".Yaw", (Object)loc.getYaw());
        cfg.set(identify + ".Pitch", (Object)loc.getPitch());
        try {
            cfg.save(file);
        }
        catch (Exception ex) {}
    }
    
    public static String getString(final String filePath, final String identify) {
        final File file = new File(filePath);
        final FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        return cfg.getString(identify);
    }
    
    public static boolean getBoolean(final String filePath, final String identify) {
        final File file = new File(filePath);
        final FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        return cfg.getBoolean(identify);
    }
    
    public static int getInteger(final String filePath, final String identify) {
        final File file = new File(filePath);
        final FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        return cfg.getInt(identify);
    }
    public static Location getLocation(final String filePath, final String identify) {
        final File file = new File(filePath);
        final FileConfiguration cfg = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
        final World w = Bukkit.getWorld(cfg.getString(identify + ".World"));
        final double x = cfg.getDouble(identify + ".X");
        final double y = cfg.getDouble(identify + ".Y")-1;
        final double z = cfg.getDouble(identify + ".Z");
        final float yaw = (float)cfg.getDouble(identify + ".Yaw");
        final float pitch = (float)cfg.getDouble(identify + ".Pitch");
        return new Location(w, x, y, z, yaw, pitch);
    }
}
