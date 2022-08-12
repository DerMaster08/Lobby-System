package de.dermaster.lobbysystem.utils;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.MySQL.MySQL;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.service.ServiceState;
import eu.thesimplecloud.api.servicegroup.grouptype.ICloudServerGroup;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.entity.*;
import org.bukkit.*;

import java.sql.SQLException;

public class Hotbar
{
    private static ItemStack[] team;
    
    public static void setItems(final Player p) throws SQLException {
            p.getInventory().setItem(0, null);
            p.getInventory().setItem(1, null);
            p.getInventory().setItem(2, null);
            p.getInventory().setItem(3, null);
            p.getInventory().setItem(4, null);
            p.getInventory().setItem(5, null);
            p.getInventory().setItem(6, null);
            p.getInventory().setItem(7, null);
            p.getInventory().setItem(8, null);
        try {
            if(MySQL.getStateS(p.getName(), "Animation") == 1){
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§6Navigator").build());
                    }
                }, 1*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Playerhider.getPlayerHider(p);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }, 2*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(3,  new ItemBuilder(Material.CHEST).setName("§6Cosmetic").build());
                    }
                }, 3*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(5, new ItemBuilder(Material.NETHER_STAR).setName("§6LobbySwitcher").build());
                    }
                }, 4*20L);

                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        GadgetsClass.getGadget(p);
                    }
                }, 5*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setName("§6Profil").setOwner(p.getName()).build());
                    }
                }, 6*20L);
            }else {
                p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("§6Navigator").build());
                Playerhider.getPlayerHider(p);
                p.getInventory().setItem(3,  new ItemBuilder(Material.CHEST).setName("§6Cosmetic").build());
                p.getInventory().setItem(5, new ItemBuilder(Material.NETHER_STAR).setName("§6LobbySwitcher").build());
                GadgetsClass.getGadget(p);
                p.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setName("§6Profil").setOwner(p.getName()).build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void setItemsTeam(final Player p) {
        p.getInventory().setItem(0, null);
        p.getInventory().setItem(1, null);
        p.getInventory().setItem(2, null);
        p.getInventory().setItem(3, null);
        p.getInventory().setItem(4, null);
        p.getInventory().setItem(5, null);
        p.getInventory().setItem(6, null);
        p.getInventory().setItem(7, null);
        p.getInventory().setItem(8, null);
            for (int i = 0; i < Hotbar.team.length; ++i) {
                p.getInventory().setItem(i, Hotbar.team[i]);
            }
                final ItemStack Bauserver = new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§6TeamServer").build();
                p.getInventory().setItem(4, Bauserver);
    }
    public static void openTeam(final Player p) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§6TeamServer");
        ICloudPlayer cloudPlayer = getCloudPlayer(p);
        ICloudServerGroup group = CloudAPI.getInstance().getCloudServiceGroupManager().getServerGroupByName("BauServer");
        ICloudService server = null;
        if (group.getAllServices().isEmpty()) {
            server = null;
        } else {
            server = group.getAllServices().get(0);
        }
        if (server == null) {
            inv.setItem(1, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§6BauServer").setLore("§4Offline").build());
        } else if (server.getState().equals(ServiceState.CLOSED)) {
            inv.setItem(1, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§6BauServer").setLore("§4Offline").build());
        } else if (server.getState().equals(ServiceState.STARTING)) {
            inv.setItem(1, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§6BauServer").setLore("§eStartet").build());
        } else {
            inv.setItem(1, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("§6BauServer").setLore("§aOnline").build());
        }
        group = CloudAPI.getInstance().getCloudServiceGroupManager().getServerGroupByName("TestServer");
        server = null;
        if (group.getAllServices().isEmpty()) {
            server = null;
        } else {
            server = group.getAllServices().get(0);
        }
        if (server == null) {
            inv.setItem(3, new ItemBuilder(Material.REDSTONE).setName("§6TestServer").setLore("§4Offline").build());
        } else if (server.getState().equals(ServiceState.CLOSED)) {
            inv.setItem(3, new ItemBuilder(Material.REDSTONE).setName("§6TestServer").setLore("§4Offline").build());
        } else if (server.getState().equals(ServiceState.STARTING)) {
            inv.setItem(3, new ItemBuilder(Material.REDSTONE).setName("§6TestServer").setLore("§eStartet").build());
        } else {
            inv.setItem(3, new ItemBuilder(Material.REDSTONE).setName("§6TestServer").setLore("§aOnline").build());
        }
        inv.setItem(0,new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        inv.setItem(2,new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§2").build());
        inv.setItem(4,new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§4").build());
        p.openInventory(inv);
    }
    public static void openNavigator(Player p){
        Inventory inv = Bukkit.createInventory(null, 54, "§6Navigator");
        for(int i =0; i<54; i++){
            inv.setItem(i,new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        }
        inv.setItem(13, new ItemBuilder(Material.BEACON).setName("§6Spawn").build());
        inv.setItem(19, new ItemBuilder(Material.GRASS_BLOCK).setName("§6Skyblock").build());
        inv.setItem(25, new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("§6Citybuild").build());
        inv.setItem(40, new ItemBuilder(Material.WOODEN_AXE).setName("§6Gungame").build());
        p.openInventory(inv);
    }
    public static void openCosmetik(Player p){
        Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST, "§6Cosmetic");
        for (int i =0; i<inv.getSize(); i++){
            inv.setItem(i,new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        }
        inv.setItem(10, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§6Rüstung").build());
        inv.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setName("§6Köpfe").setOwner(p.getName()).build());
        inv.setItem(16, new ItemBuilder(Material.FISHING_ROD).setName("§6Gadgets").build());
        p.openInventory(inv);
    }
    public static void openRüstung(Player p){
        Inventory inv = Bukkit.createInventory(null, 36, "§6Rüstung");
        for (int i =0; i<inv.getSize(); i++){
            inv.setItem(i,new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        }
        inv.setItem(7, new ItemBuilder(Material.LEATHER_HELMET).setName("§6Rainbow Helm").setColor().build());
        inv.setItem(16, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§6Rainbow Chestplate").setColor().build());
        inv.setItem(25, new ItemBuilder(Material.LEATHER_LEGGINGS).setName("§6Rainbow Hose").setColor().build());
        inv.setItem(34, new ItemBuilder(Material.LEATHER_BOOTS).setName("§6Rainbow Schuhe").setColor().build());

        inv.setItem(6, new ItemBuilder(Material.NETHERITE_HELMET).setName("§6Netherite Helm").build());
        inv.setItem(15, new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("§6Netherite Chestplate").build());
        inv.setItem(24, new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("§6Netherite Hose").build());
        inv.setItem(33, new ItemBuilder(Material.NETHERITE_BOOTS).setName("§6Netherite Schuhe").build());

        inv.setItem(5, new ItemBuilder(Material.DIAMOND_HELMET).setName("§6Diamond Helm").build());
        inv.setItem(14, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("§6Diamond Chestplate").build());
        inv.setItem(23, new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("§6Diamond Hose").build());
        inv.setItem(32, new ItemBuilder(Material.DIAMOND_BOOTS).setName("§6Diamond Schuhe").build());

        inv.setItem(4, new ItemBuilder(Material.GOLDEN_HELMET).setName("§6Gold Helm").build());
        inv.setItem(13, new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("§6Gold Chestplate").build());
        inv.setItem(22, new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("§6Gold Hose").build());
        inv.setItem(31, new ItemBuilder(Material.GOLDEN_BOOTS).setName("§6Gold Schuhe").build());

        inv.setItem(3, new ItemBuilder(Material.IRON_HELMET).setName("§6Eisen Helm").build());
        inv.setItem(12, new ItemBuilder(Material.IRON_CHESTPLATE).setName("§6Eisen Chestplate").build());
        inv.setItem(21, new ItemBuilder(Material.IRON_LEGGINGS).setName("§6Eisen Hose").build());
        inv.setItem(30, new ItemBuilder(Material.IRON_BOOTS).setName("§6Eisen Schuhe").build());

        inv.setItem(2, new ItemBuilder(Material.CHAINMAIL_HELMET).setName("§6Chain Helm").build());
        inv.setItem(11, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("§6Chain Chestplate").build());
        inv.setItem(20, new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("§6Chain Hose").build());
        inv.setItem(29, new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("§6Chain Schuhe").build());

        inv.setItem(1, new ItemBuilder(Material.LEATHER_HELMET).setName("§6Leder Helm").build());
        inv.setItem(10, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("§6Leder Chestplate").build());
        inv.setItem(19, new ItemBuilder(Material.LEATHER_LEGGINGS).setName("§6Leder Hose").build());
        inv.setItem(28, new ItemBuilder(Material.LEATHER_BOOTS).setName("§6Leder Schuhe").build());
        inv.setItem(27, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("§cZurück").build());
        p.openInventory(inv);
    }
    public static void openKöpfe(Player p) throws SQLException {
        Inventory inv = Bukkit.createInventory(null, 36, "§6Köpfe");
        for (int i =0; i<inv.getSize(); i++){
            inv.setItem(i,new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        }
        //>>Team<<
        if(MySQL.getKopf(p.getName(), "§4LbGame")) {
            inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("§4LbGame").setOwner("LbGame").build());
        }else {
            inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("§4LbGame").setOwner("LbGame").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "§4Lenniilll")) {
            inv.setItem(11, new ItemBuilder(Material.PLAYER_HEAD).setName("§4Lenniilll").setOwner("Lenniilll").build());
        }else {
            inv.setItem(11, new ItemBuilder(Material.PLAYER_HEAD).setName("§4Lenniilll").setOwner("Lenniilll").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "§cLetsVrime")) {
            inv.setItem(12, new ItemBuilder(Material.PLAYER_HEAD).setName("§cLetsVrime").setOwner("LetsVrime").build());
        }else {
            inv.setItem(12, new ItemBuilder(Material.PLAYER_HEAD).setName("§cLetsVrime").setOwner("LetsVrime").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "§aMasterBen007")) {
            inv.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setName("§aMasterBen007").setOwner("MasterBen007").build());
        }else {
            inv.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setName("§aMasterBen007").setOwner("MasterBen007").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        //>>Andere<<
        if(MySQL.getKopf(p.getName(), "§7Steve")) {
            inv.setItem(14, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Steve").setOwner("Steve").build());
        }else {
            inv.setItem(14, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Steve").setOwner("Steve").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "§7Geschenk")) {
            inv.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Geschenk").setOwner("MHF_Present1").build());
        }else {
            inv.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Geschenk").setOwner("MHF_Present1").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "§7Villager")) {
            inv.setItem(16, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Villager").setOwner("MHF_Villager").build());
        }else {
            inv.setItem(16, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Villager").setOwner("MHF_Villager").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "§7Roter Slime")) {
            inv.setItem(19, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Roter Slime").setOwner("ISmellGood21").build());
        }else {
            inv.setItem(19, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Roter Slime").setOwner("ISmellGood21").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "§7Muffin")) {
            inv.setItem(20, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Muffin").setOwner("Molflin").build());
        }else {
            inv.setItem(20, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Muffin").setOwner("Molflin").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "§7Glassblock")) {
            inv.setItem(21, new ItemBuilder(Material.GLASS).setName("§7Glassblock").build());
        }else {
            inv.setItem(21, new ItemBuilder(Material.GLASS).setName("§7Glassblock").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        /*inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("").setOwner("").build());
        inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("").setOwner("").build());
        inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("").setOwner("").build());*/
        inv.setItem(35, new ItemBuilder(Material.BARRIER).setName("§cDen Kopf ausziehen").build());
        inv.setItem(27, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("§cZurück").build());
        p.openInventory(inv);
    }
    public static void openGadgets(Player p) throws SQLException {
        Inventory inv = Bukkit.createInventory(null, 36, "§6Gadgets");
        for (int i =0; i<inv.getSize(); i++){
            inv.setItem(i,new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        }
        if(MySQL.getGadgets(String.valueOf(p.getUniqueId()), "Grappler") == 1) {
            inv.setItem(10, new ItemBuilder(Material.FISHING_ROD).setName("§6Grapling Hook").build());
        }else {
            inv.setItem(10, new ItemBuilder(Material.FISHING_ROD).setName("§4Grapling Hook").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        if(MySQL.getGadgets(String.valueOf(p.getUniqueId()), "Enderpearl") == 1) {
            inv.setItem(12, new ItemBuilder(Material.ENDER_PEARL).setName("§6Enderperle").build());
        }else {
            inv.setItem(12, new ItemBuilder(Material.ENDER_PEARL).setName("§4Enderperle").setLore("§cDu musst dir dies noch Kaufen!\n §7Preis§8: §61000Coins").build());
        }
        inv.setItem(27, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("§cZurück").build());
        p.openInventory(inv);
    }
    public static void openLobbyswitcher(Player p){
        Inventory inv = Bukkit.createInventory(null, 9, "§6Lobbys");
        ICloudServerGroup lobby = CloudAPI.getInstance().getCloudServiceGroupManager().getLobbyGroupByName("Lobby");
        ICloudService server = null;
        inv.setItem(0, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        inv.setItem(8, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        for (int i = 1;i < lobby.getAllServices().size()+1; i++){
            if (lobby.getAllServices().isEmpty()) {
                server = null;
            } else {
                server = lobby.getAllServices().get(i-1);
            }
            if(!server.getState().equals(ServiceState.STARTING) || !server.getState().equals(ServiceState.CLOSED)) {
                inv.addItem(new ItemBuilder(Material.GUNPOWDER).setName("§7"+server.getName()).setLore("§7Spieler§8: §7"+server.getOnlineCount()+"§8/§7"+server.getMaxPlayers()).build());
            }
        }
        ICloudServerGroup PLobby = CloudAPI.getInstance().getCloudServiceGroupManager().getServerGroupByName("PremiumLobby");
        for (int i = 1;i < PLobby.getAllServices().size()+1; i++){
            if (PLobby.getAllServices().isEmpty()) {
                server = null;
            } else {
                server = PLobby.getAllServices().get(i-1);
            }
            if(!server.getState().equals(ServiceState.STARTING) || !server.getState().equals(ServiceState.CLOSED)) {
                inv.addItem(new ItemBuilder(Material.GLOWSTONE_DUST).setName("§6"+server.getName()).setLore("§7Spieler§8: §7"+server.getOnlineCount()+"§8/§7"+server.getMaxPlayers()).build());
            }
        }
        try {
            inv.addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§0").build());
            inv.addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§1").build());
            inv.addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§2").build());
            inv.addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§3").build());
            inv.addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§4").build());
            inv.addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§5").build());
            inv.addItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§7").build());
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
        p.openInventory(inv);
    }
    static {
        Hotbar.team = new ItemStack[] {null, new ItemBuilder(Material.FEATHER).setName("§6Fly").build()};
    }
    private static ICloudPlayer getCloudPlayer(Player p) {
        try {
            return (ICloudPlayer)CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(p.getUniqueId()).get();
        } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
