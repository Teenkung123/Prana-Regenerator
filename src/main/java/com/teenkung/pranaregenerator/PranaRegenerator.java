package com.teenkung.pranaregenerator;

import com.teenkung.pranaregenerator.Handlers.DataHandler;
import com.teenkung.pranaregenerator.Handlers.JoinEvent;
import com.teenkung.pranaregenerator.Handlers.OnlineHandlers;
import com.teenkung.pranaregenerator.Handlers.QuitEvent;
import com.teenkung.pranaregenerator.utils.Database;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This is the main class of PR this class will handle plugin enable / disable
//and Instance and some database things
public final class PranaRegenerator extends JavaPlugin {

    //This variable is getting Instance of this class to use in another class
    private static PranaRegenerator Instance;
    //This variable is getting Database from Database class
    static Database database;

    //This method is for send Instance of this class to another class
    //construction: PranaRegenerator.getInstance()
    public static PranaRegenerator getInstance() { return Instance; }


    @Override
    public void onEnable() {
        //This defines the variable Instance to this class's Instance
        Instance = this;

        //This code is for loading default config and save it
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //This code for connect database
        database = new Database();
        try {
            database.Connect();
            database.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //This code is for register event to classes
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);

        //This code is for running online calculations task
        OnlineHandlers.runTask();

        //This code is for loop every online player and load their data
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!DataHandler.isLoaded(player)) { DataHandler.getPlayerData(player); }
        }


    }

    @Override
    public void onDisable() {
        database.Disconnect();
    }

    //This method for getting Connection from Database class
    public static Connection getConnection() {
        return database.getConnection();
    }

    //This method for colorize String
    public static String colorize(String s) {
        if (s == null || s.equals(""))
            return "";
        if (!Bukkit.getVersion().contains("1.16") && !Bukkit.getVersion().contains("1.17"))
            return ChatColor.translateAlternateColorCodes('&', s);
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(s);
        while (match.find()) {
            String hexColor = s.substring(match.start(), match.end());
            s = s.replace(hexColor, ChatColor.valueOf(hexColor).toString());
            match = pattern.matcher(s);
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
