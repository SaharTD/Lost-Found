package com.example.lostsandfounds.Controller;

import com.example.lostsandfounds.Api.ApiResponse;
import com.example.lostsandfounds.Model.Reward;
import com.example.lostsandfounds.Service.RewardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reward")
@RequiredArgsConstructor
public class RewardController {


    private final RewardService rewardService;



    @GetMapping("get")
    public ResponseEntity getAllRewards(){
        return ResponseEntity.status(200).body(rewardService.getAllReward()) ;
    }


    /// 15
    @PostMapping("add")
    public ResponseEntity addReward(@Valid @RequestBody Reward reward, Errors e ){

        if (e.hasErrors()){
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());

        }
        rewardService.addReward(reward);
        return ResponseEntity.status(200).body(new ApiResponse("The reward is added successfully"));

    }


    @PutMapping("update/{id}")
    public ResponseEntity updateReward(@Valid @RequestBody Reward reward, Errors e, @PathVariable Integer id){

        if(e.hasErrors()){
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }


        if(rewardService.updateReward(reward,id)){
            return ResponseEntity.status(200).body(new ApiResponse("The Reward is added successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The Reward is not found"));


    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteReward(@PathVariable Integer id){

        if(rewardService.deleteReward(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The Reward is deleted successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The Reward is not found"));

    }






/// 16
    @PutMapping("request-reward/{userId}")
    public ResponseEntity requestReward( @PathVariable Integer userId){


        String result =  rewardService.requestReward(userId);
        if (result.equals("userNF")) {
            return ResponseEntity.status(400).body(new ApiResponse("The user id  is not found"));
        }else if (result.equals("points NE")) {
            return ResponseEntity.status(400).body(new ApiResponse("The user points are is not enough"));
        } else  return ResponseEntity.status(200).body(new ApiResponse("The reward is added successfully, enjoy!"));


    }











}
