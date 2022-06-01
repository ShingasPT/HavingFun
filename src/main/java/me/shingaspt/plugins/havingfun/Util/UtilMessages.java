package me.shingaspt.plugins.havingfun.Util;

import me.shingaspt.plugins.havingfun.HavingFun;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class UtilMessages {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component getChatFormat(Player p, Component message){
        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                        Placeholder.parsed("rank", getPrefix(p)),
                                                        Placeholder.component("message", message));
        return mm.deserialize("<rank><player> <dark_gray>➥ <gray><message>", placeholders);
    }

    public static String getPrefix(Player p) {
        RegisteredServiceProvider<Chat> rsp = HavingFun.getInstance().getServer().getServicesManager().getRegistration(Chat.class);
        assert rsp != null;
        Chat chat = rsp.getProvider();
        return chat.getPlayerPrefix(p);
    }

    public static Component getJoinMessage(Player p) {
        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                        Placeholder.parsed("rank", getPrefix(p)));
        return mm.deserialize("<gray>Welcome Back <rank><light_purple><player> <gray>[<green>+<gray>]", placeholders);
    }

    public static Component getNewJoinMessage(Player p) {
        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                        Placeholder.parsed("rank", getPrefix(p)),
                                                        Placeholder.parsed("unique", String.valueOf(Bukkit.getOfflinePlayers().length)));
        return mm.deserialize("<gray>Welcome <rank><light_purple><player> <gray>to the server! <gray>#<white><unique>", placeholders);
    }

    public static Component getLeaveMessage(Player p) {
        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()));
        return mm.deserialize("<gray>Hope to see you soon <light_purple><player> <gray>[<red>-<gray>]", placeholder);
    }

    public static Component getStarterMessage(){
        return mm.deserialize("<newline>" +
                "<gradient:#D400FF:#B40092:#9F15FF>╔════════════════════════════════╗</gradient><newline>" +
                "<newline>" +
                "  <gray>Start Your Journey With <gradient:#9F15FF:#E304FF>/mine</gradient><newline>" +
                "  <gray>Check Your/a Players Blocks With <gradient:#9F15FF:#E304FF>/blocks [player]</gradient><newline>" +
                "  <gray>There's a low chance of getting a <green>Cash Boost <gray>while mining!<newline>" +
                "<newline>" +
                "<gradient:#D400FF:#B40092>╚════════════════════════════════╝</gradient>" +
                "<newline>");
    }

}
