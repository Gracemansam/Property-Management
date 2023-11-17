package com.graceman.propertymangement.repository;
import com.graceman.propertymangement.model.User;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByOwnerEmailAndPassword(String email, String password);
    Optional<User> findByOwnerEmail(String email);
}
