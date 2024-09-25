/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.protocol;

import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.protocol.ServerboundPacketType;

public abstract class SimpleProtocol
extends Protocol<DummyPacketTypes, DummyPacketTypes, DummyPacketTypes, DummyPacketTypes> {
    protected SimpleProtocol() {
    }

    public static enum DummyPacketTypes implements ClientboundPacketType,
    ServerboundPacketType
    {

    }
}

