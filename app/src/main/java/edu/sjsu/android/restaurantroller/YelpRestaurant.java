package edu.sjsu.android.restaurantroller;

import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.Category;

import java.util.TreeSet;

public class YelpRestaurant extends Restaurant{
    private String imageURL, websiteURL;
    private int ratingCount = 0;
    private double distance = 0, rating = 0;
    public YelpRestaurant(String restaurantName, double rating, int ratingCount, double distance, String imageURL, String websiteURL, TreeSet<String> tagSet) {
        super(restaurantName, tagSet);
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.distance = distance;
        this.imageURL = imageURL;
        this.websiteURL = websiteURL;
    }

    public String getRestaurantName(){
        return super.getRestaurantName();
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public int getWeight(){
        return super.getWeight();
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
        setRestaurantName(restaurantName);
    }

    public void setWeight(int weight){
        super.setWeight(weight);
    }
}
