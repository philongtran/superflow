package de.superflow.rest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by philo on 25.05.2017.
 */
@Entity
public class HighscoreSF {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name="Nickname")
    private String nickname;
    @Column(name="Gamename")
    private String gameName = "SuperFlow";
    @Column(name="point")
    private int point;
    @Column(name="level")
    private int level;
    @Column(name="date")
    private Date date;

    public HighscoreSF() {

    }

    public HighscoreSF(String nickname, int point, int level, Date date) {
        this.nickname = nickname;
        this.point = point;
        this.level = level;
        this.date = date;
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
        return this.level;
    }

    public Date getDate() {
        return this.date;
    }
}
