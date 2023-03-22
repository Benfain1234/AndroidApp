package com.example.adnroidProject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.adnroidProject.model.Model;
import com.example.adnroidProject.model.Post;

import java.util.List;

public class PostListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getAllPosts();

    LiveData<List<Post>> getData(){
        return data;
    }
}
