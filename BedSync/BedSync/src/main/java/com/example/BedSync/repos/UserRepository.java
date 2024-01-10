package com.example.BedSync.repos;

import com.example.BedSync.models.Role;
import com.example.BedSync.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
   List<User> findByRolesContains(Role role);
}
