package me.shingaspt.plugins.havingfun.Util;

import me.shingaspt.plugins.havingfun.HavingFun;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class UtilMessages {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component getChatFormat(Player p, Component message){
        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                        Placeholder.component("rank", getPrefix(p)),
                                                        Placeholder.component("message", message));
        return mm.deserialize("<rank><player> <dark_gray>➔ <gray><message>", placeholders);
    }

    public static Component getPrefix(Player p) {
        RegisteredServiceProvider<Chat> rsp = HavingFun.getInstance().getServer().getServicesManager().getRegistration(Chat.class);
        Chat chat = rsp.getProvider();
        return mm.deserialize(chat.getPlayerPrefix(p));
    }

    public static Component getJoinMessage(Player p) {
        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()));
        return mm.deserialize("<gold>Welcome Back <player><gold>!", placeholder);
    }

    public static Component getNewJoinMessage(Player p) {
        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()));
        return mm.deserialize("<gold>Welcome <player> <gold>to the server! Enjoy your stay!", placeholder);
    }

    public static Component getLeaveMessage(Player p) {
        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()));
        return mm.deserialize("<gold>Hope to see you soon <player><gold>!", placeholder);
    }
}
