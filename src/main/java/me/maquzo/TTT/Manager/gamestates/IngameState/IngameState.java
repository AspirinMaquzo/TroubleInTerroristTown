package me.maquzo.TTT.Manager.gamestates.IngameState;

import me.maquzo.TTT.Manager.Roles.RoleManager;
import me.maquzo.TTT.Manager.gamestates.GameState;
import me.maquzo.TTT.Manager.Roles.Role;
import me.maquzo.TTT.Voting.Map;
import me.maquzo.TTT.*;
import me.maquzo.TTT.utils.ConfigLocation;
import me.maquzo.TTT.utils.Profile;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import me.maquzo.TTT.Tasks.*;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;

public class IngameState extends GameState {

    private TTT plugin;
    private Map map;

    private ArrayList<Player> players;
    private ArrayList<Player> specPlayers;

    private boolean grace;

    private RoleCountdown roleCountdown;
    private Role winRole;

    public IngameState(TTT plugin) {
        this.plugin = plugin;
        roleCountdown = new RoleCountdown(plugin);
        specPlayers = new ArrayList<>();
    }


    @Override
    public void start() {
        grace = true;

        Collections.shuffle(plugin.getPlayers());
        players = plugin.getPlayers();

        map = plugin.getVoting().getWinnerMap();
        map.loadMap();
        for (int i = 0; i < players.size(); i++)
            players.get(i).teleport(map.getSpawnLocations()[i]);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setGameMode(GameMode.SURVIVAL);
            player.setExhaustion(20);
            player.getInventory().clear();
        }
        roleCountdown.start();
    }


    public void checkGameEnd() {
        if (plugin.getRoleManager().getTraitorList().size() <= 0) {
            winRole = Role.INNOCENT;
            plugin.getGameStateManager().setGameState(ENDING_STATE);
            for (Player all : Bukkit.getOnlinePlayers()) {
                ConfigLocation configLocation = new ConfigLocation(plugin, "Lobby");
                Profile profile = plugin.getProfileManager().getProfile(all);
                if (!profile.getData().getStats().isKARMA_1()) {
                    if (profile.getData().getStats().getKarma() == 20000) {
                        profile.getData().getStats().setKARMA_1(true);
                        Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §eBekomme 20.000 Karma §k| §7geschafft!");
                        profile.getData().getStats().increaseKarma(100);
                        profile.getData().save();
                    }
                }
                if (!profile.getData().getStats().isKARMA_2()) {
                    if (profile.getData().getStats().getKarma() == 50000) {
                        profile.getData().getStats().setKARMA_2(true);
                        Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §eBekomme 50.000 Karma §k| §7geschafft!");
                        profile.getData().getStats().increaseKarma(100);
                        profile.getData().save();
                    }
                }
                if (!profile.getData().getStats().isKARMA_3()) {
                    if (profile.getData().getStats().getKarma() == 100000) {
                        profile.getData().getStats().setKARMA_3(true);
                        Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §e§k| §eBekomme 100.000 Karma §k| §7geschafft!");
                        profile.getData().getStats().increaseKarma(100);
                        profile.getData().save();
                    }
                }
                all.setGameMode(GameMode.ADVENTURE);
                all.setHealth(20.0D);
                all.setFoodLevel(20);
                all.setLevel(0);
                all.setFlying(false);
                all.setAllowFlight(false);
                all.teleport(configLocation.loadLocation());
            }
        } else if (plugin.getRoleManager().getTraitorList().size() == plugin.getPlayers().size()) {
            winRole = Role.TRAITOR;
            plugin.getGameStateManager().setGameState(ENDING_STATE);
            for (Player all : Bukkit.getOnlinePlayers()) {
                ConfigLocation configLocation = new ConfigLocation(plugin, "Lobby");
                all.teleport(configLocation.loadLocation());
                all.setGameMode(GameMode.ADVENTURE);
                all.setHealth(20.0D);
                all.setFoodLevel(20);
                all.setLevel(0);
                all.setFlying(false);
                all.setAllowFlight(false);
            }
        }
    }

    public void addSpec(Player player) {
        specPlayers.add(player);
        player.setGameMode(GameMode.SPECTATOR);
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setLevel(0);
        player.setExp(0);
        player.setFireTicks(0);
        player.setHealth(20);
        player.setFoodLevel(20);

        double x = 0.5;
        double y = 5;
        double z = 0.5;

        Vector v = new Vector(x, y, z).normalize().setY(y + 5);
        player.setVelocity(v);

        for (Player all : plugin.getPlayers()) {
            player.hidePlayer(all);
        }

        for (Player all : plugin.getPlayers()) {
            player.showPlayer(all);
        }
    }

    @Override
    public void stop() {
        RoleManager roleManager = new RoleManager(plugin);
        Bukkit.broadcastMessage(winRole + "");
        if (winRole == Role.INNOCENT || winRole == Role.DETECTIVE) {
            for (Player player : players) {
                if (!roleManager.getTraitorList().contains(player)) {
                    Profile profile = plugin.getProfileManager().getProfile(player);
                    profile.getData().getStats().increaseWins(1);
                    profile.getData().getStats().increaseGamesPlayed(1);
                    profile.getData().save();



                    Role role = plugin.getRoleManager().getRole(player);
                    if (role == Role.DETECTIVE) {
                        if (profile.getData().getStats().isFIRST_DET_WIN_Done()) return;
                        profile.getData().getStats().setFIRST_DET_WIN_Done(true);
                        profile.getData().getStats().increaseKarma(100);
                        profile.getData().save();
                        Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §eFirst Detective Win §k| §7geschafft!");
                    }


                    PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                            IChatBaseComponent.ChatSerializer.a("{\"text\":\"§aInnocent\"}"), 40, 20, 20);

                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
                    player.playSound(player.getLocation(), Sound.BURP, 3, 3);
                }
            }


            Bukkit.broadcastMessage(TTT.prefix + "Die §l" + winRole.getRoleColor() + winRole.getName() + " §7haben das Spiel gewonnen");
        }


        for (Player player : players) {
            Profile profile = plugin.getProfileManager().getProfile(player);
            if (profile.getData().getStats().getWins() == 10) {
                profile.getData().getStats().setWINS_1_Done(true);
                profile.getData().getStats().increaseKarma(100);
                Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §e10 wins §k| §7geschafft!");
                profile.getData().save();
            }
            if (profile.getData().getStats().getWins() == 25) {
                profile.getData().getStats().setWINS_2_Done(true);
                profile.getData().getStats().increaseKarma(100);
                Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §e25 wins §k| §7geschafft!");
                profile.getData().save();
            }
            if (profile.getData().getStats().getWins() == 50) {
                profile.getData().getStats().setWINS_3_Done(true);
                profile.getData().getStats().increaseKarma(100);
                Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §e50 wins §k| §7geschafft!");
                profile.getData().save();
            }
            if (profile.getData().getStats().getWins() == 75) {
                profile.getData().getStats().setWINS_4_Done(true);
                profile.getData().getStats().increaseKarma(100);
                Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §e75 wins §k| §7geschafft!");
                profile.getData().save();
            }
            if (profile.getData().getStats().getWins() == 100) {
                profile.getData().getStats().setWINS_5_Done(true);
                profile.getData().getStats().increaseKarma(100);
                Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §e100 wins §k| §7geschafft!");
                profile.getData().save();
            }

            if (!profile.getData().getStats().isFirstWinDone()) {
                profile.getData().getStats().setFirstWinDone(true);
                profile.getData().getStats().increaseKarma(100);
                Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §eErster win §k| §7geschafft!");
                profile.getData().save();
            }
        }

        if (winRole == Role.TRAITOR) {
            for (Player player : players) {
                Profile profile = plugin.getProfileManager().getProfile(player);
                profile.getData().getStats().increaseWins(1);
                profile.getData().getStats().increaseGamesPlayed(1);
                profile.getData().save();

                if (!profile.getData().getStats().isFIRST_TRAITOR_WIN_Done()) {
                    profile.getData().getStats().setFIRST_TRAITOR_WIN_Done(true);
                    profile.getData().getStats().increaseKarma(100);
                    Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + profile.getPlayerName() + " §7Hat die Challenge: §k§e| §eFirst Traitor Win §k| §7geschafft!");
                    profile.getData().save();
                }



                PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
                        IChatBaseComponent.ChatSerializer.a("{\"text\":\"§4Traitor\"}"), 40, 20, 20);

                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
                player.playSound(player.getLocation(), Sound.BURP, 3, 3);
            }

            Bukkit.broadcastMessage(TTT.prefix + "Die §l" + winRole.getRoleColor() + winRole.getName() + " §7haben das Spiel gewonnen");
        }
    }

    public boolean isInGrace() {
        return grace;
    }

    public void setGrace(boolean grace) {
        this.grace = grace;
    }

    public ArrayList<Player> getSpecPlayers() {
        return specPlayers;
    }

    public Map getMap() {
        return map;
    }

}
