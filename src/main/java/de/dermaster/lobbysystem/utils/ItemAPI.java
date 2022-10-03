package de.dermaster.lobbysystem.utils;

import de.dermaster.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.List;
import java.util.Random;

public class ItemAPI {

    public static ItemStack setColorBoots(Color color, Player p){
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) boots.getItemMeta();
        itemMeta.setColor(color);
        itemMeta.setDisplayName("§6Rainbow Schuhe");
        boots.setItemMeta(itemMeta);
        p.getInventory().setBoots(boots);
        return boots;
    }
    public static ItemStack setColorLeggins(Color color, Player p){
        ItemStack boots = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) boots.getItemMeta();
        itemMeta.setColor(color);
        itemMeta.setDisplayName("§6Rainbow Hose");
        boots.setItemMeta(itemMeta);
        p.getInventory().setLeggings(boots);
        return boots;
    }
    public static ItemStack setColorChestplate(Color color, Player p){
        ItemStack boots = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) boots.getItemMeta();
        itemMeta.setColor(color);
        itemMeta.setDisplayName("§6Rainbow Chestplate");
        boots.setItemMeta(itemMeta);
        p.getInventory().setChestplate(boots);
        return boots;
    }
    public static ItemStack setColorHelm(Color color, Player p){
        ItemStack boots = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) boots.getItemMeta();
        itemMeta.setColor(color);
        itemMeta.setDisplayName("§6Rainbow Helm");
        boots.setItemMeta(itemMeta);
        p.getInventory().setHelmet(boots);
        return boots;
    }
    public static Color getRandomC(){
        Color color= null;
        Random r = new Random();
        int b = r.nextInt(16);
        switch (b){
            case 0:
                color = Color.AQUA;
                break;
            case 1:
                color = Color.BLACK;
                break;
            case 2:
                color = Color.BLUE;
                break;
            case 3:
                color = Color.FUCHSIA;
                break;
            case 4:
                color = Color.GRAY;
                break;
            case 5:
                color = Color.GREEN;
                break;
            case 6:
                color = Color.LIME;
                break;
            case 7:
                color = Color.MAROON;
                break;
            case 8:
                color = Color.NAVY;
                break;
            case 9:
                color = Color.OLIVE;
                break;
            case 10:
                color = Color.ORANGE;
                break;
            case 11:
                color = Color.PURPLE;
                break;
            case 12:
                color = Color.RED;
                break;
            case 13:
                color = Color.SILVER;
                break;
            case 14:
                color = Color.TEAL;
                break;
            case 15:
                color = Color.WHITE;
                break;
            case 16:
                color = Color.YELLOW;
                break;
            default:
                color = Color.WHITE;
        }
        return color;
    }
    public static ItemStack GrapplingHook;
    public static void init(){
        createGraplingHook();
    }

    public static void createGraplingHook(){
        ItemStack item = new ItemStack(Material.FISHING_ROD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Grapling Hook");
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        GrapplingHook = item;
    }

}
