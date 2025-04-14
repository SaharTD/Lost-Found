package com.example.lostsandfounds.Repository;

import com.example.lostsandfounds.Model.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RewardRepository extends JpaRepository<Reward,Integer> {
    Reward findRewardById(Integer id);








}
