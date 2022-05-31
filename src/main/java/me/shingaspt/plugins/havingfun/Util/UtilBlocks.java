package me.shingaspt.plugins.havingfun.Util;

import me.shingaspt.plugins.havingfun.Blocks.Cobblestone;
import me.shingaspt.plugins.havingfun.Blocks.Stone;
import me.shingaspt.plugins.havingfun.Data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class UtilBlocks {

    private static final ArrayList<ItemStack> blocks = new ArrayList<>();

    public static ItemStack getRandomBlock(UUID uuid){
        PlayerData player = UtilPlayerData.getPlayerFromUUID(uuid);
        if(player.getUpgrades() != 0){
            Random rand = new Random();
            int random = rand.nextInt(player.getUpgrades());
            return UtilBlocks.getAllBlocks().get(random);
        }
        return new Cobblestone();
    }

    public static void loadBlocks(){
        blocks.add(new Cobblestone());
        blocks.add(new Stone());
        Bukkit.getLogger().info("Blocks have been successfully loaded!");
    }

    public static ArrayList<ItemStack> getAllBlocks() { return blocks; }
}
