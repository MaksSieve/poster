package ru.hse.edu.masitnikov.poster.dao;

import ru.hse.edu.masitnikov.poster.domain.Tweet;

import java.util.List;

public interface ITweetDao {

    void addTweet(Tweet tweet);

    List<Tweet> getList();

    void removeTweetById(Integer id);

}
