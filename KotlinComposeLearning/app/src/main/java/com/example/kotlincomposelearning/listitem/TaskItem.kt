package com.example.kotlincomposelearning.listitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.kotlincomposelearning.R
import com.example.kotlincomposelearning.ui.theme.ShapeCardPriority

@Composable
fun TaskItem() {

    Card(
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(20.dp)
        ) {
            // Criando as referências para os elementos que serão usados dentro do ConstraintLayout
            val (txtTitulo, txtDescricao, cardPrioridade, txtPrioridade, btDeletar) = createRefs()

            // Texto de exemplo com restrição aplicada
            Text(
                text = "Task 1",
                modifier = Modifier.constrainAs(txtTitulo) {
                    // Aqui você pode definir as constraints como top.linkTo(...), start.linkTo(...), etc.
                    // Exemplo de posicionamento:
                    top.linkTo(parent.top, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            Text(
                text = "Description",
                modifier = Modifier.constrainAs(txtDescricao) {
                    // Aqui você pode definir as constraints como top.linkTo(...), start.linkTo(...), etc.
                    // Exemplo de posicionamento:
                    top.linkTo(txtTitulo.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                }
            )

            Text(
                text = "Low priority",
                modifier = Modifier.constrainAs(txtPrioridade) {
                    // Aqui você pode definir as constraints como top.linkTo(...), start.linkTo(...), etc.
                    // Exemplo de posicionamento:
                    top.linkTo(txtDescricao.bottom, margin = 10.dp)
                    start.linkTo(parent.start, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            )

            // Card de prioridade
            Card(
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(cardPrioridade) {
                        top.linkTo(txtDescricao.bottom, margin = 10.dp)
                        start.linkTo(txtPrioridade.end, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    },
                shape = ShapeCardPriority.large,
                colors = CardDefaults.cardColors(containerColor = Color(0xFF46BE4B))
            ) {
                // conteúdo opcional aqui
            }

            // Botão com ícone para deletar
            IconButton(
                onClick = {
                    // Ação ao clicar no ícone (ex: deletar item)
                },
                modifier = Modifier.constrainAs(btDeletar) {
                    top.linkTo(txtDescricao.bottom, margin = 10.dp)
                    start.linkTo(cardPrioridade.end, margin = 30.dp)
                    end.linkTo(parent.end, margin = 10.dp)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete),
                    contentDescription = null
                )

            }
        }
    }
}

@Composable
@Preview
fun TaskItemPreview() {
    TaskItem()
}