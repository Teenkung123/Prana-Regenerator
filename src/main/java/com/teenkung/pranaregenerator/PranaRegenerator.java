package com.teenkung.pranaregenerator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PranaRegenerator extends JavaPlugin {

    //This is the main class of PR this class will handle plugin enable / disable
    //and Instance and some database things

    //This variable is getting Instance of this class to use in another class
    private static PranaRegenerator Instance;

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


    }

    @Override
    public void onDisable() {
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
