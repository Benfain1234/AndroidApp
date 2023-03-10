package com.example.adnroidProject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.adnroidProject.model.Model;
import com.example.adnroidProject.model.Post;

import java.util.List;

public class StudentsListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getAllStudents();

    LiveData<List<Post>> getData(){
        return data;
    }
}
