package com.example.newsapp.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
class News(
    @field:PrimaryKey var id: String,
    @field:ColumnInfo(name = "dataclassnews") var dataclassnews: String
)