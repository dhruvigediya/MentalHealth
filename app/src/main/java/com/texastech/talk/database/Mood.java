package com.texastech.talk.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Mood {

    @PrimaryKey(autoGenerate = true)
    int mid;

    @ColumnInfo(name = "date")
    public int date;

    @ColumnInfo(name = "value")
    public int value;

    @ColumnInfo (name="severity_level")
    public int severityLevel;


    public Mood(int date, int value, int severityLevel) {
        this.date = date;
        this.value = value;
        this.severityLevel= severityLevel;
    }
}
