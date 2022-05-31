package me.shingaspt.plugins.havingfun.Data;

import java.util.UUID;

public class PlayerData {

    private int balance;
    private int mined;
    private int upgrades;
    private int upgradePrice;
    private int fortune;
    private int fortunePrice;
    private final UUID uuid;

    public PlayerData(UUID uuid){
        this.balance = 0;
        this.mined = 0;
        this.upgrades = 0;
        this.upgradePrice = 100;
        this.fortune = 0;
        this.fortunePrice = 500;
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

    public int getMined() {
        return mined;
    }

    public void setMined(int mined) {
        this.mined = mined;
    }

    public int getFortune() {
        return this.fortune;
    }

    public void setFortune(int fortune) {
        this.fortune = fortune;
    }

    public int getFortunePrice() {
        return this.fortunePrice;
    }

    public void setFortunePrice(int fortunePrice) {
        this.fortunePrice = fortunePrice;
    }
}
