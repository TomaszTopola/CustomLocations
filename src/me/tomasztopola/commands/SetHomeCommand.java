package me.tomasztopola.commands;

import me.tomasztopola.CustomLocations;
import me.tomasztopola.fileManagers.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    private final CustomLocations plugin;

    public SetHomeCommand(CustomLocations plugin){
        this.plugin = plugin;
        this.plugin.getCommand("sethome").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(plugin.getConfig().getString("warnings.access.forPlayersOnly"));
            return true;
        }

        Player player = (Player) sender;
        ConfigManager playerConfig = new ConfigManager(plugin, player);
        Location home = player.getLocation();

        if(args.length == 3){
            home.setX(Double.parseDouble(args[0]));
            home.setY(Double.parseDouble(args[1]));
            home.setZ(Double.parseDouble(args[2]));
        }

        playerConfig.getConfig().set("home", home);
        playerConfig.saveConfig();

        player.sendMessage(ChatColor.DARK_AQUA + "Home was set to " +  ChatColor.DARK_PURPLE
                + home.getBlockX() + " "
                + home.getBlockY() + " "
                + home.getBlockZ()
        );

        return true;
    }
}
