package com.example.jimelrestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class RestaurantAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Restaurant> restaurantList = new
            ArrayList<>();
    public void setRestaurantList(ArrayList<Restaurant>
                                          restaurantList) {
        this.restaurantList = restaurantList;
    }
    public RestaurantAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return restaurantList.size();
    }
    @Override
    public Object getItem(int i) {
        return restaurantList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView = view;
        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                    .inflate(R.layout.item_restaurant, viewGroup, false);
        }
        ViewHolder viewHolder = new ViewHolder(itemView);
        Restaurant restaurant = (Restaurant) getItem(i);
        viewHolder.bind(restaurant);
        return itemView;
    }
    private class ViewHolder {
        private TextView txtNama, txtPesanan, txtTotal_harga;
        ViewHolder(View view) {
            txtNama = view.findViewById(R.id.txt_nama);
            txtPesanan = view.findViewById(R.id.txt_pesanan);
            txtTotal_harga = view.findViewById(R.id.txt_total_harga);
        }
        void bind(Restaurant restaurant) {
            txtNama.setText(restaurant.getNama());
            txtPesanan.setText(restaurant.getPesanan());
            txtTotal_harga.setText(restaurant.getTotal_harga());
        }
    }
}
