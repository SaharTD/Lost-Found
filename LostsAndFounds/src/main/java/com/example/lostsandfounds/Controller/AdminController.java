package com.example.lostsandfounds.Controller;

import com.example.lostsandfounds.Api.ApiResponse;
import com.example.lostsandfounds.Model.Admin;
import com.example.lostsandfounds.Service.AdminService;
import com.example.lostsandfounds.Service.ItemService;
import com.example.lostsandfounds.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

private final AdminService adminService;
    private final UserService userService;
    private final ItemService itemService;

///  3 endpoints  (unclaimed item,claimApproval,donation Approval)

    @GetMapping("get")
    public ResponseEntity getAllAdmins (){
        return ResponseEntity.status(200).body(adminService.getAllAdmins()) ;
    }

    @PostMapping("add")
    public ResponseEntity addAdmins(@Valid @RequestBody Admin admin, Errors e ){

        if (e.hasErrors()){
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());

        }
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body(new ApiResponse("The admin is added successfully"));
    }


    @PutMapping("update/{id}")
    public ResponseEntity updateAdmins(@Valid @RequestBody Admin admin, Errors e, @PathVariable Integer id){

        if(e.hasErrors()){
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }


        if(adminService.updateAdmin(admin,id)){
            return ResponseEntity.status(200).body(new ApiResponse("The admin is added successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The admin is not found"));


    }


    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteAdmins(@PathVariable Integer id){

        if(adminService.deleteAdmin(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The admin is deleted successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The admin is not found"));

    }








    /// 3
    ///  checkUnclaimedItem > this method will allow admin to check the items that been found but not claimed
    @GetMapping("get-unclaimed/{adminId}")
    public ResponseEntity checkUnclaimedItem (@PathVariable Integer adminId){
        if(itemService.checkUnclaimedItem(adminId).isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("there are no item that hasn't been claimed"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("The item that are not claimed yet"+itemService.checkUnclaimedItem(adminId)));

    }



    /// 9


    @PutMapping("claim-approval/{adminId}/{requestId}")
    public ResponseEntity claimApproval(@PathVariable Integer adminId, @PathVariable Integer requestId){

String result = adminService.claimApproval(adminId,requestId);


        if (result.equalsIgnoreCase("adminNF")) {
            return ResponseEntity.status(400).body(new ApiResponse("The admin is not found"));

        }else if (result.equalsIgnoreCase("requestNF")) {

            return ResponseEntity.status(400).body(new ApiResponse("The request is not found"));

        } else if (result.equalsIgnoreCase("userNF")) {

            return ResponseEntity.status(400).body(new ApiResponse("The user is not found"));

        }else if (result.equalsIgnoreCase("BL")) {

            return ResponseEntity.status(400).body(new ApiResponse("The user is blacklisted"));

        } else if (result.equalsIgnoreCase("No Match")) {

            return ResponseEntity.status(400).body(new ApiResponse("no match is found"));

        } else if (result.equalsIgnoreCase("claimed")) {

            return ResponseEntity.status(400).body(new ApiResponse("Warning the item is already been claimed , you false claim count is raised by 1 . note: if the false claim count reaches 3 you will be blackListed"));

        } else if (result.equalsIgnoreCase("wrong type")) {

            return ResponseEntity.status(400).body(new ApiResponse("you are requesting the wrong type"));
        }else return ResponseEntity.status(200).body(new ApiResponse("TThe request is approved successfully !. you can book an appointment now "));


    }




    /// 10


    @PutMapping("donation-approval/{adminId}/{requestId}")
    public ResponseEntity donationApproval(@PathVariable Integer adminId, @PathVariable Integer requestId){

        String result = adminService.donationApproval(adminId,requestId);


        if (result.equalsIgnoreCase("adminNF")) {
            return ResponseEntity.status(400).body(new ApiResponse("The admin is not found"));

        } else if (result.equalsIgnoreCase("requestNF")) {

            return ResponseEntity.status(400).body(new ApiResponse("The request is not found"));

        }else if (result.equalsIgnoreCase("userNF")) {

            return ResponseEntity.status(400).body(new ApiResponse("The user is not found"));

        } else if (result.equalsIgnoreCase("Not eligible")) {

            return ResponseEntity.status(400).body(new ApiResponse("The user is not eligible fot donation "));

        } else if (result.equalsIgnoreCase("wrong type")) {

            return ResponseEntity.status(400).body(new ApiResponse("you are requesting the wrong type"));


        } else return ResponseEntity.status(200).body(new ApiResponse("The request is approved successfully !. you can book an appointment now "));


    }



    /// 12
    @GetMapping("find-black-listed")
    public ResponseEntity getAllBlackListed() {
        return ResponseEntity.status(200).body(adminService.findUserByIsBlacklisted());
    }


    /// 13
    @GetMapping("find-by-location/{location}")
    public ResponseEntity getLostItemByAddress(@PathVariable String location) {
        return ResponseEntity.status(200).body(adminService.findLostItemBasedOnTheLocation(location));
    }










}
