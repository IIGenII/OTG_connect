package com.example.otg_connect

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log

class UsbHelper(private val context: Context) {

    private val usbManager: UsbManager by lazy {
        context.getSystemService(Context.USB_SERVICE) as UsbManager
    }

    private var device: UsbDevice? = null

    fun connectToDevice(device: UsbDevice) {
        this.device = device
        val usbInterface = device.getInterface(0)
        val connection = usbManager.openDevice(device)
        if (connection != null && connection.claimInterface(usbInterface, true)) {
            Log.d("UsbHelper", "Подключение к устройству выполнено: ${device.deviceName}, VID: ${device.vendorId}, PID: ${device.productId}")
        } else {
            Log.d("UsbHelper", "Ошибка подключения к устройству")
        }
    }

    fun disconnect() {
        device = null
        Log.d("UsbHelper", "Отключение устройства завершено")
    }
}