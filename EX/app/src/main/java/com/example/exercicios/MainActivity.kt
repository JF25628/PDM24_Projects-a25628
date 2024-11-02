package com.example.exercicios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import androidx.constraintlayout.widget.ConstraintLayout
import com.example.exercicios.ui.theme.ExerciciosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExerciciosTheme {
                NavegacaoApp3()
            }
        }
    }
}


@Composable
fun EcraInicio(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Welcome to the Start Screen") // Additional Text

        Spacer(modifier = Modifier.height(16.dp)) // Adds space between elements

        Button(onClick = { navController.navigate("destino") }) {
            Text("Ir para o destino")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Adds space between elements

        Button(onClick = { navController.navigate("details") }) {
            Text("Go to details") // Another button for navigating to details
        }
    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()

    ExerciciosTheme {
        NavegacaoApp3()

    }
}


//coisas carro

data class Carro(
    val marca: String,
    val nome: String,
    val modelo: String,
    val ano: Int
)

@Composable
fun CarroDropdown() {
    val marcas = listOf("Toyota", "Honda", "Ford")

    val carros = listOf(
        Carro("Toyota","Toyota", "Corolla", 2020),
        Carro("Toyota","Toyota", "Camry", 2019),
        Carro("Honda","Honda", "Civic", 2018),
        Carro("Honda","Honda", "Accord", 2021),
        Carro("Ford","Ford", "Focus", 2021),
        Carro("Ford","Ford", "Mustang", 2022)
    )

    var expandedMarca by remember { mutableStateOf(false) }
    var expandedCarro by remember { mutableStateOf(false) }
    var marcaSelecionada by remember { mutableStateOf<String?>(null) }
    var carrosFiltrados by remember { mutableStateOf<List<Carro>>(emptyList()) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Seletor de marcas
            Button(onClick = { expandedMarca = true }) {
                Text(text = marcaSelecionada ?: "Selecione uma marca")
            }

            DropdownMenu(
                expanded = expandedMarca,
                onDismissRequest = { expandedMarca = false }
            ) {
                marcas.forEach { marca ->
                    DropdownMenuItem(
                        text = { Text(text = marca) },
                        onClick = {
                            marcaSelecionada = marca
                            expandedMarca = false

                            // Filtra os carros pela marca selecionada
                            carrosFiltrados = carros.filter { it.marca == marca }
                        }
                    )
                }
            }

            // Exibir lista de carros da marca selecionada
            if (carrosFiltrados.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp)) // Espaço entre o botão e a lista de carros
                Text(text = "Carros da marca ${marcaSelecionada}:")
                carrosFiltrados.forEach { carro ->
                    Text(
                        text = "${carro.modelo} (${carro.ano})",
                        modifier = Modifier.padding(4.dp)
                    )
                }
            } else if (marcaSelecionada != null) {
                Text(text = "Nenhum carro encontrado para a marca ${marcaSelecionada}.")
            }
        }
    }
}

@Composable
fun CarrosPorMarcaScreenPage(marca: String, carros: List<Carro>, navController: NavController) {
    // Filtrar a lista de carros com base na marca
    val carrosFiltrados = carros.filter { it.marca == marca }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Alinhamento vertical no centro
    ) {
        Text(text = "Carros da marca $marca", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(8.dp))

        if (carrosFiltrados.isNotEmpty()) {
            carrosFiltrados.forEach { carro ->
                Text(text = "${carro.modelo} (${carro.ano})", modifier = Modifier.padding(4.dp))
            }
        } else {
            Text(text = "Nenhum carro encontrado para a marca $marca.")
        }

        Spacer(modifier = Modifier.height(16.dp)) // Espaço entre a lista de carros e o botão

        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Voltar")
        }
    }
}



// Adicione isso na sua classe MainActivity
@Composable
fun NavegacaoApp3() {
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
            val marca = backStackEntry.arguments?.getString("marca") ?: ""
            CarrosPorMarcaScreenPage(marca, carros, navController)
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

