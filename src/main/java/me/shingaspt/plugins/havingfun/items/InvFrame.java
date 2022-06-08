package me.shingaspt.plugins.havingfun.items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvFrame extends ItemStack {

    public InvFrame(){
        this.setAmount(1);
        this.setType(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = this.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize("<white>"));
        this.setItemMeta(meta);
    }

}
