package ca.gbc.comp3074.restaurantguide.database;

import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.List;

public class TagsConverter {
    @TypeConverter
    public String fromList(List<String> tags) {
        return String.join(",", tags);
    }

    @TypeConverter
    public List<String> toList(String tagsString) {
        return Arrays.asList(tagsString.split("\\s*,\\s*"));
    }
}
