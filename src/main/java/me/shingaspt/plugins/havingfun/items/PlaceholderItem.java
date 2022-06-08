package me.shingaspt.plugins.havingfun.items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceholderItem extends ItemStack {

    public PlaceholderItem(){
        this.setAmount(1);
        this.setType(Material.BEDROCK);
        ItemMeta meta = this.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize("<italic:false><dark_gray>Respawning"));
        this.setItemMeta(meta);
    }

}
