package com.example.jimelrestaurant;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class CreateActivity extends AppCompatActivity implements
        View.OnClickListener {
    //kode yang ditambahkan 1
    Spinner menuSpinner;
    private EditText edtNama, edtTotal_Harga;
    private Button btnSave;
    String [] menu = { "Rama Shinta", "Tri Musketeer", "Qura Qura Ninja", "Acelole", "Heksosial", "Heptonius"};
    private Restaurant restaurant;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
//kode yang ditambahkan 2
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtNama = findViewById(R.id.edt_nama);
        edtTotal_Harga =findViewById(R.id.edt_total_harga);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        restaurant = new Restaurant();
        menuSpinner = findViewById(R.id.spinner_menu);
    }


    //kode yang ditambahkan 3
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            saveRestaurant();
        }
    }
    private void saveRestaurant()
    {
        String nama = edtNama.getText().toString().trim();
        String menu = menuSpinner.getSelectedItem().toString();
        String total_harga = edtTotal_Harga.getText().toString().trim();

        boolean isEmptyFields = false;
        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            edtNama.setError("Field ini tidak boleh kosong");
        }
        if (! isEmptyFields) {
            Toast.makeText(CreateActivity.this, "Saving Data...",
                    Toast.LENGTH_SHORT).show();
            DatabaseReference dbRestaurant =
                    mDatabase.child("restaurant");
            String id = dbRestaurant.push().getKey();
            restaurant.setId(id);
            restaurant.setNama(nama);
            restaurant.setPesanan(menu);
            restaurant.setTotal_harga(total_harga);
//insert data
            dbRestaurant.child(id).setValue(restaurant);


            finish();
        }
    }
}
