package ca.gbc.comp3074.restaurantguide.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;

@Entity
public class Restaurant {
    @PrimaryKey(autoGenerate = true) // Enable automatic ID generation by Room
    private int id;
    private String restaurantName;
    private String address;
    private String phone;
    private String description;

    @TypeConverters(TagsConverter.class)
    private List<String> tags;

    private float rating; // Updated field to store rating as a float
    private String review; // New field for storing review

    // Full Constructor for Room
    public Restaurant(int id, String restaurantName, String address, String phone, String description, List<String> tags, float rating, String review) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.tags = tags;
        this.rating = rating;
        this.review = review;
    }

    // Simplified Constructor for Manual Use
    @Ignore
    public Restaurant(String restaurantName, String address, String phone, String description, List<String> tags, float rating, String review) {
        this.restaurantName = restaurantName;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.tags = tags;
        this.rating = rating;
        this.review = review;
    }

    // Default Constructor
    @Ignore
    public Restaurant() {
        this.rating = 0.0f; // Default rating
        this.review = ""; // Default empty review
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", restaurantName='" + restaurantName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", rating=" + rating +
                ", review='" + review + '\'' +
                '}';
    }
}
