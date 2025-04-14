package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.Item;
import com.example.lostsandfounds.Model.Request;
import com.example.lostsandfounds.Model.Admin;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.AdminRepository;
import com.example.lostsandfounds.Repository.ItemRepository;
import com.example.lostsandfounds.Repository.RequestRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {


    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final ItemRepository itemRepository;



    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }


    public void addAdmin(Admin user){
        adminRepository.save(user);
    }


    public Boolean updateAdmin (Admin admin, Integer id)
    {
        Admin oldAdmin= adminRepository.findAdminById(id);

        if(oldAdmin!=null){
            oldAdmin.setFull_name(admin.getFull_name());
            oldAdmin.setUsername(admin.getUsername());
            oldAdmin.setPassword(admin.getPassword());
            oldAdmin.setEmail(admin.getEmail());
            oldAdmin.setPhone_number(admin.getPhone_number());

            adminRepository.save(oldAdmin);
            return true;
        }
        return false;
    }



    public Boolean deleteAdmin (Integer id){

        if(adminRepository.findAdminById(id)!=null){
            adminRepository.delete(adminRepository.findAdminById(id));
            return true;
        }
        return false;

    }




    /// check if 2 description have 2 matches keywords

    public Boolean checkDescription(String originalD, String secondD) {
        originalD = originalD.toLowerCase();
        secondD = secondD.toLowerCase();

        String[] arrayOfWords = originalD.split("\\s+");

        int matchCount = 0;

        for (String w : arrayOfWords) {

            if (secondD.contains(w)) {
                matchCount++;
            }
        }
        if (matchCount >= 2) {
            return true;
        } else return false;


    }



    /// Accepts Or reject request


    /// claimApproval >> the admin will review the claim request
    /// -first it will check if the method is called by admin
    /// then it will check the user if they are blacklisted
    /// -then it will match the item category , item type , date and location
    /// - then it will match the description if it has more than one keyword then the admin will approve the claim and set an appointment
    /// if the description is match but item is claimed  the user will receive a warning then after 3 they will be blacklisted

    public String claimApproval(Integer adminId , Integer requestId) {

        Request request= requestRepository.findRequestById(requestId);


        if (request==null){
            return"requestNF";
        }


        User user = userRepository.findUserById(request.getUserId());

        if (user == null) {
            return "userNF";
        }


        if (adminRepository.findAdminById(adminId) == null) {

            return "adminNF";
        }



        if (user.getIsBlacklisted()) {
            return "BL";
        }


        if (!request.getRequestType().equalsIgnoreCase("Claim")) {
            return "wrong type";

        }


        for (Item i : itemRepository.findAll()) {
            //check if the category and name and location and date are match
            if (i.getCategory().equalsIgnoreCase(request.getItemCategory()) && i.getItemName().equalsIgnoreCase(request.getItemName()) && i.getLocation().equalsIgnoreCase(request.getItemLocation()) && i.getTheDate().equals(request.getTheDate())) {

                if (checkDescription(i.getDescription(), request.getItemDescription())) {
                    if (!i.getIsClaimed()) {
                        /// set the request approval to true
                        request.setIsApproved(true);
                        /// set the item claimed by user id
                        i.setClaimedBy(user.getId());
                        /// change the status claimed to true
                        i.setIsClaimed(true);
                        ///  change item type to returned
                        i.setItemType("Returned");

                        // set user appointment status to true sto allow user to request appointment
                        user.setAppointment(true);

                        i.setItemType("Returned");

                        requestRepository.save(request);
                        itemRepository.save(i);
                        userRepository.save(user);
                        return "successfully";


                    }

                }
                /// if the item description not match means that the user made a false claim
                user.setFalseClaims(user.getFalseClaims() + 1);
                /// blackList the user if the false claims are larger than 3
                if (user.getFalseClaims() > 3) {
                    user.setIsBlacklisted(true);

                }
                return "claimed";

            }

        }
        return "No Match";





    }




/// donation request
///  donationApproval >> admin can view a donation request
/// first it will check if the method is called by admin
/// then it will check if user has taken a donation already (user is allowed 1 donation only )
/// based on the category and item name and if they ready for donation or not



    public String donationApproval(Integer adminId , Integer requestId) {

        Request request= requestRepository.findRequestById(requestId);
        User user = userRepository.findUserById(request.getUserId());



        if (adminRepository.findAdminById(adminId) == null) {

            return "adminNF";
        }

        if (user == null) {
            return "userNF";
        }


        if (request.getRequestType().equalsIgnoreCase("Donation")) {

            if (user.getDonationRequests() > 1) {
                return "Not eligible";
            }


            Item matchedItem = itemRepository.findItemByCategoryAndItemNameAndIsReadyForDonation(request.getItemCategory(), request.getItemName(), true);

            /// uncreased donation requests
            user.setDonationRequests(user.getDonationRequests()+1);
            /// set the request approval to true
            request.setIsApproved(true);
           ///set appointment true
            user.setAppointment(true);
            /// link the user id to the donated item
            matchedItem.setDonatedFor(user.getId());
            /// set ready for donation false
            matchedItem.setIsReadyForDonation(false);
            /// set donation = true
            matchedItem.setDonated(true);
            matchedItem.setItemType("Donated");
            itemRepository.save(matchedItem);
            requestRepository.save(request);
            userRepository.save(user);
           return "successfully";
        }

        return "wrong type";

    }



    public List<User> findUserByIsBlacklisted(){
        return  userRepository.findUserByIsBlacklisted(true);
    }


    public List<Item> findLostItemBasedOnTheLocation(String location){
        return itemRepository.findLostItemBasedOnTheLocation(location);
    }








}










