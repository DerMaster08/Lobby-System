package de.dermaster.lobbysystem.commands;

import de.dermaster.lobbysystem.LobbySystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class TestCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        Inventory inv = Bukkit.createInventory(null, 9*5, "Test-GUI");
        Player p = (Player) sender;
        p.openInventory(inv);
        ItemStack White = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemStack Black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack Gray = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        inv.setItem(9, White);
        inv.setItem(17, White);
        inv.setItem(9+9, White);
        inv.setItem(17+9, White);
        inv.setItem(9+9+9, White);
        inv.setItem(17+9+9, White);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, White);
                inv.setItem(8, White);
                inv.setItem(9, Black);
                inv.setItem(17, Black);
                inv.setItem(9+9, White);
                inv.setItem(17+9, White);
                inv.setItem(9+9+9, Black);
                inv.setItem(17+9+9, Black);
                inv.setItem(9+9+9+9, White);
                inv.setItem(17+9+9+9, White);
            }
        }, 1);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, Black);
                inv.setItem(1, White);
                inv.setItem(7, White);
                inv.setItem(8, Black);
                inv.setItem(9, Gray);
                inv.setItem(17, Gray);
                inv.setItem(9+9, White);
                //inv.setItem(17+9, White);
                inv.setItem(9+9+9, Gray);
                inv.setItem(17+9+9, Gray);
                inv.setItem(9+9+9+9, Black);
                inv.setItem(17+9+9+9, Black);
                inv.setItem(1+9+9+9+9, White);
                inv.setItem(7+9+9+9+9, White);
            }
        }, 2);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, Black);
                inv.setItem(1, White);
                inv.setItem(7, White);
                inv.setItem(8, Black);
                inv.setItem(9, Gray);
                inv.setItem(17, Gray);
                inv.setItem(9+9, White);
                inv.setItem(17+9, White);
                inv.setItem(9+9+9, Gray);
                inv.setItem(17+9+9, Gray);
                inv.setItem(9+9+9+9, Black);
                inv.setItem(17+9+9+9, Black);
                inv.setItem(1+9+9+9+9, White);
                inv.setItem(7+9+9+9+9, White);
            }
        }, 3);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, Black);
                inv.setItem(1, Gray);
                inv.setItem(2, White);
                inv.setItem(6, White);
                inv.setItem(7, Gray);
                inv.setItem(8, Black);
                inv.setItem(9, Gray);
                inv.setItem(17, Gray);
                inv.setItem(9+9, White);
                inv.setItem(17+9, White);
                inv.setItem(9+9+9, Gray);
                inv.setItem(17+9+9, Gray);
                inv.setItem(9+9+9+9, Black);
                inv.setItem(17+9+9+9, Black);
                inv.setItem(1+9+9+9+9, Gray);
                inv.setItem(7+9+9+9+9, Gray);
                inv.setItem(2+9+9+9+9, White);
                inv.setItem(6+9+9+9+9, White);
            }
        }, 4);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, Black);
                inv.setItem(1, Gray);
                inv.setItem(2, White);
                inv.setItem(3, Gray);
                inv.setItem(4, Black);
                inv.setItem(5, Gray);
                inv.setItem(6, White);
                inv.setItem(7, Gray);
                inv.setItem(8, Black);
                inv.setItem(9, Gray);
                inv.setItem(17, Gray);
                inv.setItem(9+9, White);
                inv.setItem(17+9, White);
                inv.setItem(9+9+9, Gray);
                inv.setItem(17+9+9, Gray);
                inv.setItem(9+9+9+9, Black);
                inv.setItem(17+9+9+9, Black);
                inv.setItem(1+9+9+9+9, Gray);
                inv.setItem(7+9+9+9+9, Gray);
                inv.setItem(2+9+9+9+9, White);
                inv.setItem(8+9+9+9+9, Black);
                inv.setItem(3+9+9+9+9, Gray);
                inv.setItem(6+9+9+9+9, White);
                inv.setItem(4+9+9+9+9, Black);
                inv.setItem(5+9+9+9+9, Gray);
            }
        }, 5);
        return true;
    }
}

