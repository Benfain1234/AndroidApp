package com.example.adnroidProject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.adnroidProject.databinding.FragmentSecondBinding;
import com.example.adnroidProject.model.FirebaseModel;
import com.example.adnroidProject.model.Model;
import com.example.adnroidProject.model.Post;
import com.example.adnroidProject.model.User;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SecondFragment extends Fragment {
    private FirebaseModel firebaseModel = new FirebaseModel();
    private Executor executor = Executors.newSingleThreadExecutor();

    private FragmentSecondBinding binding;
    ArrayList<User> users;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        users= new ArrayList<>();
         Model.instance().getAllUsers(list->{
            users.addAll(list);
        });
         binding.button2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 while (users.size()==0){
                     try {
                         wait(1000);
                     } catch (InterruptedException e) {
                         throw new RuntimeException(e);
                     }
                 }
                 boolean valid = true;
              String username= binding.editTextTextPersonName2.getText().toString();
              if (username.length()<8)
              {
                  binding.textView2.setText("need longer username");

               return;
              }

              String passWord =  binding.editTextTextPersonName3.getText().toString();
              if (passWord.length()<8){
                  binding.textView2.setText("need longer password");
                 return;
              }

                  users.forEach(user -> {
                     Log.d(TAG, "input username: "+username);
                     Log.d(TAG, "user username: "+user.userName);
                     if (user.userName.equals(username) ||user.passWord.equals(passWord)){
                         binding.textView2.setText("UserName/Password exists");

                     }
                 });
                 String name =  binding.editTextTextPersonName4.getText().toString();
                 if (!binding.textView2.getText().equals("UserName/Password exists")){
                     if (name.length()==0){
                         binding.textView2.setText("name input is empty");
                         return;
                     }
                 }else {
                      valid =  false;
                 }



                 if (valid==true){
                     addUser(view, username, passWord, name);
                 }


             }
         });
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    private void addUser(View view, String username, String passWord, String name) {
        int b = (int)(Math.random()*(100000-1+1)+1);
        String id =  String.valueOf(b);
        ArrayList<String> posts =  new ArrayList<>();
        User user =  new User(id, name, username, passWord,posts);
        Model.instance().addUser(user, (unused) -> {
            Navigation.findNavController(view).popBackStack();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}