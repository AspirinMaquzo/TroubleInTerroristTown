package me.maquzo.TTT.Commands;

import me.maquzo.TTT.Listener.GUIS.ShopGUI;
import me.maquzo.TTT.Manager.Roles.Role;
import me.maquzo.TTT.TTT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

    private TTT plugin;

    public ShopCommand(TTT plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
                if (args.length == 0) {
                    Role role = plugin.getRoleManager().getRole(player);
                    ShopGUI shops = plugin.getShops();
                    if (role == null) {
                        player.sendMessage(TTT.error + "Bitte warte bis die Rollen bekannt gegeben wurden!");
                        return true;
                    }
                    switch (role) {
                        case INNOCENT:
                            player.sendMessage(TTT.error + "Als §aInnocent §7kannst du keinen Shop öffnen!");
                            break;
                        case DETECTIVE:
                            player.openInventory(plugin.getShops().getDetectiveShop());
                            break;

                        case TRAITOR:
                            player.openInventory(plugin.getShops().getTraitorShop());
                            break;

                        default:
                            break;

                    }
                } else
                    player.sendMessage(TTT.error + "Bitte benutze §c/shop");

        }

        return false;
    }
}
