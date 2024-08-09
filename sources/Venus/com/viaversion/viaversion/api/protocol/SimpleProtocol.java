/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

public interface SimpleProtocol
extends Protocol<DummyPacketTypes, DummyPacketTypes, DummyPacketTypes, DummyPacketTypes> {

    public static enum DummyPacketTypes implements ClientboundPacketType,
    ServerboundPacketType
    {


        @Override
        public int getId() {
            return 1;
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public Direction direction() {
            throw new UnsupportedOperationException();
        }
    }
}

