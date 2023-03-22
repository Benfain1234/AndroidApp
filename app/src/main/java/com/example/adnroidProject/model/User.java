package com.example.adnroidProject.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.adnroidProject.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.gson.JsonArray;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id="";
    public String name="";
    public String userName;

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String passWord;
    public ArrayList<String> postsId;
    public Timestamp lastUpdated;

    public User(){
    }
    public User(String id, String name,String userName,String passWord,ArrayList<String> postsId) {
        this.name = name;
        this.id = id;

        this.userName =  userName;
        this.passWord =  passWord;
        this.postsId =  new ArrayList<>();
       if (postsId!=null){
            postsId.forEach(idpost->{
               this.postsId.add(idpost.toString());
            });
        }

    }

    static final String NAME = "name";
    static final String ID = "id";
    static final String POSTS = "posts";
    static final String USERNAME = "username";
    static final String PASSWORD = "password";

 
    static final String COLLECTION = "users";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "users_local_last_update";

    public static User fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(NAME);
        ArrayList postsArray = (ArrayList) json.get(POSTS);
        String userName = (String) json.get(USERNAME);
        String passWord = (String) json.get(PASSWORD);
        User user = new User(id,name,userName,passWord,postsArray);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            user.setLastUpdated(time);
        }catch(Exception e){

        }
        return user;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(POSTS,postsId);
        json.put(USERNAME,userName);
        json.put(PASSWORD,passWord);
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

   public void addPost(String id){
        postsId.add(id);
   }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getPostsId(){
        return postsId;
    }

    

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
