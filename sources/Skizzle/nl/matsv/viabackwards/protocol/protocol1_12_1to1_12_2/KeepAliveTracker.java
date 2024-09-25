/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_12_1to1_12_2;

import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;

public class KeepAliveTracker
extends StoredObject {
    private long keepAlive = Integer.MAX_VALUE;

    public KeepAliveTracker(UserConnection user) {
        super(user);
    }

    public long getKeepAlive() {
        return this.keepAlive;
    }

    public void setKeepAlive(long keepAlive) {
        this.keepAlive = keepAlive;
    }

    public String toString() {
        return "KeepAliveTracker{keepAlive=" + this.keepAlive + '}';
    }
}

