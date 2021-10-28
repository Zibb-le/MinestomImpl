package com.pepedevs.minestom;

import com.pepedevs.minestom.logger.Logger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;

public class Server {

    private final String address;
    private final int port;
    private MinecraftServer server;
    private InstanceContainer defaultInstance;
    private Logger logger;

    public Server(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public Server(int port) {
        this("0.0.0.0", port);
    }
    
    public void init() {
        this.server = MinecraftServer.init();
        this.logger = new Logger();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        defaultInstance = instanceManager.createInstanceContainer();
        defaultInstance.setChunkGenerator(new ChunkGen());
        defaultInstance.saveChunksToStorage();

        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(defaultInstance);
            player.setRespawnPoint(new Pos(0, 42, 0));
            this.logger.info("UUID of player " + player.getUsername() + " is " + player.getUuid());
            this.logger.info(player.getUsername() + "[" + player.getPlayerConnection().getRemoteAddress() + "] logged in with entity id " + player.getEntityId() + ".");
        });

        server.start(this.address, this.port);
    }

    public MinecraftServer getServer() {
        return this.server;
    }

    public InstanceContainer getDefaultInstance() {
        return this.defaultInstance;
    }

    public Logger getLogger() {
        return this.logger;
    }

}
