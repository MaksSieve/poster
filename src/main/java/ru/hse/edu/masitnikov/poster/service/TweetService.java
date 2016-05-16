package ru.hse.edu.masitnikov.poster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.edu.masitnikov.poster.dao.ITweetDao;
import ru.hse.edu.masitnikov.poster.dao.TweetDao;
import ru.hse.edu.masitnikov.poster.domain.Tweet;

import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetDao dao;

    @Transactional
    public void addTweet(Tweet tweet) {
        dao.addTweet(tweet);
    }

    @Transactional
    public List<Tweet> getList() {
        return dao.getList();
    }

    @Transactional
    public void removeTweetById(Integer id) {
        dao.removeTweetById(id);
    }
}
