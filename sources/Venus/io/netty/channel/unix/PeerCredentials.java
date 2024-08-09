/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

import io.netty.util.internal.EmptyArrays;

public final class PeerCredentials {
    private final int pid;
    private final int uid;
    private final int[] gids;

    PeerCredentials(int n, int n2, int ... nArray) {
        this.pid = n;
        this.uid = n2;
        this.gids = nArray == null ? EmptyArrays.EMPTY_INTS : nArray;
    }

    public int pid() {
        return this.pid;
    }

    public int uid() {
        return this.uid;
    }

    public int[] gids() {
        return (int[])this.gids.clone();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("UserCredentials[pid=").append(this.pid).append("; uid=").append(this.uid).append("; gids=[");
        if (this.gids.length > 0) {
            stringBuilder.append(this.gids[0]);
            for (int i = 1; i < this.gids.length; ++i) {
                stringBuilder.append(", ").append(this.gids[i]);
            }
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

