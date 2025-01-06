package com.example.calculator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.calculator.ui.Screen
import com.example.calculator.ui.CalculatorViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel = CalculatorViewModel()

            Screen(
                viewModel = viewModel
            )

        }
    }
}
