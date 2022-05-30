package me.shingaspt.plugins.havingfun.Items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InvFrame extends ItemStack {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public InvFrame(){
        this.setAmount(1);
        this.setType(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = this.getItemMeta();
        meta.displayName(mm.deserialize("<white>"));
        this.setItemMeta(meta);
    }

}
