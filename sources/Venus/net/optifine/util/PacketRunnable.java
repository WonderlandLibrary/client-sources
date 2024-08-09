/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import net.minecraft.network.IPacket;

public class PacketRunnable
implements Runnable {
    private IPacket packet;
    private Runnable runnable;

    public PacketRunnable(IPacket iPacket, Runnable runnable) {
        this.packet = iPacket;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        this.runnable.run();
    }

    public IPacket getPacket() {
        return this.packet;
    }

    public String toString() {
        return "PacketRunnable: " + this.packet;
    }
}

