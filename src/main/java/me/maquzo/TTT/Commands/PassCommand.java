package me.maquzo.TTT.Commands;

import me.maquzo.TTT.Listener.GUIS.SettingsGUI;
import me.maquzo.TTT.TTT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PassCommand implements CommandExecutor {

    private TTT plugin;
    private SettingsGUI settingsGUI;

    public PassCommand(TTT plugin) {
        this.plugin = plugin;
        settingsGUI = plugin.getSettings();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                player.openInventory(settingsGUI.getPassInventory());
            }
        }
        return false;
    }
}
