package ru.hse.edu.masitnikov.poster.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.hse.edu.masitnikov.poster.domain.Status;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import ru.hse.edu.masitnikov.poster.main.Main;

import java.util.Date;
import java.util.List;

@Repository
public class TweetDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void addTweet(Tweet tweet) {
    }

    @SuppressWarnings("unchecked")
    public List<Tweet> getList() {
        Session session = sessionFactory.getCurrentSession();
        Date curr = new Date();
        System.out.println("Current date-time: " + curr.toString());
        Query query = session.createQuery("from Tweet where date > :curr and status = :stat order by date asc");
        query.setParameter("stat", Status.ready);
        query.setParameter("curr", curr);
        List<Tweet> list = query.list();
        System.out.println(list.toString());
        return list;
    }

    public void removeTweetById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Tweet tweet = (Tweet) session.load(
                Tweet.class, id);
        if (null != tweet) {
            session.delete(tweet);
        }
    }

    public void removeTweet(Tweet tweet) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(tweet);
    }

    public void update(Tweet tweet) {
        Session session = sessionFactory.getCurrentSession();
        session.update(tweet);
        session.flush();
    }

}
