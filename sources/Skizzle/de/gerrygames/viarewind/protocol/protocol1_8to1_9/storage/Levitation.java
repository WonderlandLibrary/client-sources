/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;

import de.gerrygames.viarewind.protocol.protocol1_8to1_9.Protocol1_8TO1_9;
import de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import de.gerrygames.viarewind.utils.PacketUtil;
import de.gerrygames.viarewind.utils.Tickable;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.type.Type;

public class Levitation
extends StoredObject
implements Tickable {
    private int amplifier;
    private volatile boolean active = false;

    public Levitation(UserConnection user) {
        super(user);
    }

    @Override
    public void tick() {
        if (!this.active) {
            return;
        }
        int vY = (this.amplifier + 1) * 360;
        PacketWrapper packet = new PacketWrapper(18, null, this.getUser());
        packet.write(Type.VAR_INT, this.getUser().get(EntityTracker.class).getPlayerId());
        packet.write(Type.SHORT, (short)0);
        packet.write(Type.SHORT, (short)vY);
        packet.write(Type.SHORT, (short)0);
        PacketUtil.sendPacket(packet, Protocol1_8TO1_9.class);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }
}

