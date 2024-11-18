package com.example.studentlist

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studentlist.ui.theme.StudentListTheme
import kotlinx.coroutines.flow.Flow


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StudentListTheme {
        Greeting("Android")
    }
}

/*
build.gradle app

implementation "android.room:room-runtime:2.6.1"
kapt "android.room:room-compiler:2.6.1"
implementation "android.room:room-ktx:2.6.1"
*/

//entidade/modelo
@Entity(tableName = "example_table")
data class ExampleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    val description: String
)

//Data Access Object
interface ExampleDao
{
    @Insert
    suspend fun insert(example: ExampleEntity)

    @Query("Select * From example_table where id = :id")
    fun getById(id: Int): Flow<ExampleEntity>

    @Delete
    suspend fun delete(example: ExampleEntity)
}

//DataBase
@Database(entities = [ExampleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun exampleDao(): ExampleDao

    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

//Inicializar
//val db = Room.databaseBuilder(
//    context,
//    AppDatabase::class.java, "example-database"
//).build()


