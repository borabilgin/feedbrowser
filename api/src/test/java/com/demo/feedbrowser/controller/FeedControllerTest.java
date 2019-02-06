package com.demo.feedbrowser.controller;

import com.demo.feedbrowser.domain.Feed;
import com.demo.feedbrowser.domain.FeedCategory;
import com.demo.feedbrowser.service.FeedService;
import com.demo.feedbrowser.service.impl.UserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(FeedController.class)
public class FeedControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeedService feedService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void shouldReturnJSON_whenRequestedAllFeeds() throws Exception {
        FeedCategory category = new FeedCategory("test");
        List<Feed> feeds = Arrays.asList(new Feed("test", "test", category));

        given(feedService.getAll()).willReturn(feeds);

        mvc.perform(get("/feed")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value(feeds.get(0).getName()));

    }
}