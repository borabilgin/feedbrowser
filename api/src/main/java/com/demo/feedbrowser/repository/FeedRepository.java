package com.demo.feedbrowser.repository;

import com.demo.feedbrowser.domain.Feed;
import com.demo.feedbrowser.domain.FeedCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeedRepository extends CrudRepository<Feed, Long> {
    List<Feed> findByCategory(FeedCategory category);
}
