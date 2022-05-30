package me.shingaspt.plugins.havingfun.Util;

import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.Items.InvFrame;
import me.shingaspt.plugins.havingfun.Items.LockedItem;
import me.shingaspt.plugins.havingfun.Items.UpgradeChest;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class UtilGUI {

    private static final MiniMessage mm = MiniMessage.miniMessage();
    private static final ArrayList<Integer> mineSlots = new ArrayList<>(Arrays.asList(20,21,22,23,24,29,30,31,32,33,38,39,40,41,42));

    public static Inventory getBoxInventory(Player p, int upgrades, int price) {

        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()));
        Component title = mm.deserialize("<player> <bold><gold>Box", placeholder);

        Inventory temp = Bukkit.createInventory(null, 54, title);

        for(int slot: mineSlots){
            temp.setItem(slot, UtilBlocks.getRandomBlock(p.getUniqueId()));
        }

        fillInv(temp, new InvFrame());

        temp.setItem(12, getPlayerSkull(p));
        temp.setItem(14, new UpgradeChest(upgrades, price));

        return temp;

    }

    public static ItemStack getPlayerSkull(Player p){

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1 );
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        skullMeta.displayName(p.displayName());

        TagResolver placeholder = TagResolver.resolver(Placeholder.parsed("price", String.valueOf(UtilPlayerData.getBalance(p.getUniqueId()))));
        skullMeta.lore(Arrays.asList(mm.deserialize(""), mm.deserialize("<gradient:#A600FF:#D200EC>Balance » <price></gradient>",placeholder)));

        skullMeta.setOwningPlayer(p);
        skull.setItemMeta(skullMeta);

        return skull;
    }

    public static Inventory getBlocksInventory(Player p) {

        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()));
        Component title = mm.deserialize("<player> <bold><gold>Blocks", placeholder);

        Inventory temp = Bukkit.createInventory(null, 54, title);

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
        for(ItemStack block : player.getPlayerBlocks()){
            temp.addItem(block);
        }

        fillInv(temp, new LockedItem());

        return temp;
    }

    private static void fillInv(Inventory inv, ItemStack item) {
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null){
                inv.setItem(i, item);
            }
        }
    }

    public static ArrayList<Integer> getMineSlots(){
        return mineSlots;
    }
}
