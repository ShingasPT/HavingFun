package me.shingaspt.plugins.havingfun.util;

import me.shingaspt.plugins.havingfun.HavingFun;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.Arrays;
public class UtilMessages {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component getChatFormat(Player p, Component message){

        String plain = PlainTextComponentSerializer.plainText().serialize(message);
        Component finalMessage;


        if(p.hasPermission("chat.colors")){
            finalMessage = getColoredMessage(plain);
        }else{
            finalMessage = message;
        }

        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                        Placeholder.parsed("rank", getPrefix(p)),
                                                        Placeholder.component("message", finalMessage));
        return mm.deserialize("<rank><player> <dark_gray>➥ <gray><message>", placeholders);
    }

    private static Component getColoredMessage(String message) {
        ArrayList<String> colors = new ArrayList<>(Arrays.asList("&0","&8","&7","&f","&5","&d","&9","&1","&3","&b","&a","&2","&e","&6","&c","&4","&r","&o","&n","&m","&l","&k"));
        ArrayList<String> format = new ArrayList<>(Arrays.asList("<black>","<dark_gray>","<gray>","<white>","<dark_purple>","<light_purple>","<blue>","<dark_blue>","<dark_aqua>","<aqua>","<green>","<dark_green>","<yellow>","<gold>","<red>","<dark_red>","<reset>","<italic>","<underlined>","<strikethrough>","<bold>","<obfuscated>"));
        for(int i = 0; i < colors.size(); i++) {
            message = message.replaceAll(colors.get(i), format.get(i));
        }
        return mm.deserialize(message);
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
        return mm.deserialize("<gray>Welcome back <rank><player> <gray>[<green>+<gray>]", placeholders);
    }

    public static Component getNewJoinMessage(Player p) {
        TagResolver placeholders = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                        Placeholder.parsed("rank", getPrefix(p)),
                                                        Placeholder.parsed("unique", String.valueOf(UtilPlayerData.getPlayers().size())));
        return mm.deserialize("<gray>Welcome <rank><player> <gray>to the server! <gray>#<white><unique>", placeholders);
    }

    public static Component getLeaveMessage(Player p) {
        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()),
                                                       Placeholder.parsed("rank", getPrefix(p)));
        return mm.deserialize("<gray>Hope to see you soon <rank><player> <gray>[<red>-<gray>]", placeholder);
    }

    public static Component getStarterMessage(){
        return mm.deserialize("<newline>" +
                "<gradient:#D400FF:#B40092:#9F15FF>╔════════════════════════════════╗</gradient><newline>" +
                "<newline>" +
                "  <gray>Start Your Journey With <gradient:#9F15FF:#E304FF>/mine</gradient><newline>" +
                "  <gray>Check your Statistics With <gradient:#9F15FF:#E304FF>/stats [player]</gradient><newline>" +
                "  <gray>Check Your/a Players Blocks With <gradient:#9F15FF:#E304FF>/blocks [player]</gradient><newline>" +
                "  <gray>There's a low chance of getting a <green>Cash Boost <gray>while mining!<newline>" +
                "<newline>" +
                "<gradient:#D400FF:#B40092>╚════════════════════════════════╝</gradient>" +
                "<newline>");
    }

}
