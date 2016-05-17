package ru.hse.edu.masitnikov.poster.main;

import org.hibernate.*;
import ru.hse.edu.masitnikov.poster.dao.TweetDao;
import ru.hse.edu.masitnikov.poster.domain.Status;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.*;

public class Main {
    public static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = HibernateUtil.getSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() throws HibernateException {
        return sessionFactory;
    }

    public static void exit(Integer code) {
        System.exit(code);
    }

    public static String CONSUMER_KEY = "VzTR3pk7IXXkVr5y49utkiWpM";
    public static String CONSUMER_SECRET = "cBZNTyGfqSGOBg9epUhyFVHUCfEKA43pHhd5gsANkZyXpgfUGN";

    public static String KEYS = "keys";
    public static String ACCESS = "access";

    public static void main(final String[] args) throws Exception {
        final File keys = new File(KEYS);
        if (!keys.exists()){
            if(keys.createNewFile()){
                PrintWriter out = new PrintWriter(keys);
                out.println(CONSUMER_KEY);
                out.println(CONSUMER_SECRET);
                out.close();
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader(keys));
        HashMap<String, String> appKeys = new HashMap<String, String>();
        appKeys.put("key", reader.readLine());
        appKeys.put("secret", reader.readLine());
        reader.close();
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setDebugEnabled(true)
            .setOAuthConsumerKey(appKeys.get("key"))
            .setOAuthConsumerSecret(appKeys.get("secret"))
            .setOAuthAccessToken(null)
            .setOAuthAccessTokenSecret(null);
        twitter4j.conf.Configuration conf = builder.build();
        TwitterFactory twitterFactory = new TwitterFactory(conf);
        final Twitter twitter = twitterFactory.getInstance();

        final File access = new File(ACCESS);
        if (!access.exists()){
            TwitterLogin(twitter);
        }else{
            reader = new BufferedReader(new FileReader(access));
            String token = reader.readLine();
            String secret = reader.readLine();
            AccessToken accessToken = new AccessToken(token, secret);
            twitter.setOAuthAccessToken(accessToken);
        }

        long userID = twitter.getId();
        twitter4j.User newUser=twitter.showUser(twitter.getScreenName());
        String name = newUser.getName();
        System.out.println("Logged in Twitter as: " + name);



        final Session session = getSessionFactory().openSession();
        session.setFlushMode(FlushMode.ALWAYS);
        session.setCacheMode(CacheMode.IGNORE);
        session.close();
        final TweetDao dao = new TweetDao();



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
                tweetList = dao.getList();
                if (tweetList.size() != 0) {
                    for (Tweet tweet:tweetList) {
                        System.out.println("Tweet: " + tweet.getText()+" ::: " + tweet.getDate().toString() + " ::: " + tweet.getId());
//                        Timer timer = new Timer();
//                        timer.schedule(new Twit(timer, twitter, tweet.getText()), tweet.getDate());
//                        tweet.setStatus(Status.published);
//                        dao.update(tweet);
                    }

                }else{
                    System.out.println("List is empty");
                }
                thread.sleep(20000);
            }

        }catch (Exception e){
            System.out.println(e.toString());
        } finally {
            sessionFactory.close();
            System.exit(1);
        }
    }

    private static void TwitterLogin (Twitter twitter) throws TwitterException{
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
                try {
                    PrintWriter fout = new PrintWriter(new File(ACCESS));
                    fout.println(accessToken.getToken());
                    fout.println(accessToken.getTokenSecret());
                    fout.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
    }
}