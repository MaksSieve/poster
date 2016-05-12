package ru.hse.edu.masitnikov.poster.main;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Timer;
import java.util.TimerTask;


public class Twit extends TimerTask {

    public Twit(Timer timer, Twitter twitter, String text) {
        setText(text);
        setTwitter(twitter);
        setTimer(timer);
    }

    private Timer timer;

    private Twitter twitter;

    private String text;

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void run() {
        try {
            System.out.println("TIME TO TWEEEEEEET!!!!!\nTweet: "+text);
            twitter.updateStatus(text);
        } catch (TwitterException e) {
            e.printStackTrace();
        }finally {
            timer.cancel();
        }
    }
}
