package com.example.nsamir.bakingapp.utils;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {

    private static RequestQueueSingleton sInstance;
    private RequestQueue mRequestQueue;
    private Context sContext;

    private RequestQueueSingleton(Context context) {
        this.sContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RequestQueueSingleton(context);
        }
        return sInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(sContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
