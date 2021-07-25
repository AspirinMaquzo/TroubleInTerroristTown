package me.maquzo.TTT.Commands;

import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetDataCommand implements CommandExecutor {

    private TTT plugin;

    public ResetDataCommand(TTT plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                if (!player.isOp() || player.hasPermission("TTT.ResetData")) return true;
                Profile profile = plugin.getProfileManager().getProfile(player);
                profile.getData().resetData();
                player.sendMessage(TTT.prefix + "Du hast deine Daten zurück gesetzt!");
            }
        }

        return false;
    }
}

