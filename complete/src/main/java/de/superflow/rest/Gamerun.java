package de.superflow.rest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

@Entity
public class Gamerun {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name="playerid")
    private String nickname;
    @Column(name="gameid")
    private String gameName = "SuperFlow";
    @Column(name="points")
    private int point;
    //@Column(name="level")
    //private int level;
    @Column(name="time")
    private Date time;

    public Gamerun() {

    }

    public Gamerun(String nickname, int point, int level, Date time) {
        this.nickname = nickname;
        this.point = point;
        //this.level = level;
        this.time = time;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getGameName() {
        return this.gameName;
    }

    public int getPoint() {
        return this.point;
    }

    public int getLevel() {
        return 1;
    }

    public Date getTime() {
        return this.time;
    }
}
