package com.example.noticiasapi


import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.noticiasapi.app.data.remote.api.RetrofitInstance.api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Intent
import android.util.Log

import android.widget.TextView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.noticiasapi.app.presentation.news_list.screens.NewsDetailListScreen
import com.example.noticiasapi.app.presentation.news_list.screens.NewsListScreen
import com.example.noticiasapi.app.presentation.news_list.viewModel.NewsDetailListViewModel
import com.example.noticiasapi.app.presentation.news_list.viewModel.NewsListViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MainScreen()
        }
    }
}

//@Composable
//fun MainScreen() {
//    var selectedNewsId by remember { mutableStateOf<Int?>(null) }
//
//    if (selectedNewsId == null) {
//        // Obtain the instance of NewsListViewModel using viewModel()
//        val newsListViewModel: NewsListViewModel = viewModel()
//
//        // Pass the ViewModel and onItemClick handler to NewsListScreen
//        NewsListScreen(newsListViewModel) { newsId ->
//            selectedNewsId = newsId
//        }
//    } else {
//        // Obtain the instance of NewsDetailListViewModel using viewModel()
//        val newsDetailListViewModel: NewsDetailListViewModel = viewModel()
//
//        // Pass the ViewModel and a back handler to NewsDetailScreen
//        NewsDetailScreen(newsDetailListViewModel) {
//            selectedNewsId = null
//        }
//    }
//}


//
//private fun fetchNewsAndDisplay(container: LinearLayout){
//    CoroutineScope(Dispatchers.Main).launch {
//        try{
//            val response = api.getNews(
//                apiKey = "dbd175677492464fae34a6fb041753cc",
//                country = "us",
//                language = "en"
//            )
//            val newsList = response.body()?.news ?: emptyList()
//
//            for (news in newsList) {
//                val textView = TextView(this@MainActivity).apply {
//                    text = news.title
//                    textSize = 18f
//                    setPadding(16, 16, 16, 16)
//                    setOnClickListener {
//                        // Navigate to DetailActivity when clicked
//                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
//                        intent.putExtra("newsId", news.id)
//                        startActivity(intent)
//                    }
//                }
//                container.addView(textView)
//            }
//        } catch (e: Exception) {
//            val errorTextView = TextView(this@MainActivity).apply {
//                text = "Failed to load news: ${e.message}"
//                textSize = 16f
//                setPadding(16, 16, 16, 16)
//            }
//            container.addView(errorTextView)
//        }
//    }
//}






//class MainActivity : ComponentActivity() {
//
//    private lateinit var studentViewModel: StudentViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val database = AppDatabase.getDatabase(applicationContext)
//        val repository = StudentRepository(database.studentDao())
//        studentViewModel = StudentViewModel(repository)
//        enableEdgeToEdge()
//        setContent {
//            StudentsTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    StudentListScreen(studentViewModel, modifier = Modifier.padding(innerPadding))
//                }
//            }
//        }
//    }
//}

/**
 *
 * Desenvolver uma aplicação que lista e apresenta as noticias a partir da
 * API
 *
 * Um ecrã com listagem de notícias
 *
 * Um ecrã com a descrição/pormenor da notícia
 *
 * Utilizar as Top Stories da API do NYT (com as categorias
 */



//@Composable
//fun MainScreen() {
//    var selectedNewsId by remember { mutableStateOf<String?>(null) }
//
//    if (selectedNewsId == null) {
//        val newsListViewModel: NewsListViewModel = viewModel()
//        NewsListScreen(newsListViewModel) { newsId ->
//            selectedNewsId = newsId
//        }
//    } else {
//        val newsDetailViewModel: NewsDetailListViewModel = viewModel()
//        NewsDetailsScreen(newsDetailViewModel, selectedNewsId!!) {
//            selectedNewsId = null
//        }
//    }
//}



//val newsApi = RetrofitInstance.api
//
//val newsList = newsApi.getNews(apiToken = "75gcsHoIspuVi2tAM5PXLEEp4wOtnljFLTeykaPM")
//val newsDetail = newsApi.getNewsDetail(newsId = "12345", apiToken = "75gcsHoIspuVi2tAM5PXLEEp4wOtnljFLTeykaPM")






//@Composable
//fun EcraInicio(navController: NavController) {
//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//        Text("Welcome to the Start Screen") // Additional Text
//
//        Spacer(modifier = Modifier.height(16.dp)) // Adds space between elements
//
//        Button(onClick = { navController.navigate("destino") }) {
//            Text("Ir para o destino")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp)) // Adds space between elements
//
//        Button(onClick = { navController.navigate("details") }) {
//            Text("Go to details") // Another button for navigating to details
//        }
//    }
//}
//
//
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    val navController = rememberNavController()
//
//    ExerciciosTheme {
//        NavegacaoApp3()
//
//    }
//}
//
//
////coisas carro
//
//data class Carro(
//    val marca: String,
//    val nome: String,
//    val modelo: String,
//    val ano: Int
//)
//
//@Composable
//fun CarroDropdown() {
//    val marcas = listOf("Toyota", "Honda", "Ford")
//
//    val carros = listOf(
//        Carro("Toyota","Toyota", "Corolla", 2020),
//        Carro("Toyota","Toyota", "Camry", 2019),
//        Carro("Honda","Honda", "Civic", 2018),
//        Carro("Honda","Honda", "Accord", 2021),
//        Carro("Ford","Ford", "Focus", 2021),
//        Carro("Ford","Ford", "Mustang", 2022)
//    )
//
//    var expandedMarca by remember { mutableStateOf(false) }
//    var expandedCarro by remember { mutableStateOf(false) }
//    var marcaSelecionada by remember { mutableStateOf<String?>(null) }
//    var carrosFiltrados by remember { mutableStateOf<List<Carro>>(emptyList()) }
//
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            // Seletor de marcas
//            Button(onClick = { expandedMarca = true }) {
//                Text(text = marcaSelecionada ?: "Selecione uma marca")
//            }
//
//            DropdownMenu(
//                expanded = expandedMarca,
//                onDismissRequest = { expandedMarca = false }
//            ) {
//                marcas.forEach { marca ->
//                    DropdownMenuItem(
//                        text = { Text(text = marca) },
//                        onClick = {
//                            marcaSelecionada = marca
//                            expandedMarca = false
//
//                            // Filtra os carros pela marca selecionada
//                            carrosFiltrados = carros.filter { it.marca == marca }
//                        }
//                    )
//                }
//            }
//
//            // Exibir lista de carros da marca selecionada
//            if (carrosFiltrados.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(16.dp)) // Espaço entre o botão e a lista de carros
//                Text(text = "Carros da marca ${marcaSelecionada}:")
//                carrosFiltrados.forEach { carro ->
//                    Text(
//                        text = "${carro.modelo} (${carro.ano})",
//                        modifier = Modifier.padding(4.dp)
//                    )
//                }
//            } else if (marcaSelecionada != null) {
//                Text(text = "Nenhum carro encontrado para a marca ${marcaSelecionada}.")
//            }
//        }
//    }
//}
//
//@Composable
//fun CarrosPorMarcaScreenPage(marca: String, carros: List<Carro>, navController: NavController) {
//    // Filtrar a lista de carros com base na marca
//    val carrosFiltrados = carros.filter { it.marca == marca }
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center // Alinhamento vertical no centro
//    ) {
//        Text(text = "Carros da marca $marca", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        if (carrosFiltrados.isNotEmpty()) {
//            carrosFiltrados.forEach { carro ->
//                Text(text = "${carro.modelo} (${carro.ano})", modifier = Modifier.padding(4.dp))
//            }
//        } else {
//            Text(text = "Nenhum carro encontrado para a marca $marca.")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp)) // Espaço entre a lista de carros e o botão
//
//        Button(onClick = { navController.popBackStack() }) {
//            Text(text = "Voltar")
//        }
//    }
//}
//
//
//
//// Adicione isso na sua classe MainActivity
//@Composable
//fun NavegacaoApp3() {
//    val navController = rememberNavController()
//    val carros = remember { mutableStateListOf<Carro>() } // Lista de carros
//
//    NavHost(navController = navController, startDestination = "inicio") {
//        composable("inicio") {
//            EcraInicio(navController, carros)
//        }
//        composable("destino") {
//            EcraDestino(navController, carros)
//        }
//        composable("adicionar_carro") {
//            AdicionarCarroScreen(navController, carros)
//        }
//
//        composable("carrosPorMarca/{marca}") { backStackEntry ->
//            val marca = backStackEntry.arguments?.getString("marca") ?: ""
//            CarrosPorMarcaScreenPage(marca, carros, navController)
//        }
//    }
//}
//
//
//@Composable
//fun EcraInicio(navController: NavController, carros: SnapshotStateList<Carro>) {
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Welcome to the Start Screen")
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = { navController.navigate("destino") }) {
//            Text("Ir para o destino")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = { navController.navigate("adicionar_carro") }) {
//            Text("Adicionar Carro") // Botão para ir à tela de adição de carro
//        }
//    }
//}
//
//
//@Composable
//fun EcraDestino(navController: NavController, carros: SnapshotStateList<Carro>) {
//    var expanded by remember { mutableStateOf(false) }
//    var marcaSelecionada by remember { mutableStateOf<String?>(null) }
//
//    val marcas = carros.map { it.marca }.distinct() // Marcas únicas
//
//    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
//        Button(onClick = { expanded = true }) {
//            Text(text = marcaSelecionada ?: "Selecione uma marca")
//        }
//
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//            marcas.forEach { marca ->
//                DropdownMenuItem(
//                    text = { Text(text = marca) },
//                    onClick = {
//                        marcaSelecionada = marca
//                        expanded = false
//                        navController.navigate("carrosPorMarca/$marca") // Navegar para a nova tela
//                    }
//                )
//            }
//        }
//
//        // Exibir carros da marca selecionada
//        marcaSelecionada?.let { marca ->
//            val carrosDaMarca = carros.filter { it.marca == marca }
//            if (carrosDaMarca.isNotEmpty()) {
//                Text("Carros da marca $marca:")
//                for (carro in carrosDaMarca) {
//                    Text("${carro.nome} ${carro.modelo} (${carro.ano})")
//                }
//            } else {
//                Text("Nenhum carro encontrado para a marca $marca.")
//            }
//        }
//    }
//}
//
//
//@Composable
//fun AdicionarCarroScreen(navController: NavController, carros: SnapshotStateList<Carro>) {
//    var marca by remember { mutableStateOf("") }
//    var nome by remember { mutableStateOf("") }
//    var modelo by remember { mutableStateOf("") }
//    var ano by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        TextField(
//            value = marca,
//            onValueChange = { marca = it },
//            label = { Text("Marca") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = nome,
//            onValueChange = { nome = it },
//            label = { Text("Nome") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = modelo,
//            onValueChange = { modelo = it },
//            label = { Text("Modelo") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = ano,
//            onValueChange = { ano = it },
//            label = { Text("Ano") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Button(onClick = {
//            val anoInt = ano.toIntOrNull()
//            if (!marca.isBlank() && !nome.isBlank() && !modelo.isBlank() && anoInt != null) {
//                carros.add(Carro(marca, nome, modelo, anoInt))
//                navController.popBackStack() // Volta para a tela anterior
//            } else {
//                // Adicionar um feedback para o usuário sobre a entrada inválida
//            }
//        }) {
//            Text("Adicionar Carro")
//        }
//    }
//}


@Composable
fun MainScreen() {
    var selectedNewsId by remember { mutableStateOf<Int?>(null) }

    if (selectedNewsId == null) {
        val newsListViewModel: NewsListViewModel = viewModel()
        NewsListScreen (newsListViewModel) { newsId ->
            selectedNewsId = newsId
        }
    } else {
        val newsDetailViewModel: NewsDetailListViewModel = viewModel()
        NewsDetailListScreen (newsDetailViewModel,selectedNewsId!!) {
            selectedNewsId = null
        }
    }
}