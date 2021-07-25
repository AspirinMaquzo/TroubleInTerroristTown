package me.maquzo.TTT.Listener.Ingame;


import me.maquzo.TTT.Manager.Roles.Role;
import me.maquzo.TTT.Manager.Roles.RoleManager;
import me.maquzo.TTT.Manager.gamestates.IngameState.IngameState;
import me.maquzo.TTT.TTT;
import me.maquzo.TTT.utils.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class ProgressListener implements Listener {

    private TTT plugin;
    private RoleManager roleManager;


    public ProgressListener(TTT plugin) {
        this.plugin = plugin;
        roleManager = plugin.getRoleManager();
    }

    @EventHandler
    public void onPlayerDamageTraitorTeamMate(EntityDamageByEntityEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof Player)) return;

        Player damager = (Player) event.getDamager();
        Player victim = (Player) event.getEntity();

        Role damagerRole = roleManager.getRole(damager);
        Role victimRole = roleManager.getRole(victim);

        if (damagerRole == Role.TRAITOR && victimRole == Role.TRAITOR)
            event.setDamage(0);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;
        event.getDrops().clear();
        event.setDroppedExp(0);
        IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
        Player victim = event.getEntity();
        if (victim.getKiller() != null) {
            Player killer = victim.getKiller();
            Profile killerProfile = plugin.getProfileManager().getProfile(killer.getUniqueId());
            Profile victimProfile = plugin.getProfileManager().getProfile(victim.getUniqueId());


            Role killerRole = roleManager.getRole(killer);
            Role victimRole = roleManager.getRole(victim);


            killerProfile.getData().getStats().increaseKills(1);
            victimProfile.getData().getStats().increaseDeaths(1);
            killerProfile.getData().save();

            victimProfile.getData().save();


            switch (killerRole) {
                case TRAITOR:
                    if (victimRole == Role.TRAITOR) {
                        if (killerProfile.getData().getStats().getKarma() < 0) {
                            killerProfile.getData().save();
                            return;
                        }


                        if (killerProfile.getData().getStats().getKills() == 10) {
                            killerProfile.getData().getStats().setKILLS_1_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 10 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 25) {
                            killerProfile.getData().getStats().setKILLS_2_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 25 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 50) {
                            killerProfile.getData().getStats().setKILLS_3_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 50 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 75) {
                            killerProfile.getData().getStats().setKILLS_4_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 75 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 100) {
                            killerProfile.getData().getStats().setKILLS_5_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 100 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }

                        killerProfile.getData().getStats().decreseKarma(50);
                        killer.sendMessage(TTT.prefix + "§c-50 Karma Grund: Tötung eines §lTeammitgliedes");
                        killerProfile.getData().save();
                    } else {
                        killerProfile.getData().getStats().increaseKarma(10);
                        killer.sendMessage(TTT.prefix + "§a+10 Karma Grund: Tötung eines §lInnocents");
                        killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 4, 4);
                        killerProfile.getData().save();
                    }
                    break;
                case INNOCENT:
                case DETECTIVE:
                    if (victimRole == Role.TRAITOR) {
                        killerProfile.getData().getStats().increaseKills(1);
                        killerProfile.getData().getStats().increaseKarma(10);
                        killerProfile.getData().save();
                        Bukkit.broadcastMessage(TTT.prefix + "§l" + killerRole.getRoleColor() + killer.getName() + " §7hat den Traitor §l" + victimRole.getRoleColor() + victim.getName() + " §7getötet");
                        killer.sendMessage(TTT.prefix + "§a+10 Points Grund: Tötung eines §lTraitors");

                        if (killerProfile.getData().getStats().getKills() == 10) {
                            killerProfile.getData().getStats().setKILLS_1_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 10 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 25) {
                            killerProfile.getData().getStats().setKILLS_2_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 25 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 50) {
                            killerProfile.getData().getStats().setKILLS_3_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 50 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 75) {
                            killerProfile.getData().getStats().setKILLS_4_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 75 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 100) {
                            killerProfile.getData().getStats().setKILLS_5_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 100 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }


                        killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 4, 4);
                        killerProfile.getData().save();
                    } else if (victimRole == Role.INNOCENT || victimRole == Role.DETECTIVE) {


                        if (killerProfile.getData().getStats().getKarma() < 0) {
                            killerProfile.getData().save();
                            return;
                        }

                        killerProfile.getData().getStats().decreseKarma(10);
                        killerProfile.getData().save();
                        killer.sendMessage(TTT.prefix + "§l" + victimRole.getRoleColor() + victim.getName() + "§7War KEIN §4§lTraitor!");
                        killer.sendMessage(TTT.prefix + "§c-5 Points Grund: Tötung eines §lTeammitgliedes");

                        if (killerProfile.getData().getStats().getKills() == 10) {
                            killerProfile.getData().getStats().setKILLS_1_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 10 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 25) {
                            killerProfile.getData().getStats().setKILLS_2_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 25 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 50) {
                            killerProfile.getData().getStats().setKILLS_3_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 50 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 75) {
                            killerProfile.getData().getStats().setKILLS_4_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 75 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }
                        if (killerProfile.getData().getStats().getKills() == 100) {
                            killerProfile.getData().getStats().setKILLS_5_Done(true);
                            Bukkit.broadcastMessage(TTT.prefix + "Der Spieler §c§l" + killerProfile.getPlayerName() + " §7Hat die Challenge: §k§e| §eTöte 100 Spieler §k| §7geschafft!");
                            killerProfile.getData().getStats().increaseKarma(100);
                            killerProfile.getData().save();
                        }

                        killerProfile.getData().save();
                    }
                    break;

                default:
                    break;
            }

            victim.sendMessage(TTT.prefix + "Du wurdest von §l" + killerRole.getRoleColor() + victim.getName() + " §7getötet");

            if (victimRole == Role.TRAITOR)
                plugin.getRoleManager().getTraitorList().remove(victim.getName());
            plugin.getPlayers().remove(victim);

            ingameState.checkGameEnd();
        } else {
            if (plugin.getRoleManager().getRole(victim) == Role.TRAITOR)
                plugin.getRoleManager().getTraitorList().remove(victim.getName());
            plugin.getPlayers().remove(victim);

            ingameState.checkGameEnd();
        }
        for (Player all : Bukkit.getOnlinePlayers())
            event.setDeathMessage(null);
    }


    @EventHandler
    public void quitListener(PlayerQuitEvent event) {
        if (!(plugin.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;
        Player player = event.getPlayer();
        if (plugin.getPlayers().contains(player)) {
            IngameState ingameState = (IngameState) plugin.getGameStateManager().getCurrentGameState();
            plugin.getPlayers().remove(player);
            event.setQuitMessage(null);
            ingameState.checkGameEnd();
        }
    }

}
