package com.teenkung.pranaregenerator.utils;

import com.teenkung.pranaregenerator.Handlers.DataHandler;
import com.teenkung.pranaregenerator.PranaRegenerator;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlaceholderAPIRegister extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "Teenkung123";
    }

    @Override
    public String getIdentifier() {
        return "PranaRegen";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("getRegenerationRate")) {
            return String.valueOf(ConfigLoader.getRegenerationRate());
        } else if (params.equalsIgnoreCase("getGiveAmount")) {
            return String.valueOf(ConfigLoader.getGiveAmount());
        } else if (params.equalsIgnoreCase("getPlayerRegenTime")) {
            if (player.isOnline()) {
                PranaPlayerData data = DataHandler.getPlayerData(Bukkit.getPlayer(player.getUniqueId()));
                if (data == null) {
                    return "Player not online";
                } else {
                    return data.getRegenTime();
                }
            } else {
                return "Player not online";
            }
        } else if (params.equalsIgnoreCase("getPlayerFullRegenTime")) {
            if (player.isOnline()) {
                PranaPlayerData data = DataHandler.getPlayerData(Bukkit.getPlayer(player.getUniqueId()));
                if (data == null) {
                    return "Player not online";
                } else {
                    return data.getFullRegenTime();
                }
            } else {
                return "Player not online";
            }
        }

        return null; // Placeholder is unknown by the Expansion
    }
}
