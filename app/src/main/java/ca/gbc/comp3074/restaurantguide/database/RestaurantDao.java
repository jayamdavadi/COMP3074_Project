package ca.gbc.comp3074.restaurantguide.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Restaurant restaurant);

    @Query("SELECT * FROM Restaurant WHERE id = :id")
    Restaurant getById(int id);

    @Query("SELECT * FROM Restaurant")
    List<Restaurant> getAllRestaurants();

    @Update
    void update(Restaurant restaurant); // Updates a restaurant
}
