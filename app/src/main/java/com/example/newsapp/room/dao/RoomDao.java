package com.example.newsapp.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.newsapp.room.model.DataClassNewsLocal;

import java.util.List;

@Dao
public interface RoomDao {
    @Insert
    void insert(DataClassNewsLocal dataClassNewsLocal);


    @Query("SELECT * FROM news ORDER BY id DESC LIMIT 1")
    LiveData<DataClassNewsLocal> getLatestLocal(); //LiveData variable will allow realtime update of the list without reloading the page


}
