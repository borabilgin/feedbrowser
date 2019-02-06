package com.demo.feedbrowser.repository;

import com.demo.feedbrowser.domain.FeedCategory;
import org.springframework.data.repository.CrudRepository;

public interface FeedCategoryRepository extends CrudRepository<FeedCategory, Long> {
    FeedCategory findByName(String name);
    void deleteByName(String name);
}
