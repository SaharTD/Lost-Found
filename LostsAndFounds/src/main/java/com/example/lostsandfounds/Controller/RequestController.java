package com.example.lostsandfounds.Controller;

import com.example.lostsandfounds.Api.ApiResponse;
import com.example.lostsandfounds.Model.Request;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.UserRepository;
import com.example.lostsandfounds.Service.RequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/request")
@RequiredArgsConstructor
public class RequestController {


    /// 2 endpoints (request to claim and request a donation)


    private final RequestService requestService;
    private final UserRepository userRepository;


    @GetMapping("get")
    public ResponseEntity getAllRequests() {
        return ResponseEntity.status(200).body(requestService.getAllRequests());
    }


    @PutMapping("update/{requestId}/{userId}")
    public ResponseEntity updateRequest(@Valid @RequestBody Request request, Errors e, @PathVariable Integer requestId, @PathVariable Integer userId) {

        if (e.hasErrors()) {
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }


        if (requestService.updateRequest(request, requestId, userId)) {
            return ResponseEntity.status(200).body(new ApiResponse("The request is added successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The request or the user are not found"));


    }


    @DeleteMapping("delete/{id}/{adminId}")
    public ResponseEntity deleteRequest(@PathVariable Integer id, @PathVariable Integer adminId) {

        if (requestService.deleteRequest(id, adminId)) {
            return ResponseEntity.status(200).body(new ApiResponse("The request is deleted successfully"));

        }
        return ResponseEntity.status(400).body(new ApiResponse("The admin or the request are not found"));

    }


    /// 1
    /// requestToClaim >> the user will ClaimRequest to claim an item adding all the item information
    @PostMapping("request-to-claim")
    public ResponseEntity requestToClaim(@Valid @RequestBody Request claimRequest, Errors e) {
        User user = userRepository.findUserById(claimRequest.getUserId());

        if (e.hasErrors()) {
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }
        if (requestService.requestToClaim(claimRequest)) {
            return ResponseEntity.status(200).body(new ApiResponse("The request is done successfully , , please wait for the admin approval !"));
        } else return ResponseEntity.status(400).body(new ApiResponse("The user id  is not found"));


    }

    /// 2  requestADonation >> user can request a donation only once
    /// based on the category and item name


    @PostMapping("request-donation")
    public ResponseEntity requestADonation(@Valid @RequestBody Request donationRequest, Errors e) {
        User user = userRepository.findUserById(donationRequest.getUserId());

        if (e.hasErrors()) {
            return ResponseEntity.status(400).body(e.getFieldError().getDefaultMessage());
        }

        if (requestService.requestADonation(donationRequest)) {
            return ResponseEntity.status(200).body(new ApiResponse("The request is done successfully , please wait for the admin approval !"));
        } else return ResponseEntity.status(400).body(new ApiResponse("The user id  is not found"));


    }












}
