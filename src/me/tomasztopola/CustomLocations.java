package me.tomasztopola;

import me.tomasztopola.commands.GoToCommand;
import me.tomasztopola.commands.HomeCommand;
import me.tomasztopola.commands.SetHomeCommand;
import me.tomasztopola.commands.SetLocationCommand;
import me.tomasztopola.fileManagers.DirCreator;
import me.tomasztopola.tabCompleters.GoToTabCompleter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomLocations extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        DirCreator.mkdir(this.getDataFolder().getPath(), this.getConfig().getString("directories.playerData"));

        new HomeCommand(this);
        new SetHomeCommand(this);
        new SetLocationCommand(this);
        new GoToCommand(this);

        new GoToTabCompleter(this);

        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + this.getConfig().getString("console.pluginEnabled"));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.AQUA + this.getConfig().getString("console.pluginDisabled"));
    }
}
