package ru.hse.edu.masitnikov.poster.core;

import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class TweetLoginer {

    public static String CONSUMER_KEY = "VzTR3pk7IXXkVr5y49utkiWpM";
    public static String CONSUMER_SECRET = "cBZNTyGfqSGOBg9epUhyFVHUCfEKA43pHhd5gsANkZyXpgfUGN";

    public static String KEYS = "keys";
    public static String ACCESS = "access";

    public static Twitter login () throws Exception{

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
            auth(twitter);
        }else{
            reader = new BufferedReader(new FileReader(access));
            String token = reader.readLine();
            String secret = reader.readLine();
            AccessToken accessToken = new AccessToken(token, secret);
            twitter.setOAuthAccessToken(accessToken);
        }

        return twitter;
    };

    private static void auth(Twitter twitter) throws TwitterException {
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
