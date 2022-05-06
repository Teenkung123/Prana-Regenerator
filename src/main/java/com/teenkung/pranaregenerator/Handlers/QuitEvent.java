package com.teenkung.pranaregenerator.Handlers;


//This class is for saving Logout time when player Logout

import com.teenkung.pranaregenerator.utils.PranaPlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (DataHandler.isLoaded(event.getPlayer())) {
            PranaPlayerData data = DataHandler.getPlayerData(event.getPlayer());
            if (data == null) { return; }
            data.setLogoutTime(Instant.now().getEpochSecond());
            DataHandler.removePlayerData(event.getPlayer());
        }
    }

}
