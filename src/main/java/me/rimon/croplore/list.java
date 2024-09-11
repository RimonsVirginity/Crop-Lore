package me.rimon.croplore;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class list implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        // Ensure meta is not null
        if (meta == null) return;

        // Check if the item is a hoe
        if (!item.getType().name().endsWith("_HOE")) return;

        // Check if the broken block is a crop
        List<Material> crops = CropLore.getInstance().getCrops();
        Material blockType = block.getType();

        if (!crops.contains(blockType)) return;

        // Calculate the total number of crops broken
        int totalCropsBroken = 0;
        for (Material crop : crops) {
            totalCropsBroken += player.getStatistic(Statistic.MINE_BLOCK, crop);
        }

        // Get or create the lore list
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<>();

        // Update or add the "Crops Broken:" line
        String cropsBrokenLine = "Crops Broken: " + totalCropsBroken;
        boolean found = false;
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).startsWith("Crops Broken:")) {
                lore.set(i, cropsBrokenLine);
                found = true;
                break;
            }
        }
        if (!found) {
            lore.add(cropsBrokenLine);
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}
