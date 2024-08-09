/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.base;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.ProtocolPipeline;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
import com.viaversion.viaversion.protocols.base.ServerboundHandshakePackets;
import java.util.ArrayList;
import java.util.List;

public class BaseProtocol
extends AbstractProtocol {
    @Override
    protected void registerPackets() {
        this.registerServerbound(ServerboundHandshakePackets.CLIENT_INTENTION, BaseProtocol::lambda$registerPackets$0);
    }

    @Override
    public boolean isBaseProtocol() {
        return false;
    }

    @Override
    public void register(ViaProviders viaProviders) {
        viaProviders.register(VersionProvider.class, new BaseVersionProvider());
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        super.transform(direction, state, packetWrapper);
        if (direction == Direction.SERVERBOUND && state == State.HANDSHAKE && packetWrapper.getId() != 0) {
            packetWrapper.user().setActive(false);
        }
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.STRING);
        packetWrapper.passthrough(Type.UNSIGNED_SHORT);
        int n2 = packetWrapper.passthrough(Type.VAR_INT);
        ProtocolInfo protocolInfo = packetWrapper.user().getProtocolInfo();
        protocolInfo.setProtocolVersion(n);
        VersionProvider versionProvider = Via.getManager().getProviders().get(VersionProvider.class);
        if (versionProvider == null) {
            packetWrapper.user().setActive(false);
            return;
        }
        int n3 = versionProvider.getClosestServerProtocol(packetWrapper.user());
        protocolInfo.setServerProtocolVersion(n3);
        List<ProtocolPathEntry> list = null;
        if (protocolInfo.getProtocolVersion() >= n3 || Via.getPlatform().isOldClientsAllowed()) {
            list = Via.getManager().getProtocolManager().getProtocolPath(protocolInfo.getProtocolVersion(), n3);
        }
        ProtocolPipeline protocolPipeline = packetWrapper.user().getProtocolInfo().getPipeline();
        if (list != null) {
            ArrayList<Protocol> arrayList = new ArrayList<Protocol>(list.size());
            for (ProtocolPathEntry protocolPathEntry : list) {
                arrayList.add(protocolPathEntry.protocol());
                Via.getManager().getProtocolManager().completeMappingDataLoading(protocolPathEntry.protocol().getClass());
            }
            protocolPipeline.add(arrayList);
            ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(n3);
            packetWrapper.set(Type.VAR_INT, 0, protocolVersion.getOriginalVersion());
        }
        protocolPipeline.add(Via.getManager().getProtocolManager().getBaseProtocol(n3));
        if (n2 == 1) {
            protocolInfo.setState(State.STATUS);
        } else if (n2 == 2) {
            protocolInfo.setState(State.LOGIN);
        }
    }
}

