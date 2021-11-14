package com.texastech.talk.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoodDao {
    @Query("SELECT * FROM mood")
    List<Mood> getAll();

    @Insert
    void insert(Mood mood);

    @Insert
    void insertAll(Mood... moods);

    @Delete
    void delete(Mood mood);
}