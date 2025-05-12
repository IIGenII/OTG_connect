package com.example.otg_connect

import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import com.example.otg_connect.ui.theme.OTG_connectTheme
import android.content.Context

class MainActivity : ComponentActivity() {
    private val deviceInfo = mutableStateOf("Нет подключённого устройства")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем лямбда-функцию для получения информации об устройстве
        UsbReceiver.onDeviceConnected = { info ->
            deviceInfo.value = info // Обновляем информацию об устройстве
        }

        // Регистрация BroadcastReceiver для обработки событий USB
        val usbReceiver = UsbReceiver()
        registerReceiver(
            usbReceiver,
            IntentFilter().apply {
                addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
                addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
            },
            Context.RECEIVER_NOT_EXPORTED
        )

        setContent {
            OTG_connectTheme {
                MainScreen(
                    deviceInfo = deviceInfo.value,
                    onSendClick = { message ->
                        // Логика отправки данных
                    },
                    onClearHistoryClick = {
                        // Логика очистки данных
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UsbReceiver.onDeviceConnected = null // Очищаем лямбда-функцию
    }
}