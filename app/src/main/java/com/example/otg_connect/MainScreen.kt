package com.example.otg_connect

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen(
    deviceInfo: String,
    onSendClick: (String) -> Unit,
    onClearHistoryClick: () -> Unit,
    messages: List<String> = emptyList() // Значение по умолчанию — пустой список
) {
    var inputText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class) // Добавляем аннотацию
            TopAppBar(title = { Text("OTG Connect") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = deviceInfo, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(16.dp))
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Введите сообщение") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onSendClick(inputText) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Text("Отправить")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onClearHistoryClick() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Text("Очистить историю")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("История сообщений:")
                messages.forEach {
                    Text(it, modifier = Modifier.padding(4.dp))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        deviceInfo = "Подключено устройство: VID=1234, PID=5678",
        onSendClick = {},
        onClearHistoryClick = {}
    )
}