package com.example.adnroidProject;

import static android.content.ContentValues.TAG;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.adnroidProject.databinding.FragmentFirstBinding;
import com.example.adnroidProject.model.Model;
import com.example.adnroidProject.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    ArrayList<User> users;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState


    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        users= new ArrayList<>();
         Model.instance().getAllUsers(list->{
             users.addAll(list);
         });
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                Log.d(TAG, "users size "+users.size());
            }
        });
    binding.button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String userName =  binding.editTextTextPersonName.getText().toString();
            String passWord =  binding.editTextTextPassword.getText().toString();

            users.forEach(user -> {
                if (user.userName.equals(userName) && user.passWord.equals(passWord)){
                    binding.errorWindow.setText("Correct details");
                    LoginToTheSystem(user);
                }
            });
        if (!binding.errorWindow.getText().toString().equals("Correct details"))
                 binding.errorWindow.setText("incorrect details");

        }
    });

    }

    private void LoginToTheSystem(User user) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}