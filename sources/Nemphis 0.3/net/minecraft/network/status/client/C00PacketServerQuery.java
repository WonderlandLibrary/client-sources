/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.network.status.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.status.INetHandlerStatusServer;

public class C00PacketServerQuery
implements Packet {
    private static final String __OBFID = "CL_00001393";

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
    }

    public void func_180775_a(INetHandlerStatusServer p_180775_1_) {
        p_180775_1_.processServerQuery(this);
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.func_180775_a((INetHandlerStatusServer)handler);
    }
}

