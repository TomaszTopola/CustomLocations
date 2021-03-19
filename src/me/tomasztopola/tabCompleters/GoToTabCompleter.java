package me.tomasztopola.tabCompleters;

import me.tomasztopola.CustomLocations;
import me.tomasztopola.fileManagers.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class GoToTabCompleter implements TabCompleter {

    private final CustomLocations plugin;

    public GoToTabCompleter(CustomLocations plugin){
        this.plugin = plugin;
        Objects.requireNonNull(this.plugin.getCommand("goto")).setTabCompleter(this);
        Objects.requireNonNull(this.plugin.getCommand("setlocation")).setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player))
            return null;

        if((args.length > 2 && cmd.getName().contentEquals("goto") )|| args.length >6)
            return null;

        Player player = (Player) sender;
        List<String> completer = new ArrayList<>();

        if(args.length==1){
            completer.add("private");
            completer.add("global");
        }else if(args.length == 2) {
            ConfigurationSection locations;
            if (args[0].equals("private")) {
                ConfigManager config = new ConfigManager(plugin, player);
                locations = config.getConfig().getConfigurationSection("customLocations");
            } else if (args[0].equals("global")) {
                ConfigManager config = new ConfigManager(plugin, plugin.getDataFolder().getPath(), "globalLocations.yml");
                locations = config.getConfig().getConfigurationSection("");
            } else return null;
            if(locations == null) return null;
            Set<String> setOfLocations = locations.getKeys(true);
            completer.addAll(setOfLocations);
        }else if(args.length > 2 && cmd.getName().contentEquals("goto"))
            return null;

        return MatchResults.match(args, completer, args.length-1);
    }

}
