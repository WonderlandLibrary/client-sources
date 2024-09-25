/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.MovementTracker;

public class PlayerMovementMapper
implements PacketHandler {
    @Override
    public void handle(PacketWrapper wrapper) throws Exception {
        MovementTracker tracker = wrapper.user().get(MovementTracker.class);
        tracker.incrementIdlePacket();
        if (wrapper.is(Type.BOOLEAN, 0)) {
            tracker.setGround(wrapper.get(Type.BOOLEAN, 0));
        }
    }
}

