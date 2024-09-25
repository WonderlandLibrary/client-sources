/*
 * Decompiled with CFR 0.150.
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;

public class CompressionSendStorage
extends StoredObject {
    private boolean compressionSend = false;

    public CompressionSendStorage(UserConnection user) {
        super(user);
    }

    public boolean isCompressionSend() {
        return this.compressionSend;
    }

    public void setCompressionSend(boolean compressionSend) {
        this.compressionSend = compressionSend;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CompressionSendStorage)) {
            return false;
        }
        CompressionSendStorage other = (CompressionSendStorage)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return this.isCompressionSend() == other.isCompressionSend();
    }

    protected boolean canEqual(Object other) {
        return other instanceof CompressionSendStorage;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.isCompressionSend() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "CompressionSendStorage(compressionSend=" + this.isCompressionSend() + ")";
    }
}

