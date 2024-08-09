/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CInputPacket
implements IPacket<IServerPlayNetHandler> {
    private float strafeSpeed;
    private float forwardSpeed;
    private boolean jumping;
    private boolean sneaking;

    public CInputPacket() {
    }

    public CInputPacket(float f, float f2, boolean bl, boolean bl2) {
        this.strafeSpeed = f;
        this.forwardSpeed = f2;
        this.jumping = bl;
        this.sneaking = bl2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.strafeSpeed = packetBuffer.readFloat();
        this.forwardSpeed = packetBuffer.readFloat();
        byte by = packetBuffer.readByte();
        this.jumping = (by & 1) > 0;
        this.sneaking = (by & 2) > 0;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeFloat(this.strafeSpeed);
        packetBuffer.writeFloat(this.forwardSpeed);
        byte by = 0;
        if (this.jumping) {
            by = (byte)(by | 1);
        }
        if (this.sneaking) {
            by = (byte)(by | 2);
        }
        packetBuffer.writeByte(by);
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processInput(this);
    }

    public float getStrafeSpeed() {
        return this.strafeSpeed;
    }

    public float getForwardSpeed() {
        return this.forwardSpeed;
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public boolean isSneaking() {
        return this.sneaking;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }
}

