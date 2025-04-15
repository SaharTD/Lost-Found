package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.*;
import com.example.lostsandfounds.Repository.*;
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
    private final FountItemRepository fountItemRepository;


    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }


    public void addAdmin(Admin user) {
        adminRepository.save(user);
    }


    public Boolean updateAdmin(Admin admin, Integer id) {
        Admin oldAdmin = adminRepository.findAdminById(id);

        if (oldAdmin != null) {
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


    public Boolean deleteAdmin(Integer id) {

        if (adminRepository.findAdminById(id) != null) {
            adminRepository.delete(adminRepository.findAdminById(id));
            return true;
        }
        return false;

    }


    /// the second ---check if 2 description have 3 or more matches keywords

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
        if (matchCount >= 3) {
            return true;
        } else return false;


    }


    /// handle false claim

    public void handleFalseClaims(User user) {
        /// if the item description not match means that the user made a false claim
        user.setFalseClaims(user.getFalseClaims() + 1);
        /// blackList the user if the false claims are larger than 3
        if (user.getFalseClaims() > 3) {
            user.setIsBlacklisted(true);
        }
        userRepository.save(user);
    }

    /// Accepts Or reject request


    /// claimApproval >> the admin will review the claim request
    /// -first it will check if the method is called by admin
    /// then it will check the user if they are blacklisted
    /// -then it will match the item category , item type , date and location
    /// - then it will match the description if it has more than one keyword then the admin will approve the claim and set an appointment
    /// if the description is match but item is claimed  the user will receive a warning then after 3 they will be blacklisted

    public String claimApproval(Integer adminId, Integer requestId) {

        Request request = requestRepository.findRequestById(requestId);
        Item item = itemRepository.findItemByUserId(request.getUserId());

        if (request == null) {
            return "requestNF";
        }


        User user = userRepository.findUserById(request.getUserId());

        if (user == null) {
            return "userNF";
        }


        if (adminRepository.findAdminById(adminId) == null) {

            return "adminNF";
        }



        if (!request.getRequestType().equalsIgnoreCase("Claim")){
           return "wrong type";
        }


            if (user.getIsBlacklisted()) {
            return "BL";
        }


        List<FoundItem> matchingItems = fountItemRepository.findFoundItemByItemNameAndCategoryAndTheDateAndLocation(request.getItemName(), request.getItemCategory(), request.getTheDate(), request.getItemLocation());

        for (FoundItem match : matchingItems) {
            if (checkDescription(request.getItemDescription(), match.getDescription())) {
                if (!match.getIsClaimed()) {
                    /// set the request approval to true
                    request.setIsApproved(true);
                    /// set the item claimed by user id
                    match.setReturnedTo(user.getId());
                    /// change the status claimed to true
                    match.setIsClaimed(true);
                    ///  change item type to returned
                    match.setItemStatus("Returned");

                    // set user appointment status to true sto allow user to request appointment
                    user.setAppointment(true);

                    // set user item to found
                    item.setItemStatus("Found");
                    itemRepository.save(item);


                    requestRepository.save(request);
                    fountItemRepository.save(match);
                    userRepository.save(user);
                    return "successfully";


                }
                handleFalseClaims(user);
                return "claimed";

            }
        }


        return "No Match";


    }




    public List<User> findUserByIsBlacklisted() {
        return userRepository.findUserByIsBlacklisted(true);
    }


    public List<Item> findLostItemBasedOnTheLocation(String location) {
        return itemRepository.findLostItemBasedOnTheLocation(location);
    }


    public List<FoundItem> findItemsByStatus(String status) {
        return fountItemRepository.findItemsByStatus(status);
    }



}
















