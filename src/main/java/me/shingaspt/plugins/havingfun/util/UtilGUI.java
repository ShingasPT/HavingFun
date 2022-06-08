package me.shingaspt.plugins.havingfun.util;

import me.shingaspt.plugins.havingfun.data.PlayerData;
import me.shingaspt.plugins.havingfun.HavingFun;
import me.shingaspt.plugins.havingfun.items.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Objects;

public class UtilGUI {

    private static final MiniMessage mm = MiniMessage.miniMessage();
    public static Inventory getBoxInventory(Player p) {

        Inventory temp = Bukkit.createInventory(null, 54, getBoxTitle());

        float delay = 3;

        for(int slot: UtilBlocks.getMineSlots()){
            temp.setItem(slot, new PlaceholderItem());
            Bukkit.getScheduler().runTaskLater(HavingFun.getInstance(), () -> temp.setItem(slot, UtilBlocks.getRandomBlock(p.getUniqueId())), Math.round(20 * delay));
            delay += 0.5;
        }

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());

        temp.setItem(0, new UpgradeChest(player.getUpgrades(), player.getUpgradePrice()));
        temp.setItem(8, new FortuneBook(player.getFortune(), player.getFortunePrice()));

        fillInv(temp, new InvFrame());

        return temp;

    }

    public static Inventory getBlocksInventory(OfflinePlayer p) {

        Inventory temp = Bukkit.createInventory(null, 54, getBlocksTitle());

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
        for(int i = 0; i <= player.getUpgrades(); i++){
            temp.addItem(UtilBlocks.getAllBlocks().get(i));
        }

        fillInv(temp, new LockedItem());

        return temp;
    }

    public static Inventory getStatsInventory(OfflinePlayer p){

        Inventory temp = Bukkit.createInventory(null, InventoryType.HOPPER, getStatsTitle());

        temp.setItem(2, getPlayerSkull(p));

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

    public static ItemStack getPlayerSkull(OfflinePlayer p){

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1 );
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());

        TagResolver placeholders = TagResolver.resolver(Placeholder.parsed("playtime", String.valueOf(p.getFirstPlayed())),
                                                        Placeholder.parsed("balance", String.valueOf(player.getBalance())),
                                                        Placeholder.parsed("blocks", String.valueOf(player.getMined())),
                                                        Placeholder.parsed("fortune", String.valueOf(player.getFortune())),
                                                        Placeholder.component("player", getPlayerDisplayName(p)));

        skullMeta.lore(Arrays.asList(mm.deserialize(""),
                                     mm.deserialize("<italic:false><gradient:#A600FF:#D200EC>Playtime » <playtime></gradient>"),
                                     mm.deserialize("<italic:false><gradient:#A600FF:#D200EC>Balance » <balance></gradient>",placeholders),
                                     mm.deserialize("<italic:false><gradient:#A600FF:#D200EC>Fortune » <fortune></gradient>",placeholders),
                                     mm.deserialize("<italic:false><gradient:#A600FF:#D200EC>Mined Blocks » <blocks></gradient>",placeholders)));

        skullMeta.setOwningPlayer(p);

        skullMeta.displayName(mm.deserialize("<light_purple><player>",placeholders));
        skull.setItemMeta(skullMeta);

        return skull;
    }
    public static Component getBlocksTitle(){
        return mm.deserialize("<italic:false><gradient:#A300FB:#D200FD>Blocks</gradient>");
    }

    public static Component getBoxTitle() {
        return mm.deserialize("<italic:false><gradient:#A300FB:#D200FD>Mine</gradient>");
    }

    public static Component getStatsTitle() {
        return mm.deserialize("<italic:false><gradient:#A300FB:#D200FD>Stats</gradient>");
    }

    public static Component getPlayerDisplayName(OfflinePlayer p) {
        TagResolver placeholder = TagResolver.resolver(Placeholder.component("player", Objects.requireNonNull(p.getPlayer()).displayName()));
        return mm.deserialize("<italic:false><player>",placeholder);
    }
}
