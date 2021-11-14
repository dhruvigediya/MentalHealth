package com.texastech.talk.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResourcesDao {
    @Query("SELECT * FROM resources")
    List<Resources> getAll();

    @Insert
    void insert(Resources resources);

    @Insert
    void insertAll(Resources... resources);

    @Delete
    void delete(Resources resource);
}