package me.shingaspt.plugins.havingfun.util;

import com.google.gson.Gson;
import me.shingaspt.plugins.havingfun.data.PlayerData;
import me.shingaspt.plugins.havingfun.data.PlayerDataType;
import me.shingaspt.plugins.havingfun.HavingFun;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;

public class UtilPlayerData {

    private static Map<UUID, PlayerData> players = new HashMap<>();

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
        writer.flush();
        writer.close();
        Bukkit.getLogger().info("PlayerData has been successfully saved!");
    }

    public static void loadPlayerData() throws IOException {
        File file = new File(HavingFun.getInstance().getDataFolder().getAbsolutePath() + "/playerData.json");
        if(file.exists()){
            Gson gson = new Gson();
            Reader reader = new FileReader(file);
            players = gson.fromJson(reader, new PlayerDataType().getType());
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
