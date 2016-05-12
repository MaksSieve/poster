package ru.hse.edu.masitnikov.poster.main;

import ru.hse.edu.masitnikov.poster.dao.TweetDao;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final SessionFactory ourSessionFactory;

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
        final Session session = getSession();

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

            while (true) {
                System.out.println("Get list of tweets");
                List<Tweet> tweetList = dao.getList();
                if (tweetList.size() != 0) {
                    System.out.println("Start posting");
                    for (Tweet tweet:tweetList) {
                        System.out.println("Tweet: " + tweet.getText());
                        twitter.updateStatus(tweet.getText());
                        dao.removeTweet(tweet.getId());
                        System.out.println("Wait...");
                        thread.sleep(10000);
                    }
                }else{
                    System.out.println("List is empty");
                }
                thread.sleep(60000);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        } finally {
            ourSessionFactory.close();
            System.exit(1);
        }
    }
}