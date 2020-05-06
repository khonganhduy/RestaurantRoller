package edu.sjsu.android.restaurantroller;

public class WeightedRestaurant {
    private String restaurantName, imageURL, websiteURL;
    private int weight = 0, ratingCount = 0;
    private double distance = 0, rating = 0;
    public WeightedRestaurant(String restaurantName, double rating, int ratingCount, double distance, String imageURL, String websiteURL) {
        this.restaurantName = restaurantName;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.distance = distance;
        this.imageURL = imageURL;
        this.websiteURL = websiteURL;
    }

    public String getRestaurantName(){
        return restaurantName;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public int getWeight(){
        return weight;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getRatingCount(){
        return ratingCount;
    }

    public double getDistance() {
        return distance;
    }

    public double getRating() {
        return rating;
    }

    public void setRestaurantName(String restaurantName){
        this.restaurantName = restaurantName;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }
}
