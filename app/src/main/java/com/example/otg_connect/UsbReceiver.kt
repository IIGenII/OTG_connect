package com.example.otg_connect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log

class UsbReceiver : BroadcastReceiver() {

    companion object {
        var onDeviceConnected: ((String) -> Unit)? = null // Лямбда-функция для передачи данных об устройстве
        var onDeviceAttached: ((UsbDevice) -> Unit)? = null // Лямбда-функция для передачи объекта устройства
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                if (device != null) {
                    onDeviceAttached?.invoke(device) // Передаем объект устройства
                    val deviceInfo = "Подключено устройство: VID=${device.vendorId}, PID=${device.productId}"
                    onDeviceConnected?.invoke(deviceInfo) // Обновляем строковую информацию
                }
            }
            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                onDeviceConnected?.invoke("Нет подключённого устройства")
                Log.d("UsbReceiver", "USB-устройство отключено")
            }
        }
    }
}