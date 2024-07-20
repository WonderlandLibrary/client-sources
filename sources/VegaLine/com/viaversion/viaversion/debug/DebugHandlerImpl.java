/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.debug;

import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.HashSet;
import java.util.Set;

public final class DebugHandlerImpl
implements DebugHandler {
    private final Set<String> packetTypesToLog = new HashSet<String>();
    private final IntSet clientboundPacketIdsToLog = new IntOpenHashSet();
    private final IntSet serverboundPacketIdsToLog = new IntOpenHashSet();
    private boolean logPostPacketTransform;
    private boolean enabled;

    @Override
    public boolean enabled() {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void addPacketTypeNameToLog(String packetTypeName) {
        this.packetTypesToLog.add(packetTypeName);
    }

    @Override
    public void addPacketTypeToLog(PacketType packetType) {
        (packetType.direction() == Direction.SERVERBOUND ? this.serverboundPacketIdsToLog : this.clientboundPacketIdsToLog).add(packetType.getId());
    }

    @Override
    public boolean removePacketTypeNameToLog(String packetTypeName) {
        return this.packetTypesToLog.remove(packetTypeName);
    }

    @Override
    public void clearPacketTypesToLog() {
        this.packetTypesToLog.clear();
    }

    @Override
    public boolean logPostPacketTransform() {
        return this.logPostPacketTransform;
    }

    @Override
    public void setLogPostPacketTransform(boolean logPostPacketTransform) {
        this.logPostPacketTransform = logPostPacketTransform;
    }

    @Override
    public boolean shouldLog(PacketWrapper wrapper, Direction direction) {
        return this.packetTypesToLog.isEmpty() && this.serverboundPacketIdsToLog.isEmpty() && this.clientboundPacketIdsToLog.isEmpty() || wrapper.getPacketType() != null && this.packetTypesToLog.contains(wrapper.getPacketType().getName()) || (direction == Direction.SERVERBOUND ? this.serverboundPacketIdsToLog : this.clientboundPacketIdsToLog).contains(wrapper.getId());
    }
}

