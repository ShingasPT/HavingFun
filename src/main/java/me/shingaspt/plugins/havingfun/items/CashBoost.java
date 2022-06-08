package me.shingaspt.plugins.havingfun.items;

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

public class CashBoost extends ItemStack {

    public CashBoost(int boost, int minutes){
        this.setAmount(1);
        this.setType(Material.PAPER);

        ItemMeta meta = this.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey amount = new NamespacedKey(HavingFun.getInstance(), "boost");
        NamespacedKey time = new NamespacedKey(HavingFun.getInstance(), "time");

        container.set(amount, PersistentDataType.INTEGER, boost);
        container.set(time, PersistentDataType.INTEGER, minutes);

        TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("boost", String.valueOf(container.get(amount, PersistentDataType.INTEGER))),
                                                        Placeholder.parsed("time", String.valueOf(container.get(time, PersistentDataType.INTEGER))));

        MiniMessage mm = MiniMessage.miniMessage();
        meta.displayName(mm.deserialize("<green>Cash Boost +<boost>",placeholders));
        meta.lore(Arrays.asList(mm.deserialize(""),
                                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Boost Time ➤ <time>m",placeholders)));

        this.setItemMeta(meta);
    }

}
