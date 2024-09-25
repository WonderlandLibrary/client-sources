/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_14_4to1_15.data;

import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;

public class ImmediateRespawn
extends StoredObject {
    private boolean immediateRespawn;

    public ImmediateRespawn(UserConnection user) {
        super(user);
    }

    public boolean isImmediateRespawn() {
        return this.immediateRespawn;
    }

    public void setImmediateRespawn(boolean immediateRespawn) {
        this.immediateRespawn = immediateRespawn;
    }
}

