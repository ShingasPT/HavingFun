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
        for(PlayerData player : UtilPlayerData.getPlayers()){
            if(player.getUUID().equals(uuid)){
                Random rand = new Random();
                int random = rand.nextInt(player.getPlayerBlocks().size());
                return player.getPlayerBlocks().get(random);
            }
        }
        return new Stone();
    }

    public static void loadBlocks(){
        blocks.add(new Stone());
    }

    public static ArrayList<ItemStack> getAllBlocks() { return blocks; }
}
