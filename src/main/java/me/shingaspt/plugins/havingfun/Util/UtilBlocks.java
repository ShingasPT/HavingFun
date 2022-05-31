package me.shingaspt.plugins.havingfun.Util;

import me.shingaspt.plugins.havingfun.Blocks.Stone;
import me.shingaspt.plugins.havingfun.Data.PlayerData;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class UtilBlocks {

    private static final ArrayList<ItemStack> blocks = new ArrayList<>();

    public static ItemStack getRandomBlock(UUID uuid){
        for(UUID playerUUID : UtilPlayerData.getPlayers().keySet()){
            if(playerUUID.equals(uuid)){
                Random rand = new Random();
                int random = rand.nextInt(UtilPlayerData.getPlayers().get(uuid).getPlayerBlocks().size());
                return UtilPlayerData.getPlayers().get(uuid).getPlayerBlocks().get(random);
            }
        }
        return new Stone();
    }

    public static void loadBlocks(){
        blocks.add(new Stone());
    }

    public static ArrayList<ItemStack> getAllBlocks() { return blocks; }
}
