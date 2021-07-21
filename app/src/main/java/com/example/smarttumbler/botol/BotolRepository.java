package com.example.smarttumbler.botol;

import android.app.Application;

import androidx.lifecycle.LiveData;



import java.util.List;

public class BotolRepository {

    private BotolDAO botolDAO;
    private LiveData<List<Botol>> allBotol;

    BotolRepository(Application application){
        BotolDatabase database = BotolDatabase.getInstance(application);
        botolDAO = database.botolDAO();
        allBotol = botolDAO.getAllData();
    }

    LiveData<List<Botol>> getAllBotol() {
        return allBotol;
    }

    void insert(Botol botol){
        BotolDatabase.databaseWriteExecutor.execute(() -> {
            botolDAO.insert(botol);
        });
    }
    void update(Botol botol){
        BotolDatabase.databaseWriteExecutor.execute(() ->{
            botolDAO.update(botol);
        });
    }
    void delete(Botol botol){
        BotolDatabase.databaseWriteExecutor.execute(() ->{
            botolDAO.delete(botol);
        });
    }
    void deleteAllProfile(){
        BotolDatabase.databaseWriteExecutor.execute(() ->{
            botolDAO.deleteAllData();
        });
    }
}
