/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SEntityPropertiesPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private final List<Snapshot> snapshots = Lists.newArrayList();

    public SEntityPropertiesPacket() {
    }

    public SEntityPropertiesPacket(int n, Collection<ModifiableAttributeInstance> collection) {
        this.entityId = n;
        for (ModifiableAttributeInstance modifiableAttributeInstance : collection) {
            this.snapshots.add(new Snapshot(this, modifiableAttributeInstance.getAttribute(), modifiableAttributeInstance.getBaseValue(), modifiableAttributeInstance.getModifierListCopy()));
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        int n = packetBuffer.readInt();
        for (int i = 0; i < n; ++i) {
            ResourceLocation resourceLocation = packetBuffer.readResourceLocation();
            Attribute attribute = Registry.ATTRIBUTE.getOrDefault(resourceLocation);
            double d = packetBuffer.readDouble();
            ArrayList<AttributeModifier> arrayList = Lists.newArrayList();
            int n2 = packetBuffer.readVarInt();
            for (int j = 0; j < n2; ++j) {
                UUID uUID = packetBuffer.readUniqueId();
                arrayList.add(new AttributeModifier(uUID, "Unknown synced attribute modifier", packetBuffer.readDouble(), AttributeModifier.Operation.byId(packetBuffer.readByte())));
            }
            this.snapshots.add(new Snapshot(this, attribute, d, arrayList));
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeInt(this.snapshots.size());
        for (Snapshot snapshot : this.snapshots) {
            packetBuffer.writeResourceLocation(Registry.ATTRIBUTE.getKey(snapshot.func_240834_a_()));
            packetBuffer.writeDouble(snapshot.getBaseValue());
            packetBuffer.writeVarInt(snapshot.getModifiers().size());
            for (AttributeModifier attributeModifier : snapshot.getModifiers()) {
                packetBuffer.writeUniqueId(attributeModifier.getID());
                packetBuffer.writeDouble(attributeModifier.getAmount());
                packetBuffer.writeByte(attributeModifier.getOperation().getId());
            }
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityProperties(this);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public List<Snapshot> getSnapshots() {
        return this.snapshots;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public class Snapshot {
        private final Attribute field_240833_b_;
        private final double baseValue;
        private final Collection<AttributeModifier> modifiers;
        final SEntityPropertiesPacket this$0;

        public Snapshot(SEntityPropertiesPacket sEntityPropertiesPacket, Attribute attribute, double d, Collection<AttributeModifier> collection) {
            this.this$0 = sEntityPropertiesPacket;
            this.field_240833_b_ = attribute;
            this.baseValue = d;
            this.modifiers = collection;
        }

        public Attribute func_240834_a_() {
            return this.field_240833_b_;
        }

        public double getBaseValue() {
            return this.baseValue;
        }

        public Collection<AttributeModifier> getModifiers() {
            return this.modifiers;
        }
    }
}

