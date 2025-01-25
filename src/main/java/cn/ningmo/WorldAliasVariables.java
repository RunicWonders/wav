package cn.ningmo;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class WorldAliasVariables extends JavaPlugin {
    
    private static WorldAliasVariables instance;
    private FileConfiguration config;
    private FileConfiguration messages;
    
    @Override
    public void onEnable() {
        instance = this;
        loadConfigs();
        
        // 注册命令
        getCommand("wav").setExecutor(new WorldAliasCommand(this));
        
        // 注册 PlaceholderAPI 扩展
        if(getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new WorldAliasPlaceholder(this).register();
        } else {
            getLogger().warning(getMessage("plugin.papi-not-found"));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        getLogger().info(getMessage("plugin.enable"));
    }
    
    @Override
    public void onDisable() {
        getLogger().info(getMessage("plugin.disable"));
    }
    
    private void loadConfigs() {
        // 加载主配置
        saveDefaultConfig();
        config = getConfig();
        
        // 加载消息配置
        File messagesFile = new File(getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }
    
    public void reloadPlugin(CommandSender sender) {
        loadConfigs();
        sender.sendMessage(getMessage("plugin.reload"));
    }
    
    public String getMessage(String path) {
        String message = messages.getString(path);
        if (message == null) return "消息未找到: " + path;
        
        message = message.replace("{prefix}", messages.getString("prefix", ""));
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public String getMessage(String path, String... placeholders) {
        String message = getMessage(path);
        for (int i = 0; i < placeholders.length; i += 2) {
            if (i + 1 < placeholders.length) {
                message = message.replace(placeholders[i], placeholders[i + 1]);
            }
        }
        return message;
    }
    
    public static WorldAliasVariables getInstance() {
        return instance;
    }
} 