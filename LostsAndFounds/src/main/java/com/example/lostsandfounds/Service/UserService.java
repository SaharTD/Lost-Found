package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.*;
import com.example.lostsandfounds.Repository.AdminRepository;
import com.example.lostsandfounds.Repository.FountItemRepository;
import com.example.lostsandfounds.Repository.RequestRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final FountItemRepository fountItemRepository;

    private final UserRepository userRepository;
    private final RequestRepository requestRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


    public void addUser(User user){
        user.setIsBlacklisted(false);
        user.setAppointment(false);
        userRepository.save(user);
    }


    public Boolean updateUser (User user, Integer id)
    {
        User oldUser=userRepository.findUserById(id);

        if(oldUser!=null){
            oldUser.setFull_name(user.getFull_name());
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            oldUser.setEmail(user.getEmail());
            oldUser.setPhone_number(user.getPhone_number());

            userRepository.save(oldUser);
            return true;
        }
        return false;
    }



    public Boolean deleteUser (Integer id){

        if(userRepository.findUserById(id)!=null){
            userRepository.delete(userRepository.findUserById(id));
            return true;
        }
        return false;

    }






    /// checkRequestStatus>> this method will allow user to check
    /// if their requests has been approved or not
    public List<Request> checkRequestStatus(Integer userId) {
        User user = userRepository.findUserById(userId);
        List<Request> userRequest = requestRepository.findRequestByUserId(userId);
        return userRequest;
    }








    }










