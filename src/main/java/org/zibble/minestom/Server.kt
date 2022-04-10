package org.zibble.minestom

import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.block.Block
import net.minestom.server.network.player.PlayerConnection
import org.zibble.minestom.command.StopCommand
import org.zibble.minestom.logger.Logger

class Server(private val address: String, private val port: Int) {

    lateinit var server: MinecraftServer
        private set
    lateinit var defaultInstance: InstanceContainer
        private set
    lateinit var logger: Logger
        private set

    constructor(port: Int) : this("0.0.0.0", port)

    fun init() {
        this.server = MinecraftServer.init()
        logger = Logger()
        PlayerData.load()

        val instanceManager = MinecraftServer.getInstanceManager()
        this.defaultInstance = instanceManager.createInstanceContainer()
        this.defaultInstance.setGenerator{ unit -> unit.modifier().fillHeight(0, 40, Block.STONE) }
        this.defaultInstance.saveChunksToStorage()

        MinecraftServer.getConnectionManager().setUuidProvider { _: PlayerConnection, username: String ->
            PlayerData.getOrCreate(username).uuid
        }
        MinecraftServer.getConnectionManager().setPlayerProvider { uuid, username, connection ->
            Player(uuid, username, connection).apply {
                this.skin = PlayerData.getOrCreate(username).skin
            }
        }

        val eventHandler = MinecraftServer.getGlobalEventHandler()
        eventHandler.addListener(PlayerLoginEvent::class.java) {
            it.setSpawningInstance(this.defaultInstance)
            it.player.respawnPoint = Pos(0.0, 140.0, 0.0)
            this.logger.info("UUID of player ${it.player.username} is ${it.player.uuid}")
            this.logger.info("${it.player.username} + [${it.player.playerConnection.remoteAddress}] logged in with entity id ${it.player.entityId}.")
        }

        this.server.start(this.address, this.port)
    }

    fun registerCommands() {
        MinecraftServer.getCommandManager().register(StopCommand())
    }

}