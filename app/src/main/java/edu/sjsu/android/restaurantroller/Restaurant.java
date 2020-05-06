package edu.sjsu.android.restaurantroller;

import java.util.TreeSet;

public class Restaurant {
    private String restaurantName;
    private int weight = MainActivity.MIN_WEIGHT;
    private TreeSet<String> tags;
    public Restaurant(String restaurantName, TreeSet<String> tagSet) {
        this.restaurantName = restaurantName;
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
}
