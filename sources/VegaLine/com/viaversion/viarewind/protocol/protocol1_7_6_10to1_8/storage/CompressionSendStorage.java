/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;

public class CompressionSendStorage
extends StoredObject {
    private boolean removeCompression = false;

    public CompressionSendStorage(UserConnection user) {
        super(user);
    }

    public boolean isRemoveCompression() {
        return this.removeCompression;
    }

    public void setRemoveCompression(boolean removeCompression) {
        this.removeCompression = removeCompression;
    }
}

