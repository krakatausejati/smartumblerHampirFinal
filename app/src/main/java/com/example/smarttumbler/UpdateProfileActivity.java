package com.example.smarttumbler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarttumbler.profile.Profile_Fragment;

public class UpdateProfileActivity extends AppCompatActivity {
    private final String TAG = "UpdateProfileActivity";
    EditText namaEdit,beratEdit,usiaEdit,tinggiEdit;
    Spinner spinner;
    Button save;
    ImageButton backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        namaEdit = findViewById(R.id.inputNamaPengguna);
        beratEdit = findViewById(R.id.inputBerat);
        usiaEdit = findViewById(R.id.inputUsia);
        tinggiEdit = findViewById(R.id.inputTinggi);
        save = findViewById(R.id.savebutton);
        backButton = (ImageButton)findViewById(R.id.backButton);

        spinner = (Spinner) findViewById(R.id.button_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        if(intent.hasExtra(Constant.EXTRA_ID)){
            namaEdit.setText(intent.getStringExtra(Constant.EXTRA_NAMA));
            beratEdit.setText(Integer.toString(intent.getIntExtra(Constant.EXTRA_BERAT,1)));
            usiaEdit.setText(Integer.toString(intent.getIntExtra(Constant.EXTRA_USIA,1)));
            tinggiEdit.setText(Integer.toString(intent.getIntExtra(Constant.EXTRA_TINGGI,1)));
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save(){
        String nama = namaEdit.getText().toString();
        int usia = Integer.parseInt(usiaEdit.getText().toString());
        int berat = Integer.parseInt(beratEdit.getText().toString());
        int tinggi = Integer.parseInt(tinggiEdit.getText().toString());
        String gender = spinner.getSelectedItem().toString();

        if(nama.trim().isEmpty()){
            Toast.makeText(this,"Nama tidak boleh kosong",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent(UpdateProfileActivity.this, MainActivity.class);
        data.putExtra(Constant.EXTRA_NAMA,nama);
        data.putExtra(Constant.EXTRA_USIA,usia);
        data.putExtra(Constant.EXTRA_BERAT,berat);
        data.putExtra(Constant.EXTRA_TINGGI,tinggi);
        data.putExtra(Constant.EXTRA_JENISKELAMIN,gender);

        int id = getIntent().getIntExtra(Constant.EXTRA_ID,-1);
        if(id != -1){
            data.putExtra(Constant.EXTRA_ID,id);
        }
        setResult(RESULT_OK,data);
        finish();
    }
}
