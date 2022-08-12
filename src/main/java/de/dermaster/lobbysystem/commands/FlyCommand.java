package de.dermaster.lobbysystem.commands;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.utils.Config;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class FlyCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            if (p.hasPermission(Config.getString("flyPerm"))) {
                if (LobbySystem.flyPlayers.contains(p.getUniqueId())) {
                    LobbySystem.flyPlayers.remove(p.getUniqueId());
                    p.sendMessage(LobbySystem.PREFIX + Config.getString("flyDeactivated"));
                    p.setFlying(false);
                    p.setAllowFlight(false);
                }
                else {
                    LobbySystem.flyPlayers.add(p.getUniqueId());
                    p.sendMessage(LobbySystem.PREFIX + Config.getString("flyActivated"));
                    p.setAllowFlight(true);
                    p.setFlying(true);
                }
            }
            else {
                p.sendMessage(LobbySystem.PREFIX + Config.getString("noperm"));
            }
        }
        return true;
    }
}
