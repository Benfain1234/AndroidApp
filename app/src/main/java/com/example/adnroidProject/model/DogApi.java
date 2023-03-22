package com.example.adnroidProject.model;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adnroidProject.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
public class DogApi {
    public void dogsApi(){

        String url = "https://dog.ceo/api/breeds/list/all";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                (Response.Listener<JSONObject>) response -> {

                    String dogImageUrl;
                    try {
                        dogImageUrl = response.getString("message");
                        Log.d("tag", "api respinse: "+dogImageUrl);
                        // load the image into the ImageView using Glide.
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },

                // lambda function for handling the case
                // when the HTTP request fails
                (Response.ErrorListener) error -> {
                    // make a Toast telling the user
                    // that something went wrong

                    // log the error message in the error stream
                    Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}");
                }
        );
    }



}
