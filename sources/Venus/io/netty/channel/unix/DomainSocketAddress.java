/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import java.io.File;
import java.net.SocketAddress;

public final class DomainSocketAddress
extends SocketAddress {
    private static final long serialVersionUID = -6934618000832236893L;
    private final String socketPath;

    public DomainSocketAddress(String string) {
        if (string == null) {
            throw new NullPointerException("socketPath");
        }
        this.socketPath = string;
    }

    public DomainSocketAddress(File file) {
        this(file.getPath());
    }

    public String path() {
        return this.socketPath;
    }

    public String toString() {
        return this.path();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof DomainSocketAddress)) {
            return true;
        }
        return ((DomainSocketAddress)object).socketPath.equals(this.socketPath);
    }

    public int hashCode() {
        return this.socketPath.hashCode();
    }
}

