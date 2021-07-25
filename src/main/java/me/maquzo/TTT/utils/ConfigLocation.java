package me.maquzo.TTT.utils;

import me.maquzo.TTT.TTT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;


public class ConfigLocation {

    private TTT plugin;
    private Location location;
    private String path;
    private World world;

    public ConfigLocation(TTT plugin, Location location, String path) {
        this.plugin = plugin;
        this.location = location;
        this.path = path;

    }

    public ConfigLocation(TTT plugin, String path) {
        this(plugin, null, path);
    }

    public void setLocation() {
        FileConfiguration config = plugin.getConfig();
        config.set(path + ".World", location.getWorld().getName());
        config.set(path + ".X", location.getX());
        config.set(path + ".Y", location.getY());
        config.set(path + ".Z", location.getZ());
        config.set(path + ".Yaw", location.getYaw());
        config.set(path + ".Pitch", location.getPitch());
        plugin.saveConfig();
    }


    public Location loadLocation() {
        FileConfiguration config = plugin.getConfig();
        if (config.contains(path)) {
            World world = Bukkit.getWorld(config.getString(path + ".World"));
            double x = config.getDouble(path + ".X");
            double y = config.getDouble(path + ".Y");
            double z = config.getDouble(path + ".Z");
            float yaw = (float) config.getDouble(path + ".Yaw");
            float pitch = (float) config.getDouble(path + ".Pitch");
            return new Location(world, x, y, z, yaw, pitch);
        } else
            return null;
    }

    public void saveBlockLocation() {
        FileConfiguration config = plugin.getConfig();
        config.set(path + ".World", location.getWorld().getName());
        config.set(path + ".X", location.getBlockX());
        config.set(path + ".Y", location.getBlockY());
        config.set(path + ".Z", location.getBlockZ());
        plugin.saveConfig();
    }

    public Block loadBlockLocation() {
        FileConfiguration config = plugin.getConfig();
        if (config.contains(path)) {
            World world = Bukkit.getWorld(config.getString(path + ".World"));
            int x = plugin.getConfig().getInt(path + ".X"),
                y = plugin.getConfig().getInt(path + ".Y"),
                z = plugin.getConfig().getInt(path + ".Z");
            return new Location(world, x, y, z).getBlock();
        }
        return null;
    }






}
