package com.example.adnroidProject.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.adnroidProject.MyApplication;

@Database(entities = {Post.class}, version = 81)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao studentDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}

