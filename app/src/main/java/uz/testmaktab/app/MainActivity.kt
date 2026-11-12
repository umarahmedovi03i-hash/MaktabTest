package uz.testmaktab.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { TestMaktab() }
    }
}

@Composable
fun TestMaktab() {
    var language by remember { mutableStateOf("O‘zbekcha") }
    var page by remember { mutableStateOf(0) }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    var answer by remember { mutableIntStateOf(-1) }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("TestMaktab", fontSize = 30.sp)
            Spacer(Modifier.height(25.dp))

            when (page) {
                0 -> {
                    Text("Tilni tanlang")
                    listOf("O‘zbekcha", "Тоҷикӣ", "Русский", "English").forEach {
                        OutlinedButton(
                            onClick = { language = it },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text(if (language == it) "✓ $it" else it) }
                    }
                    Button(onClick = { page = 1 }) {
                        Text("Davom etish")
                    }
                }

                1 -> {
                    OutlinedTextField(
                        value = login,
                        onValueChange = { login = it; error = false },
                        label = { Text("Login") }
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; error = false },
                        label = { Text("Parol") },
                        visualTransformation = PasswordVisualTransformation()
                    )
                    if (error) Text("Login yoki parol noto‘g‘ri", color = MaterialTheme.colorScheme.error)

                    Button(onClick = {
                        if ((login == "student" && password == "1234") ||
                            (login == "admin" && password == "admin123")) {
                            error = false
                            page = 2
                        } else error = true
                    }) { Text("Kirish") }
                    TextButton(onClick = { page = 0 }) { Text("Orqaga") }
                }

                2 -> {
                    Text("Xush kelibsiz!", fontSize = 22.sp)
                    Button(onClick = { answer = -1; page = 3 }) {
                        Text("Testni boshlash")
                    }
                }

                3 -> {
                    Text("O‘zbekiston poytaxti qaysi shahar?", fontSize = 20.sp)
                    listOf("Samarqand", "Toshkent", "Buxoro").forEachIndexed { i, option ->
                        OutlinedButton(
                            onClick = { answer = i },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text(if (answer == i) "✓ $option" else option) }
                    }
                    Button(
                        onClick = { page = 4 },
                        enabled = answer >= 0
                    ) { Text("Yakunlash") }
                }

                4 -> {
                    Text("Natija", fontSize = 25.sp)
                    Text(if (answer == 1) "1 / 1" else "0 / 1", fontSize = 30.sp)
                    Button(onClick = { page = 2 }) { Text("Orqaga") }
                }
            }
        }
    }
}
