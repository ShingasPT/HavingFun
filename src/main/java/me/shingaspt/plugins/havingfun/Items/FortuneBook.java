package me.shingaspt.plugins.havingfun.Items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class FortuneBook extends ItemStack {

    public FortuneBook(int fortune, int fortunePrice) {
        this.setAmount(1);
        this.setType(Material.ENCHANTED_BOOK);

        ItemMeta meta = this.getItemMeta();

        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize("<italic:false><gradient:#8700BE:#C600B8>Upgrade Fortune</gradient>"));

        TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("fortune", String.valueOf(fortune)),
                                                        Placeholder.parsed("price", String.valueOf(fortunePrice)));
        meta.lore(Arrays.asList(mm.deserialize(""),
                                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Fortune ➤ <fortune></gradient>", placeholders),
                                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Fortune Price ➤ <price></gradient>", placeholders)));

        this.setItemMeta(meta);
    }
}
