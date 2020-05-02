package edu.sjsu.android.restaurantroller;

import android.content.Context;
import android.content.res.Resources;

import android.util.Log;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.SearchResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


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
    private void keyTest(){


        Map<String, String> params = new HashMap<>();
        // general params
        params.put("term", "food");
        params.put("limit", "3");

// locale params
        params.put("lang", "fr");

        Call<SearchResponse> call = api.getBusinessSearch(params);
        Log.i("Testing", call.toString());
    }
}
