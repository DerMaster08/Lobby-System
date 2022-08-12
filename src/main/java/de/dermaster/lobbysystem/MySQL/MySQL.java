package de.dermaster.lobbysystem.MySQL;

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
import java.util.Properties;

public class MySQL {
    private static Connection con = null;
    public static void connect() {
        try {
            con = null;
            Class.forName("com.mysql.cj.jdbc.Driver");
            java.util.Properties info = new Properties();
            info.put("user", "Server");
            info.put("password", "Server08!");
            info.put("autoReconnect", true);
            con = DriverManager.getConnection("jdbc:mysql://85.214.204.242:3306/Lobby-System?autoReconnect=true", info);
            Statement stmt = con.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PlayerHider(UUID VARCHAR(255), State INT, Name VARCHAR(255));");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Gadget(UUID VARCHAR(255), State INT, Name VARCHAR(255), Grappler INT, Enderpearl INT);");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Kopf(Kopfname VARCHAR(255), Name VARCHAR(255));");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Settings(Name VARCHAR(255), Animation INT);");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>PlayerHider<<\\
    public static void setStatePH(int state, String UUID) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        PreparedStatement ps = con.prepareStatement("UPDATE `PlayerHider` SET State='"+state+"' WHERE UUID=\""+UUID+"\";");
        ps.execute();
    }
    public static boolean hasaccount(String UUID) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `PlayerHider` WHERE UUID=\""+UUID+"\";");
        while (rs.next()) {
            if(rs.getString("Name").isEmpty()){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }
    public static int getState(String UUID) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `PlayerHider` WHERE UUID=\""+UUID+"\";");
        while (rs.next()) {
                return rs.getInt("State");
        }
        return 1;
    }
    public static void register(String UUID) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        PreparedStatement ps = con.prepareStatement("INSERT INTO PlayerHider (UUID, State, Name) VALUES (?,?,?)");
        ps.setString(1, UUID);
        ps.setInt(2, 1);
        ps.setString(3,Bukkit.getPlayer(java.util.UUID.fromString(UUID)).getName());
        ps.execute();
        ps = con.prepareStatement("INSERT INTO Gadget (UUID, State, Name, Grappler, Enderpearl) VALUES (?,?,?,?,?)");
        ps.setString(1, UUID);
        ps.setInt(2, 0);
        ps.setString(3,Bukkit.getPlayer(java.util.UUID.fromString(UUID)).getName());
        ps.setInt(4, 0);
        ps.setInt(5, 0);
        ps.execute();
        ps = con.prepareStatement("INSERT INTO `Settings`(`Name`, `Animation`) VALUES (?,?)");
        ps.setString(1, Bukkit.getPlayer(java.util.UUID.fromString(UUID)).getName());
        ps.setInt(2, 1);
        ps.execute();
    }
    //>>Gadgets<<\\
    public static void setStateG(int state, String UUID) throws SQLException {
        if(con.isClosed()){
            connect();
        }
        PreparedStatement ps = con.prepareStatement("UPDATE `Gadget` SET State='"+state+"' WHERE UUID=\""+UUID+"\";");
        ps.execute();
    }
    public static int getStateG(String UUID) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Gadget` WHERE UUID=\""+UUID+"\";");
        while (rs.next()) {
            return rs.getInt("State");
        }
        return 0;
    }
    public static void buyGadgets(String UUID, String Gadget) throws SQLException {
        PreparedStatement ps;
        switch (Gadget){
            case "Grappler":
                ps = con.prepareStatement("UPDATE `Gadget` SET Grappler='1' WHERE UUID=\""+UUID+"\";");
                ps.execute();
                break;
            case "Enderpearl":
                ps = con.prepareStatement("UPDATE `Gadget` SET Enderpearl='1' WHERE UUID=\""+UUID+"\";");
                ps.execute();
                break;
            default:
                break;
        }

    }
    public static int getGadgets(String UUID, String Gadget) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Gadget` WHERE UUID=\""+UUID+"\";");
        while (rs.next()) {
            switch (Gadget) {
                case "Grappler":
                    return rs.getInt("Grappler");
                case "Enderpearl":
                    return rs.getInt("Enderpearl");
                default:
                    break;
            }
        }
        return 0;
    }
    //>>Köpfe<<
    public static void buyKopf(String Pname, String Kopf) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO Kopf (Kopfname, Name) VALUES (?,?)");
        ps.setString(1, Kopf);
        ps.setString(2, Pname);
        ps.execute();
    }
    public static boolean getKopf(String PName, String Kopf) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Kopf` WHERE Kopfname=\""+Kopf+"\";");
        while (rs.next()) {
            if(rs.getString("Name").contains(PName)){
                return true;
            }
        }
        return false;
    }
    //>>Settings<<
    public static int getStateS(String Name, String Setting) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Settings` WHERE Name=\""+Name+"\";");
        while (rs.next()) {
            switch (Setting){
                case "Animation":
                    return rs.getInt("Animation");
                default:
                    break;
            }
        }
        return 1;
    }
    public static void setStateS(String Setting,int state, String Name) throws SQLException {
        switch (Setting){
            case "Animation":
            PreparedStatement ps = con.prepareStatement("UPDATE `Settings` SET Animation='"+state+"' WHERE Name=\""+Name+"\";");
            ps.execute();
            break;
            default:
                break;

        }

    }
    public static void sendSettings(Player p) throws SQLException {
        Inventory inv = Bukkit.createInventory(null, 27 * 2, "§cEinstellungen");
        for (int i1 = 0; i1 < inv.getSize(); i1++) {
            inv.setItem(i1, new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName("§6").build());
        }
        ItemStack friend = new ItemBuilder(Material.COMPARATOR).setName("§cEinstellungen").build();
        ItemMeta friendm = friend.getItemMeta();
        friendm.addEnchant(Enchantment.DURABILITY, -1, true);
        friendm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        friendm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        friendm.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        friend.setItemMeta(friendm);
        inv.setItem(1, new ItemBuilder(Material.GOLDEN_HELMET).setName("§6Deine Freunde").build());
        inv.setItem(2, new ItemBuilder(Material.PLAYER_HEAD).setName("§7Freundschaftsanfragen").setOwner("MHF_Question").build());
        inv.setItem(6, new ItemBuilder(Material.CAKE).setName("§5Party").build());
        inv.setItem(7, friend);
        p.openInventory(inv);
        if(getStateS(p.getName(), "Animation") == 1){
            inv.setItem(19, new ItemBuilder(Material.STICKY_PISTON).setName("§2Animation").setLore("§7× §aAktiviert", "§7× Stelle ein, ob deine Hotbar animiert ist.").build());
        }else {
            inv.setItem(19, new ItemBuilder(Material.STICKY_PISTON).setName("§2Animation").setLore("§7× §cDeaktiviert", "§7× Stelle ein, ob deine Hotbar animiert ist.").build());
        }
        p.updateInventory();
    }
}
