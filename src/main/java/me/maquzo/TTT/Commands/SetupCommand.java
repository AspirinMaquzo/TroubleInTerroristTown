package me.maquzo.TTT.Commands;

import me.maquzo.TTT.Manager.gamestates.LobbyState.LobbyState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.Settings.TrapSetup;
import me.maquzo.TTT.utils.ConfigLocation;
import me.maquzo.TTT.Tester.TesterSetup;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    private TTT plugin;

    public SetupCommand(TTT plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.isOp() || player.hasPermission("TTT.setup")) {
            if (!(args.length == 0)) {
                if (args[0].equalsIgnoreCase("setlobby")) {
                    if (args.length == 1) {
                        new ConfigLocation(plugin, player.getLocation(), "Lobby").setLocation();
                        player.sendMessage(TTT.prefix + "Die §c§lLobby §7wurde §c§lerfolgreich §7gesetzt!");
                    } else
                        player.sendMessage(TTT.error + "Bitte benutze §c/setup setLobby");
                } else if (args[0].equalsIgnoreCase("createMap")) {
                    if (args.length == 3) {
                        Map map = new Map(plugin, args[1]);
                        if (!map.exists()) {
                            map.createMap(args[2]);
                            player.sendMessage(TTT.prefix + "Du hast die Map §c§l" + map.getMapname() + " §7gebaut von §c§l" + map.getBuilder() + " §7erstellt!");
                        } else
                            player.sendMessage(TTT.error + "Die Map §c§l" + map.getMapname() + " §7existiert bereits!");
                    } else
                        player.sendMessage(TTT.error + "§7Benutze §c/setup createMap <Mapname> <Builder>");
                } else if (args[0].equalsIgnoreCase("setSpawn")) {
                    if (args.length == 3) {
                        Map map = new Map(plugin, args[1]);
                        if (map.exists()) {
                            try {
                                int spawnValue = Integer.parseInt(args[2]);
                                if (spawnValue > 0 && spawnValue <= LobbyState.MAX_PLAYERS) {
                                    map.setSpawnLocation(spawnValue, player.getLocation());
                                    player.sendMessage(TTT.prefix + "Du hast den Spawn §c§l" + spawnValue + " §7für die Map §c§l " + map.getMapname() + " §7erstellt!");
                                } else
                                    player.sendMessage(TTT.error + "Es sind nur Zahlen zwischen §c§l1 §7und §c§l" + LobbyState.MAX_PLAYERS + " §7gestattet!");
                            } catch (NumberFormatException e) {
                                player.sendMessage(TTT.error + "Bitte Benutze einen gültigen §c§lInt! §7(Ziffer)");
                            }
                        } else
                            player.sendMessage(TTT.error + "Die Map §c§l" + map.getMapname() + " §7existiert noch nicht!");
                    } else
                        player.sendMessage(TTT.error + "Bitte benutze §c§l/setSpawn <MAPNAME> <1/" + LobbyState.MAX_PLAYERS + ">");
                } else if (args[0].equalsIgnoreCase("setupTester")) {
                    if (args.length == 2) {
                        Map map = new Map(plugin, args[1]);
                        if (map.exists()) {
                            new TesterSetup(player, map, plugin);
                        } else
                            player.sendMessage(TTT.error + "Die Map §c§l" + map.getMapname() + " §7existiert nicht!");
                    } else
                        player.sendMessage(TTT.error + "Bitte benutze §c/setup setupTester <MAP>");
                } else if (args[0].equalsIgnoreCase("setupTrap")) {
                    if (args.length == 2) {
                        Map map = new Map(plugin, args[1]);
                        if (map.exists()) {
                            new TrapSetup(player, map, plugin);
                        } else
                            player.sendMessage(TTT.error + "Die Map §c§l" + map.getMapname() + " §7existiert nicht!");
                    } else
                        player.sendMessage(TTT.error + "Bitte benutze §c/setup setupTrap <MAP>");
                } else if (args[0].equalsIgnoreCase("setupLeaderboard")) {
                    if (args.length == 1) {
                        new ConfigLocation(plugin, player.getLocation(), "Leaderboard").setLocation();
                        player.sendMessage(TTT.prefix + "Das §c§lLeaderboard §7wurde §c§lerfolgreich §7gesetzt!");
                    } else
                        player.sendMessage(TTT.error + "/setup setupLeaderboard");
                }

            } else
                player.sendMessage(TTT.error + "bitte benutze §c/setup | setLobby | createMap <MAPNAME> <BUILDER> | setSpawn <MAPNAME> <1/" + LobbyState.MAX_PLAYERS + "> | setupTester");
        } else
            player.sendMessage(TTT.noperms);

        return false;
    }
}

