package me.shingaspt.plugins.havingfun.Commands;

import me.shingaspt.plugins.havingfun.Util.UtilGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMDMine implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player p)) {
            sender.sendMessage("Console is not allowed to run this command!");
            return true;
        }

        p.openInventory(UtilGUI.getBoxInventory(p));

        return true;

    }

}
