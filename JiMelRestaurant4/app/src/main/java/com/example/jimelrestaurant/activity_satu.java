package com.example.jimelrestaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_satu extends AppCompatActivity
        implements View.OnClickListener{
    //kode yang ditambahkan 1
    private ListView listView;
    private Button btnAdd;
    //tambahkan kode ini
    private RestaurantAdapter adapter;
    private ArrayList<Restaurant> restaurantList;
    DatabaseReference dbRestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satu);
//kode yang ditambahkan 2
        listView = findViewById(R.id.lv_list);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
        dbRestaurant = FirebaseDatabase.getInstance().getReference("restaurant");
        restaurantList = new ArrayList<>();

        //kode yang ditambahkan
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(activity_satu.this, UpdateActivity.class);
                intent.putExtra(UpdateActivity.EXTRA_RESTAURANT, restaurantList.get(i));
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        dbRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                restaurantList.clear();
                for (DataSnapshot restaurantSnapshot :
                        dataSnapshot.getChildren()) {
                    Restaurant restaurant =
                            restaurantSnapshot.getValue(Restaurant.class);
                    restaurantList.add(restaurant);
                }
                RestaurantAdapter adapter = new
                        RestaurantAdapter(activity_satu.this);
                adapter.setRestaurantList(restaurantList);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(activity_satu.this, "Terjadi kesalahan.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    //kode yang ditambahkan 3
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_add) {
            Intent intent = new Intent(activity_satu.this,
                    CreateActivity.class);
            startActivity(intent);
        }
    }
}