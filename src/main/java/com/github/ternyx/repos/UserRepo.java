package com.github.ternyx.repos;

import java.util.Optional;
import com.github.ternyx.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepo
 */
@Repository
public interface UserRepo extends CrudRepository<User, Integer>{
    Optional<User> findByUsername(String username);
}
