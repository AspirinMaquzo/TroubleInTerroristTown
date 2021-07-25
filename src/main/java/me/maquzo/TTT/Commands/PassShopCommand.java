package me.maquzo.TTT.Commands;

import me.maquzo.TTT.Listener.GUIS.PassBuyGUI;
import me.maquzo.TTT.TTT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PassShopCommand implements CommandExecutor {


    TTT plugin;

    public PassShopCommand(TTT plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.openInventory(plugin.getPass().getPassShop());
            }
        }


        return false;
    }
}
