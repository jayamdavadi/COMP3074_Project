package ca.gbc.comp3074.restaurantguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.gbc.comp3074.restaurantguide.R;
import ca.gbc.comp3074.restaurantguide.database.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);

        holder.tvName.setText(restaurant.getRestaurantName());
        holder.tvAddress.setText(restaurant.getAddress());
        holder.tvDescription.setText(restaurant.getDescription());

        holder.btnGetDirections.setOnClickListener(v -> {
            String address = restaurant.getAddress();
            if (!address.isEmpty()) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    // Display a message if Google Maps is not installed
                    android.widget.Toast.makeText(context, "Google Maps is not installed!", android.widget.Toast.LENGTH_SHORT).show();
                }
            } else {
                android.widget.Toast.makeText(context, "Address not available!", android.widget.Toast.LENGTH_SHORT).show();
            }
        });


        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public void updateData(List<Restaurant> newRestaurantList) {
        this.restaurantList = newRestaurantList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvDescription;
        Button btnGetDirections;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnGetDirections = itemView.findViewById(R.id.btnGetDirections);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Restaurant restaurant);
    }

}
