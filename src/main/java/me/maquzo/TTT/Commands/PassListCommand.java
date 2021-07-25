package me.maquzo.TTT.Commands;

import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PassListCommand implements CommandExecutor {


    private TTT plugin;

    public PassListCommand(TTT plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                Profile profile = plugin.getProfileManager().getProfile(player);
                player.sendMessage(" ");
                player.sendMessage(TTT.prefix + "§8• §4Traitor-Pass(e) §8• §7" + profile.getData().getStats().getPassTraitor());
                player.sendMessage(TTT.prefix + "§8• §bDetective-Pass(e) §8• §7" + profile.getData().getStats().getPassDetective());
                player.sendMessage(" ");
            }
        }

        return false;
    }
}
