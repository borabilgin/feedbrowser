package com.demo.feedbrowser.service.impl;

import com.demo.feedbrowser.domain.Feed;
import com.demo.feedbrowser.domain.FeedCategory;
import com.demo.feedbrowser.repository.FeedCategoryRepository;
import com.demo.feedbrowser.repository.FeedRepository;
import com.demo.feedbrowser.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedCategoryRepository feedCategoryRepository;

    @Override
    public List<Feed> findByCategoryName(String categoryName) {
        FeedCategory category = feedCategoryRepository.findByName(categoryName);
        if (category != null) {
            return feedRepository.findByCategory(category);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteCategoryByName(String categoryName) {
        feedCategoryRepository.deleteByName(categoryName);
    }

    @Override
    public Boolean categoryExists(String categoryName) {
        return feedCategoryRepository.findByName(categoryName) != null;
    }

    @Override
    public FeedCategory addCategory(String categoryName) {
        if (!categoryExists(categoryName)) {
            FeedCategory category = new FeedCategory();
            category.setName(categoryName);
            feedCategoryRepository.save(category);
            return category;
        } else {
            return null;
        }
    }

    @Override
    public Feed addFeed(Feed feed) {
        return feedRepository.save(feed);
    }

    @Override
    public List<Feed> getAll() {
        return StreamSupport.stream(feedRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
}
