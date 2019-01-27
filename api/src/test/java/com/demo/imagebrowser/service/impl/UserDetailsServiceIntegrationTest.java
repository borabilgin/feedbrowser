package com.demo.imagebrowser.service.impl;

import com.demo.imagebrowser.domain.Role;
import com.demo.imagebrowser.domain.User;
import com.demo.imagebrowser.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class UserDetailsServiceIntegrationTest {

    private static String USERNAME = "TEST_USER";
    private static String ROLE = "ELEVATED_USER_ROLE";

    private List<Role> roles = Arrays.asList(new Role("description", ROLE));

    private User testUser = new User(USERNAME, "password", roles);

    @TestConfiguration
    static class UserDetailsServiceIntegrationTestContextConfiguration {
        @Bean
        public UserDetailsService userDetailsService() {
            return new UserDetailsService();
        }
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(testUser);
    }

    @Test
    public void shouldReturnUser_whenSearchByUsername(){
        assertNotNull("Should find user", userDetailsService.loadUserByUsername(USERNAME));
    }

    @Test
    public void shouldMatchUsername_whenSearchByUsername(){
        assertEquals("Should find by username", testUser.getUsername(), userDetailsService.loadUserByUsername(USERNAME).getUsername());
    }

    @Test
    public void shouldMatchRoles_whenSearchByUsername(){
        roles.stream().forEach(r -> assertTrue("Role should match", userDetailsService.loadUserByUsername(USERNAME).getAuthorities().stream().anyMatch(a -> ((GrantedAuthority) a).getAuthority().equals(r.getRoleName()))));
    }
}