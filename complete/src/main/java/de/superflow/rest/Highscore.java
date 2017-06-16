package de.superflow.rest;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Long on 16.06.2017.
 */
@Entity
public class Highscore {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name="gamename")
    private String gameName = "SuperFlow";
    @Column(name="playerid")
    private String playerid;
    @Column(name="level")
    private int level;
    @Column(name="point")
    private int point;
    @Column(name="timestamp")
    private Timestamp timestamp;

    public Highscore() {
    }

    public Highscore(String nickname, int point, int level, Timestamp timestamp) {
        this.playerid = nickname;
        this.point = point;
        this.level = level;
        this.timestamp = timestamp;
    }

    public String getNickname() {
        return this.playerid;
    }

    public String getGameName() {
        return this.gameName;
    }

    public int getPoint() {
        return this.point;
    }

    public int getLevel() {
        return this.level;
    }

    public Timestamp getDate() {
        return this.timestamp;
    }

    public void setPlayerid(String uuid) {this.playerid = uuid;}
}
