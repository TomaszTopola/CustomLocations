package me.tomasztopola.commands;

import me.tomasztopola.CustomLocations;
import me.tomasztopola.fileManagers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class GoToCommand implements CommandExecutor {

    private final CustomLocations plugin;

    public GoToCommand(CustomLocations plugin){
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("goto")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(
                    Objects.requireNonNull(plugin.getConfig().getString("warnings.access.forPlayersOnly")));
            return true;
        }

        Player player = (Player) sender;
        if(args.length != 2){
            sender.sendMessage(
                    ChatColor.DARK_RED + plugin.getConfig().getString("warnings.access.invalidArguments"));
            return true;
        }

        ConfigManager config;
        if(args[0].toLowerCase().contentEquals("global")){
            config = new ConfigManager(plugin, plugin.getDataFolder().getPath(), "globalLocations.yml");
            player.teleport(Objects.requireNonNull(config.getConfig().getLocation(args[1])));
        }else if(args[0].toLowerCase().contentEquals("private")){
            config = new ConfigManager(plugin, player);
            player.teleport(Objects.requireNonNull(config.getConfig().getLocation("customLocations." + args[1])));
        }

        player.sendMessage(ChatColor.DARK_AQUA + "You were teleported to "+ ChatColor.DARK_PURPLE + args[1]);

        return true;
    }
}
