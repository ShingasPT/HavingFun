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

        NBTItem nbt = new NBTItem(this);
        nbt.setString("Reward", "2");

        ItemMeta meta = this.getItemMeta();
        meta.displayName(mm.deserialize("<italic:false><gradient:#6A6A6A:#909090>Stone"));

        TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("reward", nbt.getString("Reward")));
        meta.lore(Arrays.asList(mm.deserialize(""),
                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Reward » <reward></gradient>", placeholder)));

        this.setItemMeta(meta);
    }

}
