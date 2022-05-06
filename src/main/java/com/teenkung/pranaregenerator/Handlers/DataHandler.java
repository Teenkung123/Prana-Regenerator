package com.teenkung.pranaregenerator.Handlers;


//This class is for handle PlayerData and save into hashmap reduce memory usage

import com.teenkung.pranaregenerator.utils.PranaPlayerData;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;

public class DataHandler {

    private static final HashMap<Player, PranaPlayerData> Data = new HashMap<>();

    public static PranaPlayerData getPlayerData(Player player) {
        if (Data.containsKey(player)) {
            return Data.get(player);
        } else {
            try {
                PranaPlayerData data = new PranaPlayerData(player.getUniqueId());
                Data.put(player, data);
                return data;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static void removePlayerData(Player player) {
        Data.remove(player);
    }

    public static Boolean isLoaded(Player player) {
        return Data.containsKey(player);
    }

}
