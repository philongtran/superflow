package de.superflow.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;

/**
 * Created by Long on 16.06.2017.
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
