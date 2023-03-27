package com.example.newsapp.room.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="news_table")
public class News {

    @PrimaryKey
    @NonNull
    private String id;


    @NonNull
    @ColumnInfo(name = "dataclassnews")

    private String dataclassnews;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }


    public News(@NonNull String id, @NonNull String dataclassnews) {
        this.id=id;
        this.dataclassnews=dataclassnews;
    }

    public String getDataclassnews() {
        return dataclassnews;
    }

    public void setDataclassnews(String dataclassnews) {
        this.dataclassnews=dataclassnews;
    }
}
