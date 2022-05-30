package me.shingaspt.plugins.havingfun.Util;

import com.google.gson.Gson;
import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.HavingFun;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.*;

public class UtilPlayerData {

    private static ArrayList<PlayerData> players = new ArrayList<>();

    public static int getBalance(UUID uuid){
        for(PlayerData player : players){
            if(player.getUUID().equals(uuid)){
                return player.getBalance();
            }
        }
        return 0;
    }

    public static void setBalance(UUID uuid, int balance){
        for(PlayerData player : players) {
            if (player.getUUID().equals(uuid)) {
                player.setBalance(balance);
            }
        }
    }

    public static int getUpgrades(UUID uuid){
        for(PlayerData player : players) {
            if (player.getUUID().equals(uuid)) {
                return player.getUpgrades();
            }
        }
        return 0;
    }

    public static int getUpgradePrice(UUID uuid){
        for(PlayerData player : players) {
            if (player.getUUID().equals(uuid)) {
                return player.getUpgradePrice();
            }
        }
        return 0;
    }

    public static ArrayList<PlayerData> getPlayers() { return players; }

    public static PlayerData getPlayerFromUUID(UUID uuid){
        for(PlayerData player : players){
            if(player.getUUID().equals(uuid)){
                return player;
            }
        }
        return null;
    }

    public static void savePlayerData() throws IOException {
        Gson gson = new Gson();
        File file = new File(HavingFun.getInstance().getDataFolder().getAbsolutePath() + "/playerData.json");
        Writer writer = new FileWriter(file);
        gson.toJson(file, writer);
        Bukkit.getLogger().info("PlayerData has been successfully saved!");
    }

    public static void loadPlayerData() throws IOException {
        Gson gson = new Gson();
        File file = new File(HavingFun.getInstance().getDataFolder().getAbsolutePath() + "/playerData.json");
        if(file.exists()){
            Reader reader = new FileReader(file);
            PlayerData[] temp = gson.fromJson(reader, PlayerData[].class);
            players = new ArrayList<>(Arrays.asList(temp));
            Bukkit.getLogger().info("PlayerData has been successfully loaded!");
        }else {
            Bukkit.getLogger().info("No PlayerData has been found! When server stops/restarts PlayerData will be automatically saved!");
        }
    }
}
