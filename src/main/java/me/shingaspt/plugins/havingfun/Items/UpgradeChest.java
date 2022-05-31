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
        ItemMeta meta = this.getItemMeta();
        meta.displayName(mm.deserialize("<gradient:#FF5400:#E0EC00>Upgrade Mine</gradient>"));

        NBTItem nbt = new NBTItem(this);
        nbt.setString("Upgrades", String.valueOf(upgrades));
        nbt.setString("UpgradePrice", String.valueOf(price));

        TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("upgrade", nbt.getString("Upgrade")), Placeholder.parsed("price", nbt.getString("UpgradePrice")));
        meta.lore(Arrays.asList(mm.deserialize(""),
                                mm.deserialize("<gray>Use /blocks to find out what blocks you have/can get!"),
                                mm.deserialize("<gradient:#B500FF:#EC00DC>Upgrades » <upgrade></gradient>", placeholders),
                                mm.deserialize("<gradient:#B500FF:#EC00DC>Upgrade Price » <price></gradient>", placeholders)));
    }

}
