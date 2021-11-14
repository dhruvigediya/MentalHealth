package com.texastech.talk.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class
Resources {
    @PrimaryKey(autoGenerate = true)
    int rid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "hyperlink")
    public String hyperlink;

    @ColumnInfo(name = "mood")
    public int mood;

    public Resources(String title, String content, String hyperlink, int mood) {
       this.title = title;
       this.content = content;
       this.hyperlink = hyperlink;
       this.mood = mood;
    }
}
