package de.dermaster.lobbysystem.MySQL;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.utils.Hotbar;
import de.dermaster.lobbysystem.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.*;
import java.util.ArrayList;

public class MySQLf {
    private static Connection con = null;
    public static void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection("jdbc:mysql://85.214.204.242:3306/BungeeSystem?autoReconnect=true&amp","Server","Server08!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static int getFriendListSidesCount(int i) {
        int j = 0;
        while (j * 27 < i) {
            j++;
        }
        return j;
    }
    public static int getfriends(String Namef) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `PlayerStats` WHERE Name=\""+Namef+"\";");
        while (rs.next()){
            return rs.getInt("Friends");
        }
        return 0;
    }
    public static int getFas(String Namef) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `PlayerStats` WHERE Name=\""+Namef+"\";");
        while (rs.next()){
            return rs.getInt("Fas");
        }
        return 0;
    }
    public static ArrayList<String> getFriendssorted(String Namef) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Friends` WHERE Namef=\""+Namef+"\";");
        while (rs.next()) {
            ArrayList<String> result = new ArrayList<>();
            for(int i = 0; i < getfriends(Namef); i++) {
                String allFriends = rs.getString("Namet");
                result.add(allFriends);
                rs.next();
            }
            return result;
        }
        return new ArrayList<>();
    }
    public static ArrayList<String> getFassorted(String Namef) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Request` WHERE Namef=\""+Namef+"\";");
        while (rs.next()) {
            ArrayList<String> result = new ArrayList<>();
            for(int i = 0; i < getFas(Namef); i++) {
                String allFriends = rs.getString("Namet");
                result.add(allFriends);
                rs.next();
            }
            return result;
        }
        return new ArrayList<>();
    }
    public static void sendFriendList(Player p, int page) {
        try {
            ArrayList<String> friends = getFriendssorted(p.getName());
            if(friends.size() == 0 || ((String)friends.get(0)).equals("")) {
                Inventory inv = Bukkit.createInventory(null, 9*6, "§bSeite: 0/" + getFriendListSidesCount(friends.size()));
                Hotbar.SetRandd(inv, p);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        ItemStack friend = new ItemBuilder(Material.GOLDEN_HELMET).setName("§6Deine Freunde").build();
                        ItemMeta friendm = friend.getItemMeta();
                        friendm.addEnchant(Enchantment.DURABILITY, -1, true);
                        friendm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        friendm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        friendm.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        friend.setItemMeta(friendm);
                        inv.setItem(1, friend);
                        inv.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Freundschaftsanfragen").setOwner("MHF_Question").build());
                        inv.setItem(6, new ItemBuilder(Material.CAKE).setName("§5Party").build());
                        inv.setItem(7, new ItemBuilder(Material.COMPARATOR).setName("§cEinstellungen").build());
                        inv.setItem(31, new ItemBuilder(Material.PLAYER_HEAD).setName("§cDu hast keine Freunde!").setOwner("MHF_Exclamation").build());
                    }
                }, 6);
                p.openInventory(inv);
            } else {
                Inventory inv = Bukkit.createInventory(null, 27 * 2, "§bSeite: " + page + "/" + getFriendListSidesCount(friends.size()));
                Hotbar.SetRandd(inv, p);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        ItemStack friend = new ItemBuilder(Material.GOLDEN_HELMET).setName("§6Deine Freunde").build();
                        ItemMeta friendm = friend.getItemMeta();
                        friendm.addEnchant(Enchantment.DURABILITY, -1, true);
                        friendm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        friendm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        friendm.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        friend.setItemMeta(friendm);
                        inv.setItem(1, friend);
                        inv.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Freundschaftsanfragen").setOwner("MHF_Question").build());
                        inv.setItem(6, new ItemBuilder(Material.CAKE).setName("§5Party").build());
                        inv.setItem(7, new ItemBuilder(Material.COMPARATOR).setName("§cEinstellungen").build());
                        if (getFriendListSidesCount(friends.size()) >= page + 1) {
                            int page1 = page + 1;
                            inv.setItem(53, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowRight").setName("§bSeite: " + page1).build());
                        }
                        if (page != 1) {
                            int page1 = page - 1;
                            inv.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("§bSeite: " + page1).build());
                        }
                        p.openInventory(inv);
                        int local = 18;
                        int f = friends.size();
                        int multipliyer = 27;
                        if (page == 1) {
                            multipliyer = 0;
                        } else {
                            multipliyer = multipliyer * (page - 1);
                        }
                        for (int i1 = 18; i1 < f + local; ) {
                            if (i1 + multipliyer - local == f) {
                                return;
                            } else if (i1 + multipliyer == 45 + multipliyer) {
                                return;
                            } else {
                                inv.addItem(new ItemBuilder(Material.PLAYER_HEAD).setName(friends.get(i1 + multipliyer - local)).setOwner(friends.get(i1 + multipliyer - local)).build());
                            }
                            i1++;
                        }
                    }
                }, 6);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendFasList(Player p, int page) {
        try {
            ArrayList<String> friends = getFassorted(p.getName());
            if(friends.size() == 0 || ((String)friends.get(0)).equals("")) {
                Inventory inv = Bukkit.createInventory(null, 9*6, "§bSeite: 0/" + getFriendListSidesCount(friends.size()));
                Hotbar.SetRandd(inv, p);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        ItemStack friend = new ItemBuilder(Material.PLAYER_HEAD).setName("§7Freundschaftsanfragen").setOwner("MHF_Question").build();
                        ItemMeta friendm = friend.getItemMeta();
                        friendm.addEnchant(Enchantment.VANISHING_CURSE, -1, true);
                        friendm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        friendm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        friendm.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        friend.setItemMeta(friendm);
                        inv.setItem(1, new ItemBuilder(Material.GOLDEN_HELMET).setName("§6Deine Freunde").build());
                        inv.setItem(2, friend);
                        inv.setItem(6, new ItemBuilder(Material.CAKE).setName("§5Party").build());
                        inv.setItem(7, new ItemBuilder(Material.COMPARATOR).setName("§cEinstellungen").build());
                        inv.setItem(31, new ItemBuilder(Material.PLAYER_HEAD).setName("§cDu hast keine Freundschaftsanfragen erhalten!").setOwner("MHF_Exclamation").build());
                    }
                }, 6);
                p.openInventory(inv);
            } else {
                Inventory inv = Bukkit.createInventory(null, 27 * 2, "§bSeite: " + page + "/" + getFriendListSidesCount(friends.size()));
                Hotbar.SetRandd(inv, p);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        ItemStack friend = new ItemBuilder(Material.PLAYER_HEAD).setName("§7Freundschaftsanfragen").setOwner("MHF_Question").build();
                        ItemMeta friendm = friend.getItemMeta();
                        friendm.addEnchant(Enchantment.VANISHING_CURSE, -1, true);
                        friendm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        friendm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        friendm.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        friend.setItemMeta(friendm);
                        inv.setItem(1, new ItemBuilder(Material.GOLDEN_HELMET).setName("§6Deine Freunde").build());
                        inv.setItem(2, friend);
                        inv.setItem(6, new ItemBuilder(Material.CAKE).setName("§5Party").build());
                        inv.setItem(7, new ItemBuilder(Material.COMPARATOR).setName("§cEinstellungen").build());
                        p.openInventory(inv);
                        if (getFriendListSidesCount(friends.size()) >= page + 1) {
                            int page1 = page + 1;
                            inv.setItem(53, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowRight").setName("§bSeite: " + page1).build());
                        }
                        if (page != 1) {
                            int page1 = page - 1;
                            inv.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("§bSeite: " + page1).build());
                        }
                        int local = 18;
                        int f = friends.size();
                        int multipliyer = 27;
                        if (page == 1) {
                            multipliyer = 0;
                        } else {
                            multipliyer = multipliyer * (page - 1);
                        }
                        for (int i1 = 18; i1 < f + local; ) {
                            if (i1 + multipliyer - local == f) {
                                p.openInventory(inv);
                                return;
                            } else if (i1 + multipliyer == 45 + multipliyer) {
                                p.openInventory(inv);
                                return;
                            } else {
                                inv.addItem(new ItemBuilder(Material.PLAYER_HEAD).setName(friends.get(i1 + multipliyer - local)).setOwner(friends.get(i1 + multipliyer - local)).build());
                            }
                            i1++;
                        }
                    }
                }, 6);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //Party
    public static String getOwner(String p) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Party_Members` WHERE Member=\""+p+"\";");
        while (rs.next()){
            if(rs.getString("Partyowner") != null) {
                return rs.getString("Partyowner");
            }
        }
        return null;
    }
    public static ArrayList<String> getPartyMembers(String Name) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Party_Members` WHERE Partyowner=\""+getOwner(Name)+"\";");
        while (rs.next()) {
            ArrayList<String> result = new ArrayList<>();
            for (int i = 0; i < 1000; i++){
                String allFriends = rs.getString("Member");
                if(allFriends.isEmpty()){
                    i=1000;
                    System.out.println(allFriends);
                    return result;
                }else {
                    result.add(allFriends);
                    System.out.println(allFriends);
                    rs.next();
                }
            }
            return result;
        }
        return new ArrayList<>();
    }
    public static String getRang(String Name) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Party_Members` WHERE Partyowner=\""+getOwner(Name)+"\" AND Member=\""+Name+"\";");
        while (rs.next()) {
            return rs.getString("Rang");
        }
        return "Member";
    }
    public static void sendParty(Player p, int page) {
        try {
            ArrayList<String> friends = getPartyMembers(getOwner(p.getName()));
            System.out.println(friends);
            if(friends.size() == 0 || ((String)friends.get(0)).equals("")) {
                Inventory inv = Bukkit.createInventory(null, 9*6, "§bSeite: 0/" + getFriendListSidesCount(friends.size()));
                Hotbar.SetRandd(inv, p);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        ItemStack friend = new ItemBuilder(Material.CAKE).setName("§5Party").build();
                        ItemMeta friendm = friend.getItemMeta();
                        friendm.addEnchant(Enchantment.VANISHING_CURSE, -1, true);
                        friendm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        friendm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        friendm.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        friend.setItemMeta(friendm);
                        inv.setItem(1, new ItemBuilder(Material.GOLDEN_HELMET).setName("§6Deine Freunde").build());
                        inv.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Freundschaftsanfragen").setOwner("MHF_Question").build());
                        inv.setItem(6, friend);
                        inv.setItem(7, new ItemBuilder(Material.COMPARATOR).setName("§cEinstellungen").build());
                        inv.setItem(31, new ItemBuilder(Material.PLAYER_HEAD).setName("§cDu bist in keiner Party!").setOwner("MHF_Exclamation").build());
                    }
                }, 6);
                p.openInventory(inv);
            } else {
                Inventory inv = Bukkit.createInventory(null, 27 * 2, "§bSeite: " + page + "/" + getFriendListSidesCount(friends.size()));
                Hotbar.SetRandd(inv, p);
                Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        ItemStack friend = new ItemBuilder(Material.CAKE).setName("§5Party").build();
                        ItemMeta friendm = friend.getItemMeta();
                        friendm.addEnchant(Enchantment.VANISHING_CURSE, -1, true);
                        friendm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        friendm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        friendm.addItemFlags(ItemFlag.HIDE_PLACED_ON);
                        friend.setItemMeta(friendm);
                        inv.setItem(1, new ItemBuilder(Material.GOLDEN_HELMET).setName("§6Deine Freunde").build());
                        inv.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Freundschaftsanfragen").setOwner("MHF_Question").build());
                        inv.setItem(6, friend);
                        inv.setItem(7, new ItemBuilder(Material.COMPARATOR).setName("§cEinstellungen").build());
                        p.openInventory(inv);
                    if(getFriendListSidesCount(friends.size()) >= page+1) {
                        int page1 = page+1;
                        inv.setItem(53, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowRight").setName("§bSeite: "+page1).build());
                    }
                    if(page != 1){
                        int page1 = page-1;
                        inv.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setOwner("MHF_ArrowLeft").setName("§bSeite: "+page1).build());
                    }
                    int local = 18;
                    int f = friends.size();
                    int multipliyer = 27;
                    if (page == 1){
                        multipliyer = 0;
                    }else {
                        multipliyer = multipliyer*(page-1);
                    }
                    for (int i1 = 18; i1 < f + local;) {
                        if (i1+multipliyer-local == f) {
                            return;
                        } else if (i1+multipliyer == 45+multipliyer) {
                            return;
                        } else  {
                            try {
                                inv.addItem(new ItemBuilder(Material.PLAYER_HEAD).setName(friends.get(i1+multipliyer - local)).setOwner(friends.get(i1+multipliyer - local)).setLore(getRang(friends.get(i1+multipliyer - local))).build());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        i1++;
                    }
                    }
                    }, 6);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
