package ru.hse.edu.masitnikov.poster.core;

import ru.hse.edu.masitnikov.poster.dao.TweetDao;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import twitter4j.Twitter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Schedule extends TimerTask {

    public Schedule(Timer timer, Twitter twitter, TweetDao dao) {
        setTwitter(twitter);
        setDao(dao);
        setTime(timer);
    }

    private Twitter twitter;

    private Timer time;

    private TweetDao dao;

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    public Timer getTime() {
        return time;
    }

    public void setTime(Timer time) {
        this.time = time;
    }

    public TweetDao getDao() {
        return dao;
    }

    public void setDao(TweetDao dao) {
        this.dao = dao;
    }

    @Override
    public void run() {
        System.out.println("Get list of tweets");
        List<Tweet> tweetList = dao.getList();
        List<Timer> timerList = null;
        if (tweetList.size() != 0) {
            System.out.println("Create schedule");
            for (Tweet tweet:tweetList) {
                System.out.println("Tweet: " + tweet.getText());
                Timer timer = new Timer();
                timer.schedule(new Twit(timer, twitter, tweet.getText()), tweet.getDate());
                timerList.add(timer);
                dao.removeTweetById(tweet.getId());
            }
        }else{
            System.out.println("List is empty");
        }
    }

}
