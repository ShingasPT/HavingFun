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

    public static Component getChatFormat(Player player, Component message){
        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", player.displayName()),
                                                        Placeholder.component("rank", getPrefix(player)),
                                                        Placeholder.component("message", message));
        return mm.deserialize("<rank><player> <dark_gray>➔ <gray><message>", placeholders);
    }

    public static Component getPrefix(Player player) {
        RegisteredServiceProvider<Chat> rsp = HavingFun.getInstance().getServer().getServicesManager().getRegistration(Chat.class);
        Chat chat = rsp.getProvider();
        return mm.deserialize(chat.getPlayerPrefix(player));
    }
}
