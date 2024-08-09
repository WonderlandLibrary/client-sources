/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package via.platform.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.MovementTracker;

public class ViaMovementTransmitterProvider
extends MovementTransmitterProvider {


//    @Override
//    public Object getFlyingPacket() {
//        return null;
//    }
//
//    @Override
//    public Object getGroundPacket() {
//        return null;
//    }

    @Override
    public void sendPlayer(UserConnection userConnection) {
        if (userConnection.getProtocolInfo().getState() != State.PLAY) {
            return;
        }
        if (userConnection.getEntityTracker(Protocol1_9To1_8.class).clientEntityId() == -1) {
            return;
        }
        MovementTracker movementTracker = userConnection.get(MovementTracker.class);
        movementTracker.incrementIdlePacket();
        try {
            PacketWrapper c03 = PacketWrapper.create(ServerboundPackets1_8.PLAYER_MOVEMENT, userConnection);
            c03.write(Type.BOOLEAN, movementTracker.isGround());
            c03.scheduleSendToServer(Protocol1_9To1_8.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}

