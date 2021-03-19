package me.tomasztopola.commands;

import me.tomasztopola.CustomLocations;
import me.tomasztopola.fileManagers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SetLocationCommand implements CommandExecutor {

    CustomLocations plugin;

    public SetLocationCommand(CustomLocations plugin){
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("setlocation")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(
                    Objects.requireNonNull(plugin.getConfig().getString("warnings.access.forPlayersOnly")));
            return true;
        }

        Player player = (Player) sender;
        String name = null;
        String level = null;

        Location location;
        if(args.length == 2)
            location = player.getLocation();
        if(args.length == 3 && args[2].toLowerCase().contentEquals("delete"))
            location = null;
        else if(args.length == 5){
            location = new Location(
                    player.getWorld(),
                    Double.parseDouble(args[2]),
                    Double.parseDouble(args[3]),
                    Double.parseDouble(args[4])
            );
        }else {
            sender.sendMessage(
                    ChatColor.DARK_RED + plugin.getConfig().getString("warnings.access.invalidArguments"));
            return  true;
        }

        level = args[0];
        name = args[1];

        if(level == null || name == null) return true;
        ConfigManager config;
        if(level.toLowerCase().contentEquals("global") && player.hasPermission("setLocation.global")){
            config = new ConfigManager(plugin, plugin.getDataFolder().getPath(), "globalLocations.yml");
            config.getConfig().set(name, location);
        }else if(level.toLowerCase().contentEquals("private")){
            config = new ConfigManager(plugin, player);
            config.getConfig().set("customLocations." + name, location);
        }else{
            return true;
        }

        config.saveConfig();
        if(location == null) {
            player.sendMessage(ChatColor.DARK_RED + name + " deleted.");
            return true;
        }
        player.sendMessage(
                ChatColor.DARK_PURPLE + name
                    + ChatColor.DARK_AQUA + " was saved as " + ChatColor.DARK_PURPLE +
                    location.getBlockX() + " " +
                    location.getBlockY() + " " +
                    location.getBlockY()
        );
        return true;
    }
}
