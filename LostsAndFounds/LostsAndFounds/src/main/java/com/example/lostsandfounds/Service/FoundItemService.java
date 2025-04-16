package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.FoundItem;
import com.example.lostsandfounds.Model.Item;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.FountItemRepository;
import com.example.lostsandfounds.Repository.ItemRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoundItemService {


    private final FountItemRepository fountItemRepository;
    private final UserRepository userRepository;
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


    public Boolean deleteFound(Integer id) {

        FoundItem item = fountItemRepository.findFoundItemById(id);
        //check if the delete is made by the admin and the item is not empty
        if ( item != null) {

            fountItemRepository.delete(item);
            return true;

        }
        return false;
    }




///-check if 2 description have 3 or more matches keywords

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



    /// checkMatchedItems> this method will allow user to check if the found item matches their own
    // request by user in user controller

    public String checkMatchedItems(Integer userId,Integer foundItemId) {


        User user = userRepository.findUserById(userId);
        FoundItem foundItem = fountItemRepository.findFoundItemById(foundItemId);
        Item lostItem =itemRepository.findItemByUserId(userId);



        if (user == null) {
            return "userNF";
        }

        // the user has item and it is lost
        if (lostItem == null  ) {
            return "item not found";

        }


        if (foundItem == null  ) {
            return "found item not found";
        }

        if (lostItem.getItemName().equalsIgnoreCase(foundItem.getItemName()) &&
                lostItem.getCategory().equalsIgnoreCase(foundItem.getCategory()) &&
                lostItem.getTheDate().equals(foundItem.getTheDate()) &&
                lostItem.getLocation().equalsIgnoreCase(foundItem.getLocation())) {

            if (checkDescription(lostItem.getDescription(), foundItem.getDescription())) {
                foundItem.setItemId(lostItem.getId());
                lostItem.setItemStatus("Found");
                fountItemRepository.save(foundItem);
                itemRepository.save(lostItem);
                userRepository.save(user);
                return "found";

            }

        }
        return "not match";

    }





}
