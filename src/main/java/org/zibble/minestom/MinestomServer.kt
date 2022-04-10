package org.zibble.minestom

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.zibble.minestom.io.UUIDTypeAdaptor
import java.io.File
import java.util.UUID

class MinestomServer {

    companion object {
        private lateinit var minestom: MinestomServer

        fun getInstance(): MinestomServer {
            return minestom
        }
    }

    fun main() {
        minestom = MinestomServer()
        minestom.loadProperties()
        minestom.initServer()
    }

    val gson: Gson = GsonBuilder().setPrettyPrinting().registerTypeAdapter(UUID::class.java, UUIDTypeAdaptor()).create()
    lateinit var server: Server
    private set
    lateinit var propertiesManager: PropertiesManager
    private set

    private fun loadProperties() {
        this.propertiesManager = PropertiesManager(File("server.properties"))
    }

    private fun initServer() {
        this.server = Server(this.propertiesManager.getIp(), this.propertiesManager.getPort())
        this.server.init()
        this.server.registerCommands()
    }

}