package org.zibble.minestom.logger;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public enum LogLevel {

    INFO(Component.text("[INFO]"), TextColor.color(255, 255, 255)),
    WARN(Component.text("[WARN]"), TextColor.color(255, 255, 0)),
    SEVERE(Component.text("[SEVERE]"), TextColor.color(255, 0, 0)),
    ;

    private final Component prefix;
    private final TextColor color;

    LogLevel(Component prefix, TextColor color) {
        this.prefix = prefix;
        this.color = color;
    }

    public Component getPrefix() {
        return prefix;
    }

    public TextColor getColor() {
        return color;
    }
}
