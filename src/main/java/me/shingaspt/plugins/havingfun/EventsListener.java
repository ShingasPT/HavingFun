package me.shingaspt.plugins.havingfun;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.Items.FortuneBook;
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
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class EventsListener implements Listener {

    private final MiniMessage mm = MiniMessage.miniMessage();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        Player p = (Player) event.getWhoClicked();

        if(event.getView().title().equals(UtilGUI.getBoxTitle())){
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
                if(UtilBlocks.getAllBlocks().contains(event.getCurrentItem())){
                    PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());

                    PersistentDataContainer container = event.getCurrentItem().getItemMeta().getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(HavingFun.getInstance(), "Reward");

                    int reward = container.get(key, PersistentDataType.INTEGER);

                    player.setBalance(player.getBalance() + reward + player.getFortune());
                    player.setMined(player.getMined() + 1);

                    event.getInventory().setItem(0, UtilGUI.getPlayerSkull(p));
                    event.getInventory().setItem(event.getSlot(), new PlaceholderItem());
                    Bukkit.getScheduler().runTaskLater(HavingFun.getInstance(), () -> event.getInventory().setItem(event.getSlot(), UtilBlocks.getRandomBlock(p.getUniqueId())), 120);
                }
            }
        }else if(event.getView().title().equals(UtilGUI.getBlocksTitle())){
            event.setCancelled(true);
        }else if(event.getView().title().equals(UtilGUI.getEnchantTitle())){
            event.setCancelled(true);

            if(event.getSlot() == 2){

                PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());

                int fortune = player.getFortune();
                int fortunePrice = player.getFortunePrice();

                if(player.getBalance() >= fortunePrice){
                    p.sendMessage(mm.deserialize("<green>Successfully bought another fortune level!"));

                    player.setBalance(player.getBalance() - fortunePrice);
                    player.setFortune(player.getFortune() + 1);
                    player.setFortunePrice(player.getFortunePrice() * 2);
                    p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 1, 0);

                    event.getInventory().setItem(event.getSlot(), new FortuneBook(player.getFortune(), player.getFortunePrice()));
                }else{
                    p.sendMessage(mm.deserialize("<red>Insufficient balance to do this purchase!"));
                    p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 0);
                }
            }
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
            event.getPlayer().sendMessage(mm.deserialize("<gradient:#D400FF:#B40092>┌───────────────────────────────────────────┐</gradient><newline>" +
                                                         "<newline>" +
                                                         "  <gray>Start Your Journey With <gradient:#9F15FF:#E304FF>/box</gradient><newline>" +
                                                         "  <gray>Check Out All The Blocks You've Achieved With <gradient:#9F15FF:#E304FF>/blocks</gradient><newline>" +
                                                         "  <gray>Enchant Your Mine With <gradient:#9F15FF:#E304FF>/fortune</gradient><newline>" +
                                                         "<newline>" +
                                                         "<gradient:#D400FF:#B40092>└───────────────────────────────────────────┘</gradient>"));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        event.quitMessage(UtilMessages.getLeaveMessage(event.getPlayer()));
    }

}
