package me.shingaspt.plugins.havingfun.Data;

import me.shingaspt.plugins.havingfun.Blocks.Stone;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerData {

    private int balance;
    private int upgrades;
    private int upgradePrice;
    private final UUID uuid;

    public PlayerData(UUID uuid){
        this.balance = 0;
        this.upgrades = 0;
        this.upgradePrice = 100;
        this.uuid = uuid;
    }

    public int getUpgrades() {
        return upgrades;
    }

    public void setUpgrades(int upgrades){
        this.upgrades = upgrades;
    }

    public int getUpgradePrice() {
        return upgradePrice;
    }

    public void setUpgradePrice(int upgradePrice) {
        this.upgradePrice = upgradePrice;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public UUID getUUID() { return uuid; }
}
