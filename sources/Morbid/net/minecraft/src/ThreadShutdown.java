package net.minecraft.src;

import net.minecraft.client.*;

public final class ThreadShutdown extends Thread
{
    @Override
    public void run() {
        Minecraft.stopIntegratedServer();
    }
}
