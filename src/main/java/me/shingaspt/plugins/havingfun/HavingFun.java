package me.shingaspt.plugins.havingfun;

import me.shingaspt.plugins.havingfun.Commands.CMDBlocks;
import me.shingaspt.plugins.havingfun.Commands.CMDMine;
import me.shingaspt.plugins.havingfun.Util.UtilBlocks;
import me.shingaspt.plugins.havingfun.Util.UtilPlayerData;
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
