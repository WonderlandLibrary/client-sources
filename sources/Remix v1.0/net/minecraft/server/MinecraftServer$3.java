package net.minecraft.server;

import java.util.concurrent.*;

class MinecraftServer$3 implements Callable {
    @Override
    public String call() {
        return MinecraftServer.access$100(MinecraftServer.this).getCurrentPlayerCount() + " / " + MinecraftServer.access$100(MinecraftServer.this).getMaxPlayers() + "; " + MinecraftServer.access$100(MinecraftServer.this).playerEntityList;
    }
}