package com.example.lostsandfounds.Repository;

import com.example.lostsandfounds.Model.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);


    @Query("select u from User u where u.points>?1")
    List<User> findUserByPoints(Integer points);






}
