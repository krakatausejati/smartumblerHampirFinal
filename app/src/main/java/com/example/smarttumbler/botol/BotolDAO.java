package com.example.smarttumbler.botol;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;


@Dao
public interface BotolDAO {
    @Insert
    void insert(Botol botol);

    @Delete
    void delete(Botol botol);

    @Update
    void update(Botol botol);

    @Query("DELETE FROM botol")
    void deleteAllData();

    @Query("SELECT * FROM botol")
    LiveData<List<Botol>> getAllData();

}
