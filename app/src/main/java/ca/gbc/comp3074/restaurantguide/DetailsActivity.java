package ca.gbc.comp3074.restaurantguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private int restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Retrieve restaurant details from Intent
        restaurantId = getIntent().getIntExtra("restaurantId", -1);
        restaurantName = getIntent().getStringExtra("restaurantName");
        restaurantAddress = getIntent().getStringExtra("restaurantAddress");
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);

        // Populate TextViews with restaurant details
        populateDetails();

        // Initialize buttons
        initializeRateButton();
        initializeViewOnMapButton();
        initializeGetDirectionsButton();
        initializeFullScreenMapButton();
        initializeShareButtons();
    }

    private void populateDetails() {
        TextView nameTextView = findViewById(R.id.restaurantNameTextView);
        TextView addressTextView = findViewById(R.id.restaurantAddressTextView);

        if (nameTextView != null) nameTextView.setText(restaurantName);
        if (addressTextView != null) addressTextView.setText(restaurantAddress);
    }

    private void initializeRateButton() {
        Button rateButton = findViewById(R.id.rateButton);
        rateButton.setOnClickListener(v -> {
            Toast.makeText(this, "Rating functionality coming soon!", Toast.LENGTH_SHORT).show();
        });
    }

    private void initializeViewOnMapButton() {
        Button viewOnMapButton = findViewById(R.id.viewOnMapButton);
        viewOnMapButton.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(restaurantAddress));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(this, "Google Maps app is not installed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeGetDirectionsButton() {
        Button getDirectionsButton = findViewById(R.id.getDirectionsButton);
        getDirectionsButton.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(restaurantAddress));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Toast.makeText(this, "Google Maps app is not installed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeFullScreenMapButton() {
        Button fullScreenMapButton = findViewById(R.id.btnFullScreenMap);
        fullScreenMapButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, FullScreenMapActivity.class);
            intent.putExtra("latitude", latitude);
            intent.putExtra("longitude", longitude);
            startActivity(intent);
        });
    }

    private void initializeShareButtons() {
        Button btnShareEmail = findViewById(R.id.btnShareEmail);
        Button btnShareFacebook = findViewById(R.id.btnShareFacebook);
        Button btnShareTwitter = findViewById(R.id.btnShareTwitter);

        String shareContent = "Check out this restaurant: " + restaurantName + ", located at: " + restaurantAddress;

        btnShareEmail.setOnClickListener(v -> shareViaEmail("Restaurant Details", shareContent));
        btnShareFacebook.setOnClickListener(v -> shareOnFacebook("https://restaurantwebsite.com", shareContent));
        btnShareTwitter.setOnClickListener(v -> shareOnTwitter("Check out this restaurant!", "https://restaurantwebsite.com"));
    }

    private void shareViaEmail(String subject, String body) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email via"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareOnFacebook(String url, String quote) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.facebook.com/sharer/sharer.php?u=" + Uri.encode(url) + "&quote=" + Uri.encode(quote)));
        startActivity(intent);
    }

    private void shareOnTwitter(String text, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String twitterUrl = "https://twitter.com/intent/tweet?text=" + Uri.encode(text) + "&url=" + Uri.encode(url);
        intent.setData(Uri.parse(twitterUrl));
        startActivity(intent);
    }
}
