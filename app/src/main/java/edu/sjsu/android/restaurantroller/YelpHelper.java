package edu.sjsu.android.restaurantroller;

import android.content.Context;
import android.content.res.Resources;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.SearchResponse;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

import static edu.sjsu.android.restaurantroller.MainActivity.QUERY_FAILED;
import static edu.sjsu.android.restaurantroller.MainActivity.QUERY_FINISHED;


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
    protected static class YelpQuery extends AsyncTask<Map<String, String>, Void, Response<SearchResponse>> {
        private Response<SearchResponse> response = null;
        private Handler handler;
        private boolean success = true;
        protected YelpQuery(Handler responseHandler){
            handler = responseHandler;
        }
        @Override
        protected Response<SearchResponse> doInBackground(Map<String, String>... maps){
            Map<String,String> params = new HashMap();

            for(Map a: maps)
                params.putAll(a);
            Call<SearchResponse> call = api.getBusinessSearch(params);
            try {
                 response = call.execute();
            } catch (SocketTimeoutException e){
                success = false;
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if(api == null)
                Log.i("api", "null");
            //Log.i("testing responses", response.toString());
            return response;
        }

        @Override
        protected void onPostExecute(Response<SearchResponse> searchResponseResponse) {
            super.onPostExecute(searchResponseResponse);
            Message m;
            if(success){
                 m = handler.obtainMessage(QUERY_FINISHED, response);
            } else
            {
                m = handler.obtainMessage(QUERY_FAILED, null);
            }
            m.sendToTarget();
        }
    }
    protected static class YelpQueryBuilder {
        private double lat = 0, lon = 0;
        private int radius = 40000;
        private int minCost = 1, maxCost = 4;
        private double rating = 0;
        private String searchTerm = null;
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
            searchTerm = term;
            return this;
        }

        protected YelpQueryBuilder setRatingMin(double min){
            rating = min;
            return this;
        }
        protected YelpQuery executeQuery(Handler responseHandler) throws ExecutionException, InterruptedException, NullPointerException {
;
            if(api == null)
                throw new NullPointerException("Api is null, instantiate a YelpHelper first");
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
                prices = prices.concat(Integer.toString(i)).concat(", ");
                i++;
            }
            prices = prices.concat(Integer.toString(i));
            params.put("price", prices);
            // Search term
            if(searchTerm != null)
                params.put("term", searchTerm);

            // Restaurants should be open
            params.put("open_now", "true");
            // Search return limit
            params.put("limit", "50");

            if(rating > 0){
                params.put("sort_by", "rating");
            }

            YelpQuery q = new YelpQuery(responseHandler);
            q.execute(params);
            return q;
        }
    }
    public static int milesToMeters(double miles){
        return (int) (miles * 1609.34);
    }

    public static double metersToMiles(double meters){
        return (meters / 1609.34);
    }
}
