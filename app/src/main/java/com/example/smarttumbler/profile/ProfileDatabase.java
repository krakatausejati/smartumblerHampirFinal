
package com.example.smarttumbler.profile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Profile.class}, version = 1, exportSchema = false)
public abstract class ProfileDatabase extends RoomDatabase {

    public abstract ProfileDAO profileDAO();

    private static volatile ProfileDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized ProfileDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProfileDatabase.class, "profile_database")
                    .addCallback(profileCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


    private static RoomDatabase.Callback profileCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


            databaseWriteExecutor.execute(() ->{

                ProfileDAO profileDAO = instance.profileDAO();
                profileDAO.deleteAllData();
                Profile profile = new Profile("Yordy",20,180,75,"Pria",2000);
                profileDAO.insert(profile);

            });

        }
    };
}
