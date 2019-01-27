package com.demo.imagebrowser.repository;

import com.demo.imagebrowser.domain.Feed;
import com.demo.imagebrowser.domain.FeedCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FeedRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FeedRepository feedRepository;

    @Test
    public void shouldReturnFeed_whenSearchByCategory() {
        FeedCategory category = new FeedCategory("sample_category_1");
        FeedCategory category2 = new FeedCategory("sample_category_2");

        entityManager.persist(category);
        entityManager.persist(category2);
        entityManager.flush();

        Feed feed1 = new Feed("test", "https://test.com", category);
        Feed feed2 = new Feed("test2", "https://test.com", category);
        Feed feed3 = new Feed("test3", "https://test.com", category2);

        entityManager.persist(feed1);
        entityManager.persist(feed2);
        entityManager.persist(feed3);
        entityManager.flush();

        List<Feed> feedFound = feedRepository.findByCategory(category);
        assertNotNull("Should have found feed items", feedFound);
        assertTrue("Should have found 2 feed items", feedFound.size() == 2);
    }

}