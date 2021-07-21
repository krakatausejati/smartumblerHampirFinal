package com.example.smarttumbler.botol;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Botol.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class BotolDatabase extends RoomDatabase {

    public abstract BotolDAO botolDAO();

    private static volatile BotolDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized BotolDatabase getInstance(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    BotolDatabase.class, "botol_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback bootleCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            Date currentDate = Calendar.getInstance().getTime();
            databaseWriteExecutor.execute(() ->{

                BotolDAO botolDAO = instance.botolDAO();
                botolDAO.deleteAllData();
                Botol botol = new Botol(currentDate,0,1500);
                botolDAO.insert(botol);

            });

        }
    };
}
