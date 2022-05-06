package com.teenkung.pranaregenerator.utils;

import com.teenkung.pranaregenerator.PranaRegenerator;

import java.util.ArrayList;
import java.util.List;

import static com.teenkung.pranaregenerator.PranaRegenerator.colorize;

public class ConfigLoader {

    //This class is for getting config files

    //This line getting Instance of the main class
    private static final PranaRegenerator Instance = PranaRegenerator.getInstance();

    //This method for getting Host of database from config
    public static String getDatabaseHost() {
        if (Instance.getConfig().contains("Database.Host")) {
            return Instance.getConfig().getString("Database.Host");
        }
        System.out.println(colorize("&cCould not load Database Host due to it does not exist!"));
        return "";
    }

    //This method for getting Port of database from config
    public static String getDatabasePort() {
        if (Instance.getConfig().contains("Database.Port")) {
            return Instance.getConfig().getString("Database.Port");
        }
        System.out.println(colorize("&cCould not load Database Port due to it does not exist!"));
        return "";
    }

    //This method for getting Username of database from config
    public static String getDatabaseUsername() {
        if (Instance.getConfig().contains("Database.Username")) {
            return Instance.getConfig().getString("Database.Username");
        }
        System.out.println(colorize("&cCould not load Database Username due to it does not exist!"));
        return "";
    }

    //This method for getting Password of database from config
    public static String getDatabasePassword() {
        if (Instance.getConfig().contains("Database.Password")) {
            return Instance.getConfig().getString("Database.Password");
        }
        System.out.println(colorize("&cCould not load Database Password due to it does not exist!"));
        return "";
    }

    //This method for getting Database Name from config
    public static String getDatabaseDatabase() {
        if (Instance.getConfig().contains("Database.Database")) {
            return Instance.getConfig().getString("Database.Database");
        }
        System.out.println(colorize("&cCould not load Database Name due to it does not exist!"));
        return "";
    }

    //finish the database thing here


    //This method is for getting Regeneration Rate if it not exists it will return 480 as default value (8 minute)
    public static Integer getRegenerationRate() {
        if (Instance.getConfig().contains("Settings.Regeneration-Rate")) {
            return Instance.getConfig().getInt("Settings.Regeneration-Rate");
        }
        System.out.println(colorize("&cCould not load Regeneration Rate due to it does not exist!"));
        return 480;
    }


    //This method is for getting Allowed Offline Calculations settings if not exists it will return true as default value
    public static Boolean getAllowedOfflineCalculations() {
        if (Instance.getConfig().contains("Settings.Allowed-Offline-Calculations")) {
            return Instance.getConfig().getBoolean("Settings.Allowed-Offline-Calculations");
        }
        System.out.println(colorize("&cCould not load Allowed Offline Calculation due to it does not exist!"));
        return true;
    }

    //This method is for getting Currency ID in config file it will return "" as default value
    public static String getCurrencyID() {
        if (Instance.getConfig().contains("Settings.Currency")) {
            return Instance.getConfig().getString("Settings.Currency");
        }
        System.out.println(colorize("&cCould not load Currency due to it does not exist!"));
        return "";
    }

    //This method is for getting give amount of Prana will return 1 as default value
    public static Double getGiveAmount() {
        if (Instance.getConfig().contains("Settings.Give-Amount")) {
            return Instance.getConfig().getDouble("Settings.Give-Amount");
        }
        System.out.println(colorize("&cCould not load Give Amount due to it does not exist!"));
        return 1D;
    }

    //This method is for getting Command on Online will return empty array as default value
    public static List<String> getCommandOnOnline() {
        if (Instance.getConfig().contains("Settings.Command-on-online")) {
            return new ArrayList<>(Instance.getConfig().getStringList("Settings.Command-on-online"));
        }
        System.out.println(colorize("&cCould not load Command On Online due to it does not exist!"));
        return new ArrayList<>();
    }

    //This method is for getting Command on Offline will return empty Array as default value
    public static ArrayList<String> getCommandOnOffline() {
        if (Instance.getConfig().contains("Settings.Command-on-offline")) {
            return new ArrayList<>(Instance.getConfig().getStringList("Settings.Command-on-offline"));
        }
        System.out.println(colorize("&cCould not load Command on Offline due to it does not exist!"));
        return new ArrayList<>();
    }

}
