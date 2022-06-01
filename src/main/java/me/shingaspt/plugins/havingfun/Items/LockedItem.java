package me.shingaspt.plugins.havingfun.Items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LockedItem extends ItemStack {

    public LockedItem(){
        this.setAmount(1);
        this.setType(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = this.getItemMeta();
        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize("<italic:false><red>Locked Block"));
        meta.lore(Arrays.asList(mm.deserialize(""),
                                mm.deserialize("<italic:false><dark_gray>You haven't unlocked this block yet"),
                                mm.deserialize("<italic:false><dark_gray>Upgrade your mine to unlock better blocks!")));
        this.setItemMeta(meta);
    }

}
