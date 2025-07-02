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

<h3> 2. Criando Primeira UI com Compose</h3>

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