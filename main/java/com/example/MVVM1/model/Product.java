package com.example.MVVM1.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "products")
public class Product{
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String description;
    @ColumnInfo
    public String category;
    @ColumnInfo
    public double price;
    @ColumnInfo
    public double discountPercentage;
    @ColumnInfo
    public double rating;
    @ColumnInfo
    public int stock;
    @ColumnInfo
    public String brand;
    @ColumnInfo
    public String sku;
    @ColumnInfo
    public int weight;
    @ColumnInfo
    public String warrantyInformation;
    @ColumnInfo
    public String shippingInformation;
    @ColumnInfo
    public String availabilityStatus;
    @ColumnInfo
    public String returnPolicy;
    @ColumnInfo
    public int minimumOrderQuantity;
    //@ColumnInfo
    //public Meta meta;
    @ColumnInfo
    public List<String> images;
    @ColumnInfo
    public String thumbnail;


    // Setter for images
    public void setImages(List<String> images) {
        this.images = images;
    }

    // You can also use the thumbnail as a fallback
    public String getThumbnail() {
        return thumbnail;
    }
    public List<String> getImages() {
        return images;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", images=" + images +
                '}';
    }
}
