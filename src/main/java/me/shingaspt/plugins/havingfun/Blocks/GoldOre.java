package me.shingaspt.plugins.havingfun.Blocks;

import me.shingaspt.plugins.havingfun.HavingFun;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class GoldOre extends ItemStack {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public GoldOre(){
        this.setAmount(1);
        this.setType(Material.GOLD_ORE);

        ItemMeta meta = this.getItemMeta();
        meta.displayName(mm.deserialize("<italic:false><gradient:#FF7A00:#FDD500>Gold Ore"));

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(HavingFun.getInstance(), "Reward");
        container.set(key, PersistentDataType.INTEGER, 4);

        TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("reward", String.valueOf(container.get(key, PersistentDataType.INTEGER))));
        meta.lore(Arrays.asList(mm.deserialize(""),
                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Reward ➤ <reward></gradient>", placeholder)));

        this.setItemMeta(meta);
    }

}