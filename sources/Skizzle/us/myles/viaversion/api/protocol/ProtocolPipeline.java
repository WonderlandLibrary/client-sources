/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.ViaPlatform;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.protocol.SimpleProtocol;
import us.myles.ViaVersion.packets.Direction;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;

public class ProtocolPipeline
extends SimpleProtocol {
    private List<Protocol> protocolList;
    private UserConnection userConnection;

    public ProtocolPipeline(UserConnection userConnection) {
        this.init(userConnection);
    }

    @Override
    protected void registerPackets() {
        this.protocolList = new CopyOnWriteArrayList<Protocol>();
        this.protocolList.add(ProtocolRegistry.BASE_PROTOCOL);
    }

    @Override
    public void init(UserConnection userConnection) {
        this.userConnection = userConnection;
        ProtocolInfo protocolInfo = new ProtocolInfo(userConnection);
        protocolInfo.setPipeline(this);
        userConnection.setProtocolInfo(protocolInfo);
        for (Protocol protocol : this.protocolList) {
            protocol.init(userConnection);
        }
    }

    public void add(Protocol protocol) {
        ArrayList<Protocol> toMove;
        if (this.protocolList != null) {
            this.protocolList.add(protocol);
            protocol.init(this.userConnection);
            toMove = new ArrayList<Protocol>();
            for (Protocol p : this.protocolList) {
                if (!ProtocolRegistry.isBaseProtocol(p)) continue;
                toMove.add(p);
            }
        } else {
            throw new NullPointerException("Tried to add protocol too early");
        }
        this.protocolList.removeAll(toMove);
        this.protocolList.addAll(toMove);
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        int originalID = packetWrapper.getId();
        packetWrapper.apply(direction, state, 0, this.protocolList, direction == Direction.OUTGOING);
        super.transform(direction, state, packetWrapper);
        if (Via.getManager().isDebug()) {
            this.logPacket(direction, state, packetWrapper, originalID);
        }
    }

    private void logPacket(Direction direction, State state, PacketWrapper packetWrapper, int originalID) {
        int clientProtocol = this.userConnection.getProtocolInfo().getProtocolVersion();
        ViaPlatform platform = Via.getPlatform();
        String actualUsername = packetWrapper.user().getProtocolInfo().getUsername();
        String username = actualUsername != null ? actualUsername + " " : "";
        platform.getLogger().log(Level.INFO, "{0}{1} {2}: {3} (0x{4}) -> {5} (0x{6}) [{7}] {8}", new Object[]{username, direction, state, originalID, Integer.toHexString(originalID), packetWrapper.getId(), Integer.toHexString(packetWrapper.getId()), Integer.toString(clientProtocol), packetWrapper});
    }

    public boolean contains(Class<? extends Protocol> pipeClass) {
        for (Protocol protocol : this.protocolList) {
            if (!protocol.getClass().equals(pipeClass)) continue;
            return true;
        }
        return false;
    }

    public <P extends Protocol> P getProtocol(Class<P> pipeClass) {
        for (Protocol protocol : this.protocolList) {
            if (protocol.getClass() != pipeClass) continue;
            return (P)protocol;
        }
        return null;
    }

    public boolean filter(Object o, List list) throws Exception {
        for (Protocol protocol : this.protocolList) {
            if (!protocol.isFiltered(o.getClass())) continue;
            protocol.filterPacket(this.userConnection, o, list);
            return true;
        }
        return false;
    }

    public List<Protocol> pipes() {
        return this.protocolList;
    }

    public void cleanPipes() {
        this.pipes().clear();
        this.registerPackets();
    }
}

