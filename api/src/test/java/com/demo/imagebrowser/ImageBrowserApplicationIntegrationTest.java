package com.demo.imagebrowser;

import com.demo.imagebrowser.domain.Feed;
import com.demo.imagebrowser.domain.FeedCategory;
import com.demo.imagebrowser.repository.FeedCategoryRepository;
import com.demo.imagebrowser.repository.FeedRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    classes = ImagebrowserApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ImageBrowserApplicationIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private FeedCategoryRepository feedCategoryRepository;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Value("${security.jwt.client-id}")
    private String jwtClientId;


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

        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .addFilter(springSecurityFilterChain).build();
    }

    private String getToken(String username, String password) throws Exception {

        String jwtSecret = "jwtclientsecret";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", jwtClientId);
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = this.mvc.perform(post("/oauth/token")
                .params(params)
                .with(httpBasic(jwtClientId, jwtSecret))
                .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
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
        String categoryName = "test_category";
        mvc.perform(put("/secure/category", categoryName)
                .contentType(MediaType.APPLICATION_JSON)
                .param("categoryName", categoryName))
                .andExpect(status().isUnauthorized());
    }


    @Test
    public void shouldReturnAddedCategory_whenAddingCategoriesAuthorized() throws Exception{
        String categoryName = "test_category";
        String jwtToken = getToken("Admin", "password");

        mvc.perform(put("/secure/category", categoryName)
                .header("Authorization", "Bearer " + jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .param("categoryName", categoryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryName));
    }
}