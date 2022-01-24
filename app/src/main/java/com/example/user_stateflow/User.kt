package com.example.user_stateflow

import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject

@Entity(tableName = "user")
data class User constructor(val name:String, val age:Int) {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}