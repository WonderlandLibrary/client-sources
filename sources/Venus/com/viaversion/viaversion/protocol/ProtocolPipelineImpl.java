/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocol;

import com.google.common.collect.Sets;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.debug.DebugHandler;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.protocol.AbstractSimpleProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ProtocolPipelineImpl
extends AbstractSimpleProtocol
implements ProtocolPipeline {
    private final UserConnection userConnection;
    private List<Protocol> protocolList;
    private Set<Class<? extends Protocol>> protocolSet;

    public ProtocolPipelineImpl(UserConnection userConnection) {
        this.userConnection = userConnection;
        userConnection.getProtocolInfo().setPipeline(this);
        this.registerPackets();
    }

    @Override
    protected void registerPackets() {
        this.protocolList = new CopyOnWriteArrayList<Protocol>();
        this.protocolSet = Sets.newSetFromMap(new ConcurrentHashMap());
        Protocol protocol = Via.getManager().getProtocolManager().getBaseProtocol();
        this.protocolList.add(protocol);
        this.protocolSet.add(protocol.getClass());
    }

    @Override
    public void init(UserConnection userConnection) {
        throw new UnsupportedOperationException("ProtocolPipeline can only be initialized once");
    }

    @Override
    public void add(Protocol protocol) {
        this.protocolList.add(protocol);
        this.protocolSet.add(protocol.getClass());
        protocol.init(this.userConnection);
        if (!protocol.isBaseProtocol()) {
            this.moveBaseProtocolsToTail();
        }
    }

    @Override
    public void add(Collection<Protocol> collection) {
        this.protocolList.addAll(collection);
        for (Protocol protocol : collection) {
            protocol.init(this.userConnection);
            this.protocolSet.add(protocol.getClass());
        }
        this.moveBaseProtocolsToTail();
    }

    private void moveBaseProtocolsToTail() {
        ArrayList<Protocol> arrayList = null;
        for (Protocol protocol : this.protocolList) {
            if (!protocol.isBaseProtocol()) continue;
            if (arrayList == null) {
                arrayList = new ArrayList<Protocol>();
            }
            arrayList.add(protocol);
        }
        if (arrayList != null) {
            this.protocolList.removeAll(arrayList);
            this.protocolList.addAll(arrayList);
        }
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.getId();
        DebugHandler debugHandler = Via.getManager().debugHandler();
        if (debugHandler.enabled() && !debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper, direction)) {
            this.logPacket(direction, state, packetWrapper, n);
        }
        packetWrapper.apply(direction, state, 0, this.protocolList, direction == Direction.CLIENTBOUND);
        super.transform(direction, state, packetWrapper);
        if (debugHandler.enabled() && debugHandler.logPostPacketTransform() && debugHandler.shouldLog(packetWrapper, direction)) {
            this.logPacket(direction, state, packetWrapper, n);
        }
    }

    private void logPacket(Direction direction, State state, PacketWrapper packetWrapper, int n) {
        int n2 = this.userConnection.getProtocolInfo().getProtocolVersion();
        ViaPlatform viaPlatform = Via.getPlatform();
        String string = packetWrapper.user().getProtocolInfo().getUsername();
        String string2 = string != null ? string + " " : "";
        viaPlatform.getLogger().log(Level.INFO, "{0}{1} {2}: {3} ({4}) -> {5} ({6}) [{7}] {8}", new Object[]{string2, direction, state, n, AbstractSimpleProtocol.toNiceHex(n), packetWrapper.getId(), AbstractSimpleProtocol.toNiceHex(packetWrapper.getId()), Integer.toString(n2), packetWrapper});
    }

    @Override
    public boolean contains(Class<? extends Protocol> clazz) {
        return this.protocolSet.contains(clazz);
    }

    @Override
    public <P extends Protocol> @Nullable P getProtocol(Class<P> clazz) {
        for (Protocol protocol : this.protocolList) {
            if (protocol.getClass() != clazz) continue;
            return (P)protocol;
        }
        return null;
    }

    @Override
    public List<Protocol> pipes() {
        return this.protocolList;
    }

    @Override
    public boolean hasNonBaseProtocols() {
        for (Protocol protocol : this.protocolList) {
            if (protocol.isBaseProtocol()) continue;
            return false;
        }
        return true;
    }

    @Override
    public void cleanPipes() {
        this.registerPackets();
    }
}

