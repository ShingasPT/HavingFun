package me.shingaspt.plugins.havingfun.util;

import me.shingaspt.plugins.havingfun.blocks.*;
import me.shingaspt.plugins.havingfun.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class UtilBlocks {

    private static final ArrayList<ItemStack> blocks = new ArrayList<>();
    private static final ArrayList<Integer> mineSlots = new ArrayList<>(Arrays.asList(11,12,13,14,15,20,21,22,23,24,29,30,31,32,33,38,39,40,41,42));
    public static ItemStack getRandomBlock(UUID uuid){
        PlayerData player = UtilPlayerData.getPlayerFromUUID(uuid);
        if(player.getUpgrades() != 0){
            Random rand = new Random();
            int random = rand.nextInt(player.getUpgrades() + 1);
            return blocks.get(random);
        }
        return new Cobblestone();
    }

    public static void loadBlocks(){
        blocks.add(new Cobblestone());
        blocks.add(new Stone());
        blocks.add(new IronOre());
        blocks.add(new GoldOre());
        blocks.add(new RedstoneOre());
        blocks.add(new LapisOre());
        blocks.add(new DiamondOre());
        blocks.add(new EmeraldOre());
        Bukkit.getLogger().info("Blocks have been successfully loaded!");
    }

    public static ArrayList<ItemStack> getAllBlocks() { return blocks; }

    public static ArrayList<Integer> getMineSlots() { return mineSlots; }
}
