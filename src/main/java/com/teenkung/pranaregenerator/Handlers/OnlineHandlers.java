package com.teenkung.pranaregenerator.Handlers;


//This class is for online calculation

import com.downyoutube.devcurrency.devcurrency.PlayerDataMySQL.API.Currency;
import com.teenkung.pranaregenerator.PranaRegenerator;
import com.teenkung.pranaregenerator.utils.ConfigLoader;
import com.teenkung.pranaregenerator.utils.PranaPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Instant;

import static com.teenkung.pranaregenerator.PranaRegenerator.colorize;

public class OnlineHandlers {

    private static Boolean running = false;
    private static Long currentTime;

    public static void runTask() {
        if (running) {
            return;
        }
        running = true;

        currentTime = Instant.now().getEpochSecond();

        Bukkit.getScheduler().runTaskTimerAsynchronously(PranaRegenerator.getInstance(), () -> {
            if (currentTime != Instant.now().getEpochSecond()) {
                currentTime = Instant.now().getEpochSecond();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    PranaPlayerData data = DataHandler.getPlayerData(player);
                    if (data == null) { continue; }
                    if ((data.getDiffrentLoginTime() + data.getTimeLeft()) % ConfigLoader.getRegenerationRate() == 0) {
                        data.setTimeLeft(0L);
                        Currency playerCurrency = new Currency(player.getUniqueId());
                        double amount = ConfigLoader.getGiveAmount();
                        if (playerCurrency.getBalance(ConfigLoader.getCurrencyID()) + amount > playerCurrency.getMaxBalance(ConfigLoader.getCurrencyID())) {
                            amount = playerCurrency.getMaxBalance(ConfigLoader.getCurrencyID()) - playerCurrency.getBalance(ConfigLoader.getCurrencyID());
                        }
                        if (playerCurrency.getMaxBalance(ConfigLoader.getCurrencyID()) > playerCurrency.getBalance(ConfigLoader.getCurrencyID())) {
                            playerCurrency.giveBalance(ConfigLoader.getCurrencyID(), amount, "Console", "Online-Regeneration");
                            for (String cmd : ConfigLoader.getCommandOnOnline()) {
                                cmd = cmd.replaceAll("<player>", player.getName());
                                cmd = cmd.replaceAll("<amount>", String.valueOf(amount));
                                cmd = cmd.replaceAll("<max>", playerCurrency.getMaxBalance(ConfigLoader.getCurrencyID()).toString());
                                cmd = cmd.replaceAll("<current>", playerCurrency.getBalance(ConfigLoader.getCurrencyID()).toString());
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                            }
                        }
                    }
                }
             }
        }, 0, 1);
    }

}
