package ca.gbc.comp3074.restaurantguide;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import ca.gbc.comp3074.restaurantguide.database.Restaurant;
import ca.gbc.comp3074.restaurantguide.database.RestaurantDatabase;

public class AddEditRestaurantActivity extends AppCompatActivity {

    private EditText etName, etAddress, etPhone, etDescription, etTags, etReview;
    private RatingBar ratingBar;
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
        etReview = findViewById(R.id.etReview);
        ratingBar = findViewById(R.id.ratingBar);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        database = RestaurantDatabase.getInstance(this);

        btnSave.setOnClickListener(view -> saveRestaurant());
        btnBack.setOnClickListener(v -> finish());
    }

    private void saveRestaurant() {
        String name = etName.getText().toString().trim();
        String address = formatAddress(etAddress.getText().toString().trim());
        String phone = formatPhoneNumber(etPhone.getText().toString().trim());
        String description = etDescription.getText().toString().trim();
        String tags = etTags.getText().toString().trim();
        String review = etReview.getText().toString().trim();
        float rating = ratingBar.getRating();

        if (!isValidAddress(address) || !isValidPhone(phone)) {
            Toast.makeText(this, "Invalid address or phone number.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> tagsList = Arrays.asList(tags.split("\\s*,\\s*"));
        Restaurant restaurant = new Restaurant(name, address, phone, description, tagsList, rating, review);

        new Thread(() -> {
            database.restaurantDao().insert(restaurant);
            runOnUiThread(() -> {
                Toast.makeText(this, "Restaurant added successfully!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }

    private String formatAddress(String address) {
        String[] words = address.toLowerCase().split("\\s+");
        StringBuilder formattedAddress = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedAddress.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
        }
        return formattedAddress.toString().trim();
    }

    private boolean isValidAddress(String address) {
        return !address.isEmpty();
    }

    private String formatPhoneNumber(String phone) {
        phone = phone.replaceAll("[^\\d]", "");
        if (phone.length() == 10) {
            return String.format("(%s) %s-%s", phone.substring(0, 3), phone.substring(3, 6), phone.substring(6));
        }
        return phone;
    }

    private boolean isValidPhone(String phone) {
        return phone.matches("\\(\\d{3}\\) \\d{3}-\\d{4}");
    }
}
