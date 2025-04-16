package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.FoundItem;
import com.example.lostsandfounds.Model.Item;
import com.example.lostsandfounds.Model.Request;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {


    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final FountItemRepository fountItemRepository;
    private final ItemRepository itemRepository;


    List<FoundItem> forDonation = new ArrayList<>();



    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }



    public Boolean updateRequest(Request request, Integer requestId, Integer userId) {

        Request oldRequest = requestRepository.findRequestById(requestId);
        ///check if the request was added by the user and if the request is not null
        if (requestRepository.findRequestByUserId(userId) != null && oldRequest != null) {

            //the user is allowed to only change the item info in the request
            oldRequest.setItemName(oldRequest.getItemName());
            oldRequest.setItemCategory(oldRequest.getItemCategory());
            oldRequest.setItemDescription(oldRequest.getItemDescription());
            oldRequest.setItemLocation(oldRequest.getItemLocation());
            oldRequest.setTheDate(oldRequest.getTheDate());

            requestRepository.save(oldRequest);
            return true;


        }
        return false;
    }


    public Boolean deleteRequest(Integer id) {
// only admin can delete a request when its done
        Request request = requestRepository.findRequestById(id);
        //check if the delete is made by the admin and the request is not empty
        if ( request != null){
            requestRepository.delete(request);
            return true;

        }
        return false;
    }



    public void handleFalseClaims(User user) {
        /// if the item description not match means that the user made a false claim
        user.setFalseClaims(user.getFalseClaims() + 1);
        /// blackList the user if the false claims are larger than 3
        if (user.getFalseClaims() > 3) {
            user.setIsBlacklisted(true);
        }
        userRepository.save(user);
    }


    /// requestToClaim >> the user will ClaimRequest to claim an item adding all the item information
    public Boolean requestToClaim(Request claimRequest) {

        User user = userRepository.findUserById(claimRequest.getUserId());
        Item lostItem =itemRepository.findItemByUserId(claimRequest.getUserId());
        FoundItem foundItem= fountItemRepository.findFoundItemByUserIAndItemId(claimRequest.getUserId(),lostItem.getUserId());



        if (user == null) {
                return false;
            }

        if (lostItem == null  ) {
            return false;

        }

        if (foundItem == null  ) {
            return false;

        }

        if (!claimRequest.getRequestType().equalsIgnoreCase("Claim")) {
          return false;
        }



        if (foundItem.getIsClaimed()){
            handleFalseClaims(user);
        }

        foundItem.setItemStatus("Returned");
        foundItem.setIsClaimed(true);
        user.setAppointment(true);
        claimRequest.setIsApproved(true);


        fountItemRepository.save(foundItem);
        itemRepository.save(lostItem);
        userRepository.save(user);
        requestRepository.save(claimRequest);
        return true;

    }







    /// displayDonation> this method make donation available
    ///  if the item is unclaimed and been lost for
    /// 20 days
//called in user controller
    public List<FoundItem> displayForDonation () {

        for (FoundItem i : fountItemRepository.findAll()) {
            //check that its been 20 day since its found and that it is not claimed and not returned
            if (i.getTheDate().isBefore(LocalDate.now().minusDays(20))&& !i.getIsClaimed()&&i.getItemStatus().equals("Found")) {
                i.setIsReadyForDonation(true);
                forDonation.add(i);

            }
        }

        return forDonation;
    }





    ///  requestADonation >> user can request a donation only once
    /// based on the category and item name

    public String requestADonation(Request donationRequest) {

        User user = userRepository.findUserById(donationRequest.getUserId());

        if (user == null) {
            return "userNF";
        }

        if (user.getDonationRequests() > 1) {
            return "Not eligible";
        }



        if (!donationRequest.getRequestType().equalsIgnoreCase("Donation")) {
            return "wrong type";

        }



        FoundItem matchedItem = fountItemRepository.findFoundItemByCategoryAndItemNameAndIsReadyForDonation(donationRequest.getItemCategory(), donationRequest.getItemName(), true);

        donationRequest.setIsApproved(true);


        /// uncreased donation requests
        user.setDonationRequests(user.getDonationRequests() + 1);
        ///set appointment true
        user.setAppointment(true);


        matchedItem.setDonated(true);
        matchedItem.setItemStatus("Donated");
        matchedItem.setIsReadyForDonation(false);


        userRepository.save(user);
        fountItemRepository.save(matchedItem);
        requestRepository.save(donationRequest);
        return "successfully";




        }







}
