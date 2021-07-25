package me.maquzo.TTT.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material, short subID) {
        item = new ItemStack(material, 1, subID);
        itemMeta = item.getItemMeta();


    }

    public ItemBuilder(Material material) {
        this(material, (short)0);
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        item.addEnchantment(enchantment, level);
        return this;
    }


    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }

}
