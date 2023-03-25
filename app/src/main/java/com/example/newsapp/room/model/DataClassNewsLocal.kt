package com.example.newsapp.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.model.DataClassNews

@Entity(tableName = "news") //by default the class name is taken as table naem
 class DataClassNewsLocal (

 @field:PrimaryKey val id: String,
 @field:ColumnInfo(
  name = "dataclassnews"
 ) val dataclassnews: DataClassNews

 )