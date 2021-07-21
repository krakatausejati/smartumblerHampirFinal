package com.example.smarttumbler.botol;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;



import java.util.List;

public class BotolViewModel extends AndroidViewModel {

    private BotolRepository botolRepository;

    private final LiveData<List<Botol>> allBotol;

    public BotolViewModel(@NonNull Application application) {
        super(application);
        botolRepository = new BotolRepository(application);
        allBotol = botolRepository.getAllBotol();
    }

    public LiveData<List<Botol>> getAllBotol() {
        return allBotol;
    }

    public void insert(Botol botol){
        botolRepository.insert(botol);
    }
    public void update(Botol botol){
        botolRepository.update(botol);
    }
    public void delete(Botol botol){
        botolRepository.delete(botol);
    }
    public void deleteAllProfile(){
        botolRepository.deleteAllProfile();
    }

}
