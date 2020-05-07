package edu.sjsu.android.restaurantroller;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//Query commands here
@Dao
public interface RestaurantDAO {
    @Query("SELECT * FROM restaurant")
    List<RestaurantEntity> getAllEntries();

    @Query("SELECT * FROM restaurant WHERE restaurant_name = (:name)")
    List<RestaurantEntity> getByName(String name);

    @Query("SELECT * FROM restaurant WHERE tag = (:tag)")
    List<RestaurantEntity> getByTag(String tag);

    @Query("DELETE FROM restaurant WHERE tag = (:tag)")
    void deleteAllByTag(String tag);

    @Query("DELETE FROM restaurant WHERE restaurant_name = (:name)")
    void deleteAllByRestaurantName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRestaurantEntityEntry(RestaurantEntity re);
    //Method above only adds one row to the database

    @Delete
    void deleteRestaurantEntityEntry(RestaurantEntity re);
    //Method above only removes one row from the database


}
