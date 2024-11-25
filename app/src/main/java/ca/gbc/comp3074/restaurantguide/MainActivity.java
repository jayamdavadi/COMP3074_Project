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

        Button btnAddRestaurant = findViewById(R.id.btnAddRestaurant);
        Button btnViewRestaurants = findViewById(R.id.btnViewRestaurants);

        Button btnSearchNearby = findViewById(R.id.btnSearchNearby);
        Button btnAbout = findViewById(R.id.btnAbout);
        Button btnExit = findViewById(R.id.btnExit);

        btnAddRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditRestaurantActivity.class);
            startActivity(intent);
        });

        btnViewRestaurants.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
            startActivity(intent);
        });


        btnSearchNearby.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=indian resturants near me");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(this, "Google Maps app is not installed!", Toast.LENGTH_SHORT).show();
            }
        });

        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        btnExit.setOnClickListener(v -> finishAffinity());


        loadRestaurantList();
    }


    private void loadRestaurantList() {
        restaurantList = new ArrayList<>();

    }
}
