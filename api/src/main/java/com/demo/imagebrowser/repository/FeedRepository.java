package com.demo.imagebrowser.repository;

import com.demo.imagebrowser.domain.Feed;
import com.demo.imagebrowser.domain.FeedCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeedRepository extends CrudRepository<Feed, Long> {
    List<Feed> findByCategory(FeedCategory category);
}
