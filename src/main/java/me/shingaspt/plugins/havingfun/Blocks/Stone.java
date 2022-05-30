package me.shingaspt.plugins.havingfun.Blocks;

import de.tr7zw.nbtapi.NBTItem;
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
        meta.displayName(mm.deserialize("<gradient:#6A6A6A:#909090>Stone"));

        NBTItem nbt = new NBTItem(this);
        nbt.setString("Price", "1");

        TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("price", nbt.getString("Price")));
        meta.lore(Arrays.asList(mm.deserialize(""),
                                mm.deserialize("<gradient:#B500FF:#EC00DC>Price » <price></gradient>", placeholder)));
        this.setItemMeta(meta);
    }

}
