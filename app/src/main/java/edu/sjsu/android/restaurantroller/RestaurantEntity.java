package edu.sjsu.android.restaurantroller;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurant")
public class RestaurantEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int entryId;

    @NonNull
    @ColumnInfo(name = "restaurant_name")
    private String name;

    @ColumnInfo(name = "tag")
    private String tag;

    public RestaurantEntity(String name, String tag){
        this.name = name;
        this.tag = tag;
    }

    public int getEntryId(){ return entryId; }
    public void setEntryId(int entryId){ this.entryId = entryId; }
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag){
        this.tag = tag;
    }
}
