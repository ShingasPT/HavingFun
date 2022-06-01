package me.shingaspt.plugins.havingfun.Util;

import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.HavingFun;
import me.shingaspt.plugins.havingfun.Items.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class UtilGUI {

    private static final MiniMessage mm = MiniMessage.miniMessage();
    public static Inventory getBoxInventory(Player p) {

        Inventory temp = Bukkit.createInventory(null, 54, getBoxTitle());

        for(int slot: UtilBlocks.getMineSlots()){
            temp.setItem(slot, new PlaceholderItem());
            Bukkit.getScheduler().runTaskLater(HavingFun.getInstance(), () -> {
                temp.setItem(slot, UtilBlocks.getRandomBlock(p.getUniqueId()));
            }, 20);
        }

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());

        temp.setItem(0, getPlayerSkull(p));
        temp.setItem(8, new UpgradeChest(player.getUpgrades(), player.getUpgradePrice()));

        fillInv(temp, new InvFrame());

        return temp;

    }

    public static Inventory getBlocksInventory(Player p) {

        Inventory temp = Bukkit.createInventory(null, 54, getBlocksTitle());

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
        for(int i = 0; i <= player.getUpgrades(); i++){
            temp.addItem(UtilBlocks.getAllBlocks().get(i));
        }

        fillInv(temp, new LockedItem());

        return temp;
    }

    public static Inventory getFortuneInventory(Player p){

        Inventory temp = Bukkit.createInventory(null, InventoryType.HOPPER, getEnchantTitle());

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
        temp.setItem(2, new FortuneBook(player.getFortune(), player.getFortunePrice()));

        fillInv(temp, new InvFrame());

        return temp;
    }

    private static void fillInv(Inventory inv, ItemStack item) {
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null){
                inv.setItem(i, item);
            }
        }
    }

    public static ItemStack getPlayerSkull(Player p){

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1 );
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("balance", String.valueOf(UtilPlayerData.getBalance(p.getUniqueId()))),
                                                        Placeholder.parsed("blocks", String.valueOf(UtilPlayerData.getPlayerMinedBlocks(p.getUniqueId()))),
                                                        Placeholder.parsed("fortune", String.valueOf(UtilPlayerData.getPlayerFortune(p.getUniqueId()))));

        skullMeta.lore(Arrays.asList(mm.deserialize(""),
                                     mm.deserialize("<italic:false><gradient:#A600FF:#D200EC>Balance » <balance></gradient>",placeholders),
                                     mm.deserialize("<italic:false><gradient:#A600FF:#D200EC>Fortune » <fortune></gradient>",placeholders),
                                     mm.deserialize("<italic:false><gradient:#A600FF:#D200EC>Mined Blocks » <blocks></gradient>",placeholders)));

        skullMeta.setOwningPlayer(p);

        skullMeta.displayName(getPlayerDisplayName(p));
        skull.setItemMeta(skullMeta);

        return skull;
    }
    public static Component getBlocksTitle(){
        return mm.deserialize("<italic:false><gradient:#A300FB:#D200FD>Player Blocks</gradient>");
    }

    public static Component getBoxTitle() {
        return mm.deserialize("<italic:false><gradient:#A300FB:#D200FD>Player Box</gradient>");
    }

    public static Component getEnchantTitle() {
        return mm.deserialize("<italic:false><gradient:#A300FB:#D200FD>Player Fortune</gradient>");
    }

    public static Component getPlayerDisplayName(Player p) {
        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", p.displayName()));
        return mm.deserialize("<italic:false><player>",placeholder);
    }

}
