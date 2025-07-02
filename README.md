# kotlin-compose-learning
Repositório para estudos e experimentos com Kotlin e Jetpack Compose, explorando construção de interfaces declarativas, gerenciamento de estado e criação de componentes modernos para Android.

<h2 style="border-left: 5px solid #4CAF50; padding-left: 10px; color: #2E7D32;">
  🛠️ Iniciando com Kotlin e Jetpack Compose
</h2>

<h3> 1. Criar Projeto com Jetpack Compose no Android Studio</h3>

1. Abra o <strong>Android Studio</strong>
2. Clique em <strong>New Project → Empty Compose Activity</strong>
3. Defina o nome e o pacote do seu app
4. Finalize a criação — o projeto já estará configurado para Compose

<p><strong>Ou, caso esteja adicionando Compose em um projeto existente:</strong></p>

<pre><code class="language-groovy">
// build.gradle (nível do app)

plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
}

android {
    compileSdk 34

    defaultConfig {
        applicationId "com.seuapp.exemplo"
        minSdk 24
        targetSdk 34
    }

    buildFeatures {
        compose true
    }

    composeOptions {
        kotlinCompilerExtensionVersion '1.5.4' // Atualize conforme necessário
    }
}

dependencies {
    implementation 'androidx.activity:activity-compose:1.8.0'
    implementation 'androidx.compose.ui:ui:1.5.4'
    implementation 'androidx.compose.material3:material3:1.1.2'
    implementation 'androidx.compose.ui:ui-tooling-preview:1.5.4'
}
</code></pre>

<h3> 2. Criando sua Primeira UI com Compose</h3>

<p><strong>Exemplo básico:</strong></p>

<pre><code class="language-kotlin">
@Composable
fun Saudacao(nome: String) {
    Text(text = "Olá, $nome!")
}

@Composable
fun TelaPrincipal() {
    Column {
        Saudacao("Leonardo")
        Button(onClick = { /* ação */ }) {
            Text("Clique aqui")
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TelaPrincipal()
        }
    }
}
</code></pre>

<h3> 3. Visualizar sua UI na IDE</h3>

<p>Adicione um Preview:</p>

<pre><code class="language-kotlin">
@Preview(showBackground = true)
@Composable
fun PreviewTelaPrincipal() {
    TelaPrincipal()
}
</code></pre>

<p>O Android Studio exibirá a prévia da tela diretamente na IDE.</p>

<h3> 4. Comparação Rápida: XML vs Compose</h3>

<table border="1" cellspacing="0" cellpadding="5">
<tr>
<th>Modelo Antigo (XML)</th>
<th>Jetpack Compose</th>
</tr>
<tr>
<td>
&lt;TextView<br/>
&nbsp;&nbsp;android:id="@+id/textView"<br/>
&nbsp;&nbsp;android:text="Olá Mundo"/&gt;
</td>
<td>
@Composable<br/>
fun Saudacao() {<br/>
&nbsp;&nbsp;Text(text = "Olá Mundo")<br/>
}
</td>
</tr>
<tr>
<td>
Manipulação via <code>findViewById</code>
</td>
<td>
UI reativa: muda conforme o estado
</td>
</tr>
<tr>
<td>
Separação entre XML e código Kotlin
</td>
<td>
Tudo unificado em Kotlin puro
</td>
</tr>
</table>

<h2 style="border-left: 5px solid #2196F3; padding-left: 10px; color: #0D47A1;">
  🚀 Passo a Passo - Como o Compose funciona na MainActivity
</h2>

<h3>✅ 1. Herda de <code>ComponentActivity</code></h3>

<pre><code class="language-kotlin">
class MainActivity : ComponentActivity()
</code></pre>

<p><strong>ComponentActivity</strong> é uma Activity preparada para funcionar com Compose.</p>
<p>Substitui a antiga necessidade de usar <code>AppCompatActivity</code> + XML.</p>

<h3>✅ 2. Define o conteúdo da tela com <code>setContent { }</code></h3>

<pre><code class="language-kotlin">
setContent {
    // Sua interface 100% declarada em Kotlin
}
</code></pre>

<p>Dentro de <code>setContent { }</code>, você escreve a interface do app com funções Composables.</p>
<p>Não usa mais <code>setContentView</code> nem arquivos XML de layout.</p>

<h3>✅ 3. Usa Composables para construir a interface</h3>

<p>Exemplo:</p>

<pre><code class="language-kotlin">
Text("Olá, mundo!")
</code></pre>

<p><code>Text</code> é um Composable: cria um texto visível na tela.</p>
<p>Toda a tela é composta por Composables (<code>Text</code>, <code>Button</code>, <code>Column</code>, etc).</p>

<h3>✅ 4. Pode criar Composables próprios</h3>

<p>Exemplo:</p>

<pre><code class="language-kotlin">
@Composable
fun MinhaTela() {
    Text("Minha Interface")
}
</code></pre>

<p>Você define suas telas e componentes como funções Kotlin comuns, usando <code>@Composable</code>.</p>
<p>Essas funções podem ser usadas dentro de outras, como peças de Lego.</p>

<h3>✅ 5. Navegação entre Telas (Opcional)</h3>

<p>Com Navegação Compose:</p>

<pre><code class="language-kotlin">
val navController = rememberNavController()

NavHost(navController, startDestination = "tela1") {
    composable("tela1") { Tela1(navController) }
    composable("tela2") { Tela2(navController) }
}
</code></pre>

<p>Você organiza as telas como "rotas", usando o <code>NavController</code>.</p>
<p>Pode navegar entre as telas com: <code>navController.navigate("tela2")</code>.</p>

<h3>✅ 6. Compose cuida das Recomposições</h3>

<p>Quando o estado ou dados mudam, o Compose atualiza só o que precisa na tela.</p>
<p>Você não precisa gerenciar o ciclo de vida manualmente como no XML antigo.</p>

<p>Exemplo de recomposição:</p>

<pre><code class="language-kotlin">
var contador by remember { mutableStateOf(0) }

Button(onClick = { contador++ }) {
    Text("Cliquei $contador vezes")
}
</code></pre>

<p>Cada clique no botão atualiza o contador e o Compose redesenha só esse trecho da tela.</p>

<h2>🎯 Resumo Final do Fluxo</h2>

<ul>
  <li>✔️ <code>MainActivity</code> herda de <code>ComponentActivity</code>.</li>
  <li>✔️ <code>setContent { }</code> define o que aparece na tela, usando só Kotlin.</li>
  <li>✔️ Interface construída com funções Composables.</li>
  <li>✔️ Pode usar Navegação Compose para trocar de tela.</li>
  <li>✔️ Compose cuida automaticamente das atualizações da interface.</li>
</ul>
