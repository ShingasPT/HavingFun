package me.shingaspt.plugins.havingfun.commands;

import me.shingaspt.plugins.havingfun.data.PlayerData;
import me.shingaspt.plugins.havingfun.util.UtilPlayerData;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CMDAdminBalance implements TabExecutor {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player p)){
            sender.sendMessage("Console is not allowed to run this command!");
            return true;
        }

        if(!(p.hasPermission("admin.bal"))){
            p.sendMessage("<gray>You do not have <red>permission <gray>for this command!");
            return true;
        }

        switch(args.length) {
            case 0, 1 -> p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
            case 2 -> {
                PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
                switch(args[0]){
                    case "add" -> {
                        try{
                            player.setBalance(player.getBalance() + Integer.parseInt(args[1]));
                            TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("balance", String.valueOf(player.getBalance())));
                            p.sendMessage(mm.deserialize("<gray>Successfully added <red><balance> <gray>to your balance!",placeholder));
                        }catch(NumberFormatException nfe){
                            p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
                        }
                    }
                    case "remove" -> {
                        try{
                            player.setBalance(player.getBalance() - Integer.parseInt(args[1]));
                            TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("balance", String.valueOf(player.getBalance())));
                            p.sendMessage(mm.deserialize("<gray>Successfully removed <red><balance> <gray>from your balance!",placeholder));
                        }catch(NumberFormatException nfe){
                            p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
                        }
                    }
                    case "set" -> {
                        try{
                            player.setBalance(Integer.parseInt(args[1]));
                            TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("balance", String.valueOf(player.getBalance())));
                            p.sendMessage(mm.deserialize("<gray>Successfully set your balance to <red><balance><gray>!",placeholder));
                        }catch(NumberFormatException nfe){
                            p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
                        }
                    }
                    default -> p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
                }
            }
            case 3 -> {
                OfflinePlayer offline = Bukkit.getOfflinePlayerIfCached(args[2]);
                if(offline == null){
                    p.sendMessage(mm.deserialize("<gray>This <red>player <gray>does not exist!"));
                    return true;
                }
                PlayerData player = UtilPlayerData.getPlayerFromUUID(offline.getUniqueId());
                switch(args[0]){
                    case "add" -> {
                        try{
                            player.setBalance(player.getBalance() + Integer.parseInt(args[1]));
                            TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("balance", String.valueOf(player.getBalance())),
                                                                           Placeholder.parsed("player", Objects.requireNonNull(Bukkit.getOfflinePlayer(player.getUUID()).getName())));
                            p.sendMessage(mm.deserialize("<gray>Successfully added <red><balance> <gray>to <red><player><gray>'s balance!",placeholders));
                        }catch(NumberFormatException nfe){
                            p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
                        }
                    }
                    case "remove" -> {
                        try{
                            player.setBalance(player.getBalance() - Integer.parseInt(args[1]));
                            TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("balance", String.valueOf(player.getBalance())),
                                                                            Placeholder.parsed("player", Objects.requireNonNull(Bukkit.getOfflinePlayer(player.getUUID()).getName())));
                            p.sendMessage(mm.deserialize("<gray>Successfully removed <red><balance> <gray>from <red><player><gray>'s balance!",placeholders));
                        }catch(NumberFormatException nfe){
                            p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
                        }
                    }
                    case "set" -> {
                        try{
                            player.setBalance(Integer.parseInt(args[1]));
                            TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("balance", String.valueOf(player.getBalance())),
                                                                            Placeholder.parsed("player", Objects.requireNonNull(Bukkit.getOfflinePlayer(player.getUUID()).getName())));
                            p.sendMessage(mm.deserialize("<gray>Successfully set <red><player><gray>'s balance to <red><balance><gray>!",placeholders));
                        }catch(NumberFormatException nfe){
                            p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
                        }
                    }
                    default -> p.sendMessage(mm.deserialize("<red>/adminBal <add/remove/set> <amount> [player]"));
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender.hasPermission("admin.balance")){
            switch(args.length){
                case 1 -> { return StringUtil.copyPartialMatches(args[0], Arrays.asList("add","remove","set"), new ArrayList<>()); }
                case 2 -> { return StringUtil.copyPartialMatches(args[1], Arrays.asList("1","10","100","1000"), new ArrayList<>()); }
                case 3 -> {
                    List<String> names = new ArrayList<>();
                    for(OfflinePlayer player : Bukkit.getOfflinePlayers()){
                        names.add(player.getName());
                    }
                    return StringUtil.copyPartialMatches(args[2], names, new ArrayList<>());
                }
            }
        }
        return null;
    }
}
