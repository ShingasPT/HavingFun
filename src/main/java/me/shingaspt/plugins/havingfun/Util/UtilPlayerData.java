package me.shingaspt.plugins.havingfun.Util;

import com.google.gson.Gson;
import me.shingaspt.plugins.havingfun.Data.PlayerData;
import me.shingaspt.plugins.havingfun.HavingFun;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;

public class UtilPlayerData {

    private static final Map<UUID, PlayerData> players = new HashMap<>();

    public static int getBalance(UUID uuid){
        return players.get(uuid).getBalance();
    }

    public static void setBalance(UUID uuid, int balance){
        players.get(uuid).setBalance(balance);
    }

    public static int getUpgrades(UUID uuid){
        return players.get(uuid).getUpgrades();
    }

    public static int getUpgradePrice(UUID uuid){
        return players.get(uuid).getUpgradePrice();
    }

    public static Map<UUID, PlayerData> getPlayers() { return players; }

    public static PlayerData getPlayerFromUUID(UUID uuid){
        return players.get(uuid);
    }

    public static void savePlayerData() throws IOException {
        Gson gson = new Gson();
        File file = new File(HavingFun.getInstance().getDataFolder().getAbsolutePath() + "/playerData.json");
        file.getParentFile().mkdir();
        file.createNewFile();
        Writer writer = new FileWriter(file, false);
        gson.toJson(players, writer);
        writer.close();
        writer.flush();
        Bukkit.getLogger().info("PlayerData has been successfully saved!");
    }

    public static void loadPlayerData() throws IOException {
        Gson gson = new Gson();
        File file = new File(HavingFun.getInstance().getDataFolder().getAbsolutePath() + "/playerData.json");
        if(file.exists()){
            Reader reader = new FileReader(file);
            PlayerData[] temp = gson.fromJson(reader, PlayerData[].class);
            for(PlayerData player : temp){
                players.put(player.getUUID(), player);
            }
            reader.close();
            Bukkit.getLogger().info("PlayerData has been successfully loaded!");
        }else {
            Bukkit.getLogger().info("No PlayerData has been found! When server stops/restarts PlayerData will be automatically saved!");
        }
    }

    public static void createNewPlayer(Player p) {
        players.put(p.getUniqueId(), new PlayerData(p.getUniqueId()));
    }
}
