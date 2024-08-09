/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  io.netty.internal.tcnative.SessionTicketKey
 */
package io.netty.handler.ssl;

import io.netty.internal.tcnative.SessionTicketKey;

public final class OpenSslSessionTicketKey {
    public static final int NAME_SIZE = 16;
    public static final int HMAC_KEY_SIZE = 16;
    public static final int AES_KEY_SIZE = 16;
    public static final int TICKET_KEY_SIZE = 48;
    final SessionTicketKey key;

    public OpenSslSessionTicketKey(byte[] byArray, byte[] byArray2, byte[] byArray3) {
        this.key = new SessionTicketKey((byte[])byArray.clone(), (byte[])byArray2.clone(), (byte[])byArray3.clone());
    }

    public byte[] name() {
        return (byte[])this.key.getName().clone();
    }

    public byte[] hmacKey() {
        return (byte[])this.key.getHmacKey().clone();
    }

    public byte[] aesKey() {
        return (byte[])this.key.getAesKey().clone();
    }
}

