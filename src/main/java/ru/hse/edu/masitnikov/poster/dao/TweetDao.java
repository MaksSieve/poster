package ru.hse.edu.masitnikov.poster.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import ru.hse.edu.masitnikov.poster.main.Main;

import java.util.Date;
import java.util.List;

public class TweetDao {

    private static final Session session = Main.getSession();

    @SuppressWarnings("unchecked")
    public List<Tweet> getList() {
        Date curr = new Date();
        Query query = session.createQuery("from Tweet where date > :curr order by date asc");
        query.setParameter("curr", curr);
        return query.list();
    }

    public void removeTweetById(Integer id) {
        session.beginTransaction();
        Tweet tweet = (Tweet) session.load(
                Tweet.class, id);
        if (null != tweet) {
            session.delete(tweet);
            session.getTransaction().commit();
        }
    }

    public void removeTweet(Tweet tweet) {
        session.beginTransaction();
        session.delete(tweet);
        session.getTransaction().commit();
    }

}
