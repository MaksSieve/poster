package ru.hse.edu.masitnikov.poster.dao;

import org.hibernate.Criteria;
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
        Date max = curr;
        max.setMinutes(curr.getMinutes()+30);
        Criteria crit = session.createCriteria(Tweet.class)
            .add(Expression.between("date",curr,max))
            .setMaxResults(30);
        return crit.list();
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
