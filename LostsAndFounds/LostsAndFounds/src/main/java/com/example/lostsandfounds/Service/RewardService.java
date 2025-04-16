package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.Reward;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.RewardRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RewardService {


    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;


    public List<Reward> getAllReward(){
        return rewardRepository.findAll();
    }


    public void addReward(Reward reward){
          rewardRepository.save(reward);
    }


    public String requestReward(Integer userId){
        User user = userRepository.findUserById(userId);
        if (user == null) {
            return "userNF";
        }
        if (user.getPoints() < 10) {
            return "points NE";

        }

        Random r = new Random();
        int random_id = r.nextInt(3) + 1;
        Reward reward = rewardRepository.findRewardById(random_id);
        reward.setUserId(userId);
        rewardRepository.save(reward);
        return "done";

    }


    public Boolean updateReward (Reward reward, Integer id)
    {
        Reward oldReward= rewardRepository.findRewardById(id);

        if(oldReward!=null){
            oldReward.setRandomReward(reward.getRandomReward());

            rewardRepository.save(oldReward);
            return true;
        }
        return false;
    }



    public Boolean deleteReward (Integer id){

        if(rewardRepository.findRewardById(id)!=null){
            rewardRepository.delete(rewardRepository.findRewardById(id));
            return true;
        }
        return false;

    }







    }










