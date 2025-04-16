package com.example.lostsandfounds.Controller;

import com.example.lostsandfounds.Api.ApiResponse;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {









    private final UserService userService;
    private final FoundItemService founditemService;
    private final RequestService requestService;


    @GetMapping("get")
    public ResponseEntity getAllUsers (){
        return ResponseEntity.status(200).body(userService.getAllUsers()) ;
    }

    @PostMapping("add")
    public ResponseEntity addUser(@Valid @RequestBody User user, Errors e ){

        if (e.hasErrors()){
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());

        }
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("The user is added successfully"));
    }


    @PutMapping("update/{id}")
    public ResponseEntity updateUser(@Valid @RequestBody User user, Errors e, @PathVariable Integer id){

        if(e.hasErrors()){
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }


        if(userService.updateUser(user,id)){
            return ResponseEntity.status(200).body(new ApiResponse("The user is updated successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The user is not found"));


    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id){

        if(userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The user is deleted successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The user is not found"));

    }






    /// 1 checkRequestStatus >>this method will allow user to check
    ///   if their requests has been approved or not
    @GetMapping("check-request-status/{userId}")
    public ResponseEntity checkRequestStatus (@PathVariable Integer userId){

        return ResponseEntity.status(200).body(new ApiResponse("The Requests You made : :"+userService.checkRequestStatus(userId)));

    }




//2
    /// displayDonation> this method make donation available for user view
    ///   if the item is unclaimed and been lost for 20 days
    @GetMapping("display-donations")
    public ResponseEntity displayDonations (){
        return ResponseEntity.status(200).body(new ApiResponse("The item that are available for donation:"+requestService.displayForDonation()));

    }


/// 3
    @GetMapping("find-by-status/{status}")
    public ResponseEntity getLostItemByStatus(@PathVariable String status) {
        return ResponseEntity.status(200).body((status));
    }

/// 4
    @GetMapping("get-matched-item/{userId}/{foundItemId}")
    public ResponseEntity checkMatchedItems( @PathVariable Integer userId,@PathVariable Integer foundItemId) {

        String result = founditemService.checkMatchedItems(userId,foundItemId);

        if (result.equalsIgnoreCase("userNF")) {
            return ResponseEntity.status(400).body(new ApiResponse("The user is not found"));

        } else if (result.equalsIgnoreCase("not match")) {
            return ResponseEntity.status(400).body(new ApiResponse("The item information does not match"));

        } else if (result.equalsIgnoreCase("item not found")) {
            return ResponseEntity.status(400).body(new ApiResponse("The lost item is not found"));

        } else if (result.equalsIgnoreCase("found item not found")) {
            return ResponseEntity.status(400).body(new ApiResponse("the found item is not found "));

        }else
            return ResponseEntity.status(200).body("Congrats !, the found item information match yours . you can request to claim it and book an appointment ");
    }




    /// 15
    @GetMapping("find-by-location/{location}")
    public ResponseEntity getLostItemByAddress(@PathVariable String location) {
        return ResponseEntity.status(200).body(userService.findLostItemBasedOnTheLocation(location));
    }



}
