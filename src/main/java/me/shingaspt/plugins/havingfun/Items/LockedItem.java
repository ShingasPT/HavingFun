package me.shingaspt.plugins.havingfun.Items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class LockedItem extends ItemStack {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public LockedItem(){
        this.setAmount(1);
        this.setType(Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = this.getItemMeta();
        meta.displayName(mm.deserialize("<red>Locked Block"));
        meta.lore(Arrays.asList(mm.deserialize(""), mm.deserialize("<dark_gray>You haven't unlocked this block yet"),mm.deserialize("<dark_gray>Upgrade your mine to unlock better blocks!")));
        this.setItemMeta(meta);
    }

}
