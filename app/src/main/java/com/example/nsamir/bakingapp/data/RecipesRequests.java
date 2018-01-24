package com.example.nsamir.bakingapp.data;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.nsamir.bakingapp.utils.RequestQueueSingleton;
import org.json.JSONArray;

public class RecipesRequests {

    private static final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static void getRecipes(Context context , final ArrayVolleyResponseListener responseListener)
    {
        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                responseListener.onSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onError(error);
            }
        });

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface ArrayVolleyResponseListener {

        void onSuccess(JSONArray jsonArray);
        void onError(VolleyError volleyError);
    }
}
