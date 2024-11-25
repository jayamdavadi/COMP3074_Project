package ca.gbc.comp3074.restaurantguide;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        TextView aboutTextView = findViewById(R.id.aboutTextView);
        aboutTextView.setText(
                "Personal Restaurant Guide\n\n" +
                        "Developed by:\n" +
                        "1. Jay Amdavadi\n" +
                        "2. Tisha Chauhan\n" +
                        "3. Sifatpreet Kaur\n\n" +
                        "This application helps you explore, manage, and save your favorite restaurants easily. " +
                        "Enjoy personalized ratings, map-based suggestions, and share your experiences with friends!"
        );

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
