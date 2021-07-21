package com.example.smarttumbler.botol;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.util.Date;

@Entity (tableName = "botol")
public class Botol {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date tanggal;
    private double airYangSudahDiminum;
    private double sisaAir;


    public Botol(Date tanggal,double airYangSudahDiminum,double sisaAir){
        this.tanggal = tanggal;
        this.airYangSudahDiminum = airYangSudahDiminum;
        this.sisaAir = sisaAir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public double getAirYangSudahDiminum() {
        return airYangSudahDiminum;
    }

    public double getSisaAir() {
        return sisaAir;
    }


}

