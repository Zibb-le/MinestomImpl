package com.pepedevs.minestom.logger;

import net.kyori.adventure.text.Component;

public interface LogFilter {

    boolean shouldLog(Component message);

}
