package cn.ningmo;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class WorldAliasPlaceholder extends PlaceholderExpansion {

    private final WorldAliasVariables plugin;

    public WorldAliasPlaceholder(WorldAliasVariables plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "wav";
    }

    @Override
    public String getAuthor() {
        return "柠枺";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String identifier) {
        // 如果玩家不在线且请求的不是特定世界名，返回空
        if (!identifier.contains("_") && !(offlinePlayer instanceof Player)) {
            return "";
        }

        String worldName;
        // 检查是否是直接请求特定世界的别名
        if (identifier.contains("_")) {
            // 移除前缀 "world_" 得到世界名
            worldName = identifier;
        } else {
            // 获取玩家当前世界名
            Player player = (Player) offlinePlayer;
            worldName = player.getWorld().getName();
        }

        // 从配置中获取世界别名
        String worldAlias = plugin.getConfig().getString("world_alias_variables." + worldName);
        
        if (worldAlias == null) {
            if (plugin.getConfig().getBoolean("debug", false)) {
                plugin.getLogger().info("未找到世界 " + worldName + " 的别名配置");
            }
            return worldName; // 如果没有配置别名，返回原始世界名
        }
        
        return ChatColor.translateAlternateColorCodes('&', worldAlias);
    }
} 