package com.example.mvp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mvp.R;
import com.example.mvp.detail.ProductDetailActivity;
import com.example.mvp.model.Product;
import com.example.mvp.remote.RemoteActivity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private List<Product> productList;
    private final boolean isRemote;
    private final OnProductActionListener actionListener;
    boolean isFavoriteScreen;

    public interface OnProductActionListener {
        void onProductAction(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, boolean isRemote,
                          OnProductActionListener actionListener) {
        this.context = context;
        this.productList = productList;
        this.isRemote = isRemote;
        this.actionListener = actionListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_model, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.tvTitle.setText(product.title);
        holder.tvBody.setText(product.description);

        // Load image
        String imageUrl = getImageUrl(product);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .error(android.R.drawable.ic_menu_close_clear_cancel)
                    .into(holder.img);
        } else {
            holder.img.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // Setup button based on context
        if (isRemote) {
            holder.actionButton.setText("Save");
            holder.actionButton.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onProductAction(product);
                }
            });
            holder.btnAddToFavorites.setOnClickListener(v->{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ((RemoteActivity)context).addToFav(product);
                    }
                }).start();
            });
        } else {
            holder.actionButton.setText("Delete");
            holder.actionButton.setOnClickListener(v -> {
                if (actionListener != null) {
                    actionListener.onProductAction(product);
                }
            });
        }


        if (isFavoriteScreen) {
            if (holder.actionButton != null) {
                if (isRemote) {
                    holder.actionButton.setText("Save");
                } else {
                    holder.actionButton.setText("Delete");
                }

                holder.actionButton.setOnClickListener(v -> {
                    if (actionListener != null) {
                        actionListener.onProductAction(product);
                    }
                });
            }
        }

        // Item click for detail view
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra("productId", product.id);
            context.startActivity(intent);
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
        return productList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Product> products) {
        this.productList = products;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvBody;
        ImageView img;
        Button actionButton;
        Button btnAddToFavorites;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvBody = itemView.findViewById(R.id.tvBody);
                img = itemView.findViewById(R.id.imageView);
                actionButton = itemView.findViewById(R.id.saveButton);
                btnAddToFavorites = itemView.findViewById(R.id.btnAddToFavorites);

        }
    }
}