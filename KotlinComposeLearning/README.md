<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>TasksList - README</title>
<style>
  body { font-family: Arial, sans-serif; line-height: 1.6; padding: 20px; background: #f9f9f9; color: #333; }
  h1, h2, h3 { color: #0D47A1; }
  h1 { border-left: 6px solid #1976D2; padding-left: 10px; }
  h2 { border-left: 4px solid #2196F3; padding-left: 8px; margin-top: 30px; }
  h3 { margin-top: 20px; }
  pre { background: #eee; padding: 10px; overflow-x: auto; }
  code { background: #eee; padding: 2px 5px; border-radius: 3px; }
  ul { margin-left: 20px; }
  hr { border: none; border-top: 1px solid #ccc; margin: 30px 0; }
</style>
</head>
<body>

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
  <li>Cada item da lista é renderizado pelo componente <code>TaskItem</code>, que mostra título, descrição, prioridade colorida e botão de deletar (função futura).</li>
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
  <li>Feedback via Toast e retorno para a lista após salvar.</li>
</ul>

<h3>4. Componente Reutilizável - <code>TaskItem.kt</code></h3>
<ul>
  <li>Representa cada tarefa na lista.</li>
  <li>Exibe:
    <ul>
      <li>Título e descrição da tarefa</li>
      <li>Indicador colorido da prioridade (verde, amarelo, vermelho)</li>
      <li>Texto do nível de prioridade</li>
      <li>Botão de deletar (com ícone), pronto para implementar ação futura.</li>
    </ul>
  </li>
</ul>

<h3>5. Componentes Auxiliares</h3>
<ul>
  <li><strong><code>CustomButton</code></strong>: Botão estilizado para o app, com cor azul e texto branco.</li>
  <li><strong><code>TextBox</code></strong>: Campo de texto customizado baseado em <code>OutlinedTextField</code> com cores e estilos consistentes.</li>
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
  <li><strong><code>TasksRepository</code></strong>: Responsável por salvar as tarefas, serve de camada intermediária entre a UI e a fonte de dados.</li>
  <li><strong><code>DataSource</code></strong>: Implementa a comunicação com o <strong>Firebase Firestore</strong>, salvando as tarefas na coleção <code>"tasks"</code>.</li>
</ul>

<hr />

<h2>📚 Tecnologias Utilizadas</h2>
<ul>
  <li>Kotlin</li>
  <li>Jetpack Compose (Material3)</li>
  <li>Navigation Compose</li>
  <li>Firebase Firestore</li>
  <li>Coroutines para operações assíncronas</li>
  <li>Arquitetura simples com separação entre UI, repositório e fonte de dados</li>
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

<hr />

<hr />

<h2>📝 Conclusão</h2>
<p>Este app serve como uma ótima base para quem deseja aprender a usar Kotlin com Jetpack Compose em conjunto com Firebase Firestore, mostrando boas práticas de navegação, componentes reutilizáveis, gerenciamento de estado e arquitetura simples, porém funcional.</p>

</body>
</html>
