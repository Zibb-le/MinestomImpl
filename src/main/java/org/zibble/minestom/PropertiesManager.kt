package org.zibble.minestom

import java.io.File
import java.io.FileReader
import java.util.*

class PropertiesManager(file: File) {

    private val properties: Properties = Properties()

    init {
        val reader = FileReader(file)
        reader.use { r ->
            properties.load(r)
        }
    }

    fun getIp(): String {
        return this.properties.getProperty("server-ip", "0.0.0.0")
    }

    fun getPort(): Int {
        return this.properties.getProperty("server-port", "25565").toInt()
    }

    fun getProperties(): Properties {
        return this.properties
    }

}