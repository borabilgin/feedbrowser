package com.demo.imagebrowser.repository;

import com.demo.imagebrowser.domain.Role;
import com.demo.imagebrowser.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnUser_whenSearchByUsername() {
        final String expectedUsername = "bora";

        User user = new User(expectedUsername, "abc", Collections.emptyList());
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByUsername(expectedUsername);

        assertEquals("Found user's name should be equal to inserted user's name", found.getUsername(), expectedUsername);
    }

    @Test
    public void shouldIncludeRole_whenSearchByUsername() {
        final String expectedUsername = "bora";
        Role role = new Role();
        role.setRoleName("user");
        role.setDescription("test role");
        entityManager.persist(role);
        entityManager.flush();

        User user = new User(expectedUsername, "abc", Arrays.asList(role));
        entityManager.persist(user);
        entityManager.flush();

        User found = userRepository.findByUsername(expectedUsername);

        assertTrue("Role size should be 1", found.getRoles().size() == 1);
        assertEquals("Found user's role should be equal to inserted user's role", found.getRoles().get(0).getRoleName(), role.getRoleName());
    }
}