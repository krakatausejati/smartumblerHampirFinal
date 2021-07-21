package com.example.smarttumbler.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarttumbler.R;

public class ProfileViewHolder extends RecyclerView.ViewHolder {

    private final TextView namaPengguna, usiaPengguna, tinggiPengguna, beratPengguna, genderPengguna, kebutuhanAir;

    private ProfileViewHolder(View itemView){
        super(itemView);

        namaPengguna = itemView.findViewById(R.id.namaPengguna);
        usiaPengguna = itemView.findViewById(R.id.usiaPengguna);
        tinggiPengguna = itemView.findViewById(R.id.tinggiPengguna);
        beratPengguna = itemView.findViewById(R.id.beratPengguna);
        genderPengguna = itemView.findViewById(R.id.genderPengguna);
        kebutuhanAir = itemView.findViewById(R.id.kebutuhanAir);

    }

    public void bind(String namaPengguna, String genderPengguna, int usiaPengguna, int tinggiPengguna, int beratPengguna, int kebutuhanAir) {

        this.namaPengguna.setText(namaPengguna);
        this.genderPengguna.setText(genderPengguna);
        this.usiaPengguna.setText(Integer.toString(usiaPengguna));
        this.tinggiPengguna.setText(Integer.toString(tinggiPengguna));
        this.beratPengguna.setText(Integer.toString(beratPengguna));
        this.kebutuhanAir.setText(Integer.toString(kebutuhanAir));
    }

    static ProfileViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_profile_, parent, false);
        return new ProfileViewHolder(view);
    }
}