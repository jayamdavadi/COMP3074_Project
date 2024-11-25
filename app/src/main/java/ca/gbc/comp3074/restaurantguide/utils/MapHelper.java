package ca.gbc.comp3074.restaurantguide.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class MapHelper {
    public static void openMap(Context context, String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openDirections(Context context, String address) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "Google Maps is not installed", Toast.LENGTH_SHORT).show();
        }
    }
}
