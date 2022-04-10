package org.zibble.minestom.io

import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class APIDataFetcher {

    companion object {
        const val USERNAME_URL = "https://api.mojang.com/users/profiles/minecraft/%s"
        const val UUID_URL = "https://api.ashcon.app/mojang/v2/user/%s"

        @JvmStatic val NAMES_CACHE = ConcurrentHashMap<UUID, String>()
        @JvmStatic val UUID_CACHE = ConcurrentHashMap<String, UUID>()

        fun getUUID(username: String): UUID {
            if (UUID_CACHE.containsKey(username)) {
                return UUID_CACHE[username]!!
            }

            val connection = URL(String.format(USERNAME_URL, username)).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.addRequestProperty("Content-Type", "application/json")

            BufferedReader(InputStreamReader(connection.inputStream)).use {
                val json = JsonParser.parseReader(it).asJsonObject
                val uuid = UUIDTypeAdaptor.fromString(json.get("id").asString)
                UUID_CACHE[username] = uuid
                NAMES_CACHE[uuid] = json.get("name").asString
                return uuid
            }
        }

        fun getUsername(uuid: UUID): String {
            if (NAMES_CACHE.containsKey(uuid)) {
                return NAMES_CACHE[uuid]!!
            }

            val connection = URL(String.format(UUID_URL, uuid.toString())).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.addRequestProperty("Content-Type", "application/json")

            BufferedReader(InputStreamReader(connection.inputStream)).use {
                val json = JsonParser.parseReader(it).asJsonObject
                val username = json.get("username").asString
                UUID_CACHE[username] = uuid
                NAMES_CACHE[uuid] = username
                return username
            }
        }
    }

}