package com.example.primeira_aplicacao

import android.os.Bundle
import android.provider.SyncStateContract.Columns
import android.renderscript.Sampler.Value
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.primeira_aplicacao.ui.theme.PrimeiraaplicacaoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrimeiraaplicacaoTheme {
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
fun Welcome(){
    Text(
        text = "Welcome"
    )
}

@Composable
fun Email(){
    Text(
        text = "Email"
    )
}

@Composable
fun Password(){
    Text(
        text = "Password"
    )
}
@Composable
fun Exercicio() {
    Column(){
        Text(text = "Welcome")
        Spacer(modifier = Modifier.height(48.dp))
        TextField(value = "", onValueChange = {}, label = { Text(text = "Email") })
        Spacer(modifier = Modifier.height(48.dp))
        TextField(value = "", onValueChange = {}, label = { Text(text = "Email") })
        Spacer(modifier = Modifier.height(48.dp))
        Text("Login");
    }

}

fun TextField(value: String, onValueChange: () -> Unit, Label: () -> Unit) {
    TODO("Not yet implemented")
}.dp


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrimeiraaplicacaoTheme {

        //Greeting("Android")

        Exercicio()
    }
}
