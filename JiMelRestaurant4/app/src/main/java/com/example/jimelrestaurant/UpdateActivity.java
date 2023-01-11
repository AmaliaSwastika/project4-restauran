package com.example.jimelrestaurant;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class UpdateActivity extends AppCompatActivity implements
        View.OnClickListener {
    //tambahkan 1
    private EditText edtNama, edtTotal_harga;
    private Button btnUpdate;
    public static final String EXTRA_RESTAURANT = "extra_restaurant";
    public final int ALERT_DIALOG_CLOSE = 10;
    public final int ALERT_DIALOG_DELETE = 20;
    Spinner menuSpinner;
    private Restaurant restaurant;
    private String restaurantId;
    String [] menu1 = { "Rama Shinta", "Tri Musketeer", "Qura Qura Ninja", "Acelole", "Heksosial", "Heptonius"};
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
//tambahkan 2
        mDatabase = FirebaseDatabase.getInstance().getReference();
        edtNama = findViewById(R.id.edt_edit_nama);
        menuSpinner = findViewById(R.id.spinner_menu1);
        edtTotal_harga = findViewById(R.id.edt_edit_total_harga);
        btnUpdate = findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        restaurant = getIntent().getParcelableExtra(EXTRA_RESTAURANT);
        if (restaurant != null) {
            restaurantId = restaurant.getId();
        } else {
            restaurant = new Restaurant();
        }
        if (restaurant != null) {
            edtNama.setText(restaurant.getNama());
            edtTotal_harga.setText(restaurant.getTotal_harga());
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Edit Data");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    //tambahkan 3
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_update) {
            updateRestaurant();
        }
    }
    private void updateRestaurant() {
        String nama = edtNama.getText().toString().trim();
        String menu1 = menuSpinner.getSelectedItem().toString().trim();
        String total_harga = edtTotal_harga.getText().toString().trim();
        boolean isEmptyFields = false;

        if (TextUtils.isEmpty(nama)) {
            isEmptyFields = true;
            edtNama.setError("Field ini tidak boleh kosong");
        }
        if (TextUtils.isEmpty(total_harga)) {
            isEmptyFields = true;
            edtTotal_harga.setError("Field ini tidak boleh kosong");
        }
        if (! isEmptyFields) {
            Toast.makeText(UpdateActivity.this, "Updating Data...",
                    Toast.LENGTH_SHORT).show();
            restaurant.setNama(nama);
            restaurant.setPesanan(menu1);
            restaurant.setTotal_harga(total_harga);
            DatabaseReference dbRestaurant =
                    mDatabase.child("restaurant");
//update data
            dbRestaurant.child(restaurantId).setValue(restaurant);
            finish();
        }
    }
    public void deleteProduct(View view){
        Intent intent = new Intent(UpdateActivity.this, activity_satu.class);
        startActivity(intent);
        Toast.makeText(UpdateActivity.this, "Deleting Data Pesanan..", Toast.LENGTH_SHORT).show();
        DatabaseReference dbRestaurant = mDatabase.child("restaurant").child(restaurantId);
        dbRestaurant.removeValue();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //pilih menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                showAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                showAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        showAlertDialog(ALERT_DIALOG_CLOSE);
    }
    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;
        if (isDialogClose) {
            dialogTitle = "Batal";
            dialogMessage = "Apakah anda ingin membatalkan perubahan pada form?";
        } else {
            dialogTitle = "Hapus Data";
            dialogMessage = "Apakah anda yakin ingin menghapus item ini?";
        }
        AlertDialog.Builder alertDialogBuilder = new
                AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);
        alertDialogBuilder.setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isDialogClose) {
                            finish();
                        } else {
//hapus data

                            DatabaseReference dbRestaurant = mDatabase.child("restaurant").child(restaurantId);
                            dbRestaurant.removeValue();
                            Toast.makeText(UpdateActivity.this,
                                    "Deleting data...",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).setNegativeButton("Tidak", new
                        DialogInterface.OnClickListener() {
                            @Override

                            public void onClick(DialogInterface

                                                        dialogInterface, int i) {
                                dialogInterface.cancel();

                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}