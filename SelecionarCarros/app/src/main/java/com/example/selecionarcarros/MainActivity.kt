package com.example.exercicios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.selecionarcarros.ui.theme.SelecionarCarrosTheme

data class Carro(
    val marca: String,
    val nome: String,
    val modelo: String,
    val ano: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SelecionarCarrosTheme {
                NavegacaoApp()
            }
        }
    }
}

@Composable
fun NavegacaoApp() {
    val navController = rememberNavController()
    val carros = remember { mutableStateListOf<Carro>() } // Lista de carros

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            EcraInicio(navController, carros)
        }
        composable("destino") {
            EcraDestino(navController, carros)
        }

        composable("adicionar_carro") {
            AdicionarCarroScreen(navController, carros)
        }
        composable("carrosPorMarca/{marca}") { backStackEntry ->
            val marca = backStackEntry.arguments?.getString("marca")
            if (marca != null) {
                EcraCarrosPorMarca(navController, carros, marca)
            } else {
                // Handle null marca
                Text("Marca não encontrada", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun EcraInicio(navController: NavController, carros: SnapshotStateList<Carro>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome to the Start Screen")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("destino") }) {
            Text("Ir para o destino")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("adicionar_carro") }) {
            Text("Adicionar Carro") // Botão para ir à tela de adição de carro
        }
    }
}

@Composable
fun EcraDestino(navController: NavController, carros: SnapshotStateList<Carro>) {
    var expanded by remember { mutableStateOf(false) }
    var marcaSelecionada by remember { mutableStateOf<String?>(null) }

    val marcas = carros.map { it.marca }.distinct() // Marcas únicas

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { expanded = true }) {
            Text(text = marcaSelecionada ?: "Selecione uma marca")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (marcas.isNotEmpty()) { // Verifique se há marcas disponíveis
                marcas.forEach { marca ->
                    DropdownMenuItem(
                        text = { Text(text = marca) },
                        onClick = {
                            marcaSelecionada = marca
                            expanded = false
                            navController.navigate("carrosPorMarca/$marca") // Navegar para a nova tela
                        }
                    )
                }
            } else {
                DropdownMenuItem(
                    text = { Text(text = "Nenhuma marca disponivel") },
                    onClick = {
                        expanded = false
                    }
                )
            }
        }

        // Exibir carros da marca selecionada
        marcaSelecionada?.let { marca ->
            val carrosDaMarca = carros.filter { it.marca == marca }
            if (carrosDaMarca.isNotEmpty()) {
                Text("Carros da marca $marca:")
                for (carro in carrosDaMarca) {
                    Text("${carro.nome} ${carro.modelo} (${carro.ano})")
                }
            } else {
                Text("Nenhum carro encontrado para a marca $marca.")
            }
        }
    }
}

// Nova Composable para exibir carros da marca selecionada
@Composable
fun EcraCarrosPorMarca(navController: NavController, carros: SnapshotStateList<Carro>, marca: String) {
    val carrosDaMarca = carros.filter { it.marca == marca }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        Text("Carros da marca $marca:")
        if (carrosDaMarca.isNotEmpty()) {
            for (carro in carrosDaMarca) {
                Text("${carro.nome} ${carro.modelo} (${carro.ano})")
            }
        } else {
            Text("Nenhum carro encontrado para a marca $marca.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Voltar")
        }
    }
}

@Composable
fun AdicionarCarroScreen(navController: NavController, carros: SnapshotStateList<Carro>) {
    var marca by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = marca,
            onValueChange = { marca = it },
            label = { Text("Marca") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = modelo,
            onValueChange = { modelo = it },
            label = { Text("Modelo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = ano,
            onValueChange = { ano = it },
            label = { Text("Ano") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val anoInt = ano.toIntOrNull()
            if (!marca.isBlank() && !nome.isBlank() && !modelo.isBlank() && anoInt != null) {
                carros.add(Carro(marca, nome, modelo, anoInt))
                navController.popBackStack() // Volta para a tela anterior
            } else {
                // Adicionar um feedback para o usuário sobre a entrada inválida
            }
        }) {
            Text("Adicionar Carro")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    SelecionarCarrosTheme {
        NavegacaoApp() // Previsualiza o app inteiro
    }
}
