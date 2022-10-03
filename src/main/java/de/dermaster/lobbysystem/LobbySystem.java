package de.dermaster.lobbysystem;

import de.dermaster.lobbysystem.MySQL.MySQL;
import de.dermaster.lobbysystem.MySQL.MySQLf;
import de.dermaster.lobbysystem.commands.*;

import de.dermaster.lobbysystem.listeners.*;
import de.dermaster.lobbysystem.utils.Config;
import de.dermaster.lobbysystem.utils.GrapplingHookColldown;
import de.dermaster.lobbysystem.utils.InventoryHelper;
import de.dermaster.lobbysystem.utils.ItemAPI;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.*;

import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.*;
import org.bukkit.command.*;

public final class LobbySystem extends JavaPlugin
{
    public static ArrayList<UUID> ECooldown;
    public static ArrayList<UUID> PHCooldown;
    public static ArrayList<UUID> flyPlayers;
    public static HashMap<UUID, Inventory> inventoryCreators;
    public static ArrayList<UUID> buildPlayers;
    public static String PREFIX;
    private static LobbySystem plugin;
    public void onEnable() {
        LobbySystem.plugin = this;
        Bukkit.getPluginManager().registerEvents((Listener)new CancelledEvents(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new JoinQuitListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new DoubleJumpListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new InteractListener(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new InventoryHelper(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents((Listener)new GadgetsEvent(), (Plugin)this);
        Bukkit.getPluginManager().registerEvents(new SneakEvent(), this);
        this.getCommand("setup").setExecutor((CommandExecutor)new SetupCommand());
        this.getCommand("fly").setExecutor((CommandExecutor)new FlyCommand());
        this.getCommand("spawn").setExecutor(new SpawnCommand());
        this.getCommand("fix").setExecutor(new FixCommand());
        this.getCommand("test").setExecutor(new TestCommand());
        CancelledEvents.startColorArmor();
        ItemAPI.init();
        GrapplingHookColldown.init();
        Config.configureConfig();
        MySQL.connect();
        System.out.println("[Lobby-System] MySQL Connect");
        MySQLf.connect();
        System.out.println("[Lobby-System] Friends MySQL Connect");
        //startActionBar();
        CancelledEvents.mainLoop().runTaskTimer(this, 1, 1);
    }
    
    public void onDisable() {
    }
    
    public static LobbySystem getInstance() {
        return LobbySystem.plugin;
    }
    
    static {
        LobbySystem.flyPlayers = new ArrayList<UUID>();
        LobbySystem.inventoryCreators = new HashMap<UUID, Inventory>();
        LobbySystem.buildPlayers = new ArrayList<UUID>();
        LobbySystem.ECooldown = new ArrayList<>();
        LobbySystem.PHCooldown = new ArrayList<>();
        LobbySystem.PREFIX = "§c§lLobby§4§lSystem §8§l| §7 ";
    }
    public static void startActionBar(){
        int k;
        k=Bukkit.getScheduler().scheduleSyncRepeatingTask(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()){
                    String[] message = new String[] {"LbGame ist uncool", "DerMaster ist der beste"};
                    Random r = new Random();
                    p.sendActionBar(new TextComponent(message[r.nextInt(message.length)]));
                }
            }
        },5,5);
    }

}
