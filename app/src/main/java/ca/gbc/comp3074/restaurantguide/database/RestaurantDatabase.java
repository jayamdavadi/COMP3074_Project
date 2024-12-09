package ca.gbc.comp3074.restaurantguide.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Restaurant.class}, version = 4, exportSchema = true) // Updated version
@TypeConverters(TagsConverter.class)
public abstract class RestaurantDatabase extends RoomDatabase {

    private static volatile RestaurantDatabase instance;

    /**
     * Get the singleton instance of the RestaurantDatabase.
     *
     * @param context Application context
     * @return The RestaurantDatabase instance
     */
    public static synchronized RestaurantDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RestaurantDatabase.class,
                            "restaurant_database"
                    )
                    .addMigrations(MIGRATION_3_4) // Apply migration from version 3 to 4
                    .fallbackToDestructiveMigration() // Use cautiously during development
                    .build();
        }
        return instance;
    }

    public abstract RestaurantDao restaurantDao();

    /**
     * Migration from version 3 to 4.
     */
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Use a safer approach to modify the table
            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS restaurants_new (" +
                        "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`restaurantName` TEXT, " +
                        "`address` TEXT, " +
                        "`phone` TEXT, " +
                        "`description` TEXT, " +
                        "`tags` TEXT, " +
                        "`rating` REAL NOT NULL DEFAULT 0.0, " +
                        "`review` TEXT)");
                database.execSQL("INSERT INTO restaurants_new (id, restaurantName, address, phone, description, tags, rating, review) " +
                        "SELECT id, restaurantName, address, phone, description, tags, rating, review FROM restaurants");
                database.execSQL("DROP TABLE restaurants");
                database.execSQL("ALTER TABLE restaurants_new RENAME TO restaurants");
            } catch (Exception e) {
                // Handle potential migration issues gracefully
                e.printStackTrace();
            }
        }
    };
}
