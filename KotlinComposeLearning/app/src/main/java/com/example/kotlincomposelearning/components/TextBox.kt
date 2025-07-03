import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier,
    label: String,
    maxLines: Int,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = {
            Text(text = label)
        },
        maxLines = maxLines,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            cursorColor = Color(0xFF03A9F4),
            focusedIndicatorColor = Color(0xFF03A9F4),  // Borda ao focar
            unfocusedIndicatorColor = Color.Gray,
            focusedLabelColor = Color(0xFF03A9F4),
            unfocusedLabelColor = Color.Gray,
            disabledContainerColor = Color.LightGray,
            disabledIndicatorColor = Color.LightGray,
            disabledLabelColor = Color.DarkGray
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextBoxPreview() {

//    MaterialTheme {
//        TextBox(
//            value = "Teste",
//            onValueChange = { },
//            modifier = Modifier.fillMaxWidth(),
//            label = "Digite algo",
//            maxLines = 1
//        )
//    }
}