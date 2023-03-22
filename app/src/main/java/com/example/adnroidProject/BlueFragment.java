package com.example.adnroidProject;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.adnroidProject.databinding.FragmentPostsListBinding;
import com.example.adnroidProject.model.User;

import java.util.ArrayList;

public class BlueFragment extends Fragment {
    TextView titleTv;
    String title;
    FragmentPostsListBinding binding;
    PostRecyclerAdapter adapter;
    PostListFragmentViewModel viewModel;

    ArrayList<User> users;

    public static BlueFragment newInstance(String title){
        BlueFragment frag = new BlueFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",title);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            this.title = bundle.getString("TITLE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blue, container, false);

        title = BlueFragmentArgs.fromBundle(getArguments()).getBlueTitle();
        String date = BlueFragmentArgs.fromBundle(getArguments()).getLastTimeUserPosted();
        Log.d("TAG", "date: "+date);
        TextView titleTv = view.findViewById(R.id.bluefrag_title_tv);
        if (title != null){
            titleTv.setText(title);
        }
        TextView dateTextView =  view.findViewById(R.id.date);
        dateTextView.setText(date);


        return view;
    }

    public void setTitle(String title) {
        this.title = title;
        if (titleTv != null){
            titleTv.setText(title);
        }
    }
}