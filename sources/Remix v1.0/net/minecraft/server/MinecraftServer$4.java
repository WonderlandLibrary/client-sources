package net.minecraft.server;

import java.util.concurrent.*;

class MinecraftServer$4 implements Callable<String> {
    public String a() {
        return MinecraftServer.a(MinecraftServer.this).o() + " / " + MinecraftServer.a(MinecraftServer.this).p() + "; " + MinecraftServer.a(MinecraftServer.this).v();
    }
}