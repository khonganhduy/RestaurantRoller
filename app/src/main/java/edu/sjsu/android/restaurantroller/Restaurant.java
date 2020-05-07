package edu.sjsu.android.restaurantroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yelp.fusion.client.models.Business;

import java.util.TreeSet;

public class Restaurant {
    private String restaurantName;
    private int weight = MainActivity.MIN_WEIGHT;
    private TreeSet<String> tags;
    private Business businessSource = null;
    public Restaurant(String restaurantName, TreeSet<String> tagSet) {
        this.restaurantName = restaurantName;
        tags = tagSet;
    }

    public Restaurant(Business business, TreeSet<String> tagSet) {
        restaurantName = business.getName();
        businessSource = business;
        tags = tagSet;
    }

    public String getRestaurantName(){
        return restaurantName;
    }

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public TreeSet<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Restaurant r = (Restaurant) obj;
        return restaurantName.equals(r.restaurantName) && weight == r.weight && tags.equals(r.tags);
    }

    @NonNull
    @Override
    public String toString() {
        return restaurantName;
    }
}
