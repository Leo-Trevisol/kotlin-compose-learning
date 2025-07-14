<h1>📱 kotlin-compose-learning</h1>

<p>Repositório para estudos e experimentos com <strong>Kotlin</strong> e <strong>Jetpack Compose</strong>, explorando:</p>
<ul>
  <li>Construção de interfaces declarativas</li>
  <li>Gerenciamento de estado reativo</li>
  <li>Navegação entre telas</li>
  <li>Criação de componentes reutilizáveis</li>
</ul>

<hr/>

<h2>🧱 O que é Jetpack Compose?</h2>
<p>
  Jetpack Compose é o moderno toolkit da Google para criar interfaces no Android de forma
  <strong>100% declarativa com Kotlin</strong> — sem XML.
  Ele substitui o modelo tradicional baseado em <code>View</code> e <code>findViewById</code>.
</p>

<hr/>

<h2>🛠️ Criando um Projeto com Jetpack Compose</h2>

<h3>Projeto Novo</h3>
<ol>
  <li>Abra o <strong>Android Studio</strong></li>
  <li>Clique em <strong>New Project → Empty Compose Activity</strong></li>
  <li>Configure nome e pacote</li>
  <li>O projeto já estará pronto para usar Compose</li>
</ol>

<h3>Projeto Existente</h3>

<pre><code>
// build.gradle (nível do app)
android {
    ...
    buildFeatures {
        compose true
    }

    composeOptions {
        kotlinCompilerExtensionVersion '1.5.4'
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

<h2>✨ Primeira Interface com Compose</h2>

<pre><code>
@Composable
fun Saudacao(nome: String) {
    Text(text = "Olá, $nome!")
}

@Composable
fun TelaPrincipal() {
    Column(modifier = Modifier.padding(16.dp)) {
        Saudacao("Leonardo")
        Button(onClick = { /* ação */ }) {
            Text("Clique aqui")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaPrincipal() {
    TelaPrincipal()
}
</code></pre>

<hr/>

<h2>🔄 Gerenciamento de Estado Reativo</h2>

<pre><code>
@Composable
fun Contador() {
    var contador by remember { mutableStateOf(0) }

    Button(onClick = { contador++ }) {
        Text("Cliquei $contador vezes")
    }
}
</code></pre>

<p>
  Mudanças no estado (como <code>contador++</code>) fazem o Compose redesenhar apenas o necessário.
</p>

<hr/>

<h2>⚖️ Comparativo: XML vs Jetpack Compose</h2>

<table border="1" cellspacing="0" cellpadding="6">
  <tr>
    <th>XML Tradicional</th>
    <th>Jetpack Compose</th>
  </tr>
  <tr>
    <td><code>&lt;TextView android:text="Olá Mundo"/&gt;</code></td>
    <td><code>Text("Olá Mundo")</code></td>
  </tr>
  <tr>
    <td><code>findViewById&lt;Button&gt;(R.id.btn)</code></td>
    <td><code>Button(onClick = { })</code></td>
  </tr>
  <tr>
    <td>Separação entre XML e código</td>
    <td>Kotlin puro, 100% integrado</td>
  </tr>
  <tr>
    <td>Gerenciamento manual de ciclo de vida</td>
    <td>Recomposição automática</td>
  </tr>
</table>

<hr/>

<h2>🧩 Componentes Comuns do Compose</h2>

<table border="1" cellspacing="0" cellpadding="6">
  <tr>
    <th>Componente</th>
    <th>Uso</th>
  </tr>
  <tr>
    <td><code>Text</code></td>
    <td>Exibe texto</td>
  </tr>
  <tr>
    <td><code>Button</code></td>
    <td>Botão clicável</td>
  </tr>
  <tr>
    <td><code>Column</code> / <code>Row</code></td>
    <td>Layout vertical / horizontal</td>
  </tr>
  <tr>
    <td><code>LazyColumn</code></td>
    <td>Lista performática (como RecyclerView)</td>
  </tr>
  <tr>
    <td><code>Box</code></td>
    <td>Sobreposição de elementos</td>
  </tr>
  <tr>
    <td><code>Scaffold</code></td>
    <td>Layout padrão com TopBar, FAB e conteúdo</td>
  </tr>
  <tr>
    <td><code>Modifier</code></td>
    <td>Estilo, padding, clique, etc</td>
  </tr>
</table>

<hr/>

<h2>🚀 Funcionamento da MainActivity</h2>

<pre><code>
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaPrincipal()
        }
    }
}
</code></pre>

<ul>
  <li><strong>ComponentActivity</strong>: base ideal para Compose</li>
  <li><strong>setContent {}</strong>: define a interface da tela sem XML</li>
</ul>

<hr/>

<h2>🔀 Navegação com Compose</h2>

<pre><code>
val navController = rememberNavController()

NavHost(navController, startDestination = "listTasks") {
    composable("listTasks") { ListTask(navController) }
    composable("saveTasks") { SaveTask(navController) }
}

// Navegar para outra tela
navController.navigate("saveTasks")
</code></pre>

<hr/>

<h2>🧠 Recomposição Inteligente</h2>

<pre><code>
var titulo by remember { mutableStateOf("") }

TextField(value = titulo, onValueChange = { titulo = it })
</code></pre>

<p>Quando o valor de <code>titulo</code> muda, o Compose redesenha apenas os componentes afetados.</p>

<hr/>

<h2>📋 Exemplo: Lista e Formulário</h2>

<h3>📄 Tela de Listagem</h3>

<pre><code>
Scaffold(
    topBar = { TopAppBar(title = { Text("Minhas Tarefas") }) },
    floatingActionButton = {
        FloatingActionButton(onClick = { /* navegar */ }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
) { innerPadding ->
    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        items(taskList) { task ->
            TaskItem(task)
        }
    }
}
</code></pre>

<h3>📝 Tela de Cadastro</h3>

<pre><code>
var titulo by remember { mutableStateOf("") }

TextField(value = titulo, onValueChange = { titulo = it })
RadioButton(selected = prioridade == "Alta", onClick = { prioridade = "Alta" })
Button(onClick = { navController.navigate("listTasks") }) {
    Text("Salvar")
}
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

<h2>✅ Resumo Final</h2>

<ul>
  <li>✔️ Interface declarada 100% com Kotlin</li>
  <li>✔️ Composables reutilizáveis e modulares</li>
  <li>✔️ Navegação com rotas nomeadas</li>
  <li>✔️ Estado reativo com recomposição automática</li>
  <li>✔️ Sem necessidade de XML ou <code>findViewById</code></li>
</ul>

<hr/>
