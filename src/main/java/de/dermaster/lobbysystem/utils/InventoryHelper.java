package de.dermaster.lobbysystem.utils;

import de.dermaster.lobbysystem.LobbySystem;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class InventoryHelper implements Listener
{
    @EventHandler
    public void onInvClose(final InventoryCloseEvent e) {
        final Player p = (Player)e.getPlayer();
        if (LobbySystem.inventoryCreators.containsKey(p.getUniqueId())) {
            p.sendMessage(LobbySystem.PREFIX + "§aDu musst das Inventar noch speichern!");
        }
    }
    
    public static Inventory getInventory(final String path) {
        final int size = FileHelper.getInteger(path, "size");
        final String invName = ChatColor.translateAlternateColorCodes('&', path.replace(".inv", "")).replace(LobbySystem.getInstance().getDataFolder().getPath()+"/", "");
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, size, invName);
        for (int i = 0; i < size; ++i) {
            if (FileHelper.contains(path, "item" + i + ".material")) {
                final String material = FileHelper.getString(path, "item" + i + ".material");
                final int amount = FileHelper.getInteger(path, "item" + i + ".amount");
                final String itemName = ChatColor.translateAlternateColorCodes('&', FileHelper.getString(path, "item" + i + ".name"));
                final ItemStack item = new ItemBuilder(Material.valueOf(material), amount).setName(itemName).build();
                inv.setItem(i, item);
            }
        }
        return inv;
    }
    
    public static void saveInventory(final Inventory inv, final String Name) {
        final String path = LobbySystem.getInstance().getDataFolder().getPath() + "/"+Name+".inv";
        FileHelper.saveInteger(path, "size", inv.getSize());
        for (int i = 0; i < inv.getSize(); ++i) {
            final ItemStack item = inv.getItem(i);
            if (item != null) {
                FileHelper.saveString(path, "item" + i + ".material", item.getType().toString());
                FileHelper.saveInteger(path, "item" + i + ".amount", item.getAmount());
                FileHelper.saveString(path, "item" + i + ".name", item.getItemMeta().getDisplayName());
            }
        }
    }
}
