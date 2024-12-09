package ca.gbc.comp3074.restaurantguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ca.gbc.comp3074.restaurantguide.database.Restaurant;

public class MainActivity extends AppCompatActivity {

    private List<Restaurant> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up buttons
        Button btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        Button btnViewRestaurants = findViewById(R.id.btnViewRestaurants);
        Button btnShowRestaurantLocation = findViewById(R.id.btnShowRestaurantLocation);
        Button btnSearchNearby = findViewById(R.id.btnSearchNearby);
        Button btnAbout = findViewById(R.id.btnAbout);
        Button btnExit = findViewById(R.id.btnExit);

        // Open Add Restaurant screen
        btnAddRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditRestaurantActivity.class);
            startActivity(intent);
        });

        // Open list of restaurants
        btnViewRestaurants.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
            startActivity(intent);
        });

        // Show location of the first restaurant in the list
        btnShowRestaurantLocation.setOnClickListener(v -> {
            if (restaurantList != null && !restaurantList.isEmpty()) {
                Restaurant restaurant = restaurantList.get(0);
                String restaurantAddress = restaurant.getAddress();

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(restaurantAddress));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText(this, "Google Maps app is not installed!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "No restaurants available!", Toast.LENGTH_SHORT).show();
            }
        });

        // Search for nearby restaurants on Google Maps
        btnSearchNearby.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=Indian restaurants");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(this, "Google Maps app is not installed!", Toast.LENGTH_SHORT).show();
            }
        });

        // Open About screen
        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        // Exit the app
        btnExit.setOnClickListener(v -> finishAffinity());

        // Load some sample restaurants
        loadRestaurantList();
    }

    // Load a list of restaurants (placeholder, replace with real data later)
    private void loadRestaurantList() {
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant(
                "Test Restaurant",
                "2645 kipling avenue,Etobicoke, ON",
                "123-456-7890",
                "A delightful Italian and Pizza place.",
                List.of("Italian", "Pizza"),
                4.0f,
                "Great food and ambiance!" // Added a review
        ));
    }
}
