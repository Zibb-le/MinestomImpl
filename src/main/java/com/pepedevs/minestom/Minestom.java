package com.pepedevs.minestom;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.instance.*;
import net.minestom.server.instance.batch.ChunkBatch;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.biomes.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class Minestom {

    public static void main(String[] args) {
        MinecraftServer server = MinecraftServer.init();
        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();
        instanceContainer.setChunkGenerator(new ChunkGen());

        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();
        handler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 42, 0));
        });

        server.start("0.0.0.0", 25565);
    }

    public static class ChunkGen implements ChunkGenerator {

        @Override
        public void generateChunkData(@NotNull ChunkBatch batch, int blockX, int blockZ) {
            for (byte x = 0; x < Chunk.CHUNK_SIZE_X; x++) {
                for (byte z = 0; z < Chunk.CHUNK_SIZE_Z; z++) {
                    for (byte y = 0; y < 40; y++) {
                        batch.setBlock(x, y, z, Block.STONE);
                    }
                }
            }
        }

        @Override
        public void fillBiomes(@NotNull Biome[] biomes, int blockX, int blockZ) {
            Arrays.fill(biomes, Biome.PLAINS);
        }

        @Override
        public @Nullable List<ChunkPopulator> getPopulators() {
            return null;
        }

    }

}
