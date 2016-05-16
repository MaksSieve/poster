package ru.hse.edu.masitnikov.poster.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hse.edu.masitnikov.poster.dao.TweetDao;
import ru.hse.edu.masitnikov.poster.domain.Status;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import ru.hse.edu.masitnikov.poster.service.TweetService;
import twitter4j.Twitter;

import java.util.List;
import java.util.Timer;

@Component
public class Poster {

    @Autowired
    private TweetService service;

    public void posting () throws Exception {

        Twitter twitter = TweetLoginer.login();

        long userID = twitter.getId();
        twitter4j.User newUser=twitter.showUser(twitter.getScreenName());
        String name=newUser.getName();
        System.out.println("Logged in Twitter as: " + name);

        try {
            Thread thread = Thread.currentThread();
//            Timer greatTimer = new Timer();
//
//            ConsoleThread console = new ConsoleThread(greatTimer);
//
//            greatTimer.schedule(new Schedule(greatTimer, twitter, dao), 0, 60000);

            while (true) {
                System.out.println("Get list of tweets");
                List<Tweet> tweetList = null;
                tweetList = service.getList();
                if (tweetList.size() != 0) {
                    for (Tweet tweet:tweetList) {
                        System.out.println("Tweet: " + tweet.getText()+" ::: " + tweet.getDate().toString() + " ::: " + tweet.getId());
                        Timer timer = new Timer();
                        timer.schedule(new Twit(timer, twitter, tweet.getText()), tweet.getDate());
                        tweet.setStatus(Status.published);
                    }

                }else{
                    System.out.println("List is empty");
                }
                thread.sleep(20000);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        } finally {
            System.exit(1);
        }
    }

}
