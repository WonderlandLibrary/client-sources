package net.optifine.util;

import lombok.Getter;
import net.minecraft.network.IPacket;

public class PacketRunnable implements Runnable {
    @Getter
    private final IPacket<?> packet;
    private final Runnable runnable;

    public PacketRunnable(IPacket<?> packet, Runnable runnable) {
        this.packet = packet;
        this.runnable = runnable;
    }

    public void run() {
        this.runnable.run();
    }

    public String toString() {
        return "PacketRunnable: " + this.packet;
    }
}
