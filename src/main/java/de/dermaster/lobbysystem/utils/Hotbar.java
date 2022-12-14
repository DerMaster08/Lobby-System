package de.dermaster.lobbysystem.utils;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.MySQL.MySQL;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.service.ServiceState;
import eu.thesimplecloud.api.servicegroup.grouptype.ICloudServerGroup;
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

                        p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("┬žoNavigator").build());
                    }
                }, 1*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                            Playerhider.getPlayerHider(p);
                    }
                }, 2*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(3,  new ItemBuilder(Material.CHEST).setName("┬žoCosmetics").build());
                    }
                }, 3*20L);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        p.getInventory().setItem(5, new ItemBuilder(Material.NETHER_STAR).setName("┬žo┬žf┬žoLobbySwitcher").build());
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
                        p.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setName("┬žo┬žf┬žoProfil").setOwner(p.getName()).build());
                    }
                }, 6*20L);
            }else {
                p.getInventory().setItem(0, new ItemBuilder(Material.COMPASS).setName("┬žoNavigator").build());
                Playerhider.getPlayerHider(p);
                p.getInventory().setItem(3,  new ItemBuilder(Material.CHEST).setName("┬žoCosmetic").build());
                p.getInventory().setItem(5, new ItemBuilder(Material.NETHER_STAR).setName("┬žo┬žf┬žoLobbySwitcher").build());
                GadgetsClass.getGadget(p);
                p.getInventory().setItem(8, new ItemBuilder(Material.PLAYER_HEAD).setName("┬žo┬žf┬žoProfil").setOwner(p.getName()).build());
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
                final ItemStack Bauserver = new ItemBuilder(Material.TRIDENT).setName("┬žoTeamServer").build();
                p.getInventory().setItem(7, Bauserver);
    }
    public static void openTeam(final Player p) {
        Inventory inv = Bukkit.createInventory(null, 9*5, "TeamServer");
        p.openInventory(inv);
        SetRand(inv, p);
        ICloudPlayer cloudPlayer = getCloudPlayer(p);
        ICloudServerGroup group = CloudAPI.getInstance().getCloudServiceGroupManager().getServerGroupByName("BauServer");
        ICloudService server = null;
        if (group.getAllServices().isEmpty()) {
            server = null;
        } else {
            server = group.getAllServices().get(0);
        }
        if (server == null) {
            inv.setItem(20, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("┬ž2BauServer").setLore("┬ž4Offline").build());
        } else if (server.getState().equals(ServiceState.CLOSED)) {
            inv.setItem(20, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("┬ž2BauServer").setLore("┬ž4Offline").build());
        } else if (server.getState().equals(ServiceState.STARTING)) {
            inv.setItem(20, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("┬ž2BauServer").setLore("┬žeStartet").build());
        } else {
            inv.setItem(20, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("┬ž2BauServer").setLore("┬žaOnline").build());
        }
        group = CloudAPI.getInstance().getCloudServiceGroupManager().getServerGroupByName("TestServer");
        server = null;
        if (group.getAllServices().isEmpty()) {
            server = null;
        } else {
            server = group.getAllServices().get(0);
        }
        if (server == null) {
            inv.setItem(24, new ItemBuilder(Material.REDSTONE).setName("┬ž4TestServer").setLore("┬ž4Offline").build());
        } else if (server.getState().equals(ServiceState.CLOSED)) {
            inv.setItem(24, new ItemBuilder(Material.REDSTONE).setName("┬ž4TestServer").setLore("┬ž4Offline").build());
        } else if (server.getState().equals(ServiceState.STARTING)) {
            inv.setItem(24, new ItemBuilder(Material.REDSTONE).setName("┬ž4TestServer").setLore("┬žeStartet").build());
        } else {
            inv.setItem(24, new ItemBuilder(Material.REDSTONE).setName("┬ž4TestServer").setLore("┬žaOnline").build());
        }
    }
    public static void openNavigator(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*5, "Navigator");
        SetRand(inv,p);
        inv.setItem(13+9, new ItemBuilder(Material.NETHER_STAR).setName("Spawn").build());
        inv.setItem(20, new ItemBuilder(Material.GRASS_BLOCK).setName("Skyblock").build());
        //inv.setItem(25, new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("Citybuild").build());
        inv.setItem(24, new ItemBuilder(Material.WOODEN_AXE).setName("GunGame").build());
        p.openInventory(inv);
    }
    public static void openCosmetik(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*5, "Cosmetic");
        p.openInventory(inv);
        SetRand(inv,p);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(11+9, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("R├╝stung").build());
            }
        }, 6);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(13+9, new ItemBuilder(Material.PLAYER_HEAD).setName("K├Âpfe").setOwner(p.getName()).build());
            }
        }, 7);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(15+9, new ItemBuilder(Material.FISHING_ROD).setName("Gadgets").build());
            }
        }, 8);
    }
    public static void openR├╝stung(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*6, "R├╝stung");
        SetRandd(inv, p);
        inv.setItem(7+9, new ItemBuilder(Material.LEATHER_HELMET).setName("┬ž5Rainbow Helm").setColor().build());
        inv.setItem(16+9, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("┬ž5Rainbow Chestplate").setColor().build());
        inv.setItem(25+9, new ItemBuilder(Material.LEATHER_LEGGINGS).setName("┬ž5Rainbow Hose").setColor().build());
        inv.setItem(34+9, new ItemBuilder(Material.LEATHER_BOOTS).setName("┬ž5Rainbow Schuhe").setColor().build());

        inv.setItem(6+9, new ItemBuilder(Material.NETHERITE_HELMET).setName("┬ž0Netherite Helm").build());
        inv.setItem(15+9, new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("┬ž0Netherite Chestplate").build());
        inv.setItem(24+9, new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("┬ž0Netherite Hose").build());
        inv.setItem(33+9, new ItemBuilder(Material.NETHERITE_BOOTS).setName("┬ž0Netherite Schuhe").build());

        inv.setItem(5+9, new ItemBuilder(Material.DIAMOND_HELMET).setName("┬žbDiamond Helm").build());
        inv.setItem(14+9, new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("┬žbDiamond Chestplate").build());
        inv.setItem(23+9, new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("┬žbDiamond Hose").build());
        inv.setItem(32+9, new ItemBuilder(Material.DIAMOND_BOOTS).setName("┬žbDiamond Schuhe").build());

        inv.setItem(4+9, new ItemBuilder(Material.GOLDEN_HELMET).setName("┬žeGold Helm").build());
        inv.setItem(13+9, new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("┬žeGold Chestplate").build());
        inv.setItem(22+9, new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("┬žeGold Hose").build());
        inv.setItem(31+9, new ItemBuilder(Material.GOLDEN_BOOTS).setName("┬žeGold Schuhe").build());

        inv.setItem(3+9, new ItemBuilder(Material.IRON_HELMET).setName("┬ž7Eisen Helm").build());
        inv.setItem(12+9, new ItemBuilder(Material.IRON_CHESTPLATE).setName("┬ž7Eisen Chestplate").build());
        inv.setItem(21+9, new ItemBuilder(Material.IRON_LEGGINGS).setName("┬ž7Eisen Hose").build());
        inv.setItem(30+9, new ItemBuilder(Material.IRON_BOOTS).setName("┬ž7Eisen Schuhe").build());

        inv.setItem(2+9, new ItemBuilder(Material.CHAINMAIL_HELMET).setName("┬ž8Chain Helm").build());
        inv.setItem(11+9, new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("┬ž8Chain Chestplate").build());
        inv.setItem(20+9, new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("┬ž8Chain Hose").build());
        inv.setItem(29+9, new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("┬ž8Chain Schuhe").build());

        inv.setItem(1+9, new ItemBuilder(Material.LEATHER_HELMET).setName("┬ž6Leder Helm").build());
        inv.setItem(10+9, new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("┬ž6Leder Chestplate").build());
        inv.setItem(19+9, new ItemBuilder(Material.LEATHER_LEGGINGS).setName("┬ž6Leder Hose").build());
        inv.setItem(28+9, new ItemBuilder(Material.LEATHER_BOOTS).setName("┬ž6Leder Schuhe").build());
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(27+9+9, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("┬žcZur├╝ck").build());
            }
        }, 6);
        p.openInventory(inv);
    }
    public static void openK├Âpfe(Player p) throws SQLException {
        Inventory inv = Bukkit.createInventory(null, 9*5, "K├Âpfe");
        p.openInventory(inv);
        SetRand(inv,p);
        //>>Team<<
        if(MySQL.getKopf(p.getName(), "┬ž4LbGame")) {
            inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž4LbGame").setOwner("LbGame").build());
        }else {
            inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž4LbGame").setOwner("LbGame").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "┬ž4Lenniilll")) {
            inv.setItem(11, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž4Lenniilll").setOwner("Lenniilll").build());
        }else {
            inv.setItem(11, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž4Lenniilll").setOwner("Lenniilll").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "┬žcLetsVrime")) {
            inv.setItem(12, new ItemBuilder(Material.PLAYER_HEAD).setName("┬žcLetsVrime").setOwner("LetsVrime").build());
        }else {
            inv.setItem(12, new ItemBuilder(Material.PLAYER_HEAD).setName("┬žcLetsVrime").setOwner("LetsVrime").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "┬žaMasterBen007")) {
            inv.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setName("┬žaMasterBen007").setOwner("MasterBen007").build());
        }else {
            inv.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setName("┬žaMasterBen007").setOwner("MasterBen007").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        //>>Andere<<
        if(MySQL.getKopf(p.getName(), "┬ž7Steve")) {
            inv.setItem(14, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Steve").setOwner("Steve").build());
        }else {
            inv.setItem(14, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Steve").setOwner("Steve").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "┬ž7Geschenk")) {
            inv.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Geschenk").setOwner("MHF_Present1").build());
        }else {
            inv.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Geschenk").setOwner("MHF_Present1").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "┬ž7Villager")) {
            inv.setItem(16, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Villager").setOwner("MHF_Villager").build());
        }else {
            inv.setItem(16, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Villager").setOwner("MHF_Villager").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "┬ž7Roter Slime")) {
            inv.setItem(19, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Roter Slime").setOwner("ISmellGood21").build());
        }else {
            inv.setItem(19, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Roter Slime").setOwner("ISmellGood21").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "┬ž7Muffin")) {
            inv.setItem(20, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Muffin").setOwner("Molflin").build());
        }else {
            inv.setItem(20, new ItemBuilder(Material.PLAYER_HEAD).setName("┬ž7Muffin").setOwner("Molflin").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getKopf(p.getName(), "┬ž7Glassblock")) {
            inv.setItem(21, new ItemBuilder(Material.GLASS).setName("┬ž7Glassblock").build());
        }else {
            inv.setItem(21, new ItemBuilder(Material.GLASS).setName("┬ž7Glassblock").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        /*inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("").setOwner("").build());
        inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("").setOwner("").build());
        inv.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setName("").setOwner("").build());*/

        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(35+9, new ItemBuilder(Material.BARRIER).setName("┬žcDen Kopf ausziehen").build());
                inv.setItem(27+9, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("┬žcZur├╝ck").build());
            }
        }, 6);
        p.openInventory(inv);
    }
    public static void openGadgets(Player p) throws SQLException {
        Inventory inv = Bukkit.createInventory(null, 9*5, "Gadgets");
        SetRand(inv,p);
        if(MySQL.getGadgets(String.valueOf(p.getUniqueId()), "Grappler") == 1) {
            inv.setItem(10, new ItemBuilder(Material.FISHING_ROD).setName("Grapling Hook").build());
        }else {
            inv.setItem(10, new ItemBuilder(Material.FISHING_ROD).setName("┬ž4Grapling Hook").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        if(MySQL.getGadgets(String.valueOf(p.getUniqueId()), "Enderpearl") == 1) {
            inv.setItem(12, new ItemBuilder(Material.ENDER_PEARL).setName("Enderperle").build());
        }else {
            inv.setItem(12, new ItemBuilder(Material.ENDER_PEARL).setName("┬ž4Enderperle").setLore("┬žcDu musst dir dies noch Kaufen!\n ┬ž7Preis┬ž8: ┬ž61000Coins").build());
        }
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(27+9, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("┬žcZur├╝ck").build());
            }
        }, 6);
        p.openInventory(inv);
    }
    public static void openLobbyswitcher(Player p){
        Inventory inv = Bukkit.createInventory(null, 5*9, "Lobbys");
        SetRand(inv,p);
        p.openInventory(inv);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                ICloudServerGroup lobby = CloudAPI.getInstance().getCloudServiceGroupManager().getLobbyGroupByName("Lobby");
                ICloudService server = null;
                for (int i = 1;i < lobby.getAllServices().size()+1; i++){
                    if (lobby.getAllServices().isEmpty()) {
                        server = null;
                    } else {
                        server = lobby.getAllServices().get(i-1);
                    }
                    if(!server.getState().equals(ServiceState.STARTING) || !server.getState().equals(ServiceState.CLOSED)) {
                        inv.addItem(new ItemBuilder(Material.GUNPOWDER).setName("┬ž7"+server.getName()).setLore("┬ž7Spieler┬ž8: ┬ž7"+server.getOnlineCount()+"┬ž8/┬ž7"+server.getMaxPlayers()).build());
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
                        inv.addItem(new ItemBuilder(Material.GLOWSTONE_DUST).setName("┬ž6"+server.getName()).setLore("┬ž7Spieler┬ž8: ┬ž7"+server.getOnlineCount()+"┬ž8/┬ž7"+server.getMaxPlayers()).build());
                    }
                }
            }
        }, 6);
    }
    static {
        Hotbar.team = new ItemStack[] {null, new ItemBuilder(Material.FEATHER).setName("┬žoFly").build()};
    }
    private static ICloudPlayer getCloudPlayer(Player p) {
        try {
            return (ICloudPlayer)CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(p.getUniqueId()).get();
        } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void SetRand(Inventory inv, Player p){
        ItemStack White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
        ItemStack Black = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("┬ž6").build();
        ItemStack Gray = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("┬ž4").build();
        try {
            switch (MySQL.getStateS(p.getName(), "Farbe")) {
                case 0:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
                case 1:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
                case 2:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
                case 3:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
                case 4:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        ItemStack finalWhite = White;
        ItemStack finalBlack = Black;
        ItemStack finalGray = Gray;
        inv.setItem(9, finalWhite);
        inv.setItem(17, finalWhite);
        inv.setItem(9+9, finalWhite);
        inv.setItem(17+9, finalWhite);
        inv.setItem(9+9+9, finalWhite);
        inv.setItem(17+9+9, finalWhite);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalWhite);
                inv.setItem(8, finalWhite);
                inv.setItem(9, finalBlack);
                inv.setItem(17, finalBlack);
                inv.setItem(9+9, finalWhite);
                inv.setItem(17+9, finalWhite);
                inv.setItem(9+9+9, finalBlack);
                inv.setItem(17+9+9, finalBlack);
                inv.setItem(9+9+9+9, finalWhite);
                inv.setItem(17+9+9+9, finalWhite);
            }
        }, 1);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalBlack);
                inv.setItem(1, finalWhite);
                inv.setItem(7, finalWhite);
                inv.setItem(8, finalBlack);
                inv.setItem(9, finalGray);
                inv.setItem(17, finalGray);
                inv.setItem(9+9, finalWhite);
                //inv.setItem(17+9, White);
                inv.setItem(9+9+9, finalGray);
                inv.setItem(17+9+9, finalGray);
                inv.setItem(9+9+9+9, finalBlack);
                inv.setItem(17+9+9+9, finalBlack);
                inv.setItem(1+9+9+9+9, finalWhite);
                inv.setItem(7+9+9+9+9, finalWhite);
            }
        }, 2);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalBlack);
                inv.setItem(1, finalWhite);
                inv.setItem(7, finalWhite);
                inv.setItem(8, finalBlack);
                inv.setItem(9, finalGray);
                inv.setItem(17, finalGray);
                inv.setItem(9+9, finalWhite);
                inv.setItem(17+9, finalWhite);
                inv.setItem(9+9+9, finalGray);
                inv.setItem(17+9+9, finalGray);
                inv.setItem(9+9+9+9, finalBlack);
                inv.setItem(17+9+9+9, finalBlack);
                inv.setItem(1+9+9+9+9, finalWhite);
                inv.setItem(7+9+9+9+9, finalWhite);
            }
        }, 3);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalBlack);
                inv.setItem(1, finalGray);
                inv.setItem(2, finalWhite);
                inv.setItem(6, finalWhite);
                inv.setItem(7, finalGray);
                inv.setItem(8, finalBlack);
                inv.setItem(9, finalGray);
                inv.setItem(17, finalGray);
                inv.setItem(9+9, finalWhite);
                inv.setItem(17+9, finalWhite);
                inv.setItem(9+9+9, finalGray);
                inv.setItem(17+9+9, finalGray);
                inv.setItem(9+9+9+9, finalBlack);
                inv.setItem(17+9+9+9, finalBlack);
                inv.setItem(1+9+9+9+9, finalGray);
                inv.setItem(7+9+9+9+9, finalGray);
                inv.setItem(2+9+9+9+9, finalWhite);
                inv.setItem(6+9+9+9+9, finalWhite);
            }
        }, 4);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalBlack);
                inv.setItem(1, finalGray);
                inv.setItem(2, finalWhite);
                inv.setItem(3, finalGray);
                inv.setItem(4, finalBlack);
                inv.setItem(5, finalGray);
                inv.setItem(6, finalWhite);
                inv.setItem(7, finalGray);
                inv.setItem(8, finalBlack);
                inv.setItem(9, finalGray);
                inv.setItem(17, finalGray);
                inv.setItem(9+9, finalWhite);
                inv.setItem(17+9, finalWhite);
                inv.setItem(9+9+9, finalGray);
                inv.setItem(17+9+9, finalGray);
                inv.setItem(9+9+9+9, finalBlack);
                inv.setItem(17+9+9+9, finalBlack);
                inv.setItem(1+9+9+9+9, finalGray);
                inv.setItem(7+9+9+9+9, finalGray);
                inv.setItem(2+9+9+9+9, finalWhite);
                inv.setItem(8+9+9+9+9, finalBlack);
                inv.setItem(3+9+9+9+9, finalGray);
                inv.setItem(6+9+9+9+9, finalWhite);
                inv.setItem(4+9+9+9+9, finalBlack);
                inv.setItem(5+9+9+9+9, finalGray);
            }
        }, 5);
    }
    public static void SetRandd(Inventory inv, Player p) {
        ItemStack White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
        ItemStack Black = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("┬ž6").build();
        ItemStack Gray = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("┬ž4").build();
        try {
            switch (MySQL.getStateS(p.getName(), "Farbe")) {
                case 0:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
                case 1:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
                case 2:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
                case 3:
                    White = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Black = new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setName("┬ž6").build();
                    Gray = new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE).setName("┬ž4").build();
                    break;
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        inv.setItem(9, White);
        inv.setItem(17, White);
        inv.setItem(9 + 9, White);
        inv.setItem(17 + 9, White);
        inv.setItem(9 + 9 + 9, White);
        inv.setItem(17 + 9 + 9, White);
        inv.setItem(17 + 9 + 9 +9, White);
        ItemStack finalWhite = White;
        ItemStack finalBlack = Black;
        ItemStack finalGray = Gray;
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalWhite);
                inv.setItem(8, finalWhite);
                inv.setItem(9, finalBlack);
                inv.setItem(17, finalBlack);
                inv.setItem(9 + 9, finalWhite);
                inv.setItem(17 + 9, finalWhite);
                inv.setItem(9 + 9 + 9, finalWhite);
                inv.setItem(17 + 9 + 9, finalWhite);
                inv.setItem(9 + 9 + 9 + 9, finalBlack);
                inv.setItem(17 + 9 + 9 + 9, finalBlack);
                inv.setItem(9 + 9 + 9 + 9 + 9, finalWhite);
                inv.setItem(17 + 9 + 9 + 9 + 9, finalWhite);
            }
        }, 1);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalBlack);
                inv.setItem(1, finalWhite);
                inv.setItem(7, finalWhite);
                inv.setItem(8, finalBlack);
                inv.setItem(9, finalGray);
                inv.setItem(17, finalGray);
                inv.setItem(9 + 9, finalWhite);
                inv.setItem(9 + 9 + 9, finalWhite);
                //inv.setItem(17+9, White);
                inv.setItem(9 + 9 + 9 + 9, finalGray);
                inv.setItem(17 + 9 + 9 + 9, finalGray);
                inv.setItem(9 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(17 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(1 + 9 + 9 + 9 + 9 + 9, finalWhite);
                inv.setItem(7 + 9 + 9 + 9 + 9 + 9, finalWhite);
            }
        }, 2);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalBlack);
                inv.setItem(1, finalWhite);
                inv.setItem(7, finalWhite);
                inv.setItem(8, finalBlack);
                inv.setItem(9, finalGray);
                inv.setItem(17, finalGray);
                inv.setItem(9 + 9, finalWhite);
                inv.setItem(9 + 9 + 9, finalWhite);
                inv.setItem(17 + 9, finalWhite);
                inv.setItem(17 + 9 + 9, finalWhite);
                //inv.setItem(17+9, White);
                inv.setItem(9 + 9 + 9 + 9, finalGray);
                inv.setItem(17 + 9 + 9 + 9, finalGray);
                inv.setItem(9 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(17 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(1 + 9 + 9 + 9 + 9 + 9, finalWhite);
                inv.setItem(7 + 9 + 9 + 9 + 9 + 9, finalWhite);
            }
        }, 3);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalBlack);
                inv.setItem(1, finalWhite);
                inv.setItem(7, finalWhite);
                inv.setItem(8, finalBlack);
                inv.setItem(9, finalGray);
                inv.setItem(17, finalGray);
                inv.setItem(9 + 9, finalWhite);
                inv.setItem(9 + 9 + 9, finalWhite);
                inv.setItem(17 + 9, finalWhite);
                inv.setItem(17 + 9 + 9, finalWhite);
                inv.setItem(9 + 9 + 9 + 9, finalGray);
                inv.setItem(17 + 9 + 9 + 9, finalGray);
                inv.setItem(9 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(17 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(1 + 9 + 9 + 9 + 9 + 9, finalGray);
                inv.setItem(7 + 9 + 9 + 9 + 9 + 9, finalGray);
                inv.setItem(2 + 9 + 9 + 9 + 9 + 9, finalWhite);
                inv.setItem(6 + 9 + 9 + 9 + 9 + 9, finalWhite);
            }
        }, 4);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, finalBlack);
                inv.setItem(1, finalGray);
                inv.setItem(2, finalWhite);
                inv.setItem(3, finalGray);
                inv.setItem(4, finalBlack);
                inv.setItem(5, finalGray);
                inv.setItem(6, finalWhite);
                inv.setItem(7, finalGray);
                inv.setItem(8, finalBlack);
                inv.setItem(9, finalGray);
                inv.setItem(17, finalGray);
                inv.setItem(9 + 9, finalWhite);
                inv.setItem(9 + 9 + 9, finalWhite);
                inv.setItem(17 + 9, finalWhite);
                inv.setItem(17 + 9 + 9, finalWhite);
                inv.setItem(9 + 9 + 9 + 9, finalGray);
                inv.setItem(17 + 9 + 9 + 9, finalGray);
                inv.setItem(9 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(17 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(1 + 9 + 9 + 9 + 9 + 9, finalGray);
                inv.setItem(7 + 9 + 9 + 9 + 9 + 9, finalGray);
                inv.setItem(2 + 9 + 9 + 9 + 9 + 9, finalWhite);
                inv.setItem(6 + 9 + 9 + 9 + 9 + 9, finalWhite);
                inv.setItem(8 + 9 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(3 + 9 + 9 + 9 + 9 + 9, finalGray);
                inv.setItem(4 + 9 + 9 + 9 + 9 + 9, finalBlack);
                inv.setItem(5 + 9 + 9 + 9 + 9 + 9, finalGray);
            }
        }, 5);
    }
}
