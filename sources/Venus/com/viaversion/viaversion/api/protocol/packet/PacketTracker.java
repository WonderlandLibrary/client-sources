/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.connection.UserConnection;

public class PacketTracker {
    private final UserConnection connection;
    private long sentPackets;
    private long receivedPackets;
    private long startTime;
    private long intervalPackets;
    private long packetsPerSecond = -1L;
    private int secondsObserved;
    private int warnings;

    public PacketTracker(UserConnection userConnection) {
        this.connection = userConnection;
    }

    public void incrementSent() {
        ++this.sentPackets;
    }

    public boolean incrementReceived() {
        long l = System.currentTimeMillis() - this.startTime;
        if (l >= 1000L) {
            this.packetsPerSecond = this.intervalPackets;
            this.startTime = System.currentTimeMillis();
            this.intervalPackets = 1L;
            return false;
        }
        ++this.intervalPackets;
        ++this.receivedPackets;
        return true;
    }

    public boolean exceedsMaxPPS() {
        if (this.connection.isClientSide()) {
            return true;
        }
        ViaVersionConfig viaVersionConfig = Via.getConfig();
        if (viaVersionConfig.getMaxPPS() > 0 && this.packetsPerSecond >= (long)viaVersionConfig.getMaxPPS()) {
            this.connection.disconnect(viaVersionConfig.getMaxPPSKickMessage().replace("%pps", Long.toString(this.packetsPerSecond)));
            return false;
        }
        if (viaVersionConfig.getMaxWarnings() > 0 && viaVersionConfig.getTrackingPeriod() > 0) {
            if (this.secondsObserved > viaVersionConfig.getTrackingPeriod()) {
                this.warnings = 0;
                this.secondsObserved = 1;
            } else {
                ++this.secondsObserved;
                if (this.packetsPerSecond >= (long)viaVersionConfig.getWarningPPS()) {
                    ++this.warnings;
                }
                if (this.warnings >= viaVersionConfig.getMaxWarnings()) {
                    this.connection.disconnect(viaVersionConfig.getMaxWarningsKickMessage().replace("%pps", Long.toString(this.packetsPerSecond)));
                    return false;
                }
            }
        }
        return true;
    }

    public long getSentPackets() {
        return this.sentPackets;
    }

    public void setSentPackets(long l) {
        this.sentPackets = l;
    }

    public long getReceivedPackets() {
        return this.receivedPackets;
    }

    public void setReceivedPackets(long l) {
        this.receivedPackets = l;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long l) {
        this.startTime = l;
    }

    public long getIntervalPackets() {
        return this.intervalPackets;
    }

    public void setIntervalPackets(long l) {
        this.intervalPackets = l;
    }

    public long getPacketsPerSecond() {
        return this.packetsPerSecond;
    }

    public void setPacketsPerSecond(long l) {
        this.packetsPerSecond = l;
    }

    public int getSecondsObserved() {
        return this.secondsObserved;
    }

    public void setSecondsObserved(int n) {
        this.secondsObserved = n;
    }

    public int getWarnings() {
        return this.warnings;
    }

    public void setWarnings(int n) {
        this.warnings = n;
    }
}

