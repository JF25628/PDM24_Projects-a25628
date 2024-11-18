package com.example.studentlist.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class Student (
//    val nome: String,
//    val idade: Int
//)

@Entity(tableName = "student_table")
class Student(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "student") val student: String
)