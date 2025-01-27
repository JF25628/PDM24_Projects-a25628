package com.example.calculator.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.Models.evaluate


class CalculatorViewModel: ViewModel()
{
    val expression = mutableStateOf("")

    fun clear() {
        expression.value = ""
    }

    fun append(char: String)
    {
        Log.d("append", "$char Expression Value:${expression.value}")

        if (char in "0123456789")
        {
            expression.value += char
        }
        else if(char in "+-×÷")
        {
            if (expression.value.isNotEmpty())
            {
                val lastChar = expression.value.last()

                if (lastChar in "+-×÷")
                {
                    expression.value = expression.value.dropLast(1)
                }
            }

            expression.value += char
        }
        else if(char == ".")
        {
            if (expression.value.isNotEmpty())
            {
                val lastChar = expression.value.last()
                if (lastChar!='.')
                {

                    if (lastChar in "+-×÷")
                    {
                        expression.value += "0"
                    }

                    expression.value += char
                }
            }

        }
        else if(char =="(")
        {
            if (expression.value.isNotEmpty())
            {
                val lastChar = expression.value.last()

                if (lastChar !in "+-×÷")
                {
                    expression.value += "×"
                }
            }

            expression.value += char

        }
        else if(char ==")")
        {
            expression.value += char
        }
    }

    fun delete()
    {
        if (expression.value.isNotEmpty())
        {
            expression.value = expression.value.dropLast(1)
        }
    }

    fun evaluate()
    {
        expression.value = try
        {
            val result = evaluate(expression.value)
            result.toString()
        }
        catch (e: Exception)
        {
            "Error"
        }
    }
}
