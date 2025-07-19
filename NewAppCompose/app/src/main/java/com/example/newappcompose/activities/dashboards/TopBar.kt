package com.example.newappcompose.activities.dashboards

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.newappcompose.R

@Preview
@Composable
fun TopBar() {
    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = 32.dp) // Padding horizontal
            .fillMaxWidth()              // Largura completa
            .wrapContentHeight()         // Altura ajustável ao conteúdo
    ) {
        val (world, name, profile, notification, title) = createRefs() // Referências

        Image( // Ícone do mundo (parece não estar sendo usado na tela)
            painter = painterResource(id = R.drawable.world), // Recurso da imagem
            contentDescription = null, // Sem descrição
            modifier = Modifier
                .clickable { } // Clicável mas sem ação
                .constrainAs(ref = world) { // Posicionamento
                    top.linkTo(parent.top)     // Topo alinhado com pai
                    bottom.linkTo(parent.bottom) // Base alinhada com pai
                    start.linkTo(parent.start) // Início alinhado com pai
                }
        )

        Image( // Foto do perfil
            painter = painterResource(id = R.drawable.profile), // Recurso da imagem
            contentDescription = null, // Sem descrição
            modifier = Modifier
                .constrainAs(ref = profile) { // Posicionamento
                    top.linkTo(parent.top)     // Topo alinhado com pai
                    bottom.linkTo(parent.bottom) // Base alinhada com pai
                    start.linkTo(parent.start) // Início alinhado com pai
                }
        )

        Image( // Ícone de notificação
            painter = painterResource(id = R.drawable.ic_bell), // Recurso da imagem
            contentDescription = null, // Sem descrição
            modifier = Modifier
                .constrainAs(ref = notification) { // Posicionamento
                    top.linkTo(parent.top)       // Topo alinhado com pai
                    bottom.linkTo(parent.bottom) // Base alinhada com pai
                    end.linkTo(parent.end)       // Fim alinhado com pai
                }
        )

        Text(
            text = "Olá Leonardo!",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.constrainAs(ref = name) {
                top.linkTo(parent.top)
                start.linkTo(profile.end, margin = 8.dp) // margem start de 8.dp
                bottom.linkTo(parent.bottom)
            }
        )

        Text( // Título da tela
            text = stringResource(id = R.string.dahboard_title), // Título do dashboard
            color = Color.White,  // Cor branca
            fontWeight = FontWeight.Bold, // Negrito
            fontSize = 25.sp, // Tamanho da fonte
            modifier = Modifier
                .constrainAs(ref = title) { // Posicionamento
                    top.linkTo(profile.bottom) // Topo após o perfil
                    bottom.linkTo(parent.bottom) // Base alinhada com pai
                }
        )
    }
}
