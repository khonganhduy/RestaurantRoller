package edu.sjsu.android.restaurantroller;

import android.content.Context;
import android.content.res.Resources;

import android.os.AsyncTask;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.SearchResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;


public class YelpHelper {
    private static YelpFusionApi api = null;

    protected YelpHelper(Context c) throws IOException
    {
        if(api == null) {
            Resources r = c.getResources();
            String yelp_key = r.getString(R.string.yelp_key);
                api = new YelpFusionApiFactory().createAPI(yelp_key);
        }
    }
    protected class YelpQuery extends AsyncTask<Map<String, String>, Void, Response<SearchResponse>> {

        @Override
        protected Response<SearchResponse> doInBackground(Map<String, String>... maps){
            Map<String,String> params = new HashMap();
            for(Map a: maps)
                params.putAll(a);
            Call<SearchResponse> call = api.getBusinessSearch(params);
            Response<SearchResponse> response = null;
            try {
                 response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
    protected class YelpQueryBuilder {
        private double lat = 0, lon = 0;
        private int radius = 0;
        private int minCost = 1, maxCost = 4;
        private String searchTerm;

        protected YelpQueryBuilder(){}

        protected YelpQueryBuilder setLatLong(double latitude, double longitude){
            lat = latitude;
            lon = longitude;
            return this;
        }

        protected YelpQueryBuilder setRadius(int meters){
            radius = meters;
            radius = radius > 40000 ? 40000 : radius < 0 ? 0 : radius;
            return this;
        }
        protected YelpQueryBuilder setRadius(double miles){
            radius = milesToMeters(miles);
            radius = radius > 40000 ? 40000 : radius < 0 ? 0 : radius;
            return this;
        }
        protected YelpQueryBuilder setMinMaxPrice(int min, int max){
            minCost = min;
            maxCost = max;
            return this;
        }
        protected YelpQueryBuilder setSearchTerm(String term){

            return this;
        }
        protected Response<SearchResponse> executeQuery() throws ExecutionException, InterruptedException {
            Map<String, String> params = new HashMap<>();
            // Location
            params.put("latitude", Double.toString(lat));
            params.put("longitude", Double.toString(lon));
            // Search radius
            params.put("radius", Integer.toString(radius));
            // Search price
            String prices = "";
            int i = minCost;
            while(i < maxCost){
                prices.concat(Integer.toString(i)).concat(", ");
                i++;
            }
            prices.concat(Integer.toString(i));
            params.put("price", prices);

            // Restaurants should be open
            params.put("open_now", "true");
            // Search return limit
            params.put("limit", "50");

            YelpQuery q = new YelpQuery();
            q.execute(params);
            return q.get();
        }
    }
    public int milesToMeters(double miles){
        return (int) (miles * 1609.34);
    }



    protected void keyTest(){


        Map<String, String> params = new HashMap<>();

        // general params
        params.put("term", "indian food");
        params.put("latitude", "40.581140");
        params.put("longitude", "-111.914184");

        new YelpQuery().execute(params);

    }

}
