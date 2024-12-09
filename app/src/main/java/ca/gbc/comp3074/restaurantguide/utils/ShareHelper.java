package ca.gbc.comp3074.restaurantguide.utils;

import android.content.Context;
import android.content.Intent;

public class ShareHelper {
    public static void shareRestaurant(Context context, String name, String address, String phone, String tags) {
        String shareText = "Check out this restaurant:\n" +
                "Name: " + name + "\n" +
                "Address: " + address + "\n" +
                "Phone: " + phone + "\n" +
                "Tags: " + tags;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
