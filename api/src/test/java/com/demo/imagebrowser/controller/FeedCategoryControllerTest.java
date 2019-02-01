package com.demo.imagebrowser.controller;

import com.demo.imagebrowser.domain.FeedCategory;
import com.demo.imagebrowser.service.FeedService;
import com.demo.imagebrowser.service.impl.UserDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FeedCategoryController.class, secure = false)
public class FeedCategoryControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FeedService feedService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    public void shouldReturnCategory_whenAddingNewCategory() throws Exception {
        String categoryName = "test";
        FeedCategory category = new FeedCategory(categoryName);
        given(feedService.addCategory(categoryName)).willReturn(category);

        mvc.perform(put("/secure/category", categoryName)
                .contentType(MediaType.APPLICATION_JSON)
                .param("categoryName", categoryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    @Test
    public void shouldReturnError_whenAddingNewCategoryWithNoNameProvided() throws Exception {
        mvc.perform(put("/secure/category")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}