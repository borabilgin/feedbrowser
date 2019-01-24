package com.demo.imagebrowser.repository;

import com.demo.imagebrowser.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}



