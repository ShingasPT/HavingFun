package me.shingaspt.plugins.havingfun;

import me.shingaspt.plugins.havingfun.commands.*;
import me.shingaspt.plugins.havingfun.util.UtilBlocks;
import me.shingaspt.plugins.havingfun.util.UtilPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class HavingFun extends JavaPlugin {

    private static HavingFun instance;
    @Override
    public void onEnable() {
        instance = this;
        Objects.requireNonNull(getCommand("mine")).setExecutor(new CMDMine());
        Objects.requireNonNull(getCommand("blocks")).setExecutor(new CMDBlocks());
        Objects.requireNonNull(getCommand("mute")).setExecutor(new CMDMute());
        Objects.requireNonNull(getCommand("unmute")).setExecutor(new CMDUnmute());
        Objects.requireNonNull(getCommand("adminBal")).setExecutor(new CMDAdminBalance());
        Objects.requireNonNull(getCommand("stats")).setExecutor(new CMDStats());
        getServer().getPluginManager().registerEvents(new EventsListener(), this);
        UtilBlocks.loadBlocks();
        try{
            UtilPlayerData.loadPlayerData();
        }catch(IOException err){
            Bukkit.getLogger().warning("Error occurred while loading PlayerData: "+err);
        }
    }

    @Override
    public void onDisable(){
        try{
            UtilPlayerData.savePlayerData();
        }catch(IOException err){
            Bukkit.getLogger().severe("Error occurred while saving PlayerData: "+err);
        }
    }

    public static HavingFun getInstance(){
        return instance;
    }
}
