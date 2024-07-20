/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network;

import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.util.IThreadListener;

public class PacketThreadUtil {
    public static <T extends INetHandler> void checkThreadAndEnqueue(final Packet<T> packetIn, final T processor, IThreadListener scheduler) throws ThreadQuickExitException {
        if (!scheduler.isCallingFromMinecraftThread()) {
            scheduler.addScheduledTask(new Runnable(){

                @Override
                public void run() {
                    packetIn.processPacket(processor);
                }
            });
            throw ThreadQuickExitException.INSTANCE;
        }
    }
}

