/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;

public class PlayerMovementMapper
implements PacketHandler {
    @Override
    public void handle(PacketWrapper packetWrapper) throws Exception {
        MovementTracker movementTracker = packetWrapper.user().get(MovementTracker.class);
        movementTracker.incrementIdlePacket();
        if (packetWrapper.is(Type.BOOLEAN, 0)) {
            movementTracker.setGround(packetWrapper.get(Type.BOOLEAN, 0));
        }
    }
}

