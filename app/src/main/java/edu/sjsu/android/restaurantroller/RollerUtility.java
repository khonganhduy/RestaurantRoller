package edu.sjsu.android.restaurantroller;

import java.util.Random;
import java.util.TreeMap;

public class RollerUtility {
    private static Random rand = null;

    public RollerUtility(){
        if(rand == null)
            rand = new Random();
    }

    public int rollWeighted(Integer[] weights){

        int weightRange = 0;
        TreeMap<Integer, Integer> weightmap = new TreeMap<>();

        for(int i = 0; i < weights.length; i++){
            // First key is (0,0)
            // Next key is (weight0, 1)
            // Third key is (weight0 + weight 1, 2), etc...
            weightmap.put(weightRange, i);
            weightRange += weights[i];
        }

        // Roll is 0 to (weightRange -1)
        int roll = rand.nextInt(weightRange);
        // Obtain the original item associated with the rolled weight
        // A roll from 0 to (weight0 - 1) returns 0
        // A roll from weight0 to (weight0 + weight1 - 1) returns 1
        return weightmap.get(weightmap.floorKey(roll));
    }
}
