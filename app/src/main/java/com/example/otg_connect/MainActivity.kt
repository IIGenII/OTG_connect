package com.example.otg_connect

import android.content.Context
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.otg_connect.ui.theme.OTG_connectTheme

class MainActivity : ComponentActivity() {
    private val deviceInfo = mutableStateOf("Нет подключённого устройства") // Информация об устройстве
    private val messages = mutableStateListOf<String>() // Список для хранения истории сообщений
    private var serialHelper: SerialHelper? = null // Объект для работы с последовательным портом

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаем USB Manager
        val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
        serialHelper = SerialHelper(usbManager)

        // Устанавливаем лямбда-функцию для обновления информации об устройстве
        UsbReceiver.onDeviceConnected = { info ->
            deviceInfo.value = info
        }

        // Регистрируем BroadcastReceiver для обработки событий USB
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
                    messages = messages,
                    onSendClick = { message ->
                        // Отправка данных через последовательный порт
                        serialHelper?.sendData(message)
                        messages.add("Вы: $message") // Добавляем отправленное сообщение в историю
                    },
                    onClearHistoryClick = {
                        // Очистка истории сообщений
                        messages.clear()
                    }
                )
            }
        }

        // Обработка подключения устройства
        UsbReceiver.onDeviceAttached = { device ->
            handleDeviceConnection(device)
        }
    }

    private fun handleDeviceConnection(device: UsbDevice) {
        serialHelper?.let { helper ->
            if (helper.connect(device)) {
                messages.add("Устройство подключено: VID=${device.vendorId}, PID=${device.productId}")
                helper.startReading { data ->
                    messages.add("Arduino: $data") // Добавляем полученные данные в историю
                }
            } else {
                messages.add("Ошибка подключения к устройству.")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Очищаем ресурсы
        serialHelper?.disconnect()
        UsbReceiver.onDeviceConnected = null
    }
}