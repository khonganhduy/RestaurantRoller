package edu.sjsu.android.restaurantroller;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

//Query commands here
public interface RestaurantDAO {
    @Query("SELECT * FROM restaurant WHERE restaurant_name = (:name)")
    ArrayList<RestaurantEntity> getByName(String name);

    @Query("SELECT * FROM restaurant WHERE tag = (:tag)")
    ArrayList<RestaurantEntity> getByTag(String tag);

    @Query("DELETE FROM restaurant WHERE tag = (:tag)")
    void deleteAllByTag(String tag);

    @Query("DELETE FROM restaurant WHERE restaurant_name = (:name)")
    void deleteAllByRestaurantName(String name);

    @Insert
    void insertRestaurantEntityEntry(RestaurantEntity re);
    //Method above only adds one row to the database

    @Delete
    void deleteRestaurantEntityEntry(RestaurantEntity re);
    //Method above only removes one row from the database


}
