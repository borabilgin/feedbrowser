package com.demo.imagebrowser.repository;

import com.demo.imagebrowser.domain.FeedCategory;
import org.springframework.data.repository.CrudRepository;

public interface FeedCategoryRepository extends CrudRepository<FeedCategory, Long> {
    FeedCategory findByName(String name);
}
