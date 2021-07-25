package me.maquzo.TTT.Files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class BookConfig {

    private static File file;
    private static FileConfiguration bookFile;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("TTT").getDataFolder(), "bookConfig.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bookFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return bookFile;
    }

    public static void save() {
        try {
            bookFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        bookFile = YamlConfiguration.loadConfiguration(file);
    }

}
