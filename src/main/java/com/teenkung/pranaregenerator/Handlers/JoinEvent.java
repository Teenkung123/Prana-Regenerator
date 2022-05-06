package com.teenkung.pranaregenerator.Handlers;

import com.downyoutube.devcurrency.devcurrency.PlayerDataMySQL.API.Currency;
import com.teenkung.pranaregenerator.utils.ConfigLoader;
import com.teenkung.pranaregenerator.utils.PranaPlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Instant;

import static com.teenkung.pranaregenerator.PranaRegenerator.colorize;

//This class is for handling join event and offline calculations

public class JoinEvent implements Listener {

    //This method handle join event of player and register it to DataHandler
    //and check if Diffrent Between Logout time and now is greater than Regenerate Rate if yes
    //it will calculate amount of prana player can get
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PranaPlayerData data = DataHandler.getPlayerData(player);
        if (data == null) {
            player.kickPlayer(colorize("&cCould not load player data!"));
            return;
        }
        data.setLoginTime(Instant.now().getEpochSecond());
        if (data.getDiffrentLogoutTime() >= ConfigLoader.getRegenerationRate()) {
            calculateOfflineCalculations(data);
        }
    }

    //This method is for calculate Prana during offline calculation
    private void calculateOfflineCalculations(PranaPlayerData data) {
        Currency currencyData = new Currency(data.getUUID());
        double amount = Math.floor(Long.valueOf(data.getDiffrentLogoutTime() / ConfigLoader.getRegenerationRate()).doubleValue());
        data.setTimeLeft(data.getDiffrentLogoutTime() % ConfigLoader.getRegenerationRate() + data.getTimeLeft());
        if (currencyData.getBalance(ConfigLoader.getCurrencyID()) + amount > currencyData.getMaxBalance(ConfigLoader.getCurrencyID())) {
            amount = currencyData.getMaxBalance(ConfigLoader.getCurrencyID()) + currencyData.getBalance(ConfigLoader.getCurrencyID());
        }
        currencyData.giveBalance(ConfigLoader.getCurrencyID(), amount , "Console", "Prana-OfflineCalc");
    }

}
