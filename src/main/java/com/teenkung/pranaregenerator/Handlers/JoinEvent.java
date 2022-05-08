package com.teenkung.pranaregenerator.Handlers;

import com.downyoutube.devcurrency.devcurrency.PlayerDataMySQL.API.Currency;
import com.teenkung.pranaregenerator.utils.ConfigLoader;
import com.teenkung.pranaregenerator.utils.PranaPlayerData;
import org.bukkit.Bukkit;
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
        data.setDummyTime(Instant.now().getEpochSecond());
        if (data.getDiffrentLogoutTime() >= ConfigLoader.getRegenerationRate()) {
            calculateOfflineCalculations(data);
        }
    }

    //This method is for calculate Prana during offline calculation
    private void calculateOfflineCalculations(PranaPlayerData data) {
        Currency currencyData = new Currency(data.getUUID());
        if (currencyData.getBalance(ConfigLoader.getCurrencyID()) < currencyData.getMaxBalance(ConfigLoader.getCurrencyID())) {
            double amount = Math.floor(Long.valueOf(data.getDiffrentLogoutTime() / ConfigLoader.getRegenerationRate()).doubleValue());
            amount = amount * ConfigLoader.getGiveAmount();
            data.setTimeLeft(data.getDiffrentLogoutTime() % ConfigLoader.getRegenerationRate() + data.getTimeLeft());
            if (currencyData.getBalance(ConfigLoader.getCurrencyID()) + amount > currencyData.getMaxBalance(ConfigLoader.getCurrencyID())) {
                amount = currencyData.getMaxBalance(ConfigLoader.getCurrencyID()) - currencyData.getBalance(ConfigLoader.getCurrencyID());
            }
            currencyData.giveBalance(ConfigLoader.getCurrencyID(), amount, "Console", "Prana-OfflineCalc");
            for (String cmd : ConfigLoader.getCommandOnOffline()) {
                cmd = cmd.replaceAll("<player>", data.getPlayer().getName());
                cmd = cmd.replaceAll("<amount>", String.valueOf(Double.valueOf(amount).intValue()));
                cmd = cmd.replaceAll("<max>", String.valueOf((int) Math.round(currencyData.getMaxBalance(ConfigLoader.getCurrencyID()))));
                cmd = cmd.replaceAll("<current>", String.valueOf((int) Math.round(currencyData.getBalance(ConfigLoader.getCurrencyID()))));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), colorize(cmd));
            }
        }
    }

}
