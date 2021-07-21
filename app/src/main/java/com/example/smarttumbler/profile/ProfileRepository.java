package com.example.smarttumbler.profile;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ProfileRepository {
    private ProfileDAO profileDAO;
    private LiveData<List<Profile>> allProfile;

    ProfileRepository(Application application){
        ProfileDatabase database = ProfileDatabase.getInstance(application);
        profileDAO = database.profileDAO();
        allProfile = profileDAO.getAllData();
    }

    LiveData<List<Profile>> getAllProfile() {
        return allProfile;
    }

    void insert(Profile profile){
        ProfileDatabase.databaseWriteExecutor.execute(() -> {
            profileDAO.insert(profile);
        });
    }
    void update(Profile profile){
        ProfileDatabase.databaseWriteExecutor.execute(() ->{
            profileDAO.update(profile);
        });
    }
    void delete(Profile profile){
        ProfileDatabase.databaseWriteExecutor.execute(() ->{
            profileDAO.delete(profile);
        });
    }
    void deleteAllProfile(){
        ProfileDatabase.databaseWriteExecutor.execute(() ->{
            profileDAO.deleteAllData();
        });
    }
}
