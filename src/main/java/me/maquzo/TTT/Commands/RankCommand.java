package me.maquzo.TTT.Commands;

import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ConfigLocation;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class RankCommand implements CommandExecutor {

    static HashMap<Integer, String> rang = new HashMap<Integer, String>();
    private TTT plugin;

    public RankCommand(TTT plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length == 0) {
                Player player = (Player) sender;
                int i = 0;
                int id = i + 1;
                Profile profile = plugin.getProfileManager().getProfile(player);
                i++;
                rang.put(i, profile.getUUID().toString());

                player.sendMessage(TTT.prefix + "Dein Rang§8: §c" + id);

            }
        }

        return false;
    }
}
