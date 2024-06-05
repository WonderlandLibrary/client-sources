/*
 * Decompiled with CFR 0.152.
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
import java.util.Iterator;
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
        Protocol baseProtocol = Via.getManager().getProtocolManager().getBaseProtocol();
        this.protocolList.add(baseProtocol);
        this.protocolSet.add(baseProtocol.getClass());
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
        if (protocol.isBaseProtocol()) return;
        this.moveBaseProtocolsToTail();
    }

    @Override
    public void add(Collection<Protocol> protocols) {
        this.protocolList.addAll(protocols);
        Iterator<Protocol> iterator = protocols.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.moveBaseProtocolsToTail();
                return;
            }
            Protocol protocol = iterator.next();
            protocol.init(this.userConnection);
            this.protocolSet.add(protocol.getClass());
        }
    }

    private void moveBaseProtocolsToTail() {
        ArrayList<Protocol> baseProtocols = null;
        Iterator<Protocol> iterator = this.protocolList.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                if (baseProtocols == null) return;
                this.protocolList.removeAll(baseProtocols);
                this.protocolList.addAll(baseProtocols);
                return;
            }
            Protocol protocol = iterator.next();
            if (!protocol.isBaseProtocol()) continue;
            if (baseProtocols == null) {
                baseProtocols = new ArrayList<Protocol>();
            }
            baseProtocols.add(protocol);
        }
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        int originalID = packetWrapper.getId();
        packetWrapper.apply(direction, state, 0, this.protocolList, direction == Direction.CLIENTBOUND);
        super.transform(direction, state, packetWrapper);
        DebugHandler debugHandler = Via.getManager().debugHandler();
        if (!debugHandler.enabled()) return;
        if (!debugHandler.logPostPacketTransform()) return;
        if (!debugHandler.shouldLog(packetWrapper)) return;
        this.logPacket(direction, state, packetWrapper, originalID);
    }

    private void logPacket(Direction direction, State state, PacketWrapper packetWrapper, int originalID) {
        int clientProtocol = this.userConnection.getProtocolInfo().getProtocolVersion();
        ViaPlatform platform = Via.getPlatform();
        String actualUsername = packetWrapper.user().getProtocolInfo().getUsername();
        String username = actualUsername != null ? actualUsername + " " : "";
        platform.getLogger().log(Level.INFO, "{0}{1} {2}: {3} (0x{4}) -> {5} (0x{6}) [{7}] {8}", new Object[]{username, direction, state, originalID, Integer.toHexString(originalID), packetWrapper.getId(), Integer.toHexString(packetWrapper.getId()), Integer.toString(clientProtocol), packetWrapper});
    }

    @Override
    public boolean contains(Class<? extends Protocol> protocolClass) {
        return this.protocolSet.contains(protocolClass);
    }

    @Override
    public <P extends Protocol> @Nullable P getProtocol(Class<P> pipeClass) {
        Protocol protocol;
        Iterator<Protocol> iterator = this.protocolList.iterator();
        do {
            if (!iterator.hasNext()) return null;
        } while ((protocol = iterator.next()).getClass() != pipeClass);
        return (P)protocol;
    }

    @Override
    public List<Protocol> pipes() {
        return this.protocolList;
    }

    @Override
    public boolean hasNonBaseProtocols() {
        Protocol protocol;
        Iterator<Protocol> iterator = this.protocolList.iterator();
        do {
            if (!iterator.hasNext()) return false;
        } while ((protocol = iterator.next()).isBaseProtocol());
        return true;
    }

    @Override
    public void cleanPipes() {
        this.registerPackets();
    }
}

