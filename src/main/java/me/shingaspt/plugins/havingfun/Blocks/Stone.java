package me.shingaspt.plugins.havingfun.Blocks;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Stone extends ItemStack {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public Stone(){
        this.setAmount(1);
        this.setType(Material.STONE);

        ItemMeta meta = this.getItemMeta();
        meta.displayName(mm.deserialize("<italic:false><gradient:#515151:#727272>Stone"));

        TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("reward", "2"));
        meta.lore(Arrays.asList(mm.deserialize(""),
                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Reward » <reward></gradient>", placeholder)));

        this.setItemMeta(meta);
    }

}
