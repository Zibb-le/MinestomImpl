package org.zibble.minestom.command

import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command

class StopCommand : Command("stop") {

    init {
        this.setDefaultExecutor { _, _ ->
            MinecraftServer.stopCleanly()
        }
    }

}