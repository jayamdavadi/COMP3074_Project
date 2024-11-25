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

        btnBack.setOnClickListener(v -> finish());

        database = RestaurantDatabase.getInstance(this);

        loadRestaurants();


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

    private void loadRestaurants() {
        new Thread(() -> {
            restaurantList = database.restaurantDao().getAllRestaurants();
            filteredList.addAll(restaurantList); // Initially, all restaurants are displayed
            runOnUiThread(() -> {
                adapter = new RestaurantAdapter(filteredList, this);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }

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
        adapter.updateData(filteredList);
    }
}
