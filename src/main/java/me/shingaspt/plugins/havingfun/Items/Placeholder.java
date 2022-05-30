package me.shingaspt.plugins.havingfun.Items;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Placeholder extends ItemStack {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public Placeholder(){
        this.setAmount(1);
        this.setType(Material.BEDROCK);
        ItemMeta meta = this.getItemMeta();
        meta.displayName(mm.deserialize("<dark_gray>Respawning"));
        this.setItemMeta(meta);
    }

}
