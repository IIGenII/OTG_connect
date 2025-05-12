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
    deviceInfo: String, // Информация об устройстве
    onSendClick: (String) -> Unit,
    onClearHistoryClick: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class) // Добавляем аннотацию
            TopAppBar(
                title = { Text("OTG Connect") }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Отображение информации об устройстве
                Text(
                    text = deviceInfo,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Поле для ввода текста
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Введите сообщение") },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка отправки
                Button(
                    onClick = { onSendClick(inputText) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Text("Отправить")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка очистки
                Button(
                    onClick = { onClearHistoryClick() },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                ) {
                    Text("Очистить")
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