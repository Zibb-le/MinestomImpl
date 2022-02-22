package org.zibble.minestom.logger;

import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.minestom.server.adventure.audience.Audiences;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Logger {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("kk:mm:ss");

    private Set<LogFilter> filters;

    public Logger() {
        this.filters = ConcurrentHashMap.newKeySet();
    }

    public void log(Component msg, LogLevel logLevel) {
        for (LogFilter filter : filters) {
            if (!filter.shouldLog(msg))
                return;
        }

        Component component = Component.text("[" + FORMATTER.format(new Date()) + "] ")
                .append(logLevel.getPrefix())
                .append(Component.text(" "))
                .append(msg);
        component = component.color(logLevel.getColor());
        Audiences.console().sendMessage(Identity.nil(), component, MessageType.CHAT);
    }

    public void logRetainFormat(Component msg, LogLevel logLevel) {
        for (LogFilter filter : filters) {
            if (!filter.shouldLog(msg))
                return;
        }

        Component component = Component.text("[" + FORMATTER.format(new Date()) + "] ")
                .color(logLevel.getColor())
                .append(logLevel.getPrefix())
                .color(logLevel.getColor())
                .append(Component.text(" "))
                .append(msg);
        Audiences.console().sendMessage(Identity.nil(), component, MessageType.CHAT);
    }

    public void info(Component msg) {
        this.log(msg, LogLevel.INFO);
    }

    public void info(String msg) {
        this.info(Component.text(msg));
    }

    public void warn(Component msg) {
        this.log(msg, LogLevel.WARN);
    }

    public void warn(String msg) {
        this.warn(Component.text(msg));
    }

    public void severe(Component msg) {
        this.log(msg, LogLevel.SEVERE);
    }

    public void severe(String msg) {
        this.severe(Component.text(msg));
    }

    public void addLogFilter(LogFilter filter) {
        this.filters.add(filter);
    }

    public void removeLogFilter(LogFilter filter) {
        this.filters.remove(filter);
    }

    public void clearFilters() {
        this.filters.clear();
    }

}