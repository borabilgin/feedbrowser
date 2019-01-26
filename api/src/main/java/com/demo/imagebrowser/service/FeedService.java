package com.demo.imagebrowser.service;

import com.demo.imagebrowser.domain.Feed;
import com.demo.imagebrowser.domain.FeedCategory;
import java.util.List;

public interface FeedService {
    List<Feed> findByCategoryName(String categoryName);
    void deleteCategoryByName(String categoryName);
    FeedCategory addCategory(String categoryName);
    Feed addFeed(Feed feed);
    List<Feed> getAll();
    Boolean categoryExists(String categoryName);
}
