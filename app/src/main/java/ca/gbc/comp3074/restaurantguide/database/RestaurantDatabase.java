package ca.gbc.comp3074.restaurantguide.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Restaurant.class}, version = 2, exportSchema = true)
@TypeConverters(TagsConverter.class)
public abstract class RestaurantDatabase extends RoomDatabase {

    private static volatile RestaurantDatabase instance;

    public static synchronized RestaurantDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RestaurantDatabase.class,
                            "restaurant_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract RestaurantDao restaurantDao();
}
