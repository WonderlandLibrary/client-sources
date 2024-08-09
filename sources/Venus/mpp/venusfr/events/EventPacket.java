/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.events;

import mpp.venusfr.events.CancelEvent;
import net.minecraft.network.IPacket;

public class EventPacket
extends CancelEvent {
    private IPacket<?> packet;
    private Type type;

    public boolean isSend() {
        return this.type == Type.SEND;
    }

    public boolean isReceive() {
        return this.type == Type.RECEIVE;
    }

    public IPacket<?> getPacket() {
        return this.packet;
    }

    public Type getType() {
        return this.type;
    }

    public void setPacket(IPacket<?> iPacket) {
        this.packet = iPacket;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public EventPacket(IPacket<?> iPacket, Type type) {
        this.packet = iPacket;
        this.type = type;
    }

    public static enum Type {
        RECEIVE,
        SEND;

    }
}

