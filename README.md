<h1 align="center">📱 kotlin-compose-learning</h1>

<p align="center">
  Repositório dedicado a experimentos e estudos com <strong>Kotlin</strong> e <strong>Jetpack Compose</strong>.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.9.x-blue?logo=kotlin"/>
  <img src="https://img.shields.io/badge/Jetpack%20Compose-Material3-orange?logo=android"/>
  <img src="https://img.shields.io/badge/Android%20Studio-Giraffe-green?logo=android"/>
</p>

<hr/>

<h2>🚀 Sobre o Projeto</h2>

<p>Este projeto é voltado para aprendizado prático de Jetpack Compose e seus principais conceitos, como:</p>
<ul>
  <li>✅ Construção de interfaces declarativas</li>
  <li>✅ Gerenciamento de estado reativo</li>
  <li>✅ Navegação entre telas com rotas nomeadas</li>
  <li>✅ Criação de componentes reutilizáveis</li>
  <li>✅ Uso de <code>Modifier</code> para personalização visual</li>
  <li>✅ Composição de layouts com Material Design 3</li>
</ul>

<hr/>

<h2>🧱 O que é Jetpack Compose?</h2>

<p>
  Jetpack Compose é o toolkit moderno do Android para construção de interfaces de forma
  <strong>100% declarativa em Kotlin</strong> — eliminando a necessidade de XML e <code>findViewById</code>.
</p>

<ul>
  <li>Código mais conciso e expressivo</li>
  <li>Gerenciamento simples de navegação e estado</li>
  <li>Alta reutilização e preview de componentes</li>
  <li>Total integração com ViewModel, LiveData e Flow</li>
</ul>

<hr/>

<h2>🛠️ Como Criar um Projeto Compose</h2>

<h3>✅ Novo Projeto</h3>
<ol>
  <li>Abrir o <strong>Android Studio</strong></li>
  <li>Selecionar <strong>New Project → Empty Compose Activity</strong></li>
  <li>Configurar nome, pacote e versão do SDK</li>
  <li>Pronto! Compose já estará habilitado</li>
</ol>

<h3>🛠️ Projeto Existente</h3>

<pre><code>
// build.gradle (módulo app)
android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = '1.5.4'
    }
}

dependencies {
    implementation 'androidx.activity:activity-compose:1.8.0'
    implementation 'androidx.compose.ui:ui:1.5.4'
    implementation 'androidx.compose.material3:material3:1.1.2'
    implementation 'androidx.compose.ui:ui-tooling-preview:1.5.4'
}
</code></pre>

<hr/>

<h2>✨ Exemplo Simples de Composable</h2>

<pre><code>
@Composable
fun Saudacao(nome: String) {
    Text(text = "Olá, $nome!")
}

@Preview
@Composable
fun PreviewSaudacao() {
    Saudacao("Leonardo")
}
</code></pre>

<hr/>

<h2>🧠 Gerenciamento de Estado</h2>

<pre><code>
@Composable
fun Contador() {
    var contador by remember { mutableStateOf(0) }

    Button(onClick = { contador++ }) {
        Text("Cliquei $contador vezes")
    }
}
</code></pre>

<hr/>

<h2>⚖️ Comparativo XML vs Jetpack Compose</h2>

<table border="1" cellspacing="0" cellpadding="6">
  <thead>
    <tr>
      <th>XML Tradicional</th>
      <th>Jetpack Compose</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>&lt;TextView android:text="Olá Mundo"/&gt;</td>
      <td><code>Text("Olá Mundo")</code></td>
    </tr>
    <tr>
      <td><code>findViewById&lt;Button&gt;(R.id.btn)</code></td>
      <td><code>Button(onClick = { })</code></td>
    </tr>
    <tr>
      <td>Separação entre XML e lógica</td>
      <td>Kotlin puro, unificado</td>
    </tr>
    <tr>
      <td>Gerenciamento manual de ciclo de vida</td>
      <td>Recomposição automática</td>
    </tr>
    <tr>
      <td>Layouts XML (LinearLayout, RelativeLayout)</td>
      <td><code>Column</code>, <code>Row</code>, <code>Box</code></td>
    </tr>
  </tbody>
</table>

<hr/>

<h2>🔧 Uso de Modifier</h2>

<pre><code>
Text(
    text = "Texto de exemplo",
    modifier = Modifier
        .padding(16.dp)
        .background(Color.LightGray)
        .fillMaxWidth()
        .clickable { println("Clicou!") }
)
</code></pre>

<ul>
  <li><code>padding</code>: espaçamento interno</li>
  <li><code>background</code>: cor de fundo</li>
  <li><code>fillMaxWidth</code>: ocupa toda a largura disponível</li>
  <li><code>clickable</code>: torna clicável</li>
</ul>

<hr/>

<h2>🧩 Componentes Comuns</h2>

<table border="1" cellspacing="0" cellpadding="6">
  <tr><th>Componente</th><th>Descrição</th></tr>
  <tr><td><code>Text</code></td><td>Exibe texto</td></tr>
  <tr><td><code>Button</code></td><td>Botão clicável</td></tr>
  <tr><td><code>TextField</code></td><td>Campo de entrada de texto</td></tr>
  <tr><td><code>LazyColumn</code></td><td>Lista otimizada (RecyclerView)</td></tr>
  <tr><td><code>Scaffold</code></td><td>Layout com TopBar, FAB, etc</td></tr>
  <tr><td><code>Box</code>, <code>Column</code>, <code>Row</code></td><td>Layouts flexíveis</td></tr>
</table>

<hr/>

<h2>🔀 Navegação com Compose</h2>

<pre><code>
val navController = rememberNavController()

NavHost(navController, startDestination = "home") {
    composable("home") { HomeScreen(navController) }
    composable("detalhe") { DetalheScreen(navController) }
}

navController.navigate("detalhe")
</code></pre>

<hr/>

<h2>🎨 Estilo com MaterialTheme</h2>

<pre><code>
MaterialTheme(
    colorScheme = lightColorScheme(
        primary = Color(0xFF4CAF50),
        secondary = Color(0xFF81C784)
    )
) {
    TelaPrincipal()
}
</code></pre>

<hr/>

<h2>📋 Exemplo: Lista de Tarefas</h2>

<h3>📄 Listagem</h3>

<pre><code>
Scaffold(
    topBar = { TopAppBar(title = { Text("Minhas Tarefas") }) },
    floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate("novaTarefa") }) {
            Icon(Icons.Default.Add, contentDescription = "Adicionar")
        }
    }
) {
    LazyColumn {
        items(tarefas) { tarefa ->
            Text(tarefa.titulo)
        }
    }
}
</code></pre>

<h3>📝 Cadastro</h3>

<pre><code>
var titulo by remember { mutableStateOf("") }

TextField(value = titulo, onValueChange = { titulo = it })
RadioButton(selected = prioridade == "Alta", onClick = { prioridade = "Alta" })
Button(onClick = { navController.navigate("listaTarefas") }) {
    Text("Salvar")
}
</code></pre>

<hr/>

<h2>💡 Boas Práticas</h2>

<ul>
  <li>🧩 Separe componentes em arquivos reutilizáveis</li>
  <li>🛠️ Use <code>Modifier</code> como parâmetro padrão</li>
  <li>🔁 Prefira <code>remember</code> para estados locais</li>
  <li>🎯 Use <code>ViewModel</code> para lógica de negócio</li>
  <li>👀 Adicione <code>@Preview</code> para testes visuais</li>
</ul>

<hr/>

<h2>✅ Conclusão</h2>

<ul>
  <li>✔️ Interfaces 100% Kotlin</li>
  <li>✔️ Sem necessidade de XML</li>
  <li>✔️ Estados e recomposição automáticos</li>
  <li>✔️ Componentes reutilizáveis e responsivos</li>
</ul>

<hr/>

<h2>📚 Referências</h2>

<ul>
  <li><a href="https://developer.android.com/jetpack/compose">Documentação oficial do Jetpack Compose</a></li>
  <li><a href="https://developer.android.com/jetpack/compose/designsystems/material3">Guia do Material 3</a></li>
</ul>
