package net.minecraft.client.main;

import net.minecraft.client.*;

static final class Main$2 extends Thread {
    @Override
    public void run() {
        Minecraft.stopIntegratedServer();
    }
}