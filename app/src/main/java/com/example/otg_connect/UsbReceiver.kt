package com.example.otg_connect

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log
import android.widget.Toast

class UsbReceiver : BroadcastReceiver() {

    companion object {
        var onDeviceConnected: ((String) -> Unit)? = null // Лямбда-функция для передачи данных
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                if (device != null) {
                    val deviceInfo = "Подключено устройство: VID=${device.vendorId}, PID=${device.productId}"
                    Log.d("UsbReceiver", deviceInfo)
                    Toast.makeText(context, deviceInfo, Toast.LENGTH_SHORT).show()
                    onDeviceConnected?.invoke(deviceInfo) // Передаём данные в MainActivity через лямбду
                }
            }

            UsbManager.ACTION_USB_DEVICE_DETACHED -> {
                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                if (device != null) {
                    Log.d("UsbReceiver", "USB-устройство отключено: ${device.deviceName}")
                    Toast.makeText(context, "USB-устройство отключено: ${device.deviceName}", Toast.LENGTH_SHORT).show()
                    onDeviceConnected?.invoke("Нет подключённого устройства") // Сбрасываем информацию
                }
            }
        }
    }
}