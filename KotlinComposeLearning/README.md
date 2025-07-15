<h1>TasksList - App de Gerenciamento de Tarefas com Kotlin e Jetpack Compose</h1>

<h2>🚀 Descrição</h2>
<p><strong>TasksList</strong> é um app Android simples e funcional para gerenciar tarefas, desenvolvido 100% com <strong>Kotlin</strong> e <strong>Jetpack Compose</strong>.</p>
<p>Ele exemplifica a construção de interfaces declarativas, navegação com Compose Navigation, gerenciamento reativo de estado, e integração com banco de dados Firestore para salvar as tarefas na nuvem.</p>

<hr />

<h2>🧩 Estrutura e Componentes Principais</h2>

<h3>1. Navegação e Estrutura Principal - <code>MainActivity.kt</code></h3>
<ul>
  <li>Herda de <code>ComponentActivity</code> e usa <code>setContent</code> para definir toda a UI com Compose, sem XML.</li>
  <li>Usa o <code>NavController</code> para controlar a navegação entre telas dentro do app.</li>
  <li>Define o <code>NavHost</code> com duas rotas principais:
    <ul>
      <li><code>"listTasks"</code> → Tela de listagem das tarefas (<code>ListTask</code>)</li>
      <li><code>"saveTasks"</code> → Tela para criação/edição de tarefas (<code>SaveTask</code>)</li>
    </ul>
  </li>
</ul>

<h3>2. Tela de Listagem - <code>ListTask.kt</code></h3>
<ul>
  <li>Exibe a lista de tarefas em um <code>LazyColumn</code> (lista otimizada).</li>
  <li>Usa <code>Scaffold</code> para estrutura básica, incluindo:
    <ul>
      <li><code>TopAppBar</code> com título do app</li>
      <li><code>FloatingActionButton</code> (FAB) para navegar à tela de cadastro</li>
    </ul>
  </li>
  <li>Cada item da lista é renderizado pelo componente <code>TaskItem</code>.</li>
</ul>

<h3>3. Tela de Cadastro/Salvar Tarefa - <code>SaveTask.kt</code></h3>
<ul>
  <li>Formulário com campos:
    <ul>
      <li>Título (obrigatório)</li>
      <li>Descrição</li>
      <li>Prioridade (seleção por RadioButtons coloridos: baixa, média, alta)</li>
    </ul>
  </li>
  <li>Botão personalizado <code>CustomButton</code> para salvar a tarefa.</li>
  <li>Valida o título e, ao salvar, persiste os dados via <code>TasksRepository</code>.</li>
  <li>Feedback via Toast e retorno automático para a tela de listagem.</li>
</ul>

<h3>4. Componente Reutilizável - <code>TaskItem.kt</code></h3>
<ul>
  <li>Representa cada tarefa visualmente como um <code>Card</code> estilizado.</li>
  <li>Exibe:
    <ul>
      <li>Título e descrição da tarefa</li>
      <li>Indicador visual de prioridade com cor (verde, amarelo ou vermelho)</li>
      <li>Texto indicando o nível de prioridade (baixa, média ou alta)</li>
      <li>Ícone de lixeira para deletar</li>
    </ul>
  </li>
  <li>Ao clicar no botão de deletar:
    <ul>
      <li>Abre um <code>AlertDialog</code> pedindo confirmação.</li>
      <li>Se confirmado, chama o <code>deleteTask()</code> do repositório.</li>
      <li>Remove a tarefa da lista atual na UI com <code>taskList.removeAt(position)</code>.</li>
      <li>Usa <code>CoroutineScope</code> com <code>launch</code> para realizar ações assíncronas.</li>
      <li>Mostra <code>Toast</code> indicando sucesso.</li>
      <li>Redireciona para a rota <code>"listTasks"</code> e limpa a pilha de navegação.</li>
    </ul>
  </li>
</ul>

<h3>5. Diálogo de Confirmação com AlertDialog</h3>
<ul>
  <li>Implementado com <code>AlertDialog</code> do Compose Material3.</li>
  <li>É exibido usando <code>remember { mutableStateOf(false) }</code> para controle do estado.</li>
  <li>Contém dois botões:
    <ul>
      <li><strong>Sim</strong>: confirma a exclusão e executa a remoção com feedback.</li>
      <li><strong>Não</strong>: apenas fecha o diálogo.</li>
    </ul>
  </li>
</ul>

<h3>6. Modelo de Dados - <code>Task.kt</code></h3>
<ul>
  <li>Data class que representa uma tarefa, com propriedades:
    <ul>
      <li><code>task</code>: título</li>
      <li><code>description</code>: descrição</li>
      <li><code>priority</code>: prioridade (int)</li>
    </ul>
  </li>
</ul>

<h3>7. Camada de Persistência</h3>
<ul>
  <li><strong><code>TasksRepository</code></strong>: Camada intermediária entre a UI e a fonte de dados. Encapsula chamadas de leitura e gravação.</li>
  <li><strong><code>DataSource</code></strong>:
    <ul>
      <li>Gerencia comunicação com o <strong>Firebase Firestore</strong>.</li>
      <li><code>saveTask()</code>: salva tarefa na coleção <code>"tasks"</code>.</li>
      <li><code>getTasks()</code>: retorna um <code>Flow</code> reativo da lista de tarefas.</li>
      <li><code>deleteTask()</code>: exclui a tarefa com base no ID (título) no Firestore.</li>
      <li>Utiliza <code>StateFlow</code> para permitir que a UI observe alterações em tempo real.</li>
    </ul>
  </li>
</ul>

<hr />

<h2>📚 Tecnologias Utilizadas</h2>
<ul>
  <li>Kotlin</li>
  <li>Jetpack Compose (Material3)</li>
  <li>Navigation Compose</li>
  <li>Firebase Firestore</li>
  <li>Coroutines e Flow</li>
  <li>Arquitetura com separação entre UI, Repositório e DataSource</li>
</ul>

<hr />

<h2>⚙️ Como Rodar o Projeto</h2>
<ol>
  <li>Clone este repositório.</li>
  <li>Abra no Android Studio versão 2022.1 ou superior.</li>
  <li>Configure seu projeto Firebase e adicione o arquivo <code>google-services.json</code> na pasta <code>app/</code>.</li>
  <li>Atualize as dependências no <code>build.gradle</code> conforme necessário.</li>
  <li>Rode o app em um dispositivo físico ou emulador.</li>
</ol>

<hr />

<h2>📝 Conclusão</h2>
<p>Este app serve como uma excelente base para quem deseja aprender a desenvolver apps modernos com Kotlin + Jetpack Compose, incluindo manipulação de estado, persistência em nuvem com Firebase Firestore, navegação declarativa e componentes reutilizáveis.</p>
<p>Com a funcionalidade de exclusão de tarefas e confirmação via <code>AlertDialog</code>, a aplicação se aproxima ainda mais de um ambiente de produção real.</p>