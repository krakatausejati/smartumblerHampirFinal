package com.example.smarttumbler.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarttumbler.Constant;
import com.example.smarttumbler.R;
import com.example.smarttumbler.UpdateProfileActivity;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class Profile_Fragment extends Fragment {

    public static final int EDIT_PROFILE_REQUEST = 1;

    ProfileViewModel profileViewModel;
    TextView namaText;
    TextView usiaText;
    TextView jenisKelaminText;
    TextView tinggiBadanText;
    TextView beratBadanText;
    TextView kebutuhanMinumText;
    private Button updateProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_, container, false);
        namaText = view.findViewById(R.id.namaPengguna);
        usiaText = view.findViewById(R.id.usiaPengguna);
        jenisKelaminText = view.findViewById(R.id.genderPengguna);
        tinggiBadanText = view.findViewById(R.id.tinggiPengguna);
        beratBadanText = view.findViewById(R.id.beratPengguna);
        kebutuhanMinumText = view.findViewById(R.id.kebutuhanAir);
        updateProfile = view.findViewById(R.id.button);
        final String[] nama = {""};
        final String[] jenisKelamin = {""};
        final int[] usia = {0};
        final int[] tinggi = {0};
        final int[] berat = {0};
        final int[] id = {0};

        profileViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())).get(ProfileViewModel.class);
        profileViewModel.getAllProfile().observe(getViewLifecycleOwner(), new Observer<List<Profile>>() {
            @Override
            public void onChanged(List<Profile> profiles) {
//                Log.d("Profile","On Changed" + profiles.toString());
//                Log.d("Profile","On Changed" + profiles.size());
                for(Profile prof: profiles){
                    namaText.setText(prof.getNamaPengguna());
                    usiaText.setText(Integer.toString(prof.getUsiaPengguna()));
                    jenisKelaminText.setText(prof.getGenderPengguna());
                    tinggiBadanText.setText(Integer.toString(prof.getTinggiPengguna())+"cm");
                    beratBadanText.setText(Integer.toString(prof.getBeratPengguna())+"Kg");
                    kebutuhanMinumText.setText(Integer.toString(hitungKebutuhan(prof.getBeratPengguna()))+"ml");
                    nama[0] = prof.getNamaPengguna();
                    jenisKelamin[0] = prof.getGenderPengguna();
                    usia[0] = prof.getUsiaPengguna();
                    tinggi[0] = prof.getTinggiPengguna();
                    berat[0] = prof.getBeratPengguna();
                    id[0] = prof.getId();
                }
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
                intent.putExtra(Constant.EXTRA_ID,id[0]);
                intent.putExtra(Constant.EXTRA_NAMA,nama[0]);
                intent.putExtra(Constant.EXTRA_BERAT,berat[0]);
                intent.putExtra(Constant.EXTRA_TINGGI,tinggi[0]);
                intent.putExtra(Constant.EXTRA_USIA,usia[0]);
                intent.putExtra(Constant.EXTRA_JENISKELAMIN,jenisKelamin[0]);
                startActivityForResult(intent,EDIT_PROFILE_REQUEST);
            }
        });

        return  view;
    }

    private int hitungKebutuhan(int berat){
        return berat * 30;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK){
            Log.d("test","masuk");
            int id = data.getIntExtra(Constant.EXTRA_ID,-1);
            if(id == -1){
                Toast.makeText(getContext(), "Data can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String nama = data.getStringExtra(Constant.EXTRA_NAMA);
            int berat = (data.getIntExtra(Constant.EXTRA_BERAT,1));
            int usia = data.getIntExtra(Constant.EXTRA_USIA,1);
            int tinggi = data.getIntExtra(Constant.EXTRA_TINGGI,1);
            String gender = data.getStringExtra(Constant.EXTRA_JENISKELAMIN);
            Profile profile = new Profile(nama,usia,tinggi,berat,gender,hitungKebutuhan(berat));
            profile.setId(id);
            profileViewModel.update(profile);
        }
    }
}