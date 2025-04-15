package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.FoundItem;
import com.example.lostsandfounds.Model.Item;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.AdminRepository;
import com.example.lostsandfounds.Repository.FountItemRepository;
import com.example.lostsandfounds.Repository.ItemRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoundItemService {


    private final FountItemRepository fountItemRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AdminService adminService;
    private final ItemRepository itemRepository;





    public List<FoundItem> getAllFoundItems() {
        return fountItemRepository.findAll();
    }


    public Boolean addFound(FoundItem item) {

        User user = userRepository.findUserById(item.getUserId());
        if (user == null) {
            return false;
        }

        user.setPoints(user.getPoints() +5);
        item.setIsClaimed(false);
        item.setDonated(false);
        item.setIsReadyForDonation(false);
        fountItemRepository.save(item);
        return true;
    }


    public Boolean updateFound(FoundItem item, Integer itemId, Integer userId) {

        FoundItem oldItem = fountItemRepository.findFoundItemById(itemId);
        ///check if the item was added by the user and if the item is not null
        /// the item type can not be changed only the info
        if (fountItemRepository.findFoundItemById(userId) != null && oldItem != null) {

            oldItem.setItemName(item.getItemName());
            oldItem.setCategory(item.getCategory());
            oldItem.setDescription(item.getDescription());
            oldItem.setLocation(item.getLocation());
            oldItem.setTheDate(item.getTheDate());


            fountItemRepository.save(oldItem);
            return true;


        }
        return false;
    }


    public Boolean deleteFound(Integer id, Integer adminId) {

        FoundItem item = fountItemRepository.findFoundItemById(id);
        //check if the delete is made by the admin and the item is not empty
        if (adminRepository.findAdminById(adminId) != null && item != null) {

            fountItemRepository.delete(item);
            return true;

        }
        return false;
    }




    /// displayDonation> this method make donation available
    ///  if the item is unclaimed and been lost for
    /// 20 days
//called in user controller
    public List<FoundItem> displayForDonation () {

        List<FoundItem> forDonation = new ArrayList<>();

        for (FoundItem i : getAllFoundItems()) {
            //check that its been 20 day since its found and that it is not claimed and not returned
            if (i.getTheDate().isBefore(LocalDate.now().minusDays(20))&& !i.getIsClaimed()&&i.getItemStatus().equals("Found")) {
                i.setIsReadyForDonation(true);
                forDonation.add(i);

            }
        }

        return forDonation;
    }

    ///the first-- check if 2 description have 2  matches keywords

    public Boolean firstCheckDescription(String originalD, String secondD) {

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


    /// checkMatchedItems> this method will allow user to check if there is an item has been uploaded
    /// that might match the user lost item
    /// request by user in user controller
    public String checkMatchedItems(Integer userId) {

        User user = userRepository.findUserById(userId);
        Item lostItem = itemRepository.findItemByUserId(userId);

        if (user == null) {
            return "userNF";
        }

        // the user has item and it is lost
        if (lostItem == null  ) {
            return "item not found";

        }



        //check if any of the founded item match the user lost item
        List<FoundItem> matchingItems =
                fountItemRepository.findFoundItemByItemNameAndCategoryAndTheDateAndLocation(
                               lostItem.getItemName(),
                                lostItem.getCategory(),
                                lostItem.getTheDate(),
                                lostItem.getLocation());

        if (matchingItems.isEmpty()){
            return "not match";

        }

        for (FoundItem match :matchingItems) {
            if (firstCheckDescription(lostItem.getDescription(), match.getDescription())) {
                return "found";
            }
        }

        return "not match";


    }






}
