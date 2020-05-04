package edu.sjsu.android.restaurantroller;

public class WeightedRestaurant {
    private String restaurantName;
    private int weight;

    public WeightedRestaurant(String restaurantName){
        this.restaurantName = restaurantName;
        this.weight = 0;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public int getWeight() {
        return weight;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
