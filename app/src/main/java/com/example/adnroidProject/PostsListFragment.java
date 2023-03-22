package com.example.adnroidProject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.adnroidProject.databinding.FragmentPostsListBinding;
import com.example.adnroidProject.model.Model;
import com.example.adnroidProject.model.Post;
import com.example.adnroidProject.model.User;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Stack;

public class PostsListFragment extends Fragment {
    FragmentPostsListBinding binding;
    PostRecyclerAdapter adapter;
    PostListFragmentViewModel viewModel;

    ArrayList<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPostsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);

        users= new ArrayList<>();
        Model.instance().getAllUsers(list->{
            users.addAll(list);
        });

        adapter.setOnItemClickListener(new PostRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Post post = viewModel.getData().getValue().get(pos);
                User result = new User();
                Log.d("TAG", "postId "+ post.id);
                for (int i=0;i<users.size();i++){
                    if (users.get(i).postsId.contains(post.id)){
                         result =  users.get(i);
                        Log.d("tag", "contain ");
                    }

                }
                Date date =  new Date();
                Timestamp timestamp =  new Timestamp(date);
                if (result!=null){
                    timestamp =  result.lastUpdated;
                }
                if (timestamp==null){
                    PostsListFragmentDirections.ActionPostsListFragmentToBlueFragment action = PostsListFragmentDirections.actionPostsListFragmentToBlueFragment(result.name, new Timestamp(date).toDate().toString()) ;
                    Navigation.findNavController(view).navigate(action);
                }else {
                    PostsListFragmentDirections.ActionPostsListFragmentToBlueFragment action = PostsListFragmentDirections.actionPostsListFragmentToBlueFragment(result.name, timestamp.toDate().toString()) ;
                    Navigation.findNavController(view).navigate(action);
                }


            }
        });

        View addButton = view.findViewById(R.id.btnAdd);
        NavDirections action = PostsListFragmentDirections.actionGlobalAddPostFragment();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        binding.progressBar.setVisibility(View.GONE);
             class lastUpdatedComperator implements Comparator<Post>
        {
            public int compare(Post o1, Post o2)
            {
                return o2.lastUpdated.compareTo(o1.lastUpdated);
            }
        }
        viewModel.getData().observe(getViewLifecycleOwner(),list->{
             ArrayList<Post> posts =  new ArrayList<>();
            Collections.sort(list,new lastUpdatedComperator());


            adapter.setData(list);
        });

        Model.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(()->{
            reloadData();
        });



        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostListFragmentViewModel.class);
    }

    void reloadData(){

        Model.instance().refreshAllPosts();
    }
}