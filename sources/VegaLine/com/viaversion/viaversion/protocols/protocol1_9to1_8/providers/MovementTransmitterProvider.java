/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;
import java.util.logging.Level;

public class MovementTransmitterProvider
implements Provider {
    public void sendPlayer(UserConnection userConnection) {
        if (userConnection.getProtocolInfo().getClientState() != State.PLAY || userConnection.getEntityTracker(Protocol1_9To1_8.class).clientEntityId() == -1) {
            return;
        }
        MovementTracker movementTracker = userConnection.get(MovementTracker.class);
        movementTracker.incrementIdlePacket();
        try {
            PacketWrapper playerMovement = PacketWrapper.create(ServerboundPackets1_8.PLAYER_MOVEMENT, userConnection);
            playerMovement.write(Type.BOOLEAN, movementTracker.isGround());
            playerMovement.scheduleSendToServer(Protocol1_9To1_8.class);
        } catch (Throwable e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to send player movement packet", e);
        }
    }
}

