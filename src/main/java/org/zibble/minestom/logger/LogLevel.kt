package org.zibble.minestom.logger

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor

enum class LogLevel(val prefix: Component, val color: TextColor) {

    INFO(Component.text("[INFO]"), TextColor.color(255, 255, 255)),
    WARN(Component.text("[WARN]"), TextColor.color(255, 255, 0)),
    SEVERE(Component.text("[SEVERE]"), TextColor.color(255, 0, 0)),
    ;

}