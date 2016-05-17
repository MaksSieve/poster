package ru.hse.edu.masitnikov.poster.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import ru.hse.edu.masitnikov.poster.domain.Status;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import ru.hse.edu.masitnikov.poster.main.HibernateUtil;
import ru.hse.edu.masitnikov.poster.main.Main;

import java.util.Date;
import java.util.List;

public class TweetDao {

   private static final SessionFactory sessionFactory = Main.getSessionFactory();

    @SuppressWarnings("unchecked")
    public List<Tweet> getList() {
        Session session = sessionFactory.openSession();
        Date curr = new Date();
        System.out.println("Current date-time: " + curr.toString());
        Query query = session.createQuery("from Tweet order by date asc");
        List<Tweet> list = query.list();
        System.out.println(list.toString());
        sessionFactory.getCache().evictDefaultQueryRegion();
        session.close();
        return list;
    }

    public void removeTweetById(Integer id) {
        Session session = sessionFactory.openSession();
        Tweet tweet = (Tweet) session.load(
                Tweet.class, id);
        if (null != tweet) {
            session.delete(tweet);
            session.flush();
        }
        session.close();
    }

    public void removeTweet(Tweet tweet) {
        Session session = sessionFactory.openSession();
        session.delete(tweet);
        session.flush();
        session.close();
    }

    public void update(Tweet tweet){
        Session session = sessionFactory.openSession();
        session.update(tweet);
        session.flush();
        session.close();
    }

}
