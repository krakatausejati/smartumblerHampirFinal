package com.example.smarttumbler.profile;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository profileRepository;

    private final LiveData<List<Profile>> allProfile;

    public ProfileViewModel(Application application){
        super(application);
        profileRepository = new ProfileRepository(application);
        allProfile = profileRepository.getAllProfile();
    }

    public LiveData<List<Profile>> getAllProfile() {
        return allProfile;
    }

    void insert(Profile profile){
        profileRepository.insert(profile);
    }
    public void update(Profile profile){
        profileRepository.update(profile);
    }
    public void delete(Profile profile){
        profileRepository.delete(profile);
    }
    void deleteAllProfile(){
        profileRepository.deleteAllProfile();
    }
}
