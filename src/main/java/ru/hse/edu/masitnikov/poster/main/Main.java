package ru.hse.edu.masitnikov.poster.main;

import ru.hse.edu.masitnikov.poster.dao.TweetDao;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

public class Main {
    public static final SessionFactory ourSessionFactory;

    static {
        try {
            ourSessionFactory = HibernateUtil.getSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void exit(Integer code) {
        System.exit(code);
    }

    public static String CONSUMER_KEY = "VzTR3pk7IXXkVr5y49utkiWpM";
    public static String CONSUMER_SECRET = "cBZNTyGfqSGOBg9epUhyFVHUCfEKA43pHhd5gsANkZyXpgfUGN";

    public static void main(final String[] args) throws Exception {

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET)
                .setOAuthAccessToken(null)
                .setOAuthAccessTokenSecret(null);
        twitter4j.conf.Configuration conf = builder.build();
        TwitterFactory twitterFactory = new TwitterFactory(conf);
        final Twitter twitter = twitterFactory.getInstance();
        final TweetDao dao = new TweetDao();

        try {
            Scanner sc = new Scanner(System.in);
            while (true) {
                RequestToken requestToken = twitter.getOAuthRequestToken();
                AccessToken accessToken = null;
                System.out.println(requestToken.getAuthenticationURL());
                System.out.print("Enter your pin please: ");

                String pin = sc.nextLine();
                try{
                    if(pin.length() > 0){
                        accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                    }else{
                        accessToken = twitter.getOAuthAccessToken();
                    }
                    break;
                } catch (TwitterException te) {
                    if(401 == te.getStatusCode()){
                        System.out.println("Unable to get the access token.");
                    }else{
                        te.printStackTrace();
                    }
                }
            }

            Thread thread = Thread.currentThread();

//
//            Timer greatTimer = new Timer();
//
//            ConsoleThread console = new ConsoleThread(greatTimer);
//
//            greatTimer.schedule(new Schedule(greatTimer, twitter, dao), 0, 60000);

            while (true) {
                System.out.println("Get list of tweets");
                List<Tweet> tweetList = dao.getList();
                Date date = new Date();
                System.out.println("Current date-time: " + date.toString());
                if (tweetList.size() != 0) {
                    for (Tweet tweet:tweetList) {
                        System.out.println("Tweet: " + tweet.getText()+" ::: " + tweet.getDate().toString() + " ::: " + tweet.getId());
                        Timer timer = new Timer();
                        timer.schedule(new Twit(timer, twitter, tweet.getText()), tweet.getDate());
                        dao.removeTweet(tweet);
                    }
                }else{
                    System.out.println("List is empty");
                }
                thread.sleep(20000);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        } finally {
            ourSessionFactory.close();
            System.exit(1);
        }
    }
}