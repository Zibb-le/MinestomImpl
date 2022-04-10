package org.zibble.minestom.logger

import net.kyori.adventure.audience.MessageType
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.Component
import net.minestom.server.adventure.audience.Audiences
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class Logger {

    companion object {
        @JvmStatic val FORMATTER: SimpleDateFormat = SimpleDateFormat("kk:mm:ss")
    }

    private val logFilter: Set<LogFilter> = ConcurrentHashMap.newKeySet()

    fun log(msg: Component, level: LogLevel = LogLevel.INFO) {
        if (!logFilter.any { it.shouldLog(msg) }) {
            return
        }

        var component = Component.text("[" + Logger.FORMATTER.format(Date()) + "] ")
            .append(level.prefix)
            .append(Component.text(" "))
            .append(msg)
        component = component.color(level.color)
        Audiences.console().sendMessage(Identity.nil(), component, MessageType.CHAT)
    }

    fun logRetainFormat(msg: Component, logLevel: LogLevel = LogLevel.INFO) {
        if (!logFilter.any { it.shouldLog(msg) }) {
            return
        }

        val component: Component = Component.text("[" + Logger.FORMATTER.format(Date()) + "] ")
            .color(logLevel.color)
            .append(logLevel.prefix)
            .color(logLevel.color)
            .append(Component.text(" "))
            .append(msg)
        Audiences.console().sendMessage(Identity.nil(), component, MessageType.CHAT)
    }

    fun info(msg: Component) {
        log(msg, LogLevel.INFO)
    }

    fun info(msg: String) {
        this.info(Component.text(msg))
    }

    fun warn(msg: Component) {
        log(msg, LogLevel.WARN)
    }

    fun warn(msg: String) {
        this.warn(Component.text(msg))
    }

    fun severe(msg: Component) {
        log(msg, LogLevel.SEVERE)
    }

    fun severe(msg: String) {
        this.severe(Component.text(msg))
    }

    fun addLogFilter(filter: LogFilter) {
        this.logFilter.plusElement(filter)
    }

    fun removeLogFilter(filter: LogFilter) {
        this.logFilter.minusElement(filter)
    }

    fun clearFilters() {
        this.logFilter.none()
    }

}