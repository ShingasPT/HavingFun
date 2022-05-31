package me.shingaspt.plugins.havingfun.Items;

import de.tr7zw.nbtapi.NBTItem;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class UpgradeChest extends ItemStack {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public UpgradeChest(int upgrades, int price){
        this.setAmount(1);
        this.setType(Material.CHEST);

        NBTItem nbt = new NBTItem(this);
        nbt.setString("Upgrades", String.valueOf(upgrades));
        nbt.setString("UpgradePrice", String.valueOf(price));

        ItemMeta meta = this.getItemMeta();
        meta.displayName(mm.deserialize("<italic:false><gradient:#FF5400:#E0EC00>Upgrade Mine</gradient>"));
        TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("upgrade", nbt.getString("Upgrade")),
                                                        Placeholder.parsed("price", nbt.getString("UpgradePrice")));
        meta.lore(Arrays.asList(mm.deserialize(""),
                                mm.deserialize("<italic:false><gray>Use /blocks to find out what blocks you have/can get!"),
                                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Upgrades » <upgrade></gradient>", placeholders),
                                mm.deserialize("<italic:false><gradient:#B500FF:#EC00DC>Upgrade Price » <price></gradient>", placeholders)));
        this.setItemMeta(meta);
    }

}
