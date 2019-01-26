package com.demo.imagebrowser.service.impl;

import com.demo.imagebrowser.domain.Feed;
import com.demo.imagebrowser.domain.FeedCategory;
import com.demo.imagebrowser.repository.FeedCategoryRepository;
import com.demo.imagebrowser.repository.FeedRepository;
import com.demo.imagebrowser.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

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
    public FeedCategory addCategory(String categoryName) {
        FeedCategory category = new FeedCategory();
        category.setName(categoryName);
        feedCategoryRepository.save(category);
        return category;
    }

    @Override
    public Feed addFeed(Feed feed) {
        return feedRepository.save(feed);
    }
}
