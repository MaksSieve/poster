package ru.hse.edu.masitnikov.poster.domain;

import twitter4j.User;

import javax.persistence.*;

@Entity
@Table(name = "COMPANIES")
public class Company {

    @Id
    @Column
    @GeneratedValue
    private Integer id;

    @Column
    private String user;

    @Column
    private String accessToken;

    @Column
    private String accessTokenSecret;

    //GETTERS AND SETTERS

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public void setAccessTokenSecret(String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }
}
