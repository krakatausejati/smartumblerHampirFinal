package com.example.smarttumbler.profile;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface ProfileDAO {

    @Insert
    void insert(Profile profile);

    @Delete
    void delete(Profile profile);

    @Update
    void update(Profile profile);

    @Query("DELETE FROM data_profile")
    void deleteAllData();

    @Query("SELECT * FROM data_profile")
    LiveData<List<Profile>> getAllData();


}
