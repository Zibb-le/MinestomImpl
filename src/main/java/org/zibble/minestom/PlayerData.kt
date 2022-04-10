package org.zibble.minestom

import net.minestom.server.entity.PlayerSkin
import org.zibble.minestom.io.APIDataFetcher
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class PlayerData(val uuid: UUID, val username: String, var skin: PlayerSkin?) {

    companion object {
        private val LOADED: List<PlayerData> = CopyOnWriteArrayList()

        fun load() {
            val folder = File("data/players")
            if (!folder.exists()) {
                folder.mkdirs()
            }

            for (file in folder.listFiles()!!) {
                if (file.name.endsWith(".json")) {
                    FileReader(file).use {
                        MinestomServer.getInstance().gson.fromJson(it, PlayerData::class.java).apply {
                            LOADED.plusElement(this)
                        }
                    }
                }
            }
        }

        fun getOrCreate(username: String): PlayerData {
            LOADED.find { it.username == username }?.let { return it }

            PlayerData(username).let {
                it.save()
                return it
            }
        }

        fun get(uuid: UUID) : PlayerData? {
            LOADED.find { it.uuid == uuid }?.let { return it }
            return null
        }
    }

    constructor(username: String) : this(APIDataFetcher.getUUID(username), username, PlayerSkin.fromUsername(username))

    fun save() {
        val folder = File("data/players")
        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = File("data/players/$uuid.json")
        if (!file.exists()) {
            file.createNewFile()
        }

        FileWriter(file).use {
            it.write(MinestomServer.getInstance().gson.toJson(this))
        }
    }

}