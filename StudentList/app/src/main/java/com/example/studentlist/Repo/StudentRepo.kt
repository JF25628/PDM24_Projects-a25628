package com.example.studentlist.Repo

import androidx.annotation.WorkerThread
import com.example.studentlist.Entity.Student
import com.example.studentlist.Interface.StudentDao
import kotlinx.coroutines.flow.Flow

class StudentRepo(private val studentDao: StudentDao)
{
    val allStudents : Flow<List<Student>> = studentDao.getAlphabetizedStudents()

    @Suppress("RedundantSuspendModifier")

    @WorkerThread
    suspend fun insert(student: Student){
        studentDao.insert(student)
    }
}