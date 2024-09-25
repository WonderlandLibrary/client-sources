/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.data;

import us.myles.ViaVersion.api.data.UserConnection;

public class StoredObject {
    private final UserConnection user;

    public StoredObject(UserConnection user) {
        this.user = user;
    }

    public UserConnection getUser() {
        return this.user;
    }
}

