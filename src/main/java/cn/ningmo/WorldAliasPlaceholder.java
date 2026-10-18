package cn.ningmo;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldAliasPlaceholder extends PlaceholderExpansion {

    private final WorldAliasVariables plugin;

    public WorldAliasPlaceholder(WorldAliasVariables plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "wav";
    }

    @Override
    public @NotNull String getAuthor() {
        return "柠枺";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.2";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        String worldName;
        // 检查是否是直接请求特定世界的别名
        if (!identifier.isEmpty()) {
            // 已经移除了前缀 "wav_" 直接就是世界名
            worldName = identifier;
        } else {
            // 获取玩家当前世界名
            if (player == null) {
                return ""; // 如果玩家不在线且请求的不是特定世界名，返回空
            }
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