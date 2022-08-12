package de.dermaster.lobbysystem.utils;


import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder
{
    private ItemStack item;
    
    public ItemBuilder(final Material m) {
        this(m, 1);
    }
    
    public ItemBuilder(final ItemStack item) {
        this.item = item;
    }
    
    public ItemBuilder(final Material m, final int amount) {
        this.item = new ItemStack(m, amount);
    }
    
    public ItemBuilder(final Material m, final int amount, final byte damage) {
        this.item = new ItemStack(m, amount, (short)damage);
    }
    
    public ItemBuilder setName(final String name) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder addUnsafeEnchantment(final Enchantment ench, final int level) {
        this.item.addUnsafeEnchantment(ench, level);
        return this;
    }
    
    public ItemBuilder removeEnchantment(final Enchantment ench) {
        this.item.removeEnchantment(ench);
        return this;
    }
    
    public ItemBuilder addEnchant(final Enchantment ench, final int level) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(ench, level, true);
        this.item.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder setLore(final String... lore) {
        final ItemMeta meta = this.item.getItemMeta();
        meta.setLore((List) Arrays.asList(lore));
        this.item.setItemMeta(meta);
        return this;
    }
    
    public ItemBuilder setOwner(final String name) {
        final SkullMeta meta = (SkullMeta)this.item.getItemMeta();
        meta.setOwner(name);
        this.item.setItemMeta((ItemMeta)meta);
        return this;
    }
    public ItemBuilder setColor(){
        final LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
        meta.setColor(ItemAPI.getRandomC());
        this.item.setItemMeta((ItemMeta)meta);
        return this;
    }
    public ItemStack build() {
        return this.item;
    }
}
