package ca.gbc.comp3074.restaurantguide;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import ca.gbc.comp3074.restaurantguide.database.Restaurant;
import ca.gbc.comp3074.restaurantguide.database.RestaurantDatabase;

public class AddEditRestaurantActivity extends AppCompatActivity {

    private EditText etName, etAddress, etPhone, etDescription, etTags;
    private Button btnSave, btnBack;
    private RestaurantDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_restaurant);


        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etTags = findViewById(R.id.etTags);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        database = RestaurantDatabase.getInstance(this);

        btnSave.setOnClickListener(view -> saveRestaurant());

        btnBack.setOnClickListener(v -> finish());
    }

    private void saveRestaurant() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String tags = etTags.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Must enter name and address.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> tagsList = Arrays.asList(tags.split("\\s*,\\s*"));

        Restaurant restaurant = new Restaurant(
                name,
                address,
                phone,
                description,
                tagsList,
                0
        );

        new Thread(() -> {
            database.restaurantDao().insert(restaurant);
            runOnUiThread(() -> {
                Toast.makeText(this, "Restaurant added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
