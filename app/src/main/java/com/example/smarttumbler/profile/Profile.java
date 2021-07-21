package com.example.smarttumbler.profile;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_profile")
public class Profile implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String namaPengguna;
    private int usiaPengguna;
    private int tinggiPengguna;
    private int beratPengguna;
    private String genderPengguna;
    private int kebutuhanAir;

    public Profile(@NonNull String namaPengguna, int usiaPengguna, int tinggiPengguna, int beratPengguna, String genderPengguna, int kebutuhanAir) {
        this.namaPengguna = namaPengguna;
        this.usiaPengguna = usiaPengguna;
        this.tinggiPengguna = tinggiPengguna;
        this.beratPengguna = beratPengguna;
        this.genderPengguna = genderPengguna;
        this.kebutuhanAir = kebutuhanAir;
    }

    public Profile(Parcel in) {
        namaPengguna = in.readString() ;
        usiaPengguna = in.readInt();
        tinggiPengguna = in.readInt();
        beratPengguna = in.readInt();
        genderPengguna = in.readString();
        kebutuhanAir = in.readInt();
    }

    @NonNull
    public String getNamaPengguna() {
        return namaPengguna;
    }

    public void setId(int id){this.id = id;}

    public int getId(){return id;}

    public void setNamaPengguna(@NonNull String namaPengguna) {
        this.namaPengguna = namaPengguna;
    }

    public int getUsiaPengguna() {
        return usiaPengguna;
    }

    public void setUsiaPengguna(int usiaPengguna) {
        this.usiaPengguna = usiaPengguna;
    }

    public int getTinggiPengguna() {
        return tinggiPengguna;
    }

    public void setTinggiPengguna(int tinggiPengguna) {
        this.tinggiPengguna = tinggiPengguna;
    }

    public int getBeratPengguna() {
        return beratPengguna;
    }

    public void setBeratPengguna(int beratPengguna) {
        this.beratPengguna = beratPengguna;
    }

    public String getGenderPengguna() {
        return genderPengguna;
    }

    public void setGenderPengguna(String genderPengguna) {
        this.genderPengguna = genderPengguna;
    }

    public int getKebutuhanAir() {
        return kebutuhanAir;
    }

    public void setKebutuhanAir(int kebutuhanAir) {
        this.kebutuhanAir = kebutuhanAir;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(namaPengguna);
        dest.writeInt(usiaPengguna);
        dest.writeInt(tinggiPengguna);
        dest.writeInt(beratPengguna);
        dest.writeString(genderPengguna);
        dest.writeInt(kebutuhanAir);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
