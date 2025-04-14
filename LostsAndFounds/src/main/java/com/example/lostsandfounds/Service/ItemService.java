package com.example.lostsandfounds.Service;


import com.example.lostsandfounds.Model.Item;
import com.example.lostsandfounds.Model.User;
import com.example.lostsandfounds.Repository.AdminRepository;
import com.example.lostsandfounds.Repository.ItemRepository;
import com.example.lostsandfounds.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {


    private final ItemRepository itemRepository;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final AdminService adminService;


    List<Item> foundedItems = new ArrayList<>();
    List<Item> unClaimedItem = new ArrayList<>();



    public List<Item> getFoundedItems(){

        for (Item i :getAllItems()){
            if (i.getItemType().equalsIgnoreCase("Found")){
                foundedItems.add(i);
            }
        }

        return foundedItems;

    }


    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }


    public Boolean addItem(Item item) {

        User user = userRepository.findUserById(item.getUserId());
        user.setPoints(user.getPoints());
        if (user == null) {
            return false;
        }

        if (item.getItemType().equalsIgnoreCase("found")) {
            user.setPoints(0);
            user.setPoints(user.getPoints() + 5);

        }

        item.setIsClaimed(false);
        item.setDonated(false);
        item.setIsReadyForDonation(false);
        itemRepository.save(item);
        return true;
    }


    public Boolean updateItem(Item item, Integer itemId, Integer userId) {

        Item oldItem = itemRepository.findItemById(itemId);
        ///check if the item was added by the user and if the item is not null
        /// the item type can not be changed only the info
        if (itemRepository.findItemByUserId(userId) != null && oldItem != null) {

            oldItem.setItemName(item.getItemName());
            oldItem.setCategory(item.getCategory());
            oldItem.setDescription(item.getDescription());
            oldItem.setLocation(item.getLocation());
            oldItem.setTheDate(item.getTheDate());


            itemRepository.save(oldItem);
            return true;


        }
        return false;
    }


    public Boolean deleteItem(Integer id, Integer adminId) {

        Item item = itemRepository.findItemById(id);
        //check if the delete is made by the admin and the item is not empty
        if (adminRepository.findAdminById(adminId) != null && item != null) {

            itemRepository.delete(item);
            return true;

        }
        return false;
    }


    ///  checkUnclaimedItem > this method will allow admin to check the items that been found but
    /// not claimed
    public List<Item> checkUnclaimedItem(Integer adminId) {

        if (adminRepository.findAdminById(adminId) != null) {
            for (Item i : getAllItems()) {
                if (!i.getIsClaimed() && !i.getItemType().equals("Returned")) {
                    unClaimedItem.add(i);
                }
            }
        }

        return unClaimedItem;

    }


    /// displayDonation> this method make donation available
    ///  if the item is unclaimed and been lost for
    /// 20 days

    public List<Item> displayDonations() {

        List<Item> forDonation = new ArrayList<>();

        for (Item i : unClaimedItem) {
            if (i.getTheDate().isBefore(LocalDate.now().minusDays(20))) {
                i.setIsReadyForDonation(true);
                forDonation.add(i);

            }
        }

        return forDonation;
    }


    /// checkMatchedItems> this method will allow user to check if there is an item has been uploaded
    /// that matches the user lost item
    public String checkMatchedItems( Integer userId) {

        User user = userRepository.findUserById(userId);
        Item lostItem = itemRepository.findLostItemByUserId("Lost",userId);

        if (user == null) {
            return "userNF";
        }

        // the user has item and it is lost
        if (lostItem == null  ) {
            return "item not found";

        }

//check if any of the founded item match the user lost item
        for  (Item i :getFoundedItems()) {
            List<Item> matchingItems = itemRepository.findItemByItemNameAndCategoryAndTheDateAndLocation(lostItem.getItemName(), lostItem.getCategory(), lostItem.getTheDate(), lostItem.getLocation());

            for  (Item match :matchingItems) {
                if (adminService.checkDescription(lostItem.getDescription(), match.getDescription())) {
                    return "found";
                }
            }
        }
            return "not match";


    }











}
