package me.shingaspt.plugins.havingfun;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.Items.CashBoost;
import me.shingaspt.plugins.havingfun.Items.FortuneBook;
import me.shingaspt.plugins.havingfun.Items.PlaceholderItem;
import me.shingaspt.plugins.havingfun.Items.UpgradeChest;
import me.shingaspt.plugins.havingfun.Util.UtilBlocks;
import me.shingaspt.plugins.havingfun.Util.UtilGUI;
import me.shingaspt.plugins.havingfun.Util.UtilMessages;
import me.shingaspt.plugins.havingfun.Util.UtilPlayerData;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class EventsListener implements Listener {

    private final MiniMessage mm = MiniMessage.miniMessage();
    private static final HashMap<UUID, Integer> boosts = new HashMap<>();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){

        Player p = (Player) event.getWhoClicked();

        if(event.getView().title().equals(UtilGUI.getBoxTitle())){
            event.setCancelled(true);
            if (event.getSlot() == 8) {
                PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());
                int upgrades = player.getUpgrades();
                int upgradePrice = player.getUpgradePrice();

                if ((upgrades + 1) == UtilBlocks.getAllBlocks().size()) {
                    p.sendMessage(mm.deserialize("<red>You already maxed out your mine... for now!"));
                    p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 1);
                } else {
                    if (player.getBalance() >= upgradePrice) {
                        p.sendMessage(mm.deserialize("<green>Successfully upgraded your mine! A new block has been added to your mine!"));
                        player.setBalance(player.getBalance() - upgradePrice);
                        player.setUpgrades(player.getUpgrades() + 1);
                        player.setUpgradePrice(player.getUpgradePrice() * 2);
                        p.playSound(p, Sound.ENTITY_PLAYER_LEVELUP, 1, 0);
                        event.getInventory().setItem(8, new UpgradeChest(player.getUpgrades(), player.getUpgradePrice()));
                        event.getInventory().setItem(0, UtilGUI.getPlayerSkull(p));
                    } else {
                        p.sendMessage(mm.deserialize("<red>Insufficient balance to do this purchase!"));
                        p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                }

            }else if (event.getSlot() == 45){
                event.setCancelled(true);

                PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());

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
                    p.playSound(p, Sound.ENTITY_VILLAGER_NO, 1, 1);
                }

            }else if(UtilBlocks.getMineSlots().contains(event.getSlot())){
                if(!(Objects.requireNonNull(event.getCurrentItem()).isSimilar(new PlaceholderItem()))){
                    PlayerData player = UtilPlayerData.getPlayerFromUUID(p.getUniqueId());

                    PersistentDataContainer container = event.getCurrentItem().getItemMeta().getPersistentDataContainer();
                    NamespacedKey key = new NamespacedKey(HavingFun.getInstance(), "Reward");

                    int reward = container.getOrDefault(key, PersistentDataType.INTEGER, 0);
                    int boost = boosts.get(p.getUniqueId()) != null ? boosts.get(p.getUniqueId()) : 0;

                    player.setBalance(player.getBalance() + reward + player.getFortune() + boost);
                    player.setMined(player.getMined() + 1);

                    Random rand = new Random();

                    int chance = rand.nextInt(10000) + 1;
                    if(Arrays.asList(1,2,3,4,5).contains(chance)){
                        int tempBoost = rand.nextInt(5) + 1;
                        int tempTime = rand.nextInt(15) + 1;
                        p.getInventory().addItem(new CashBoost(tempBoost, tempTime));
                        p.sendMessage(mm.deserialize("<green>You obtained a cash boost!"));
                        p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                    }

                    event.getInventory().setItem(0, UtilGUI.getPlayerSkull(p));
                    event.getInventory().setItem(event.getSlot(), new PlaceholderItem());
                    Bukkit.getScheduler().runTaskLater(HavingFun.getInstance(), () -> event.getInventory().setItem(event.getSlot(), UtilBlocks.getRandomBlock(p.getUniqueId())), 120);
                }
            }
        }else if(event.getView().title().equals(UtilGUI.getBlocksTitle())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {

        PlayerData player = UtilPlayerData.getPlayerFromUUID(event.getPlayer().getUniqueId());
        Date date = (player.getMute() == null) ? new Date() : player.getMute();

        if(new Date().compareTo(date) >= 0){
            event.renderer((source, sourceDisplayName, message, viewer) -> UtilMessages.getChatFormat(event.getPlayer(), event.message()));
        }else {
            event.setCancelled(true);
            event.getPlayer().sendMessage(mm.deserialize("<gray>You are currently <red>muted<gray>!"));
        }

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        if(UtilPlayerData.getPlayerFromUUID(event.getPlayer().getUniqueId()) != null){
            event.joinMessage(UtilMessages.getJoinMessage(event.getPlayer()));
        }else{
            UtilPlayerData.createNewPlayer(event.getPlayer());
            event.getPlayer().sendMessage(UtilMessages.getStarterMessage());
            event.joinMessage(UtilMessages.getNewJoinMessage(event.getPlayer()));
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        event.quitMessage(UtilMessages.getLeaveMessage(event.getPlayer()));
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        Player p = event.getPlayer();
        ItemStack item = event.getItem();
        if(item != null){
            PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
            NamespacedKey boost = new NamespacedKey(HavingFun.getInstance(), "boost");
            NamespacedKey time = new NamespacedKey(HavingFun.getInstance(), "time");
            if(container.has(boost)){
                if(boosts.containsKey(p.getUniqueId())){
                    p.sendMessage(mm.deserialize("<red>You have an already going boost! Wait until it ends."));
                }else{
                    p.getInventory().remove(item);
                    boosts.put(p.getUniqueId(), container.get(boost, PersistentDataType.INTEGER));
                    p.sendMessage(mm.deserialize("<green>Your cash boost has been activated!"));
                    Bukkit.getScheduler().runTaskLater(HavingFun.getInstance(), () -> {
                        boosts.remove(p.getUniqueId());
                        p.sendMessage(mm.deserialize("<red>Your cash boost has finished!"));
                    }, ((container.getOrDefault(time, PersistentDataType.INTEGER, 1)) * 60) * 20);
                }
            }
        }
    }
}
