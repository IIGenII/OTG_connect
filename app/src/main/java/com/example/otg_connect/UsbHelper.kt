package com.example.otg_connect

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log

class UsbHelper(private val context: Context) {

    val usbManager: UsbManager by lazy {
        context.getSystemService(Context.USB_SERVICE) as UsbManager
    }

    private var device: UsbDevice? = null
    private var connection: android.hardware.usb.UsbDeviceConnection? = null

    fun connectToDevice(device: UsbDevice) {
        this.device = device
        val usbInterface = device.getInterface(0)
        connection = usbManager.openDevice(device)
        if (connection != null && connection!!.claimInterface(usbInterface, true)) {
            Log.d("UsbHelper", "Подключение к устройству выполнено: ${device.deviceName}, VID: ${device.vendorId}, PID: ${device.productId}")
        } else {
            Log.e("UsbHelper", "Ошибка подключения к устройству: ${device.deviceName}")
            disconnect() // Освобождаем ресурсы при неудачном подключении
        }
    }

    fun disconnect() {
        try {
            device?.getInterface(0)?.let {
                connection?.releaseInterface(it)
            }
            connection?.close()
            Log.d("UsbHelper", "Отключение устройства завершено")
        } catch (e: Exception) {
            Log.e("UsbHelper", "Ошибка при отключении устройства: ${e.message}")
        } finally {
            device = null
            connection = null
        }
    }
}