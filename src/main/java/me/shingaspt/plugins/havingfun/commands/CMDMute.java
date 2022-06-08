package me.shingaspt.plugins.havingfun.commands;

import me.shingaspt.plugins.havingfun.data.PlayerData;
import me.shingaspt.plugins.havingfun.HavingFun;
import me.shingaspt.plugins.havingfun.util.UtilPlayerData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.apache.commons.lang.time.DateUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CMDMute implements TabExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(args.length == 0){
            sender.sendMessage(mm.deserialize("<red>/mute <gray><player> [time-span] [reason] "));
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null){
            sender.sendMessage(mm.deserialize("<gray>This <red>player <gray>does not exist or is not online!"));
            return true;
        }

        if(!(p.hasPermission("admin.mute"))){
            p.sendMessage(mm.deserialize("<gray>You do not have <red>permission <gray>for this!"));
            return true;
        }

        switch(args.length){
            case 1 -> Bukkit.broadcast(getMuteMessage(p, "p", "Staff Mute"));
            case 2 -> Bukkit.broadcast(getMuteMessage(p, args[1], "Staff Mute"));
            case 3 -> Bukkit.broadcast(getMuteMessage(p, args[1], args[2]));
        }

        return true;
    }

    private Component getMuteMessage(Player p, String time, String reason) {

        if(time.equals("p") || time.equals("perm")) { time = "permanently"; }

        TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("player", p.getName()),
                                                        Placeholder.parsed("time", time),
                                                        Placeholder.parsed("reason", reason));

        mutePlayer(p, time);

        return mm.deserialize("<red><player> <gray>has been muted for <red><time> <gray>due to <red><reason><gray>!",placeholders);
    }

    private void mutePlayer(Player p, String time) {

        Date date = new Date();
        Date finalDate;

        if(!(time.equals("permanently"))){
            ArrayList<String> split = new ArrayList<>();
            if(time.contains("m")){
                split.addAll(Arrays.asList(time.split("m")));
                split.add("m");
            }else if(time.contains("h")){
                split.addAll(Arrays.asList(time.split("h")));
                split.add("h");
            }else if(time.contains("d")){
                split.addAll(Arrays.asList(time.split("d")));
                split.add("d");
            }

            switch(split.get(1)){
                case "m" -> date = DateUtils.addMinutes(date, Integer.parseInt(split.get(0)));
                case "h" -> date = DateUtils.addHours(date, Integer.parseInt(split.get(0)));
                case "d" -> date = DateUtils.addDays(date, Integer.parseInt(split.get(0)));
            }
        }else{
            date = DateUtils.addYears(date, 99999);
        }

        finalDate = date;

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
        player.setMute(finalDate);
        player.setMuted(true);

        Bukkit.getScheduler().runTaskTimer(HavingFun.getInstance(), task -> {

            if(player.isMuted()) {
                if (new Date().compareTo(finalDate) > 0) {
                    player.setMute(null);
                    player.setMuted(false);
                    p.sendMessage(MiniMessage.miniMessage().deserialize("<gray>You have been <red>unmuted<gray>!"));
                }
            }else{
                task.cancel();
            }
        }, 10, 10);
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        switch(args.length){
            case 1 -> {
                List<String> names = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    names.add(player.getName());
                }
                return StringUtil.copyPartialMatches(args[0], names, new ArrayList<>());
            }
            case 2 -> { return StringUtil.copyPartialMatches(args[1], Arrays.asList("perm","1m","1h","1d"), new ArrayList<>()); }
            case 3 -> { return StringUtil.copyPartialMatches(args[2], List.of("reason"), new ArrayList<>()); }
        }

        return null;
    }


}
