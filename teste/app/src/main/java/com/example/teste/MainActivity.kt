package com.example.teste

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.teste.ui.theme.TesteTheme
import java.text.Normalizer.Form

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesteTheme {
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

@Composable
fun ExemploListaStrings(itemsList: List<String>){
    LazyColumn {
        items(itemsList){
            item -> Text(text = item)
        }
    }

    LazyRow {
        items(itemsList){
            item -> Text(text = item)
        }
    }
}

@Composable
fun ExemploFormLine(formList: List<FormLine>){
    LazyColumn {

        DrawFormLine()
    }
}

class FormLine(val name: String, val type: String, val hint: String){}

@Composable
fun DrawFormLine(FormLine){
    Row {

        Text(text = FormLine.name)
        Spacer()

        

        /*
        * text(formline.name)
        * spacer()
        * if(formline.type)=="textfield"{
        * textfield(hint = formline.hint)
        *
        *
        *}
        * */

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TesteTheme {
        val list = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o")
        ExemploListaStrings(list)
        Greeting("Android")
    }
}