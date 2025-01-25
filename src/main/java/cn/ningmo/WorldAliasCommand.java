package cn.ningmo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.ChatColor;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class WorldAliasCommand implements CommandExecutor, TabCompleter {

    private final WorldAliasVariables plugin;
    
    public WorldAliasCommand(WorldAliasVariables plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("wav.admin")) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }
        
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadPlugin(sender);
                break;
            case "set":
                if (args.length < 3) {
                    sender.sendMessage(plugin.getMessage("command.set.usage"));
                    return true;
                }
                String worldName = args[1];
                StringBuilder alias = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    alias.append(args[i]).append(" ");
                }
                setWorldAlias(sender, worldName, alias.toString().trim());
                break;
            case "list":
                listWorldAliases(sender);
                break;
            default:
                sendHelp(sender);
                break;
        }
        return true;
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(plugin.getMessage("command.help.title"));
        sender.sendMessage(plugin.getMessage("command.help.reload"));
        sender.sendMessage(plugin.getMessage("command.help.set"));
        sender.sendMessage(plugin.getMessage("command.help.list"));
    }
    
    private void setWorldAlias(CommandSender sender, String worldName, String alias) {
        plugin.getConfig().set("world_alias_variables." + worldName, alias);
        plugin.saveConfig();
        sender.sendMessage(plugin.getMessage("command.set.success", 
            "{world}", worldName,
            "{alias}", ChatColor.translateAlternateColorCodes('&', alias)));
    }
    
    private void listWorldAliases(CommandSender sender) {
        sender.sendMessage(plugin.getMessage("command.list.title"));
        for (String world : plugin.getConfig().getConfigurationSection("world_alias_variables").getKeys(false)) {
            String alias = plugin.getConfig().getString("world_alias_variables." + world);
            sender.sendMessage(plugin.getMessage("command.list.format",
                "{world}", world,
                "{alias}", ChatColor.translateAlternateColorCodes('&', alias)));
        }
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            completions.addAll(Arrays.asList("reload", "set", "list"));
        } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            plugin.getServer().getWorlds().forEach(world -> completions.add(world.getName()));
        }
        
        return completions;
    }
} 