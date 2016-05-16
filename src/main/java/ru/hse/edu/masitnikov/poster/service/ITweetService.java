package ru.hse.edu.masitnikov.poster.service;

import ru.hse.edu.masitnikov.poster.domain.Tweet;
import java.util.List;

public interface ITweetService {

	void addTweet(Tweet tweet);

	List<Tweet> getList();

	void removeTweetById(Integer id);
}
