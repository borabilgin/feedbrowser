package com.demo.imagebrowser.service.impl;

import com.demo.imagebrowser.domain.Feed;
import com.demo.imagebrowser.domain.FeedCategory;
import com.demo.imagebrowser.repository.FeedCategoryRepository;
import com.demo.imagebrowser.repository.FeedRepository;
import com.demo.imagebrowser.service.FeedService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class FeedServiceImplTest {

    private static String CATEGORY_NAME = "TEST CATEGORY";
    private static String FEED_NAME = "TEST FEED";

    private Feed testFeed;
    private FeedCategory testFeedCategory;

    @TestConfiguration
    static class FeedServiceImplTestContextConfiguration {
        @Bean
        public FeedService feedService() {
            return new FeedServiceImpl();
        }
    }

    @Autowired
    private FeedService feedService;

    @MockBean
    private FeedRepository feedRepository;

    @MockBean
    private FeedCategoryRepository feedCategoryRepository;

    @Before
    public void setUp() throws Exception {
        testFeedCategory = new FeedCategory(CATEGORY_NAME);
        testFeed = new Feed(FEED_NAME, "test", testFeedCategory);

        Mockito.when(feedCategoryRepository.findByName(CATEGORY_NAME)).thenReturn(testFeedCategory);
        Mockito.when(feedRepository.findByCategory(testFeedCategory)).thenReturn(Arrays.asList(testFeed));
        Mockito.when(feedCategoryRepository.findByName(CATEGORY_NAME)).thenReturn(testFeedCategory);
        Mockito.when(feedRepository.save(testFeed)).thenReturn(testFeed);
    }

    @Test
    public void shouldReturnFeed_whenSearchByCategoryName() {
        List<Feed> feeds = feedService.findByCategoryName(CATEGORY_NAME);

        assertNotNull("Should return feed", feeds);
        assertTrue("Category should have 1 feed", feeds.size() == 1);
        assertEquals("Correct feed should have been returned", FEED_NAME, feeds.get(0).getName());
    }

    @Test
    public void shouldReturnTrue_whenSearchByCategoryName() {
        assertTrue("Category should exist", feedService.categoryExists(CATEGORY_NAME));
    }

    @Test
    public void shouldSaveFeed_whenGivenFeedEntity() {
        assertEquals("Feed should be saved", testFeed, feedService.addFeed(testFeed));
    }


}