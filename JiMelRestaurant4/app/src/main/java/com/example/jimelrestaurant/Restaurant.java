package com.example.jimelrestaurant;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant implements Parcelable {
    private String id;
    private String nama;
    private String pesanan;
    private String total_harga;

    public Restaurant() {
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPesanan() {
        return pesanan;
    }
    public void setPesanan(String pesanan) {
        this.pesanan = pesanan;
    }

    public String getTotal_harga() {
        return total_harga;
    }
    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.pesanan);
        dest.writeString(this.total_harga);
    }
    protected Restaurant(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.pesanan = in.readString();
        this.total_harga = in.readString();
    }
    public static final Parcelable.Creator<Restaurant> CREATOR
            = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }
        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }

    };
}
