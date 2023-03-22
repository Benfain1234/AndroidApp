package com.example.adnroidProject.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel{
    FirebaseFirestore db;
    FirebaseStorage storage;

    public FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();

    }

    public void getAllNewPosts(Long since, Model.Listener<List<Post>> callback){

         db.collection(Post.COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
             @Override
             public void onComplete(@NonNull Task<QuerySnapshot> task) {
                 List<Post> list = new LinkedList<>();
                 if (task.isSuccessful()){
                     QuerySnapshot jsonsList = task.getResult();
                     int i = 0;
                     for (DocumentSnapshot json: jsonsList){
                         Post post = Post.fromJson(json.getData());
                         list.add(post);

                     }
                 }
                 callback.onComplete(list);
             }
        });
        db.collection(Post.COLLECTION)
                .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, new Timestamp(since,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> list = new LinkedList<>();
                if (task.isSuccessful()){
                    QuerySnapshot jsonsList = task.getResult();
                    int i = 0;
                    for (DocumentSnapshot json: jsonsList){
                        Post st = Post.fromJson(json.getData());
                        list.add(st);
                    }
                }
                callback.onComplete(list);
            }
        });
    }

    public void getAllUsers( Model.Listener<List<User>> callback){
        db.collection(User.COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<User> list = new LinkedList<>();
                if (task.isSuccessful()){
                    QuerySnapshot jsonsList = task.getResult();
                    int i = 0;
                    for (DocumentSnapshot json: jsonsList){
                        User user = User.fromJson(json.getData());
                        list.add(user);
                        Log.d("TAG", user.name );
                    }
                }
                callback.onComplete(list);
            }
        });
    }

    public void addPost(Post st, Model.Listener<Void> listener) {

        db.collection(Post.COLLECTION).document(st.getId()).set(st.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onComplete(null);
                  Log.d("TAG", " Post added : " );
            }
        });
    }
    public void addUser(User user,Model.Listener<Void> listener){
        db.collection(User.COLLECTION).document(user.getId()).set(user.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                        Log.d("TAG","User added");
                    }
                });
    }

    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });

    }

    public void updateUsetPostsList(String id, String userId) {
        db.collection(User.COLLECTION).document(userId).update("posts", FieldValue.arrayUnion(id)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("tag", "user list updated");
            }
        });

    }
}
