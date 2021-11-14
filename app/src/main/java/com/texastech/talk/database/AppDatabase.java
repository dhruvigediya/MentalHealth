package com.texastech.talk.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Mood.class, Resources.class ,Journal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase mSingleInstance;
    public static final String DATABASE_NAME = "database";

    public abstract MoodDao moodDao();
    public abstract ResourcesDao resourcesDao();
    public abstract JournalDao journalDao();

    public static AppDatabase getDatabase(final Context context) {
        if (mSingleInstance == null) {
            synchronized (AppDatabase.class) {
                if (mSingleInstance == null) {
                    mSingleInstance = Room.databaseBuilder(
                            context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return mSingleInstance;
    }
}
