package de.dermaster.lobbysystem.MySQL;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.utils.Config;
import de.dermaster.lobbysystem.utils.FileHelper;
import de.dermaster.lobbysystem.utils.Hotbar;
import de.dermaster.lobbysystem.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Rüstung(State INT, RüstungsName VARCHAR(255), Name VARCHAR(255));");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS RüstungE(RüstungsName VARCHAR(255), Name VARCHAR(255));");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Settings(Name VARCHAR(255), Animation INT, Farbe INT, JoinFly INT, SpawnJoin INT, CordsX INT, CordsY INT,CordsZ INT);");
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
        ps = con.prepareStatement("INSERT INTO `Settings`(`Name`, `Animation`, `Farbe`, `JoinFly`, `SpawnJoin`, `CordsX`, `CordsY`, `CordsZ`) VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(1, Bukkit.getPlayer(java.util.UUID.fromString(UUID)).getName());
        ps.setInt(2, 0);
        ps.setInt(3, 0);
        ps.setInt(4, 0);
        ps.setInt(5, 1);
        ps.setInt(6, (int) Config.getSpawnLocation().getX());
        ps.setInt(7, (int) Config.getSpawnLocation().getY());
        ps.setInt(8, (int) Config.getSpawnLocation().getZ());
        ps.execute();
        ps = con.prepareStatement("INSERT INTO RüstungE(RüstungsName, Name) VALUES (?,?)");
        ps.setString(1, "null");
        ps.setString(2, Bukkit.getPlayer(java.util.UUID.fromString(UUID)).getName());
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
    //>>Rüstung<<
    public static void buyRüstung(String Pname, String Rüstung) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO Rüstung(RüstungsName, Name) VALUES (?,?)");
        ps.setString(1, Rüstung);
        ps.setString(2, Pname);
        ps.execute();
    }
    public static boolean getRüstung(String PName, String Rüstung) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Rüstung` WHERE RüstungsName=\""+Rüstung+"\";");
        while (rs.next()) {
            if(rs.getString("Name").contains(PName)){
                return true;
            }
        }
        return false;
    }
    public static void EquipRüstung(String Pname, String Rüstung) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE `RüstungE` SET RüstungsName='"+Rüstung+"' WHERE Name=\""+Pname+"\";");
        ps.execute();
    }
    //>>Settings<<
    public static int getStateS(String Name, String Setting) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Settings` WHERE Name=\""+Name+"\";");
        while (rs.next()) {
            switch (Setting){
                case "Animation":
                    return rs.getInt("Animation");
                case "Farbe":
                    return rs.getInt("Farbe");
                case "JoinFly":
                    return rs.getInt("JoinFly");
                case "SpawnJoin":
                    return rs.getInt("SpawnJoin");
                default:
                    break;
            }
        }
        return 1;
    }
    public static Location getLocS(String Name) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM `Settings` WHERE Name=\""+Name+"\";");
        while (rs.next()) {
            return new Location(Config.getSpawnLocation().getWorld(), rs.getInt("CordsX"), rs.getInt("CordsY"), rs.getInt("CordsZ"));
        }
        return Config.getSpawnLocation();
    }
    public static void setLocS(String Name, Location loc) throws SQLException{
        LobbySystem.getInstance().getLogger().info("UPDATE `Settings` SET `CordsX`='"+(int)loc.getX()+"',`CordsY`='"+(int)loc.getY()+"',`CordsZ`=`"+(int)loc.getZ()+"` WHERE Name=\""+Name+"\";");
        PreparedStatement ps = con.prepareStatement("UPDATE `Settings` SET CordsX='"+(int)loc.getX()+"',CordsY='"+(int)loc.getY()+"',CordsZ='"+(int)loc.getZ()+"' WHERE Name=\""+Name+"\";");
        ps.execute();
    }
    public static void setStateS(String Setting,int state, String Name) throws SQLException {
        switch (Setting){
            case "Animation":
                PreparedStatement ps = con.prepareStatement("UPDATE `Settings` SET Animation='"+state+"' WHERE Name=\""+Name+"\";");
                ps.execute();
                break;
            case "Farbe":
                ps = con.prepareStatement("UPDATE `Settings` SET Farbe='"+state+"' WHERE Name=\""+Name+"\";");
                ps.execute();
                break;
            case "JoinFly":
                ps = con.prepareStatement("UPDATE `Settings` SET JoinFly='"+state+"' WHERE Name=\""+Name+"\";");
                ps.execute();
                break;
            case "SpawnJoin":
                ps = con.prepareStatement("UPDATE `Settings` SET SpawnJoin='"+state+"' WHERE Name=\""+Name+"\";");
                ps.execute();
                break;
            default:
                break;

        }

    }
    public static void sendSettings(Player p) throws SQLException {
        Inventory inv = Bukkit.createInventory(null, 27 * 2, "§cEinstellungen");
        Hotbar.SetRandd(inv, p);
        Bukkit.getScheduler().runTaskLater(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
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
            }
        }, 6);
        p.openInventory(inv);
        if(getStateS(p.getName(), "Animation") == 1){
            inv.setItem(19, new ItemBuilder(Material.STICKY_PISTON).setName("§2Animation").setLore("§7× §aAktiviert", "§7× Stelle ein, ob deine Hotbar animiert ist.").build());
        }else {
            inv.setItem(19, new ItemBuilder(Material.STICKY_PISTON).setName("§2Animation").setLore("§7× §cDeaktiviert", "§7× Stelle ein, ob deine Hotbar animiert ist.").build());
        }
        if(getStateS(p.getName(), "JoinFly") == 1){
            inv.setItem(21, new ItemBuilder(Material.FEATHER).setName("§cAutoFly").setLore("§7× §aAktiviert", "§7× Stelle ein, ob du beim Joinen automatisch fliegst.").build());
        }else {
            inv.setItem(21, new ItemBuilder(Material.FEATHER).setName("§cAutoFly").setLore("§7× §cDeaktiviert", "§7× Stelle ein, ob du beim Joinen automatisch fliegst.").build());
        }
        switch (getStateS(p.getName(), "Farbe")){
            case 0:
                inv.setItem(23, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName("§8Farbe").setLore("§7× §8Grau", "§7× Stelle ein, welche Farbe im GUI ist.").build());
                break;
            case 1:
                inv.setItem(23, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName("§aFarbe").setLore("§7× §aGrün", "§7× Stelle ein, welche Farbe im GUI ist.").build());
                break;
            case 2:
                inv.setItem(23, new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setName("§cFarbe").setLore("§7× §cRot", "§7× Stelle ein, welche Farbe im GUI ist.").build());
                break;
            case 3:
                inv.setItem(23, new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setName("§5Farbe").setLore("§7× §5Purple", "§7× Stelle ein, welche Farbe im GUI ist.").build());
                break;
            case 4:
                inv.setItem(23, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setName("§9Farbe").setLore("§7× §9Blau", "§7× Stelle ein, welche Farbe im GUI ist.").build());
                break;
        }
        if(getStateS(p.getName(), "SpawnJoin") == 1){
            inv.setItem(25, new ItemBuilder(Material.ENCHANTING_TABLE).setName("§7Spawnen").setLore("§7× §aAktiviert", "§7× Stelle ein, ob du beim Joinen am Spawn spawnst oder an deiner letzten Position.").build());
        }else {
            inv.setItem(25, new ItemBuilder(Material.ENCHANTING_TABLE).setName("§7Spawnen").setLore("§7× §cDeaktiviert", "§7× Stelle ein, ob du beim Joinen am Spawn spawnst oder an deiner letzten Position.").build());
        }
        p.updateInventory();
    }
}
