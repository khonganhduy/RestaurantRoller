package edu.sjsu.android.restaurantroller;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Class for the creation and return of the database object based on Room API standards
@Database(entities={RestaurantEntity.class}, version = 1, exportSchema = false)
public abstract class RestaurantDatabase extends RoomDatabase {
    public abstract RestaurantDAO restaurantDAO();

    private static volatile RestaurantDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 3;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    static RestaurantDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RestaurantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RestaurantDatabase.class, "restaurant_database")
                            .addCallback(dbCallback).fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }

        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    RestaurantDAO dataAccessObject = INSTANCE.restaurantDAO();
                    //Initialize some entries here if you want with dataAccessObject.insert...
                }
            });
        }
    };
}
