package me.shingaspt.plugins.havingfun;

import de.tr7zw.nbtapi.NBTItem;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.Items.PlaceholderItem;
import me.shingaspt.plugins.havingfun.Items.UpgradeChest;
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

        if(event.getView().title().equals(UtilGUI.getBoxTitle(p))){
            event.setCancelled(true);
            if (event.getSlot() == 8) {
                PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
                int upgrades = player.getUpgrades();
                int upgradePrice = player.getUpgradePrice();

                if((upgrades + 1) == UtilBlocks.getAllBlocks().size()){
                    p.sendMessage(mm.deserialize("<red>You already maxed out your mine... for now!"));
                    p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 0);
                }else{
                    if(player.getBalance() >= upgradePrice){
                        p.sendMessage(mm.deserialize("<green>Successfully upgraded your mine! A new block has been added to your mine!"));
                        player.setBalance(player.getBalance() - upgradePrice);
                        player.setUpgrades(player.getUpgrades() + 1);
                        player.setUpgradePrice(player.getUpgradePrice() * 2);
                        p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
                        event.getInventory().setItem(8, new UpgradeChest(player.getUpgrades(), player.getUpgradePrice()));
                        event.getInventory().setItem(0, UtilGUI.getPlayerSkull(p));
                    }else{
                        p.sendMessage(mm.deserialize("<red>Insufficient balance to do this purchase!"));
                        p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 0);
                    }
                }

            }else if(UtilGUI.getMineSlots().contains(event.getSlot())){
                if(!(event.getCurrentItem().isSimilar(new PlaceholderItem()))){
                    PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
                    int reward = Integer.parseInt(new NBTItem(event.getCurrentItem()).getString("Reward"));
                    player.setBalance(player.getBalance() + reward);
                    event.getInventory().setItem(12, UtilGUI.getPlayerSkull(p));
                    event.getInventory().setItem(event.getSlot(), new PlaceholderItem());
                    Bukkit.getScheduler().runTaskLater(HavingFun.getInstance(), () -> event.getInventory().setItem(event.getSlot(), UtilBlocks.getRandomBlock(p.getUniqueId())), 120);
                }
            }
        }else if(event.getView().title().equals(UtilGUI.getBlocksTitle(p))){
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
            UtilPlayerData.createNewPlayer(event.getPlayer());
            event.joinMessage(UtilMessages.getNewJoinMessage(event.getPlayer()));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        event.quitMessage(UtilMessages.getLeaveMessage(event.getPlayer()));
    }

}
