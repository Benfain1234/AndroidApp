package com.example.adnroidProject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adnroidProject.databinding.FragmentAboutBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class AboutFragment extends Fragment {
    FragmentAboutBinding binding;
    ImageView randomDogImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        binding.progressBarAPI.setVisibility(View.INVISIBLE);
        randomDogImage =  binding.getRoot().findViewById(R.id.dogimageView);
        randomDogApi();
        Button  pugButton =  binding.pugImage;
        pugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("pug");
            }
        });
        Button huskyButton  =  binding.huskyImage;
        huskyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("husky");
            }
        });

        Button rottweilerButton  =  binding.rottweilerImage;
        rottweilerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("rottweiler");
            }
        });
        Button pitbullButton =  binding.pitbullImage;
        pitbullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("pitbull");
            }
        });

        Button boxerButton =  binding.boxerButton;
        boxerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("boxer");
            }
        });

        Button dalmatianButton =  binding.dalmatianButton;
        dalmatianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("dalmatian");
            }
        });

        Button malteseButton =  binding.malteseButton;
        malteseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("maltese");
            }
        });

        Button vizslaButton =  binding.vizslaButton;
        vizslaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("vizsla");
            }
        });

        Button labradorButton =  binding.labradorButton;
        labradorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("labrador");
            }
        });

        Button dingoButton =  binding.dingoButton;
        dingoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDogApi("dingo");
            }
        });

        return binding.getRoot();
    }

    public void randomDogApi(){
        binding.progressBarAPI.setVisibility(View.VISIBLE);
        RequestQueue volleyQueue = Volley.newRequestQueue(AboutFragment.this.getContext());
        String url = "https://dog.ceo/api/breeds/image/random";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                (Response.Listener<JSONObject>) response -> {

                    String dogImageUrl;
                    try {
                        dogImageUrl = response.getString("message");
                        Log.d("tag", "api respinse: "+dogImageUrl);
                        Picasso.get().load(dogImageUrl).into(randomDogImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                binding.progressBarAPI.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onError(Exception e) {
                                binding.progressBarAPI.setVisibility(View.INVISIBLE);
                            }
                        });
                    }  catch (JSONException e) {
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
        volleyQueue.add(jsonObjectRequest);
    }

   public void selectDogApi(String name){
       binding.progressBarAPI.setVisibility(View.VISIBLE);
       RequestQueue volleyQueue = Volley.newRequestQueue(AboutFragment.this.getContext());
       String url = "https://dog.ceo/api/breed/" + name + "/images/random";
       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
               url,
               null,
               (Response.Listener<JSONObject>) response -> {

                   String dogImageUrl;
                   try {
                       dogImageUrl = response.getString("message");
                       Log.d("tag", "api respinse: "+dogImageUrl);
                       Picasso.get().load(dogImageUrl).into(randomDogImage, new Callback() {
                           @Override
                           public void onSuccess() {
                               binding.progressBarAPI.setVisibility(View.INVISIBLE);
                           }

                           @Override
                           public void onError(Exception e) {
                               binding.progressBarAPI.setVisibility(View.INVISIBLE);
                           }
                       });
                   }  catch (JSONException e) {
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
       volleyQueue.add(jsonObjectRequest);
   }

}

