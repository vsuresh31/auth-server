package com.jovora.auth.repository;

import com.jovora.auth.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsername(String username);


    Optional<User> findUserByEmailOrUsername(String email, String username);
}
