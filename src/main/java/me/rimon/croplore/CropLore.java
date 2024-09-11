package me.rimon.croplore;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CropLore extends JavaPlugin {

    private static CropLore instance;
    private List<Material> crops;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        loadCropsFromConfig();

        getServer().getPluginManager().registerEvents(new list(), this);
    }

    @Override
    public void onDisable() {
    }

    public static CropLore getInstance() {
        return instance;
    }

    public List<Material> getCrops() {
        return crops;
    }

    private void loadCropsFromConfig() {
        FileConfiguration config = getConfig();
        crops = new ArrayList<>();

        for (String cropName : config.getStringList("crops")) {
            try {
                Material crop = Material.valueOf(cropName.toUpperCase());
                crops.add(crop);
            } catch (IllegalArgumentException e) {
                getLogger().warning("Invalid crop material in config: " + cropName);
            }
        }
    }
}
