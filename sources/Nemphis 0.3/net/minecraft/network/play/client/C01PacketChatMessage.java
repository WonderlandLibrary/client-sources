/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C01PacketChatMessage
implements Packet {
    private String message;
    private static final String __OBFID = "CL_00001347";

    public C01PacketChatMessage() {
    }

    public C01PacketChatMessage(String messageIn) {
        if (messageIn.length() > 100) {
            messageIn = messageIn.substring(0, 100);
        }
        this.message = messageIn;
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.message = data.readStringFromBuffer(100);
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeString(this.message);
    }

    public void func_180757_a(INetHandlerPlayServer p_180757_1_) {
        p_180757_1_.processChatMessage(this);
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.func_180757_a((INetHandlerPlayServer)handler);
    }
}

