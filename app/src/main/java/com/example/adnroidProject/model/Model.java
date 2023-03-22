package com.example.adnroidProject.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }

    public void updateUserPostsList(String id,String userId) {
        firebaseModel.updateUsetPostsList(id,userId);
    }

    public interface Listener<T>{
        void onComplete(T data);
    }


    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
    final public MutableLiveData<LoadingState> EventPostsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);


    private LiveData<List<Post>> postList;
    private LiveData<List<User>> users;
    public LiveData<List<Post>> getAllPosts() {
        if(postList == null){
            postList = localDb.postDao().getAll();
            refreshAllPosts();
        }
        return postList;
    }


    public ArrayList<User> getAllUsers(Model.Listener<List<User>> callback) {
      ArrayList<User> userslist =  new ArrayList<>();
        firebaseModel.getAllUsers(list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                for(User user:list){
                    // insert new records into ROOM
                 userslist.add(user);

                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               callback.onComplete(userslist);

            });
        });

        return userslist;
    }



    public void refreshAllPosts(){
        EventPostsListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllNewPosts(localLastUpdate, list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Post st:list){
                    // insert new records into ROOM
                    localDb.postDao().insertAll(st);
                    if (time < st.getLastUpdated()){
                        time = st.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Post.setLocalLastUpdate(time);
                EventPostsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addPost(Post st, Listener<Void> listener){
        firebaseModel.addPost(st,(Void)->{
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void addUser(User user, Listener<Void> listener){
        firebaseModel.addUser(user,(Void)->{
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap,Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }

}
