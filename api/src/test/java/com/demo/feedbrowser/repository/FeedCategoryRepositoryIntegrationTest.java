package com.demo.feedbrowser.repository;

import com.demo.feedbrowser.domain.FeedCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedCategoryRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FeedCategoryRepository feedCategoryRepository;

    @Test
    public void shouldFindCategory_whenSearchByName() {
        FeedCategory category = new FeedCategory("sample_category_1");
        FeedCategory category2 = new FeedCategory("sample_category_2");

        entityManager.persist(category);
        entityManager.persist(category2);
        entityManager.flush();

        FeedCategory found1 = feedCategoryRepository.findByName(category.getName());
        FeedCategory found2 = feedCategoryRepository.findByName(category2.getName());

        assertTrue("Feed category should have been found", category.getName().equals(found1.getName()));
        assertTrue("Feed category should have been found", category2.getName().equals(found2.getName()));
    }
}