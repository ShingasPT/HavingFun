package me.shingaspt.plugins.havingfun.items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class UpgradeChest extends ItemStack {

    public UpgradeChest(int upgrades, int price){
        this.setAmount(1);
        this.setType(Material.CHEST);

        ItemMeta meta = this.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize("<italic:false><light_purple>Upgrade Mine"));
        TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("upgrade", String.valueOf(upgrades)),
                                                        Placeholder.parsed("price", String.valueOf(price)));
        meta.lore(Arrays.asList(mm.deserialize(""),
                                mm.deserialize("<italic:false><gray>Use /blocks to see the blocks you have!"),
                                mm.deserialize(""),
                                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Upgrades ➤ <upgrade></gradient>", placeholders),
                                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Upgrade Price ➤ <price></gradient>", placeholders)));
        this.setItemMeta(meta);
    }

}
