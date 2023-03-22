package com.example.adnroidProject.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.adnroidProject.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {
    @PrimaryKey
    @NonNull
    public String id="";
    public String caption="";
    public String title;
    public String avatarUrl="";
    public Boolean cb=false;
    public Long lastUpdated;

    public Post(){
    }

    public Post(String id,String title, String name, String avatarUrl, Boolean cb){
        this.id =  id;
        this.title = title;
        this.caption =  name;
        this.avatarUrl =  avatarUrl;
        this.cb =  cb;
    }

    static final String CAPTION = "caption";
    static final String ID = "id";
    static final String AVATAR = "avatar";
    static final String CB = "cb";
    static final String COLLECTION = "PostsList" ;
    static final String LAST_UPDATED = "lastUpdated";
    static final String TITLE = "title";
    static final String LOCAL_LAST_UPDATED = "posts_local_last_update";

    public static Post fromJson(Map<String,Object> json){
        String id = (String)json.get(ID);
        String name = (String)json.get(CAPTION);
        String avatar = (String)json.get(AVATAR);
        String title =  (String)json.get(TITLE) ;
        Boolean cb = (Boolean) json.get(CB);
        Post st = new Post(id,title,name,avatar,cb);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            st.setLastUpdated(time.getSeconds());
        }catch(Exception e){

        }
        return st;
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
        json.put(CAPTION, getName());
        json.put(AVATAR, getAvatarUrl());
        json.put(CB, getCb());
        json.put(TITLE,getTitle());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.caption = name;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setCb(Boolean cb) {
        this.cb = cb;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return caption;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Boolean getCb() {
        return cb;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }
    public String getTitle(){return title;}

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
