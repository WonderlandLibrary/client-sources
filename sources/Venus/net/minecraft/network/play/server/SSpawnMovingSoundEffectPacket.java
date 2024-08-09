/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

public class SSpawnMovingSoundEffectPacket
implements IPacket<IClientPlayNetHandler> {
    private SoundEvent field_218765_a;
    private SoundCategory field_218766_b;
    private int field_218767_c;
    private float field_218768_d;
    private float field_218769_e;

    public SSpawnMovingSoundEffectPacket() {
    }

    public SSpawnMovingSoundEffectPacket(SoundEvent soundEvent, SoundCategory soundCategory, Entity entity2, float f, float f2) {
        Validate.notNull(soundEvent, "sound", new Object[0]);
        this.field_218765_a = soundEvent;
        this.field_218766_b = soundCategory;
        this.field_218767_c = entity2.getEntityId();
        this.field_218768_d = f;
        this.field_218769_e = f2;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.field_218765_a = (SoundEvent)Registry.SOUND_EVENT.getByValue(packetBuffer.readVarInt());
        this.field_218766_b = packetBuffer.readEnumValue(SoundCategory.class);
        this.field_218767_c = packetBuffer.readVarInt();
        this.field_218768_d = packetBuffer.readFloat();
        this.field_218769_e = packetBuffer.readFloat();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(Registry.SOUND_EVENT.getId(this.field_218765_a));
        packetBuffer.writeEnumValue(this.field_218766_b);
        packetBuffer.writeVarInt(this.field_218767_c);
        packetBuffer.writeFloat(this.field_218768_d);
        packetBuffer.writeFloat(this.field_218769_e);
    }

    public SoundEvent func_218763_b() {
        return this.field_218765_a;
    }

    public SoundCategory func_218760_c() {
        return this.field_218766_b;
    }

    public int func_218762_d() {
        return this.field_218767_c;
    }

    public float func_218764_e() {
        return this.field_218768_d;
    }

    public float func_218761_f() {
        return this.field_218769_e;
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSpawnMovingSoundEffect(this);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

