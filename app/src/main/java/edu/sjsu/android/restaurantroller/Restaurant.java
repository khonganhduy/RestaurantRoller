package edu.sjsu.android.restaurantroller;

public class Restaurant {
    private String restaurantName;
    private int weight = MainActivity.MIN_WEIGHT;
    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
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
}
