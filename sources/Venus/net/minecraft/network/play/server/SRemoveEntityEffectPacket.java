/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Effect;
import net.minecraft.world.World;

public class SRemoveEntityEffectPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private Effect effectId;

    public SRemoveEntityEffectPacket() {
    }

    public SRemoveEntityEffectPacket(int n, Effect effect) {
        this.entityId = n;
        this.effectId = effect;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.effectId = Effect.get(packetBuffer.readUnsignedByte());
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeByte(Effect.getId(this.effectId));
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleRemoveEntityEffect(this);
    }

    @Nullable
    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    @Nullable
    public Effect getPotion() {
        return this.effectId;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

