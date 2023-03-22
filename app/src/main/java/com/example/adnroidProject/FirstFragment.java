package com.example.adnroidProject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.adnroidProject.databinding.FragmentFirstBinding;
import com.example.adnroidProject.model.Model;
import com.example.adnroidProject.model.User;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<User> users;
    SharedPreferences sharedPref;
    static final String LOCAL_SAVED_USERNAME = "local_username";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getMyContext());
        if(sharedPref.getString(LOCAL_SAVED_USERNAME,"").length() == 0){
            Log.d("TAG","The user: " + sharedPref.getString(LOCAL_SAVED_USERNAME,"") + " doesn't exists - move to login/register");

        }
        else {
            Log.d("TAG","The user: " + sharedPref.getString(LOCAL_SAVED_USERNAME,"") + " exists - move to app");
            LoginToTheSystem(sharedPref.getString(LOCAL_SAVED_USERNAME,"") );
        }

/*        user.userName.equals(userName)
         //= MyApplication.getMyContext().getDefaultSharedPreferences()
        //getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();*/
/*
        Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
 */
        users= new ArrayList<>();
         Model.instance().getAllUsers(list->{
             users.addAll(list);
         });

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                Log.d("TAG", "users size "+users.size());
            }
        });
    binding.button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userName =  binding.editTextTextPersonName.getText().toString();
            String passWord =  binding.editTextTextPassword.getText().toString();

            if (userName.length() == 0 || passWord.length() == 0)
            {
                binding.errorWindow.setText("Please fill username and password!");
                return;
            }
            users.forEach(user -> {
                if (user.userName.equals(userName) && user.passWord.equals(passWord)){
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    binding.errorWindow.setText("Correct details");
                    Log.d("TAG","Connected as: " + user);
                    Log.d("TAG","The user is: " + user);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(LOCAL_SAVED_USERNAME,user.id);
                    editor.commit();
                    LoginToTheSystem(user.id);
                }
            });
        if (!binding.errorWindow.getText().toString().equals("Correct details")) {
            binding.errorWindow.setText("incorrect details");
            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
        }

        }
    });

    }

    private void LoginToTheSystem(String userid) {
        Activity current = getActivity();
        Intent intent = new Intent(current, MainActivity.class);
         intent.putExtra("username",userid);
        startActivity(intent);
        current.finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}