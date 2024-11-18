package com.example.noticiasapi.app.data.database

import android.content.Context

data class appDataBase()


//@Database(entities = [Student::class], version = 1)
//abstract class AppDatabase: RoomDatabase() {
//    abstract fun studentDao(): StudentDao
//
//    companion object {
//        @Volatile private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java, "app_database")
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}

