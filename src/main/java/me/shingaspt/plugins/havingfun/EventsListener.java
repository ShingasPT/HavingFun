package me.shingaspt.plugins.havingfun;

import de.tr7zw.nbtapi.NBTItem;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.Items.PlaceholderItem;
import me.shingaspt.plugins.havingfun.Util.UtilBlocks;
import me.shingaspt.plugins.havingfun.Util.UtilGUI;
import me.shingaspt.plugins.havingfun.Util.UtilMessages;
import me.shingaspt.plugins.havingfun.Util.UtilPlayerData;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class EventsListener implements Listener {

    private final MiniMessage mm = MiniMessage.miniMessage();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player p = (Player) event.getWhoClicked();

        PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());

        if(event.getInventory().equals(UtilGUI.getBoxInventory(p, player.getUpgrades(), player.getUpgradePrice()))){
            event.setCancelled(true);
            if(event.getSlot() == 14){
                NBTItem nbt = new NBTItem(event.getCurrentItem());
                int upgrades = Integer.parseInt(nbt.getString("Upgrades"));
                int upgradePrice = Integer.parseInt(nbt.getString("UpgradePrice"));

                if((upgrades % 5) == 0){
                    if(player.getPlayerBlocks().size() == UtilBlocks.getAllBlocks().size()){
                        p.sendMessage(mm.deserialize("<red>You've already maxed out your mine... for now."));
                        p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 0);
                    }else{
                        player.getPlayerBlocks().add(UtilBlocks.getAllBlocks().get(player.getPlayerBlocks().size() + 1));
                        p.sendMessage(mm.deserialize("<light_green>Successfully upgraded your mine!"));
                        p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0);
                    }
                }

                if(UtilPlayerData.getBalance(p.getUniqueId()) >= upgradePrice){
                    nbt.setString("Upgrades", String.valueOf(upgrades + 1));
                    nbt.setString("UpgradePrice", String.valueOf(upgradePrice * 2));

                    UtilPlayerData.getPlayerFromUUID(p.getUniqueId()).setUpgrades(upgrades + 1);
                    UtilPlayerData.getPlayerFromUUID(p.getUniqueId()).setUpgradePrice(upgradePrice * 2);

                    event.getInventory().setItem(event.getSlot(), NBTItem.convertNBTtoItem(nbt));

                    p.sendMessage(mm.deserialize("<light_green>You have bought an upgrade for you mine!"));
                    p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 0);
                }else{
                    p.sendMessage(mm.deserialize("<red>You do not have enough balance!"));
                    p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 0);
                }
            }

            if(UtilGUI.getMineSlots().contains(event.getSlot())) {
                if (!(event.getCurrentItem().isSimilar(new PlaceholderItem()))) {
                    NBTItem nbt = new NBTItem(event.getCurrentItem());
                    UtilPlayerData.setBalance(p.getUniqueId(), (UtilPlayerData.getBalance(p.getUniqueId()) + Integer.parseInt(nbt.getString("Price"))));
                    event.getInventory().setItem(event.getSlot(), new PlaceholderItem());
                    Bukkit.getScheduler().runTaskLater(HavingFun.getInstance(), () -> event.getInventory().setItem(event.getSlot(), UtilBlocks.getRandomBlock(p.getUniqueId())), 60);
                }
            }
        }else if(event.getInventory().equals(UtilGUI.getBlocksInventory(p))){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent event){

        event.renderer(new ChatRenderer() {
            @Override
            public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {
                return UtilMessages.getChatFormat(event.getPlayer(), event.message());
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(UtilPlayerData.getPlayerFromUUID(event.getPlayer().getUniqueId()) != null){
            event.joinMessage(UtilMessages.getJoinMessage(event.getPlayer()));
        }else{
            new PlayerData(event.getPlayer().getUniqueId());
            event.joinMessage(UtilMessages.getNewJoinMessage(event.getPlayer()));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        event.quitMessage(UtilMessages.getLeaveMessage(event.getPlayer()));
    }

}
