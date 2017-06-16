package de.superflow.rest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Created by Long on 16.06.2017.
 */
@Entity
public class Player {

    @Id
    @Column(name="id")
    private String id = UUID.randomUUID().toString();
    @Column(name="nickname")
    private String nickname;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;
    @Column(name="securitystamp")
    private String securitystamp;

    public Player(){}

    public Player(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getID() {
        return id;
    }
}
