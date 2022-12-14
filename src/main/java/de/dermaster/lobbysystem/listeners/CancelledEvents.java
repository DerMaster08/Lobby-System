package de.dermaster.lobbysystem.listeners;

import de.dermaster.lobbysystem.LobbySystem;
import de.dermaster.lobbysystem.MySQL.MySQL;
import de.dermaster.lobbysystem.MySQL.MySQLf;
import de.dermaster.lobbysystem.MySQL.*;
import de.dermaster.lobbysystem.utils.*;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.service.ServiceState;
import eu.thesimplecloud.api.servicegroup.grouptype.ICloudServerGroup;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.weather.*;
import org.bukkit.event.world.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.hanging.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class CancelledEvents implements Listener
{
    static int k = 0;
    static List<Player> color = new ArrayList<>();
    @EventHandler
    public void onHunger(final FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onWeather(final WeatherChangeEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onDaylight(final TimeSkipEvent event) {event.setCancelled(true);}
    
    @EventHandler
    public void onPickUp(final EntityPickupItemEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getEntity().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getWhoClicked().getUniqueId())) {
            return;
        }
        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??2BauServer")) {
            ICloudPlayer cloudPlayer = getCloudPlayer((Player) event.getWhoClicked());
            ICloudServerGroup group = CloudAPI.getInstance().getCloudServiceGroupManager().getServerGroupByName("BauServer");
            ICloudService server = null;
            if (group.getAllServices().isEmpty()) {
                server = null;
            } else {
                server = group.getAllServices().get(0);
            }
            if (server == null) {
                group.startNewService();
                event.getInventory().setItem(20, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("??2BauServer").setLore("??eStartet").build());
            } else if (server.getState().equals(ServiceState.CLOSED)) {
                event.getInventory().setItem(20, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("??2BauServer").setLore("??eStartet").build());
                server.start();
            } else if (server.getState().equals(ServiceState.STARTING)) {
                event.getInventory().setItem(20, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("??2BauServer").setLore("??eStartet").build());
            } else {
                event.getInventory().setItem(20, new ItemBuilder(Material.DIAMOND_PICKAXE).setName("??2BauServer").setLore("??aOnline").build());
                cloudPlayer.connect(server);
            }
            event.setCancelled(true);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??64TestServer")) {
            Inventory inv = event.getInventory();
            ICloudPlayer cloudPlayer = getCloudPlayer((Player) event.getWhoClicked());
            ICloudServerGroup group = CloudAPI.getInstance().getCloudServiceGroupManager().getServerGroupByName("TestServer");
            ICloudService server = null;
            if (group.getAllServices().isEmpty()) {
                server = null;
            } else {
                server = group.getAllServices().get(0);
            }
            if (server == null) {
                group.startNewService();
                inv.setItem(24, new ItemBuilder(Material.REDSTONE).setName("??4TestServer").setLore("??eStartet").build());
            } else if (server.getState().equals(ServiceState.CLOSED)) {
                inv.setItem(24, new ItemBuilder(Material.REDSTONE).setName("??4TestServer").setLore("??eStartet").build());
                server.start();
            } else if (server.getState().equals(ServiceState.STARTING)) {
                inv.setItem(24, new ItemBuilder(Material.REDSTONE).setName("??4TestServer").setLore("??eStartet").build());
            } else {
                inv.setItem(24, new ItemBuilder(Material.REDSTONE).setName("??4TestServer").setLore("??aOnline").build());
                cloudPlayer.connect(server);
            }
            event.setCancelled(true);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("GunGame")) {
            Player p = (Player) event.getWhoClicked();
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 0);
            event.setCancelled(true);
            event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 50));
            p.closeInventory();
            Location loc = FileHelper.getLocation(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", "warp.gungame");
            loc.setY(loc.getY()+3);
            event.getWhoClicked().teleport(loc);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Citybuild")) {
            Player p = (Player) event.getWhoClicked();
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 0);
            event.setCancelled(true);
            event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 50));
            p.closeInventory();
            Location loc = FileHelper.getLocation(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", "warp.citybuild");
            loc.setY(loc.getY()+3);
            event.getWhoClicked().teleport(loc);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Skyblock")) {
            Player p = (Player) event.getWhoClicked();
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 100, 0);
            event.setCancelled(true);
            event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 50));
            p.closeInventory();
            Location loc = FileHelper.getLocation(LobbySystem.getInstance().getDataFolder().getPath() + "/config.yml", "warp.skyblock");
            loc.setY(loc.getY()+3);
            event.getWhoClicked().teleport(loc);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Spawn")) {
            Player p = (Player) event.getWhoClicked();
            p.playSound(p.getLocation(), Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 100, 0);
            event.setCancelled(true);
            event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 50));
            p.closeInventory();
            Location loc = Config.getSpawnLocation();
            loc.setY(loc.getY()+3);
            event.getWhoClicked().teleport(loc);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("R??stung")) {
            Hotbar.openR??stung((Player) event.getWhoClicked());
        }else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("K??pfe")) {
            try {
                Hotbar.openK??pfe((Player) event.getWhoClicked());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }  else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??5Rainbow helm")) {
            Player p = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (!hue.containsKey(event.getWhoClicked().getUniqueId())) {
                if(p.getInventory().getHelmet() == null || !p.getInventory().getHelmet().getType().equals(Material.PLAYER_HEAD)) {
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    hue.put(event.getWhoClicked().getUniqueId(), 0.0f);
                }else {
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    hue.put(event.getWhoClicked().getUniqueId(), 0.0f);
                    helm.add(p.getUniqueId());
                }
            } else {
                hue.remove(event.getWhoClicked().getUniqueId());
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??5Rainbow Chestplate")) {
            Player p = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (!hue.containsKey(event.getWhoClicked().getUniqueId())) {
                if(p.getInventory().getHelmet() ==null) {
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    hue.put(event.getWhoClicked().getUniqueId(), 0.0f);
                }else {
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    hue.put(event.getWhoClicked().getUniqueId(), 0.0f);
                    helm.add(p.getUniqueId());
                }
            } else {
                hue.remove(event.getWhoClicked().getUniqueId());
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??5Rainbow Hose")) {
            Player p = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (!hue.containsKey(event.getWhoClicked().getUniqueId())) {
                if(p.getInventory().getHelmet() ==null) {
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    hue.put(event.getWhoClicked().getUniqueId(), 0.0f);
                }else {
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    hue.put(event.getWhoClicked().getUniqueId(), 0.0f);
                    helm.add(p.getUniqueId());
                }
            } else {
                hue.remove(event.getWhoClicked().getUniqueId());
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??5Rainbow Schuhe")) {
            Player p = (Player) event.getWhoClicked();
            event.setCancelled(true);
            if (!hue.containsKey(event.getWhoClicked().getUniqueId())) {
                if(p.getInventory().getHelmet() ==null) {
                    p.getInventory().setHelmet(null);
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    hue.put(event.getWhoClicked().getUniqueId(), 0.0f);
                }else {
                    p.getInventory().setChestplate(null);
                    p.getInventory().setLeggings(null);
                    p.getInventory().setBoots(null);
                    hue.put(event.getWhoClicked().getUniqueId(), 0.0f);
                    helm.add(p.getUniqueId());
                }
            } else {
                hue.remove(event.getWhoClicked().getUniqueId());
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
            }
        }
        if (event.getWhoClicked() instanceof Player) {
            if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??0Netherite Helm")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                color.remove((Player) event.getWhoClicked());
                Player p = (Player) event.getWhoClicked();
                System.out.println("Netherite Helm");
                event.setCancelled(true);
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.NETHERITE_HELMET).setName("??0Netherite Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("??0Netherite Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("??0Netherite Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.NETHERITE_BOOTS).setName("??0Netherite Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.NETHERITE_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.NETHERITE_HELMET).setName("??0Netherite Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("??0Netherite Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("??0Netherite Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.NETHERITE_BOOTS).setName("??0Netherite Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??0Netherite Chestplate")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                color.remove((Player) event.getWhoClicked());
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.NETHERITE_HELMET).setName("??0Netherite Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("??0Netherite Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("??0Netherite Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.NETHERITE_BOOTS).setName("??0Netherite Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.NETHERITE_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.NETHERITE_HELMET).setName("??0Netherite Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("??0Netherite Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("??0Netherite Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.NETHERITE_BOOTS).setName("??0Netherite Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??0Netherite Hose")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                color.remove((Player) event.getWhoClicked());
                event.setCancelled(true);
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.NETHERITE_HELMET).setName("??0Netherite Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("??0Netherite Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("??0Netherite Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.NETHERITE_BOOTS).setName("??0Netherite Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.NETHERITE_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.NETHERITE_HELMET).setName("??0Netherite Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("??0Netherite Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("??0Netherite Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.NETHERITE_BOOTS).setName("??0Netherite Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??0Netherite Schuhe")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.NETHERITE_HELMET).setName("??0Netherite Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("??0Netherite Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("??0Netherite Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.NETHERITE_BOOTS).setName("??0Netherite Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.NETHERITE_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.NETHERITE_HELMET).setName("??0Netherite Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.NETHERITE_CHESTPLATE).setName("??0Netherite Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.NETHERITE_LEGGINGS).setName("??0Netherite Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.NETHERITE_BOOTS).setName("??0Netherite Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??bDiamond Helm")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).setName("??bDiamond Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("??bDiamond Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("??bDiamond Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setName("??bDiamond Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).setName("??bDiamond Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("??bDiamond Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("??bDiamond Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setName("??bDiamond Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??bDiamond Chestplate")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).setName("??bDiamond Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("??bDiamond Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("??bDiamond Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setName("??bDiamond Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).setName("??bDiamond Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("??bDiamond Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("??bDiamond Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setName("??bDiamond Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??bDiamond Hose")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).setName("??bDiamond Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("??bDiamond Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("??bDiamond Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setName("??bDiamond Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).setName("??bDiamond Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("??bDiamond Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("??bDiamond Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setName("??bDiamond Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??bDiamond Schuhe")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).setName("??bDiamond Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("??bDiamond Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("??bDiamond Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setName("??bDiamond Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.DIAMOND_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).setName("??bDiamond Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).setName("??bDiamond Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.DIAMOND_LEGGINGS).setName("??bDiamond Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).setName("??bDiamond Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??eGold Helm")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).setName("??eGold Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("??eGold Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("??eGold Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.GOLDEN_BOOTS).setName("??eGold Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.GOLDEN_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).setName("??eGold Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("??eGold Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("??eGold Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.GOLDEN_BOOTS).setName("??eGold Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??eGold Chestplate")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).setName("??eGold Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("??eGold Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("??eGold Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.GOLDEN_BOOTS).setName("??eGold Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.GOLDEN_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).setName("??eGold Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("??eGold Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("??eGold Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.GOLDEN_BOOTS).setName("??eGold Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??eGold Hose")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).setName("??eGold Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("??eGold Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("??eGold Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.GOLDEN_BOOTS).setName("??eGold Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.GOLDEN_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).setName("??eGold Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("??eGold Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("??eGold Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.GOLDEN_BOOTS).setName("??eGold Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??eGold Schuhe")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).setName("??eGold Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("??eGold Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("??eGold Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.GOLDEN_BOOTS).setName("??eGold Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.GOLDEN_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.GOLDEN_HELMET).setName("??eGold Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.GOLDEN_CHESTPLATE).setName("??eGold Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.GOLDEN_LEGGINGS).setName("??eGold Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.GOLDEN_BOOTS).setName("??eGold Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??7Eisen Helm")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("??7Eisen Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("??7Eisen Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("??7Eisen Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("??7Eisen Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("??7Eisen Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("??7Eisen Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("??7Eisen Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("??7Eisen Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??7Eisen Chestplate")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("??7Eisen Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("??7Eisen Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("??7Eisen Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("??7Eisen Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("??7Eisen Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("??7Eisen Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("??7Eisen Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("??7Eisen Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??7Eisen Hose")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("??7Eisen Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("??7Eisen Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("??7Eisen Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("??7Eisen Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("??7Eisen Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("??7Eisen Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("??7Eisen Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("??7Eisen Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??7Eisen Schuhe")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("??7Eisen Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("??6Eisen Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("??6Eisen Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("??6Eisen Schuhe").build());
                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.IRON_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.IRON_HELMET).setName("??7Eisen Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.IRON_CHESTPLATE).setName("??7Eisen Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).setName("??7Eisen Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.IRON_BOOTS).setName("??7Eisen Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??8Chain Helm")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName("??8Chain Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("??8Chain Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("??8Chain Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("??8Chain Schuhe").build());

                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.CHAINMAIL_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName("??8Chain Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("??8Chain Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("??8Chain Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("??8Chain Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??8Chain Chestplate")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName("??8Chain Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("??8Chain Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("??8Chain Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("??8Chain Schuhe").build());

                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.CHAINMAIL_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName("??8Chain Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("??8Chain Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("??8Chain Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("??8Chain Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??8Chain Hose")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName("??8Chain Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("??8Chain Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("??8Chain Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("??8Chain Schuhe").build());

                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.CHAINMAIL_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName("??8Chain Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("??8Chain Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("??8Chain Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("??8Chain Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??8Chain Schuhe")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName("??8Chain Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("??8Chain Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("??8Chain Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("??8Chain Schuhe").build());

                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.CHAINMAIL_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.CHAINMAIL_HELMET).setName("??8Chain Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.CHAINMAIL_CHESTPLATE).setName("??8Chain Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.CHAINMAIL_LEGGINGS).setName("??8Chain Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.CHAINMAIL_BOOTS).setName("??8Chain Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6Leder Helm")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setName("??6Leder Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("??6Leder Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("??6Leder Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setName("??6Leder Schuhe").build());

                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setName("??6Leder Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("??6Leder Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("??6Leder Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setName("??6Leder Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6Leder Chestplate")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setName("??6Leder Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("??6Leder Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("??6Leder Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setName("??6Leder Schuhe").build());

                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setName("??6Leder Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("??6Leder Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("??6Leder Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setName("??6Leder Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6Leder Hose")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setName("??6Leder Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("??6Leder Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("??6Leder Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setName("??6Leder Schuhe").build());

                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setName("??6Leder Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("??6Leder Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("??6Leder Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setName("??6Leder Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6Leder Schuhe")) {
                if(hue.containsKey(event.getWhoClicked().getUniqueId())) {
                    hue.remove(event.getWhoClicked().getUniqueId());
                }
                Player p = (Player) event.getWhoClicked();
                event.setCancelled(true);
                color.remove((Player) event.getWhoClicked());
                if (p.getInventory().getBoots() == null) {
                    p.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setName("??6Leder Helm").build());
                    p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("??6Leder Chestplate").build());
                    p.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("??6Leder Hose").build());
                    p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setName("??6Leder Schuhe").build());

                } else {
                    if (!p.getInventory().getBoots().getType().equals(Material.LEATHER_BOOTS)) {
                        p.getInventory().setHelmet(new ItemBuilder(Material.LEATHER_HELMET).setName("??6Leder Helm").build());
                        p.getInventory().setChestplate(new ItemBuilder(Material.LEATHER_CHESTPLATE).setName("??6Leder Chestplate").build());
                        p.getInventory().setLeggings(new ItemBuilder(Material.LEATHER_LEGGINGS).setName("??6Leder Hose").build());
                        p.getInventory().setBoots(new ItemBuilder(Material.LEATHER_BOOTS).setName("??6Leder Schuhe").build());

                    } else {
                        p.getInventory().setHelmet(null);
                        p.getInventory().setChestplate(null);
                        p.getInventory().setLeggings(null);
                        p.getInventory().setBoots(null);
                    }
                }
            } else if (event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase("K??pfe")) {
                Player p = (Player) event.getWhoClicked();
                if(event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equals("??cZur??ck")){
                        Hotbar.openCosmetik((Player) event.getWhoClicked());
                        event.setCancelled(true);
                    }else {
                        if(event.getCurrentItem().getItemMeta().getLore() == null) {
                            ItemStack item = event.getCurrentItem();
                            p.getInventory().setHelmet(item);
                            event.setCancelled(true);
                        }else{
                            try {
                                MySQL.buyKopf(p.getName(), event.getCurrentItem().getItemMeta().getDisplayName());
                                Hotbar.openK??pfe(p);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } else if (!event.getCurrentItem().getType().equals(Material.GRAY_STAINED_GLASS)) {
                    if(event.getCurrentItem().getType().equals(Material.BARRIER)){
                        p.getInventory().setHelmet(null);
                        helm.remove(event.getWhoClicked().getUniqueId());
                        event.setCancelled(true);
                    }else {
                        if(event.getCurrentItem().getItemMeta().getLore() == null) {
                            ItemStack item = event.getCurrentItem();
                            p.getInventory().setHelmet(item);
                            event.setCancelled(true);
                        }else{
                            try {
                                MySQL.buyKopf(p.getName(), event.getCurrentItem().getItemMeta().getDisplayName());
                                Hotbar.openK??pfe(p);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Grapling Hook")) {
                try {
                if(MySQL.getStateG(event.getWhoClicked().getUniqueId().toString()) == 1) {
                    event.getWhoClicked().getInventory().setItem(7, new ItemBuilder(Material.FIREWORK_STAR).setName("??7Kein Gadget ausgew??hlt").build());
                    MySQL.setStateG(0, event.getWhoClicked().getUniqueId().toString());
                }else {
                    event.getWhoClicked().getInventory().setItem(7, ItemAPI.GrapplingHook);
                    MySQL.setStateG(1, event.getWhoClicked().getUniqueId().toString());
                }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                event.setCancelled(true);
            }else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("??4Grapling Hook")) {
                try {
                    MySQL.buyGadgets(String.valueOf(event.getWhoClicked().getUniqueId()), "Grappler");
                    Hotbar.openGadgets((Player)event.getWhoClicked());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                event.setCancelled(true);
            }

            else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("Gadgets")) {
                try {
                    Hotbar.openGadgets(((Player) event.getWhoClicked()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Enderperle")) {
                try {
                    if(MySQL.getStateG(event.getWhoClicked().getUniqueId().toString()) == 2){
                        event.getWhoClicked().getInventory().setItem(7, new ItemBuilder(Material.FIREWORK_STAR).setName("??7Kein Gadget ausgew??hlt").build());
                        MySQL.setStateG(0, event.getWhoClicked().getUniqueId().toString());
                    }else {
                        event.getWhoClicked().getInventory().setItem(7, new ItemBuilder(Material.ENDER_PEARL).setName("Enderperle").build());
                        MySQL.setStateG(2, event.getWhoClicked().getUniqueId().toString());
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                event.setCancelled(true);
            }else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("??4Enderperle")) {
                try {
                    MySQL.buyGadgets(String.valueOf(event.getWhoClicked().getUniqueId()), "Enderpearl");
                    Hotbar.openGadgets((Player)event.getWhoClicked());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                event.setCancelled(true);
            }

            else if(event.getCurrentItem().getItemMeta().getDisplayName().equals("??cZur??ck")){
                Hotbar.openCosmetik((Player) event.getWhoClicked());
                event.setCancelled(true);
            }else if (event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase("??6Lobbys")) {
                if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("??7Lobby-")){
                    ICloudPlayer cloudPlayer = getCloudPlayer((Player) event.getWhoClicked());
                    ICloudServerGroup group = CloudAPI.getInstance().getCloudServiceGroupManager().getLobbyGroupByName("Lobby");
                    ICloudService server = null;
                    String[] args = event.getCurrentItem().getItemMeta().getDisplayName().split("-");
                    if (group.getAllServices().isEmpty()) {
                    } else {
                        server = group.getAllServices().get(Integer.parseInt(args[1])-1);
                        cloudPlayer.connect(server);
                    }
                    event.setCancelled(true);
                }else                 if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("??6PremiumLobby-")){
                    ICloudPlayer cloudPlayer = getCloudPlayer((Player) event.getWhoClicked());
                    ICloudServerGroup group = CloudAPI.getInstance().getCloudServiceGroupManager().getServerGroupByName("PremiumLobby");
                    ICloudService server = null;
                    String[] args = event.getCurrentItem().getItemMeta().getDisplayName().split("-");
                    if (group.getAllServices().isEmpty()) {
                    } else {
                        server = group.getAllServices().get(Integer.parseInt(args[1])-1);
                        cloudPlayer.connect(server);
                    }
                    event.setCancelled(true);
                }
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("??bSeite: ")){
                String[] args = event.getCurrentItem().getItemMeta().getDisplayName().split(" ");
                event.setCancelled(true);
                MySQLf.sendFriendList(((Player) event.getWhoClicked()), Integer.parseInt(args[1]));

            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??7Freundschaftsanfragen")) {
                MySQLf.sendFasList(((Player) event.getWhoClicked()).getPlayer(), 1);
                event.setCancelled(true);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??6Deine Freunde")) {
                event.setCancelled(true);
                MySQLf.sendFriendList(((Player) event.getWhoClicked()).getPlayer(), 1);
            }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??5Party")) {
                event.setCancelled(true);
                MySQLf.sendParty(((Player) event.getWhoClicked()).getPlayer(), 1);
            }
            else if(event.getCurrentItem().getItemMeta().getDisplayName().startsWith("??7Seite: ")){
                String[] args = event.getCurrentItem().getItemMeta().getDisplayName().split(" ");
                event.setCancelled(true);
                MySQLf.sendFasList(((Player) event.getWhoClicked()), Integer.parseInt(args[1]));

             }else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??cEinstellungen")){
                String[] args = event.getCurrentItem().getItemMeta().getDisplayName().split(" ");
                event.setCancelled(true);
                try {
                    MySQL.sendSettings((Player) event.getWhoClicked());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??2Animation")) {
                try {
                    if(MySQL.getStateS(((Player) event.getWhoClicked()).getName(), "Animation") == 1){
                        MySQL.setStateS("Animation", 0, ((Player) event.getWhoClicked()).getName());
                    }else {
                        MySQL.setStateS("Animation", 1, ((Player) event.getWhoClicked()).getName());
                    }
                    event.setCancelled(true);
                    MySQL.sendSettings(((Player) event.getWhoClicked()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??cAutoFly")) {
                try {
                    if(MySQL.getStateS(((Player) event.getWhoClicked()).getName(), "JoinFly") == 1){
                        MySQL.setStateS("JoinFly", 0, ((Player) event.getWhoClicked()).getName());
                    }else {
                        MySQL.setStateS("JoinFly", 1, ((Player) event.getWhoClicked()).getName());
                    }
                    event.setCancelled(true);
                    MySQL.sendSettings(((Player) event.getWhoClicked()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Farbe")) {
                try {
                switch (MySQL.getStateS(((Player) event.getWhoClicked()).getName(), "Farbe")){
                    case 0:
                        MySQL.setStateS("Farbe", 1, event.getWhoClicked().getName());
                        break;
                    case 1:
                        MySQL.setStateS("Farbe", 2, event.getWhoClicked().getName());
                        break;
                    case 2:
                        MySQL.setStateS("Farbe", 3, event.getWhoClicked().getName());
                        break;
                    case 3:
                        MySQL.setStateS("Farbe", 4, event.getWhoClicked().getName());
                        break;
                    case 4:
                        MySQL.setStateS("Farbe", 0, event.getWhoClicked().getName());
                        break;
                }
                MySQL.sendSettings(((Player) event.getWhoClicked()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("??7Spawnen")) {
                try {
                    if(MySQL.getStateS(((Player) event.getWhoClicked()).getName(), "SpawnJoin") == 1){
                        MySQL.setStateS("SpawnJoin", 0, ((Player) event.getWhoClicked()).getName());
                    }else {
                        MySQL.setStateS("SpawnJoin", 1, ((Player) event.getWhoClicked()).getName());
                    }
                    event.setCancelled(true);
                    MySQL.sendSettings(((Player) event.getWhoClicked()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else{
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onInteractAtEntity(final PlayerInteractAtEntityEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onHangingBreak(final HangingBreakEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getEntity().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onSwap(final PlayerSwapHandItemsEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onBreak(final BlockBreakEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onPlace(final BlockPlaceEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onItemFrameBreak(final EntityDamageByEntityEvent event) {
        if (LobbySystem.buildPlayers.contains(event.getEntity().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onFallDamage(final EntityDamageEvent event) {
        event.setCancelled(true);
    }
    private ICloudPlayer getCloudPlayer(Player p) {
        try {
            return (ICloudPlayer)CloudAPI.getInstance().getCloudPlayerManager().getCloudPlayer(p.getUniqueId()).get();
        } catch (InterruptedException|java.util.concurrent.ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event){
        event.callEvent();
    }
    public static void startColorArmor(){
        k=Bukkit.getScheduler().scheduleSyncRepeatingTask(LobbySystem.getInstance(), new Runnable() {
            @Override
            public void run() {
                for (Player p : color){
                    org.bukkit.Color color1 = ItemAPI.getRandomC();
                    ItemAPI.setColorHelm(color1, p);
                    ItemAPI.setColorBoots(color1, p);
                    ItemAPI.setColorChestplate(color1, p);
                    ItemAPI.setColorLeggins(color1, p);
                }
            }
        },5,5);
    }

    private static HashMap<UUID, Float> hue = new HashMap<>();
    private static List<UUID> helm = new ArrayList<>();

    public static BukkitRunnable mainLoop(){
        return new BukkitRunnable() {
            @Override
            public void run() {
            hue.forEach(((uuid, h) -> {
                Player player = Bukkit.getPlayer(uuid);
                if(player !=null){
                    h = handleColor(h, 0.005f);
                    setArmor(player, h, 0.02f);
                    hue.put(uuid, h);
                }
            }));
            }
        };
    }
    public static void setArmor(Player p, float hue, float gradientspeed){
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        org.bukkit.Color colorh = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());
        if(!helm.contains(p.getUniqueId())) {
            colorh = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());
            handleColor(hue, gradientspeed);
        }
        org.bukkit.Color colorc = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());
        handleColor(hue, gradientspeed);
        org.bukkit.Color colorl = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());
        handleColor(hue, gradientspeed);
        org.bukkit.Color colorb = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());
        handleColor(hue, gradientspeed);
        if(!helm.contains(p.getUniqueId())) {
            LeatherArmorMeta helm = (LeatherArmorMeta) helmet.getItemMeta();
            helm.setColor(colorh);
            helmet.setItemMeta(helm);
        }

        LeatherArmorMeta chest = (LeatherArmorMeta) chestplate.getItemMeta();
        chest.setColor(colorc);
        chestplate.setItemMeta(chest);

        LeatherArmorMeta legins = (LeatherArmorMeta) leggins.getItemMeta();
        legins.setColor(colorl);
        leggins.setItemMeta(legins);

        LeatherArmorMeta bots = (LeatherArmorMeta) boots.getItemMeta();
        bots.setColor(colorb);
        boots.setItemMeta(bots);
        if(!helm.contains(p.getUniqueId())) {
            p.getInventory().setHelmet(helmet);
        }
        p.getInventory().setChestplate(chestplate);
        p.getInventory().setLeggings(leggins);
        p.getInventory().setBoots(boots);
    }
    private static float handleColor(float hue, float speed){
        hue += speed;
        if(hue > 1.0f)
            hue =0.0f;
        return hue;
    }
    private static Color getRGB(float hue){
        return Color.getHSBColor(hue, 1f, 1f);
    }
}
