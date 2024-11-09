package com.jovora.auth.repository;

import com.jovora.auth.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findUserByEmail(String email);

    User findUserByUsername(String username);


    User findUserByEmailOrUsername(String email, String username);
}
