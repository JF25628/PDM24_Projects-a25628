package com.example.studentlist.Interface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.studentlist.Entity.Student
import kotlinx.coroutines.flow.Flow

//class StudentDao {
//}

@Dao
interface StudentDao{
    @Query("Select * From student_table BY student Asc")
    fun getAlphabetizedStudents(): Flow<List<Student>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Query("Delete from student_table")
    suspend fun deleteALL()
}