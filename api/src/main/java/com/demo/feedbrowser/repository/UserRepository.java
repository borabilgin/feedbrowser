package com.demo.feedbrowser.repository;

import com.demo.feedbrowser.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}



