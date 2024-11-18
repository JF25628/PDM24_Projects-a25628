package com.example.studentlist.Views

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.studentlist.Entity.Student
import com.example.studentlist.Repo.StudentRepo
import kotlinx.coroutines.launch

class StudentViewModel(private val repo: StudentRepo) : ViewModel() {

    val allStudent : LiveData<List<Student>> = repo.allStudents.asLiveData()

    fun insert(student: Student) = viewModelScope.launch {
        repo.insert(student)
    }
}

class StudentViewModelFactory(private val repo: StudentRepo) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if(modelClass.isAssignableFrom(StudentViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repo) as T
        }
        throw IllegalArgumentException("Unkown ViewModel class")

        return TODO("Provide the return value")
    }

}