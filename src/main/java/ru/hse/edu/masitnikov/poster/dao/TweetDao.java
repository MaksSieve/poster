package ru.hse.edu.masitnikov.poster.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import ru.hse.edu.masitnikov.poster.domain.Status;
import ru.hse.edu.masitnikov.poster.domain.Tweet;
import ru.hse.edu.masitnikov.poster.main.Main;

import java.util.Date;
import java.util.List;

public class TweetDao {

    private static final Session session = Main.getSession();

    @SuppressWarnings("unchecked")
    public List<Tweet> getList() {
        Date curr = new Date();
        System.out.println("Current date-time: " + curr.toString());
        Query query = session.createQuery("from Tweet where date > :curr and status = :stat order by date asc");
        query.setParameter("stat", Status.ready);
        query.setParameter("curr", curr);
        List<Tweet> list = query.list();
        System.out.println(list.toString());
        return list;
    }

    public void removeTweetById(Integer id) {;
        Tweet tweet = (Tweet) session.load(
                Tweet.class, id);
        if (null != tweet) {
            session.delete(tweet);
        }
    }

    public void removeTweet(Tweet tweet) {
        session.delete(tweet);
    }

}
