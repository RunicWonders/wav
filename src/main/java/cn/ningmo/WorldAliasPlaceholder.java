package cn.ningmo;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.ChatColor;

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
    public String onRequest(OfflinePlayer player, String identifier) {
        String worldAlias = plugin.getConfig().getString("world_alias_variables." + identifier);
        
        if(worldAlias == null) {
            return "";
        }
        
        return ChatColor.translateAlternateColorCodes('&', worldAlias);
    }
} 