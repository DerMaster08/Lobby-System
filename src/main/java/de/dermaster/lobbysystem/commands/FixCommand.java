package de.dermaster.lobbysystem.commands;

import de.dermaster.lobbysystem.utils.Hotbar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class FixCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final Player p = (Player) sender;
        try {
            Hotbar.setItems(p);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
