package me.tomasztopola.commands;

import me.tomasztopola.CustomLocations;
import me.tomasztopola.fileManagers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HomeCommand implements CommandExecutor {

    private final CustomLocations plugin;

    public HomeCommand(CustomLocations plugin){
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("home")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(
                    Objects.requireNonNull(plugin.getConfig().getString("warnings.access.forPlayersOnly")));
            return true;
        }

        Player player = (Player) sender;

        ConfigManager playerConfig =
                (args.length > 0)
                        ? new ConfigManager(plugin, Objects.requireNonNull(Bukkit.getPlayer(args[0])))
                        : new ConfigManager(plugin, player);

        if(!(playerConfig.getConfig().contains("home"))){
            player.sendMessage(
                    Objects.requireNonNull(plugin.getConfig().getString("warnings.commands.homeNotSet")));
            return true;
        }
        Location target = playerConfig.getConfig().getLocation("home");

        assert target != null;
        player.teleport(target);
        player.sendMessage(ChatColor.DARK_AQUA + "You were teleported home");
        return false;
    }
}
