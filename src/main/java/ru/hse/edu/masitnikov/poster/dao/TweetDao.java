package ru.hse.edu.masitnikov.poster.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import ru.hse.edu.masitnikov.poster.main.Main;

import java.util.List;

public class TweetDao {

    private static final Session session = Main.getSession();

    @SuppressWarnings("unchecked")
    public List<Tweet> getList() {

        return session.createQuery("from Tweet ").list();
    }

    public void removeTweet(Integer id) {
        session.beginTransaction();
        Tweet tweet = (Tweet) session.load(
                Tweet.class, id);
        if (null != tweet) {
            session.delete(tweet);
        }
        session.close();
    }

}
