package com.demo.imagebrowser;

import com.demo.imagebrowser.ImagebrowserApplication;
import com.demo.imagebrowser.domain.Feed;
import com.demo.imagebrowser.domain.FeedCategory;
import com.demo.imagebrowser.repository.FeedCategoryRepository;
import com.demo.imagebrowser.repository.FeedRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = ImagebrowserApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class ImageBrowserApplicationIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedCategoryRepository feedCategoryRepository;

    @Before
    public void setup() {
        feedCategoryRepository.deleteAll();
        feedRepository.deleteAll();

        FeedCategory category1 = new FeedCategory("category1");
        FeedCategory category2 = new FeedCategory("category2");
        Feed testFeed1 = new Feed("test", "test", category1);
        Feed testFeed2 = new Feed("test", "test", category1);
        Feed testFeed3 = new Feed("test", "test", category2);

        feedCategoryRepository.save(category1);
        feedCategoryRepository.save(category2);
        feedRepository.save(testFeed1);
        feedRepository.save(testFeed2);
        feedRepository.save(testFeed3);
    }

    @Test
    public void shouldReturnAllFeedItems_whenGetFeeds() throws Exception{
        mvc.perform(get("/feed")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("test"));
    }

    @Test
    public void shouldThrowError_whenAddingCategoriesUnauthorized() throws Exception{
        mvc.perform(get("/secure/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void shouldReturnAddedCategory_whenAddingCategoriesAuthorized() throws Exception{
        mvc.perform(get("/secure/category").with(user("Admin"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}