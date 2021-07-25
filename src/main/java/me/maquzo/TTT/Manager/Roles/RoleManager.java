package me.maquzo.TTT.Manager.Roles;

import me.maquzo.TTT.*;
import me.maquzo.TTT.utils.Profile;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RoleManager {

    private TTT plugin;
    private HashMap<String, Role> playerRoles;
    private ArrayList<Player> players;
    private ArrayList<String> traitorList;

    private int traitorSize;
    private int innocentSize;
    private int detectiveSize;

    public RoleManager(TTT plugin) {
        this.plugin = plugin;
        playerRoles = new HashMap<>();
        players = plugin.getPlayers();
        traitorList = new ArrayList<>();
    }

    public void roleManager() {
        int playerSize = players.size();
        traitorSize = (int) Math.round(Math.log(playerSize) * 1.2);
        detectiveSize = (int) Math.round(Math.log(playerSize) * 0.75);
        innocentSize = playerSize - traitorSize - detectiveSize;


        Collections.shuffle(players);

        int counter = 0;


        for (int i = counter; i < traitorSize; i++) {
            playerRoles.put(players.get(i).getName(), Role.TRAITOR);
            traitorList.add(players.get(i).getName());
        }
        counter += traitorSize;
        for (int i = counter; i < detectiveSize + counter; i++) {
            playerRoles.put(players.get(i).getName(), Role.DETECTIVE);
        }
        counter += detectiveSize;

        for (int i = counter; i < innocentSize + counter; i++) {
            playerRoles.put(players.get(i).getName(), Role.INNOCENT);
        }

        for (int i = counter; i < traitorSize; i++) {
            Player player = players.get(i);
            player.setVelocity(new Vector(1, 2, 3));
            Profile profile = plugin.getProfileManager().getProfile(player);
            if (!profile.getData().getStats().isTraitorPassActive()) return;
            playerRoles.put(players.get(i).getName(), Role.TRAITOR);
            traitorList.add(players.get(i).getName());
        }


        for (int i = counter; i < detectiveSize; i++) {
            Player player = players.get(i);
            Profile profile = plugin.getProfileManager().getProfile(player);
            if (!profile.getData().getStats().isDetectivePassActive()) return;
            playerRoles.put(players.get(i).getName(), Role.DETECTIVE);
        }


        for (Player all : Bukkit.getOnlinePlayers()) {
            switch (getRole(all)) {
                case TRAITOR:
                    for (Player otherPlayers : players)
                        setFakeArmor(otherPlayers, all.getEntityId(), (getRole(otherPlayers) != Role.TRAITOR) ? Color.GREEN : Color.RED);
                    break;
                case DETECTIVE:
                    setNormalArmor(all, Color.AQUA);
                    break;
                case INNOCENT:
                    setNormalArmor(all, Color.GREEN);
                    break;
                default:
                    break;
            }
        }

    }

    public void setNormalArmor(Player player, Color color) {
        player.getInventory().setChestplate(getRoleChest(color));
    }

    public void setFakeArmor(Player player, int playerID, Color color) {
        ItemStack chestPlate = getRoleChest(color);


        PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(playerID, 3, CraftItemStack.asNMSCopy(chestPlate));
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }


    private ItemStack getRoleChest(Color color) {
        ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestMeta = (LeatherArmorMeta) chestPlate.getItemMeta();
        chestMeta.setColor(color);
        chestPlate.setItemMeta(chestMeta);
        return chestPlate;
    }

    public Role getRole(Player player) {
        return playerRoles.get(player.getName());
    }

    public ArrayList<String> getTraitorList() {
        return traitorList;
    }
}
