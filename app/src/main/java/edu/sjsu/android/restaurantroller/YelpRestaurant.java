package edu.sjsu.android.restaurantroller;

import androidx.annotation.Nullable;

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

    public YelpRestaurant(Business b, TreeSet<String> tagSet){
        super(b, tagSet);
        rating = b.getRating();
        ratingCount = b.getReviewCount();
        distance = b.getDistance();
        imageURL = b.getImageUrl().replaceAll("o\\.jpg", "ms.jpg");
        websiteURL = b.getUrl();
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

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof YelpRestaurant))
            return false;
        YelpRestaurant r = (YelpRestaurant) obj;
        return super.equals(obj) && rating == r.rating && ratingCount == r.ratingCount && distance == r.distance && imageURL.equals(r.imageURL) && websiteURL.equals(r.websiteURL);
    }
}
