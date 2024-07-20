/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.epoll;

public final class EpollTcpInfo {
    final int[] info = new int[32];

    public int state() {
        return this.info[0] & 0xFF;
    }

    public int caState() {
        return this.info[1] & 0xFF;
    }

    public int retransmits() {
        return this.info[2] & 0xFF;
    }

    public int probes() {
        return this.info[3] & 0xFF;
    }

    public int backoff() {
        return this.info[4] & 0xFF;
    }

    public int options() {
        return this.info[5] & 0xFF;
    }

    public int sndWscale() {
        return this.info[6] & 0xFF;
    }

    public int rcvWscale() {
        return this.info[7] & 0xFF;
    }

    public long rto() {
        return (long)this.info[8] & 0xFFFFFFFFL;
    }

    public long ato() {
        return (long)this.info[9] & 0xFFFFFFFFL;
    }

    public long sndMss() {
        return (long)this.info[10] & 0xFFFFFFFFL;
    }

    public long rcvMss() {
        return (long)this.info[11] & 0xFFFFFFFFL;
    }

    public long unacked() {
        return (long)this.info[12] & 0xFFFFFFFFL;
    }

    public long sacked() {
        return (long)this.info[13] & 0xFFFFFFFFL;
    }

    public long lost() {
        return (long)this.info[14] & 0xFFFFFFFFL;
    }

    public long retrans() {
        return (long)this.info[15] & 0xFFFFFFFFL;
    }

    public long fackets() {
        return (long)this.info[16] & 0xFFFFFFFFL;
    }

    public long lastDataSent() {
        return (long)this.info[17] & 0xFFFFFFFFL;
    }

    public long lastAckSent() {
        return (long)this.info[18] & 0xFFFFFFFFL;
    }

    public long lastDataRecv() {
        return (long)this.info[19] & 0xFFFFFFFFL;
    }

    public long lastAckRecv() {
        return (long)this.info[20] & 0xFFFFFFFFL;
    }

    public long pmtu() {
        return (long)this.info[21] & 0xFFFFFFFFL;
    }

    public long rcvSsthresh() {
        return (long)this.info[22] & 0xFFFFFFFFL;
    }

    public long rtt() {
        return (long)this.info[23] & 0xFFFFFFFFL;
    }

    public long rttvar() {
        return (long)this.info[24] & 0xFFFFFFFFL;
    }

    public long sndSsthresh() {
        return (long)this.info[25] & 0xFFFFFFFFL;
    }

    public long sndCwnd() {
        return (long)this.info[26] & 0xFFFFFFFFL;
    }

    public long advmss() {
        return (long)this.info[27] & 0xFFFFFFFFL;
    }

    public long reordering() {
        return (long)this.info[28] & 0xFFFFFFFFL;
    }

    public long rcvRtt() {
        return (long)this.info[29] & 0xFFFFFFFFL;
    }

    public long rcvSpace() {
        return (long)this.info[30] & 0xFFFFFFFFL;
    }

    public long totalRetrans() {
        return (long)this.info[31] & 0xFFFFFFFFL;
    }
}

