package me.shingaspt.plugins.havingfun.commands;

import me.shingaspt.plugins.havingfun.util.UtilGUI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CMDStats implements TabExecutor {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player p)){
            sender.sendMessage("Console is not allowed to run this command!");
            return true;
        }

        switch(args.length) {
            case 0 -> p.openInventory(UtilGUI.getStatsInventory(p));
            case 1 -> {

                OfflinePlayer player = Bukkit.getOfflinePlayerIfCached(args[0]);
                if(player == null){
                    p.sendMessage(mm.deserialize("<gray>This <red>player <gray>does not exist!"));
                    return true;
                }
                p.openInventory(UtilGUI.getStatsInventory(player));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            List<String> players = new ArrayList<>();
            for(OfflinePlayer player : Bukkit.getOfflinePlayers()){
                players.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[0], players, new ArrayList<>());
        }
        return null;
    }
}
