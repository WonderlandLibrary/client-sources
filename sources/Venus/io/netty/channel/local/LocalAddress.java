/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.local;

import io.netty.channel.Channel;
import java.net.SocketAddress;

public final class LocalAddress
extends SocketAddress
implements Comparable<LocalAddress> {
    private static final long serialVersionUID = 4644331421130916435L;
    public static final LocalAddress ANY = new LocalAddress("ANY");
    private final String id;
    private final String strVal;

    LocalAddress(Channel channel) {
        StringBuilder stringBuilder = new StringBuilder(16);
        stringBuilder.append("local:E");
        stringBuilder.append(Long.toHexString((long)channel.hashCode() & 0xFFFFFFFFL | 0x100000000L));
        stringBuilder.setCharAt(7, ':');
        this.id = stringBuilder.substring(6);
        this.strVal = stringBuilder.toString();
    }

    public LocalAddress(String string) {
        if (string == null) {
            throw new NullPointerException("id");
        }
        if ((string = string.trim().toLowerCase()).isEmpty()) {
            throw new IllegalArgumentException("empty id");
        }
        this.id = string;
        this.strVal = "local:" + string;
    }

    public String id() {
        return this.id;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof LocalAddress)) {
            return true;
        }
        return this.id.equals(((LocalAddress)object).id);
    }

    @Override
    public int compareTo(LocalAddress localAddress) {
        return this.id.compareTo(localAddress.id);
    }

    public String toString() {
        return this.strVal;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((LocalAddress)object);
    }
}

