/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.config;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class SocketConfig
implements Cloneable {
    public static final SocketConfig DEFAULT = new Builder().build();
    private final int soTimeout;
    private final boolean soReuseAddress;
    private final int soLinger;
    private final boolean soKeepAlive;
    private final boolean tcpNoDelay;
    private final int sndBufSize;
    private final int rcvBufSize;
    private final int backlogSize;

    SocketConfig(int n, boolean bl, int n2, boolean bl2, boolean bl3, int n3, int n4, int n5) {
        this.soTimeout = n;
        this.soReuseAddress = bl;
        this.soLinger = n2;
        this.soKeepAlive = bl2;
        this.tcpNoDelay = bl3;
        this.sndBufSize = n3;
        this.rcvBufSize = n4;
        this.backlogSize = n5;
    }

    public int getSoTimeout() {
        return this.soTimeout;
    }

    public boolean isSoReuseAddress() {
        return this.soReuseAddress;
    }

    public int getSoLinger() {
        return this.soLinger;
    }

    public boolean isSoKeepAlive() {
        return this.soKeepAlive;
    }

    public boolean isTcpNoDelay() {
        return this.tcpNoDelay;
    }

    public int getSndBufSize() {
        return this.sndBufSize;
    }

    public int getRcvBufSize() {
        return this.rcvBufSize;
    }

    public int getBacklogSize() {
        return this.backlogSize;
    }

    protected SocketConfig clone() throws CloneNotSupportedException {
        return (SocketConfig)super.clone();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[soTimeout=").append(this.soTimeout).append(", soReuseAddress=").append(this.soReuseAddress).append(", soLinger=").append(this.soLinger).append(", soKeepAlive=").append(this.soKeepAlive).append(", tcpNoDelay=").append(this.tcpNoDelay).append(", sndBufSize=").append(this.sndBufSize).append(", rcvBufSize=").append(this.rcvBufSize).append(", backlogSize=").append(this.backlogSize).append("]");
        return stringBuilder.toString();
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(SocketConfig socketConfig) {
        Args.notNull(socketConfig, "Socket config");
        return new Builder().setSoTimeout(socketConfig.getSoTimeout()).setSoReuseAddress(socketConfig.isSoReuseAddress()).setSoLinger(socketConfig.getSoLinger()).setSoKeepAlive(socketConfig.isSoKeepAlive()).setTcpNoDelay(socketConfig.isTcpNoDelay()).setSndBufSize(socketConfig.getSndBufSize()).setRcvBufSize(socketConfig.getRcvBufSize()).setBacklogSize(socketConfig.getBacklogSize());
    }

    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    public static class Builder {
        private int soTimeout;
        private boolean soReuseAddress;
        private int soLinger = -1;
        private boolean soKeepAlive;
        private boolean tcpNoDelay = true;
        private int sndBufSize;
        private int rcvBufSize;
        private int backlogSize;

        Builder() {
        }

        public Builder setSoTimeout(int n) {
            this.soTimeout = n;
            return this;
        }

        public Builder setSoReuseAddress(boolean bl) {
            this.soReuseAddress = bl;
            return this;
        }

        public Builder setSoLinger(int n) {
            this.soLinger = n;
            return this;
        }

        public Builder setSoKeepAlive(boolean bl) {
            this.soKeepAlive = bl;
            return this;
        }

        public Builder setTcpNoDelay(boolean bl) {
            this.tcpNoDelay = bl;
            return this;
        }

        public Builder setSndBufSize(int n) {
            this.sndBufSize = n;
            return this;
        }

        public Builder setRcvBufSize(int n) {
            this.rcvBufSize = n;
            return this;
        }

        public Builder setBacklogSize(int n) {
            this.backlogSize = n;
            return this;
        }

        public SocketConfig build() {
            return new SocketConfig(this.soTimeout, this.soReuseAddress, this.soLinger, this.soKeepAlive, this.tcpNoDelay, this.sndBufSize, this.rcvBufSize, this.backlogSize);
        }
    }
}

