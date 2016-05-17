package ru.hse.edu.masitnikov.poster.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TWEETS")
public class Tweet {

    @Id
    @Column
    @GeneratedValue
    private Integer id;

    @Column
    private String text;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
