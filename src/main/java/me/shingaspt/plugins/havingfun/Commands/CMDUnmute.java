package me.shingaspt.plugins.havingfun.Commands;

import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.HavingFun;
import me.shingaspt.plugins.havingfun.Util.UtilPlayerData;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CMDUnmute implements TabExecutor {

    private final MiniMessage mm = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length == 0){
            sender.sendMessage(mm.deserialize("<red>/unmute <gray><player>"));
            return true;
        }

        Player p = Bukkit.getPlayer(args[0]);
        if(p == null){
            sender.sendMessage(mm.deserialize("<gray>This <red>player <gray>does not exist or is not online!"));
            return true;
        }

        if(!(p.hasPermission("admin.unmute"))){
            p.sendMessage(mm.deserialize("<gray>You do not have <red>permission <gray>for this!"));
            return true;
        }

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
        if(player.isMuted()){
            player.setMute(null);
            player.setMuted(false);
            TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("player", p.getName()));
            Bukkit.broadcast(mm.deserialize("<red><player> <gray>has been unmuted!", placeholder));
        }else{
            sender.sendMessage(mm.deserialize("<gray>This <red>player <gray>is not muted!"));
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(args.length == 1) {
            List<String> muted = new ArrayList<>();
            for(Player player : Bukkit.getOnlinePlayers()){
                if(player.getPersistentDataContainer().has(new NamespacedKey(HavingFun.getInstance(), "mute"))) {
                    muted.add(player.getName());
                }
            }
            if(muted.size() > 0){
                return StringUtil.copyPartialMatches(args[0], muted, new ArrayList<>());
            }else{
                return List.of("-----");
            }
        }

        return null;

    }
}
