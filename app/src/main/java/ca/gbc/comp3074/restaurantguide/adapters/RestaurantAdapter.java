package ca.gbc.comp3074.restaurantguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.gbc.comp3074.restaurantguide.R;
import ca.gbc.comp3074.restaurantguide.database.Restaurant;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;

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
        holder.ratingBar.setRating(restaurant.getRating());

        String shareContent = "Check out this restaurant: " + restaurant.getRestaurantName() +
                ", located at: " + restaurant.getAddress();

        // Share via Email
        holder.btnShareEmail.setOnClickListener(v -> shareViaEmail("Restaurant Details", shareContent));

        // Share on Facebook
        holder.btnShareFacebook.setOnClickListener(v -> shareOnFacebook("https://restaurantwebsite.com", shareContent));

        // Share on Twitter
        holder.btnShareTwitter.setOnClickListener(v -> shareOnTwitter("Check out this restaurant!", "https://restaurantwebsite.com"));

        // "Get Directions" functionality
        holder.btnGetDirections.setOnClickListener(v -> {
            String address = restaurant.getAddress();
            if (!address.isEmpty()) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                } else {
                    Toast.makeText(context, "Google Maps is not installed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Address not available!", Toast.LENGTH_SHORT).show();
            }
        });

        // "Delete Restaurant" functionality
        holder.btnDelete.setOnClickListener(v -> deleteRestaurant(restaurant, position));
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    // Update the dataset dynamically
    public void updateData(List<Restaurant> newRestaurantList) {
        this.restaurantList = newRestaurantList;
        notifyDataSetChanged();
    }

    private void deleteRestaurant(Restaurant restaurant, int position) {
        restaurantList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, restaurantList.size());
        Toast.makeText(context, "Restaurant deleted successfully!", Toast.LENGTH_SHORT).show();
    }

    // Share via Email
    private void shareViaEmail(String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send email via"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "No email client installed!", Toast.LENGTH_SHORT).show();
        }
    }

    // Share on Facebook
    private void shareOnFacebook(String url, String quote) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.facebook.com/sharer/sharer.php?u=" + Uri.encode(url) + "&quote=" + Uri.encode(quote)));
        context.startActivity(intent);
    }

    // Share on Twitter
    private void shareOnTwitter(String text, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String twitterUrl = "https://twitter.com/intent/tweet?text=" + Uri.encode(text) + "&url=" + Uri.encode(url);
        intent.setData(Uri.parse(twitterUrl));
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvDescription;
        RatingBar ratingBar;
        Button btnShareEmail, btnShareFacebook, btnShareTwitter, btnGetDirections, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            btnShareEmail = itemView.findViewById(R.id.btnShareEmail);
            btnShareFacebook = itemView.findViewById(R.id.btnShareFacebook);
            btnShareTwitter = itemView.findViewById(R.id.btnShareTwitter);
            btnGetDirections = itemView.findViewById(R.id.btnGetDirections);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
