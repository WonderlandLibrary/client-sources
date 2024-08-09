/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class SCombatPacket
implements IPacket<IClientPlayNetHandler> {
    public Event eventType;
    public int playerId;
    public int entityId;
    public int duration;
    public ITextComponent deathMessage;

    public SCombatPacket() {
    }

    public SCombatPacket(CombatTracker combatTracker, Event event) {
        this(combatTracker, event, StringTextComponent.EMPTY);
    }

    public SCombatPacket(CombatTracker combatTracker, Event event, ITextComponent iTextComponent) {
        this.eventType = event;
        LivingEntity livingEntity = combatTracker.getBestAttacker();
        switch (1.$SwitchMap$net$minecraft$network$play$server$SCombatPacket$Event[event.ordinal()]) {
            case 1: {
                this.duration = combatTracker.getCombatDuration();
                this.entityId = livingEntity == null ? -1 : livingEntity.getEntityId();
                break;
            }
            case 2: {
                this.playerId = combatTracker.getFighter().getEntityId();
                this.entityId = livingEntity == null ? -1 : livingEntity.getEntityId();
                this.deathMessage = iTextComponent;
            }
        }
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.eventType = packetBuffer.readEnumValue(Event.class);
        if (this.eventType == Event.END_COMBAT) {
            this.duration = packetBuffer.readVarInt();
            this.entityId = packetBuffer.readInt();
        } else if (this.eventType == Event.ENTITY_DIED) {
            this.playerId = packetBuffer.readVarInt();
            this.entityId = packetBuffer.readInt();
            this.deathMessage = packetBuffer.readTextComponent();
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.eventType);
        if (this.eventType == Event.END_COMBAT) {
            packetBuffer.writeVarInt(this.duration);
            packetBuffer.writeInt(this.entityId);
        } else if (this.eventType == Event.ENTITY_DIED) {
            packetBuffer.writeVarInt(this.playerId);
            packetBuffer.writeInt(this.entityId);
            packetBuffer.writeTextComponent(this.deathMessage);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleCombatEvent(this);
    }

    @Override
    public boolean shouldSkipErrors() {
        return this.eventType == Event.ENTITY_DIED;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static enum Event {
        ENTER_COMBAT,
        END_COMBAT,
        ENTITY_DIED;

    }
}

