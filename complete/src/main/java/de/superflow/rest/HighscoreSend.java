package de.superflow.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

/**
 * @author Phi Long Tran
 *
 * This class is used to send to the mobile app the highscore info
 */

public class HighscoreSend {

    private String nickname;
    private int point;
    private int level;
    private Timestamp date;

    public HighscoreSend() {
    }

    public HighscoreSend(String nickname, int point, int level, Timestamp date) {
        this.nickname = nickname;
        this.point = point;
        this.level = level;
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public int getPoint() {
        return point;
    }

    public String getNickname() {
        return nickname;
    }

    public Timestamp getDate() {
        return date;
    }
}
