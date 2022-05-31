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
        Chat chat = rsp.getProvider();
        return chat.getPlayerPrefix(p);
    }

    public static Component getJoinMessage(Player p) {
        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                        Placeholder.parsed("rank", getPrefix(p)));
        return mm.deserialize("<purple>Welcome Back <rank><player> <dark_gray>| <gray>[<green>+<gray>]", placeholders);
    }

    public static Component getNewJoinMessage(Player p) {
        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                        Placeholder.parsed("rank", getPrefix(p)),
                                                        Placeholder.parsed("unique", String.valueOf(Bukkit.getOfflinePlayers().length)));
        return mm.deserialize("<purple>Welcome <rank><player> <purple>to the server! <dark_gray>| <purple>#<pink><unique>", placeholders);
    }

    public static Component getLeaveMessage(Player p) {
        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()));
        return mm.deserialize("<gold>Hope to see you soon <player><gold>! <gray>[<red>-<gray>]", placeholder);
    }

    public static Component getStarterMessage(){
        return mm.deserialize("<gradient:#D400FF:#B40092>┌───────────────────────────────┐</gradient><newline>" +
                "<newline>" +
                "  <gray>Start Your Journey With <gradient:#9F15FF:#E304FF>/box</gradient><newline>" +
                "  <gray>Check Out All The Blocks You've Achieved With <gradient:#9F15FF:#E304FF>/blocks</gradient><newline>" +
                "  <gray>Enchant Your Mine With <gradient:#9F15FF:#E304FF>/fortune</gradient><newline>" +
                "<newline>" +
                "<gradient:#D400FF:#B40092>└───────────────────────────────┘</gradient>");
    }

}
