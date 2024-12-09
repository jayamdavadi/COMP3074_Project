package ca.gbc.comp3074.restaurantguide;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.gbc.comp3074.restaurantguide.adapters.RestaurantAdapter;
import ca.gbc.comp3074.restaurantguide.database.Restaurant;
import ca.gbc.comp3074.restaurantguide.database.RestaurantDatabase;

public class RestaurantListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private RestaurantDatabase database;
    private Button btnBack;
    private SearchView searchView;
    private List<Restaurant> restaurantList = new ArrayList<>();
    private List<Restaurant> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnBack = findViewById(R.id.btnBack);
        searchView = findViewById(R.id.searchView);

        // Handle Back Button
        btnBack.setOnClickListener(v -> finish());

        database = RestaurantDatabase.getInstance(this);

        // Load the restaurants from the database
        loadRestaurants();

        // Set up search filter functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterRestaurants(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRestaurants(newText);
                return true;
            }
        });
    }

    /**
     * Load restaurants from the database asynchronously.
     */
    private void loadRestaurants() {
        new Thread(() -> {
            restaurantList = database.restaurantDao().getAllRestaurants();
            runOnUiThread(() -> {
                filteredList.clear();
                filteredList.addAll(restaurantList); // Initially, display all restaurants
                adapter = new RestaurantAdapter(filteredList, this);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

    /**
     * Filter the list of restaurants based on a search query.
     *
     * @param query The search query entered by the user.
     */
    private void filterRestaurants(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(restaurantList);
        } else {
            for (Restaurant restaurant : restaurantList) {
                if (restaurant.getRestaurantName().toLowerCase().contains(query.toLowerCase()) ||
                        restaurant.getTags().toString().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(restaurant);
                }
            }
        }

        // Notify the adapter about the changes in the filtered list
        if (adapter != null) {
            adapter.updateData(filteredList);
        }
    }
}
