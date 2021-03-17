package me.tomasztopola.fileManagers;

import me.tomasztopola.CustomLocations;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Class responsible for managing yaml files as Bukkit FileConfiguration.
 */
public class ConfigManager {

    private FileConfiguration configData;
    private File configFile;
    private final CustomLocations plugin;
    private final String filePath;
    private final String fileName;

    /**
     * Constructor for player files.
     * @param plugin - CustomLocations address
     * @param player - Information about player whose files you're accessing
     */
    public ConfigManager(CustomLocations plugin, Player player){
        this.plugin = plugin;
        filePath = plugin.getDataFolder().getPath() + plugin.getConfig().getString("directories.playerData");
        fileName = player.getDisplayName() + "_player_data.yml";
        saveDefaultConfig();
    }
    /**
     * Constructor for any other config files
     * @param plugin  - CustomLocations address
     * @param filePath - path to file
     * @param fileName - name of file
     */
    public ConfigManager(CustomLocations plugin, String filePath, String fileName){
        this.plugin = plugin;
        this.filePath = filePath;
        this.fileName = fileName;
        saveDefaultConfig();
    }

    /**
     * Makes sure that config is loaded from disk.
     */
    public void reloadConfig(){
        if(configFile == null)
            configFile = new File(filePath, fileName);

        configData = YamlConfiguration.loadConfiguration(configFile);
        InputStream defaultStream = plugin.getResource(filePath+fileName);
        if(defaultStream == null) return;
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
        configData.setDefaults(defaultConfig);
    }

    /**
     * Returns config.
     * @return FileConfiguration
     */
    public FileConfiguration getConfig(){
        if(configData == null)
            reloadConfig();
        return configData;
    }

    /**
     * Saves content in configData and configFile
     */
    public void saveConfig(){
        if(configFile == null || configData == null) return;
        try{
            getConfig().save(configFile);
        }catch(Exception e){
            Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
        }
    }

    /**
     * Checks if file exists and if not creates one.
     */
    private void saveDefaultConfig(){
        if(configFile == null)
           configFile = new File(filePath, fileName);

        try{
            configFile.createNewFile();
            configData = YamlConfiguration.loadConfiguration(configFile);
        }catch(Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(e.getMessage());
        }
    }
}
