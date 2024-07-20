/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.unix;

public final class PeerCredentials {
    private final int pid;
    private final int uid;
    private final int gid;

    PeerCredentials(int p, int u, int g) {
        this.pid = p;
        this.uid = u;
        this.gid = g;
    }

    public int pid() {
        return this.pid;
    }

    public int uid() {
        return this.uid;
    }

    public int gid() {
        return this.gid;
    }

    public String toString() {
        return "UserCredentials[pid=" + this.pid + "; uid=" + this.uid + "; gid=" + this.gid + "]";
    }
}

