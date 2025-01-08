package com.example.calculator.Models

class Tokenizer(val input: String)
{
    var pos = 0
    var token: String? = null

    fun nextToken()
    {
        while (pos < input.length && input[pos].isWhitespace())
        {
            pos++
        }

        if (pos == input.length)
        {
            token = null
            return
        }

        if (input[pos].isDigit() || input[pos] == '.')
        {
            val sb = StringBuilder()
            while (pos < input.length && (input[pos].isDigit() || input[pos] == '.'))
            {
                sb.append(input[pos])
                pos++
            }

            token = sb.toString()
            return
        }

        if ("+-×÷()".contains(input[pos]))
        {

            token = input[pos].toString()
            pos++
            return
        }

        throw IllegalArgumentException("Caracter Invalido: ${input[pos]}")
    }
}


fun evaluate(expression: String): Double {

    val tokenizer = Tokenizer(expression)

    tokenizer.nextToken()

    return parseExpression(tokenizer)
}

fun parseExpression(tokenizer: Tokenizer): Double
{
    var result = parseTerm(tokenizer)

    while (tokenizer.token in listOf("+", "-"))
    {
        val op = tokenizer.token!!
        tokenizer.nextToken()
        val term = parseTerm(tokenizer)

        result = when (op)
        {
            "+" -> result + term
            "-" -> result - term
            else -> throw IllegalStateException("Operador invalido: $op")
        }
    }

    return result
}

fun parseTerm(tokenizer: Tokenizer): Double
{
    var result = parseFactor(tokenizer)
    while (tokenizer.token in listOf("×", "÷"))
    {
        val op = tokenizer.token!!
        tokenizer.nextToken()
        val factor = parseFactor(tokenizer)

        result = when (op)
        {
            "×" -> result * factor
            "÷" -> result / factor
            else -> throw IllegalStateException("Operador invalido: $op")
        }
    }

    return result
}

fun parseFactor(tokenizer: Tokenizer): Double
{

    if (tokenizer.token == null)
    {
        throw IllegalArgumentException("Operador invalido:")
    }

    if (tokenizer.token!!.toDoubleOrNull() != null)
    {

        val value = tokenizer.token!!.toDouble()

        tokenizer.nextToken()

        return value
    }

    if (tokenizer.token in listOf("+", "-"))
    {

        val op = tokenizer.token!!

        tokenizer.nextToken()

        val factor = parseFactor(tokenizer)

        return when (op)
        {
            "+" -> +factor
            "-" -> -factor
            else -> throw IllegalStateException("Operador invalido: $op")
        }
    }

    if (tokenizer.token == "(" && tokenizer.input.indexOf(")", tokenizer.pos) != -1)
    {
        tokenizer.nextToken()

        val value = parseExpression(tokenizer)

        if (tokenizer.token == ")")
        {
            tokenizer.nextToken()

            return value
        }
        else
        {
            throw IllegalArgumentException("Falta o parentesis para fechar")
        }
    }
    else
    {
        throw IllegalArgumentException("Fator invalido: ${tokenizer.token}")
    }
}