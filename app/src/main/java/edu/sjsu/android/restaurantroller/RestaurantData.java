package edu.sjsu.android.restaurantroller;

import android.app.Application;

import java.util.List;

public class RestaurantData {
    private RestaurantDAO dao;

    RestaurantData(Application app){
        RestaurantDatabase database = RestaurantDatabase.getDatabase(app);
        dao = database.restaurantDAO();
    }

    void insert(final RestaurantEntity re){
        RestaurantDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertRestaurantEntityEntry(re);
            }
        });
    }

    void delete(final RestaurantEntity re){
        RestaurantDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteRestaurantEntityEntry(re);
            }
        });
    }

    List<RestaurantEntity> getAll(){
        return dao.getAllEntries();
    }

    List<RestaurantEntity> getAllByName(final String name){
        return dao.getByName(name);
    }

    List<RestaurantEntity> getAllByTag(final String tag){
        return dao.getByTag(tag);
    }

    void deleteAllByTag(final String tag){
        RestaurantDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAllByTag(tag);
            }
        });
    }

    void deleteAllByName(final String name){
        RestaurantDatabase.databaseWriteExecutor.execute(new Runnable(){
            @Override
            public void run(){
                dao.deleteAllByRestaurantName(name);
            }
        });
    }
}
