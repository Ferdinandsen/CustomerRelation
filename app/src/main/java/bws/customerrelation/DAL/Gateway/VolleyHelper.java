package bws.customerrelation.DAL.Gateway;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bws.customerrelation.R;


/**
 * Created by Jaje on 11-Sep-15.
 */
public class VolleyHelper {
    Activity _activity;
    RequestQueue mRequestQueue;


    public VolleyHelper(Activity activity) {
        _activity = activity;
        mRequestQueue = Volley.newRequestQueue(_activity);
    }

    public void tutorial() {

        String url = "http://httpbin.org/html";

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        System.out.println(response.substring(0, 100));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });

// Add the request to the queue
        mRequestQueue.add(stringRequest);
    }

    public void tutJSONOBJ() {
        String url = "http://httpbin.org/get?site=code&network=tutsplus";

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // the response is already constructed as a JSONObject!
                        try {
                            response = response.getJSONObject("args");
                            String site = response.getString("site"),
                                    network = response.getString("network");
                            System.out.println("Site: " + site + "\nNetwork: " + network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(_activity).add(jsonRequest);
    }

//    public void tutImage() {
//        String url = "http://i.imgur.com/Nwk25LA.jpg";
//        mImageView = (ImageView) findViewById(R.id.image);
//
//        ImageRequest imgRequest = new ImageRequest(url,
//                new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        mImageView.setImageBitmap(response);
//                    }
//                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mImageView.setBackgroundColor(Color.parseColor("#ff0000"));
//                error.printStackTrace();
//            }
//        });
//
//        Volley.newRequestQueue(_activity).add(imgRequest);
//    }

    public void postVolley() {
        String url = "http://httpbin.org/post";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                            String site = jsonResponse.getString("site"),
                                    network = jsonResponse.getString("network");
                            System.out.println("Site: " + site + "\nNetwork: " + network);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // the POST parameters:
                params.put("site", "code");
                params.put("network", "tutsplus");
                return params;
            }
        };
        Volley.newRequestQueue(_activity).add(postRequest);
    }

/**
 * Cancel all req
 */
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
//            @Override
//            public boolean apply(Request<?> request) {
//                // do I have to cancel this?
//                return true; // -> always yes
//            }
//        });
//    }
}
