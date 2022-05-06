package com.teenkung.pranaregenerator.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

import static com.teenkung.pranaregenerator.PranaRegenerator.getConnection;

//This class is for getting Data from MySQL and set MySQL Data by using Custom class

public class PranaPlayerData {

    //This code defines UUID and Logout time of Custom Class
    private final UUID uuid;
    private Long DummyTime;
    private Long LogoutTime;
    private Long TimeLeft;

    //this is main method before getting data
    //construction: PranaPlayerData data = new PranaPlayerData(UUID uuid);
    public PranaPlayerData(UUID uuid) throws SQLException {
        this.uuid = uuid;
        PreparedStatement statement = getConnection().prepareStatement("SELECT LOGOUT FROM PranaRegenerator WHERE UUID = ?;");
        statement.setString(1, uuid.toString());
        ResultSet rs = statement.executeQuery();
        TimeLeft = 0L;
        DummyTime = Instant.now().getEpochSecond();
        if (rs.next()) {
            LogoutTime = rs.getLong("LOGOUT");
        } else {
            LogoutTime = Instant.now().getEpochSecond();
            PreparedStatement statement1 = getConnection().prepareStatement("INSERT INTO PranaRegenerator (ID, UUID, LOGOUT) VALUES (" +
                    "default," +
                    "'" + uuid + "'," +
                    LogoutTime +
                    ");");
            statement1.executeUpdate();
            statement1.close();
        }
        statement.close();

    }

    //This method is for getting uuid of PranaPlayerData
    public UUID getUUID() { return uuid; }

    //This method is for getting Logout Time of PranaPlayerData
    public Long getLogoutTime() { return LogoutTime; }

    //This method is for getting Diffrent between logout time of player and now return as Long (Seconds)
    public Long getDiffrentLogoutTime() { return Instant.now().getEpochSecond() - LogoutTime; }

    //This method is for getting Time Left to calculate in OnlineHandler Class
    public Long getTimeLeft() { return TimeLeft; }

    //This method is for getting Dummy time of PranaPlayerData
    public Long getDummyTime() { return DummyTime; }

    //This method is for getting Player class of PranaPlayerData
    public Player getPlayer() { return Bukkit.getPlayer(uuid); }

    //This method is for getting diffrent between Dummy time and now in seconds
    public Long getDiffrentDummyTime() { return Instant.now().getEpochSecond() - DummyTime; }

    //This method is for setting Time Left to calculate in OnlineHandler Class
    public void setTimeLeft(Long timeLeft) { TimeLeft = timeLeft; }

    //This method is for setting Dummy Time of PranaPlayerData
    public void setDummyTime(Long loginTime) { DummyTime = loginTime; }

    //This method is for setting Logout Time of PranaPlayerData
    public void setLogoutTime(Long time) {
        try {
            PreparedStatement statement = getConnection().prepareStatement("UPDATE PranaRegenerator SET LOGOUT = ? WHERE UUID = ?");
            statement.setLong(1, time);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            statement.close();
            LogoutTime = time;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
