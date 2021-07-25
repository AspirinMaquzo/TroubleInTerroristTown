package me.maquzo.TTT.Commands;

import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.ConfigLocation;
import me.maquzo.TTT.utils.Profile;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class StatsCommand implements CommandExecutor {

    private TTT plugin;
    static HashMap<Integer, String> rang = new HashMap<Integer, String>();

    public StatsCommand(TTT plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            Player player = (Player) sender;
            Profile playerProfile = plugin.getProfileManager().getProfile(player);

            float kills = playerProfile.getData().getStats().getKills();
            float deaths = playerProfile.getData().getStats().getDeaths();

            int in = 0;
            int id = in + 1;
            rang.put(in, playerProfile.getUUID().toString());

            player.sendMessage(TTT.prefix + "§7§m--------§8[§cStats von " + player.getName() + "§8]§7§m--------");
            player.sendMessage(TTT.prefix + "Kills§8: §c" + playerProfile.getData().getStats().getKills());
            player.sendMessage(TTT.prefix + "Deaths§8: §c" + playerProfile.getData().getStats().getDeaths());
            player.sendMessage(TTT.prefix + "Wins§8: §c" + playerProfile.getData().getStats().getWins());
            player.sendMessage(TTT.prefix + "Games§8: §c" + playerProfile.getData().getStats().getGamesPlayed());
            player.sendMessage(TTT.prefix + "Karma§8: §c" + playerProfile.getData().getStats().getKarma());


            if (deaths != 0) {
                if (kills == 0) {
                    player.sendMessage(TTT.prefix + "K/D§8: §c0");
                    player.sendMessage(TTT.prefix + "§7§m--------§8[§cStats von " + player.getName() + "§8]§7§m--------");
                    return true;
                }
            }
            if (deaths == 0) {
                player.sendMessage(TTT.prefix + "K/D§8: §c" + kills);
                player.sendMessage(TTT.prefix + "§7§m--------§8[§cStats von " + player.getName() + "§8]§7§m--------");
                return true;
            }

            if (deaths != 0) {
                player.sendMessage(TTT.prefix + "K/D§8: §c" + kills / deaths);
                player.sendMessage(TTT.prefix + "§7§m--------§8[§cStats von " + player.getName() + "§8]§7§m--------");
                return true;
            }

        }

        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            Player player = (Player) sender;
            Profile profile = plugin.getProfileManager().getProfile(target);

            float kills = profile.getData().getStats().getKills();
            float deaths = profile.getData().getStats().getDeaths();



            if (profile != null) {
                player.sendMessage(TTT.prefix + "§7§m--------§8[§cStats von " + target.getName() + "§8]§7§m--------");
                player.sendMessage(TTT.prefix + "Kills§8: §c" + profile.getData().getStats().getKills());
                player.sendMessage(TTT.prefix + "Deaths§8: §c" + profile.getData().getStats().getDeaths());
                player.sendMessage(TTT.prefix + "Wins§8: §c" + profile.getData().getStats().getWins());
                player.sendMessage(TTT.prefix + "Games§8: §c" + profile.getData().getStats().getGamesPlayed());
                player.sendMessage(TTT.prefix + "Karma§8: §c" + profile.getData().getStats().getKarma());
                if (deaths != 0) {
                    if (kills == 0) {
                        player.sendMessage(TTT.prefix + "K/D§8: §c0");
                        player.sendMessage(TTT.prefix + "§7§m--------§8[§cStats von " + target.getName() + "§8]§7§m--------");
                        return true;
                    }
                }
                if (deaths == 0) {
                    player.sendMessage(TTT.prefix + "K/D§8: §c" + kills);
                    player.sendMessage(TTT.prefix + "§7§m--------§8[§cStats von " + target.getName() + "§8]§7§m--------");
                    return true;
                }

                if (deaths != 0) {
                    player.sendMessage(TTT.prefix + "K/D§8: §c" + kills / deaths);
                    player.sendMessage(TTT.prefix + "§7§m--------§8[§cStats von " + target.getName() + "§8]§7§m--------");
                    return true;
                }

            } else
                player.sendMessage(TTT.error + "Dieser Spieler existiert nicht!");


        }


        return false;
    }
}
