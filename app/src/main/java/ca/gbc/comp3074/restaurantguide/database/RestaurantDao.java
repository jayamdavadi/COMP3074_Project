package ca.gbc.comp3074.restaurantguide.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Restaurant restaurant); // Inserts a new restaurant

    @Query("SELECT * FROM Restaurant WHERE id = :id")
    Restaurant getById(int id); // Retrieves a restaurant by its ID

    @Query("SELECT * FROM Restaurant")
    List<Restaurant> getAllRestaurants(); // Retrieves all restaurants

    @Query("SELECT * FROM Restaurant WHERE rating >= :minRating")
    List<Restaurant> getRestaurantsByRating(float minRating); // Retrieves restaurants by minimum rating

    @Query("SELECT * FROM Restaurant WHERE review LIKE '%' || :keyword || '%'")
    List<Restaurant> searchByReview(String keyword); // Searches restaurants by review keyword

    @Update
    void update(Restaurant restaurant); // Updates a restaurant

    @Delete
    void delete(Restaurant restaurant); // Deletes a restaurant
}
