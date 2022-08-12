package de.dermaster.lobbysystem.commands;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.utils.FileHelper;
import de.dermaster.lobbysystem.utils.InventoryHelper;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.inventory.*;

public class SetupCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("setSpawn")) {
                    FileHelper.saveLocation(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", "spawnLoc", p.getLocation());
                    p.sendMessage(LobbySystem.PREFIX + "§aDu hast die SpawnLoc gesetzt!");
                }
                else if (args[0].equalsIgnoreCase("openInv")) {
                    if (LobbySystem.inventoryCreators.containsKey(p.getUniqueId())) {
                        p.openInventory((Inventory)LobbySystem.inventoryCreators.get(p.getUniqueId()));
                    }
                    else {
                        p.sendMessage(LobbySystem.PREFIX + "§cDu hast kein Inventar offen!");
                    }
                }
                else {
                    this.sendHelpMsg(p);
                }
            }
            else if(args.length == 2){
                if (args[0].equalsIgnoreCase("saveInv")) {
                    InventoryHelper.saveInventory(LobbySystem.inventoryCreators.get(p.getUniqueId()), args[1]);
                    LobbySystem.inventoryCreators.remove(p.getUniqueId());
                    p.sendMessage(LobbySystem.PREFIX + "§aDu hast das Inventar gespeichert!");
                }else if(args[0].equalsIgnoreCase("setwarp")){
                        FileHelper.saveLocation(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", "warp."+args[1], p.getLocation());
                    p.sendMessage(LobbySystem.PREFIX + "§aDu hast den Warp "+args[1]+" gesetzt!");
                }
            }
            else if (args.length == 3) {
                if (args[0].equalsIgnoreCase("createInv")) {
                    try {
                        final int i = Integer.parseInt(args[1]);
                        if (i % 9 == 0 && i <= 54 && i >= 9) {
                            final Inventory inv = Bukkit.createInventory((InventoryHolder)null, i, args[2]);
                            LobbySystem.inventoryCreators.put(p.getUniqueId(), inv);
                            p.openInventory(inv);
                        }
                        else {
                            p.sendMessage(LobbySystem.PREFIX + "§cDie Inventargr\u00f6\u00dfe ist ung\u00fcltig!");
                        }
                    }
                    catch (NumberFormatException exc) {
                        p.sendMessage(LobbySystem.PREFIX + "§cDu musst eine Zahl angeben!");
                    }
                }
                else {
                    this.sendHelpMsg(p);
                }
            }
            else {
                this.sendHelpMsg(p);
            }
        }
        return true;
    }
    
    private void sendHelpMsg(final Player p) {
        p.sendMessage(LobbySystem.PREFIX + "§6/setup");
        p.sendMessage("§a/setup [setSpawn/reload/build/openInv]");
        p.sendMessage("§a/setup [saveInv/setwarp] [Name]");
        p.sendMessage("§a/setup createInv [Size] [Name]");
    }
}
