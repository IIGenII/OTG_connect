package com.example.otg_connect

import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import com.hoho.android.usbserial.util.SerialInputOutputManager
import java.io.IOException
import java.util.concurrent.Executors

class SerialHelper(private val usbManager: UsbManager) {
    private var serialPort: UsbSerialPort? = null
    private val executor = Executors.newSingleThreadExecutor()
    private var ioManager: SerialInputOutputManager? = null

    fun connect(device: UsbDevice): Boolean {
        val driver = UsbSerialProber.getDefaultProber().probeDevice(device) ?: return false
        serialPort = driver.ports.firstOrNull() ?: return false

        val connection = usbManager.openDevice(driver.device) ?: return false
        serialPort?.apply {
            open(connection)
            setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
        }
        return true
    }

    fun sendData(data: String) {
        serialPort?.let {
            try {
                it.write(data.toByteArray(), 1000)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun startReading(onDataReceived: (String) -> Unit) {
        serialPort?.let { port ->
            ioManager?.stop() // Останавливаем предыдущий IO Manager, если он уже запущен
            ioManager = SerialInputOutputManager(port, object : SerialInputOutputManager.Listener {
                override fun onNewData(data: ByteArray) {
                    onDataReceived(String(data))
                }

                override fun onRunError(e: Exception) {
                    e.printStackTrace()
                }
            }).also { manager ->
                executor.submit(manager as Runnable) // Явное приведение к Runnable
            }
        }
    }

    fun disconnect() {
        try {
            ioManager?.stop() // Останавливаем IO Manager
            ioManager = null
            serialPort?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            serialPort = null
        }
    }
}