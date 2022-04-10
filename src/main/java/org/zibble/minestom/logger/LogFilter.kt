package org.zibble.minestom.logger

import net.kyori.adventure.text.Component

interface LogFilter {

    fun shouldLog(message: Component): Boolean

}