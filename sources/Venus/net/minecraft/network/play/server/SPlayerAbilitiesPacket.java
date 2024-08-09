/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SPlayerAbilitiesPacket
implements IPacket<IClientPlayNetHandler> {
    private boolean invulnerable;
    private boolean flying;
    private boolean allowFlying;
    private boolean creativeMode;
    private float flySpeed;
    private float walkSpeed;

    public SPlayerAbilitiesPacket() {
    }

    public SPlayerAbilitiesPacket(PlayerAbilities playerAbilities) {
        this.invulnerable = playerAbilities.disableDamage;
        this.flying = playerAbilities.isFlying;
        this.allowFlying = playerAbilities.allowFlying;
        this.creativeMode = playerAbilities.isCreativeMode;
        this.flySpeed = playerAbilities.getFlySpeed();
        this.walkSpeed = playerAbilities.getWalkSpeed();
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        byte by = packetBuffer.readByte();
        this.invulnerable = (by & 1) != 0;
        this.flying = (by & 2) != 0;
        this.allowFlying = (by & 4) != 0;
        this.creativeMode = (by & 8) != 0;
        this.flySpeed = packetBuffer.readFloat();
        this.walkSpeed = packetBuffer.readFloat();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        byte by = 0;
        if (this.invulnerable) {
            by = (byte)(by | 1);
        }
        if (this.flying) {
            by = (byte)(by | 2);
        }
        if (this.allowFlying) {
            by = (byte)(by | 4);
        }
        if (this.creativeMode) {
            by = (byte)(by | 8);
        }
        packetBuffer.writeByte(by);
        packetBuffer.writeFloat(this.flySpeed);
        packetBuffer.writeFloat(this.walkSpeed);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handlePlayerAbilities(this);
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public boolean isAllowFlying() {
        return this.allowFlying;
    }

    public boolean isCreativeMode() {
        return this.creativeMode;
    }

    public float getFlySpeed() {
        return this.flySpeed;
    }

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

