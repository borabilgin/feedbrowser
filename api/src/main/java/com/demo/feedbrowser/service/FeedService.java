package com.demo.feedbrowser.service;

import com.demo.feedbrowser.domain.Feed;
import com.demo.feedbrowser.domain.FeedCategory;
import java.util.List;

public interface FeedService {
    List<Feed> findByCategoryName(String categoryName);
    void deleteCategoryByName(String categoryName);
    FeedCategory addCategory(String categoryName);
    Feed addFeed(Feed feed);
    List<Feed> getAll();
    Boolean categoryExists(String categoryName);
}
