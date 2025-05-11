package com.example.otg_connect

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource // Импорт для stringResource
import com.example.otg_connect.ui.theme.OTG_connectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OTG_connectTheme {
                MainScreen(
                    onSendClick = { message ->
                        // Проверяем, подключено ли устройство
                        if (isDeviceConnected()) {
                            sendMessageToUsb(message)
                        } else {
                            // Показываем сообщение об ошибке
                            Toast.makeText(this, getString(R.string.error_no_device), Toast.LENGTH_SHORT).show()
                        }
                    },
                    onClearHistoryClick = {
                        // Очистка истории сообщений
                        clearMessageHistory()
                    }
                )
            }
        }
    }

    // Проверка подключения устройства
    private fun isDeviceConnected(): Boolean {
        // Логика проверки подключения устройства через USB
        return false // Заменить на реальную проверку
    }

     // Отправка сообщения по USB
    private fun sendMessageToUsb(message: String) {
        // TODO: Добавить логику отправки данных через USB
        println("Отправка сообщения: $message")
    }

    // Очистка истории сообщений
    private fun clearMessageHistory() {
        // Логика очистки истории
    }
}

@Composable
fun MainScreen(
    onSendClick: (String) -> Unit,
    onClearHistoryClick: () -> Unit
) {
    // Состояние для хранения текста поля ввода
    var inputText by remember { mutableStateOf("") }
    // Состояние для хранения списка сообщений
    val messageHistory by remember { mutableStateOf(listOf<String>()) }
    // Состояние для переключателя автопрокрутки
    var isAutoScrollEnabled by remember { mutableStateOf(true) }
    // Состояние статуса подключения
    val isConnected = true // Заменить на реальный статус подключения
    val deviceInfo = "??? (VID: ????, PID: ????)" // Пример данных устройства

    Scaffold(
        topBar = {
            // Верхняя панель со статусом подключения
            @OptIn(ExperimentalMaterial3Api::class) // Добавляем аннотацию
            TopAppBar(
                title = {
                    Text(
                        text = if (isConnected) {
                            "${stringResource(R.string.status_connected)}: $deviceInfo"
                        } else {
                            stringResource(R.string.status_disconnected)
                        }
                    )
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Область для отображения сообщений
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(messageHistory.size) { index ->
                        Text(text = messageHistory[index])
                    }
                }

                // Переключатель автопрокрутки
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.auto_scroll_toggle))
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isAutoScrollEnabled,
                        onCheckedChange = { isAutoScrollEnabled = it }
                    )
                }

                // Поле ввода и кнопки
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier.weight(1f),
                        label = { Text(text = "Введите текст") }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { onSendClick(inputText) }
                    ) {
                        Text(text = stringResource(R.string.send_button))
                    }
                }

                // Кнопка очистки истории
                Button(
                    onClick = { onClearHistoryClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = stringResource(R.string.clear_history_button))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    OTG_connectTheme {
        MainScreen(
            onSendClick = { },
            onClearHistoryClick = { }
        )
    }
}