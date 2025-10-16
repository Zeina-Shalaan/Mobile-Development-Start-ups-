package com.example.MVVM1.view.favorites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.MVVM1.R;
import com.example.MVVM1.model.Product;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private Context context;
    private List<Product> favoriteList;

    public void updateData(List<Product> products) {
        this.favoriteList = products;
        notifyDataSetChanged();
    }

    // Interface for delete button


    public FavoriteAdapter(Context context, List<Product> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        Product product = favoriteList.get(position);

        holder.name.setText(product.title);
        holder.price.setText("$" + product.price);

        String imageUrl = getImageUrl(product);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_close_clear_cancel)
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Handle delete
        holder.deleteBtn.setOnClickListener(v -> {
            ((FavoriteProductsActivity)context).deleteProduct(product);
            favoriteList.remove(position);
            notifyDataSetChanged();
        });

    }

    private String getImageUrl(Product product) {
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            return product.getImages().get(0);
        } else if (product.getThumbnail() != null && !product.getThumbnail().isEmpty()) {
            return product.getThumbnail();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView productImage;
        ImageButton deleteBtn;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.fav_item_title);
            price = itemView.findViewById(R.id.priceView);
            productImage = itemView.findViewById(R.id.fav_item_image);
            deleteBtn = itemView.findViewById(R.id.btn_remove_fav);
        }
    }
}
