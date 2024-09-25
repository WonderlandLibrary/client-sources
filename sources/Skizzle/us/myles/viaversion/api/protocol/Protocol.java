/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.protocol;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.MappingData;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.providers.ViaProviders;
import us.myles.ViaVersion.api.protocol.ClientboundPacketType;
import us.myles.ViaVersion.api.protocol.PacketType;
import us.myles.ViaVersion.api.protocol.ServerboundPacketType;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.exception.CancelException;
import us.myles.ViaVersion.exception.InformativeException;
import us.myles.ViaVersion.packets.Direction;
import us.myles.ViaVersion.packets.State;

public abstract class Protocol<C1 extends ClientboundPacketType, C2 extends ClientboundPacketType, S1 extends ServerboundPacketType, S2 extends ServerboundPacketType> {
    private final Map<Packet, ProtocolPacket> incoming = new HashMap<Packet, ProtocolPacket>();
    private final Map<Packet, ProtocolPacket> outgoing = new HashMap<Packet, ProtocolPacket>();
    private final Map<Class, Object> storedObjects = new HashMap<Class, Object>();
    protected final Class<C1> oldClientboundPacketEnum;
    protected final Class<C2> newClientboundPacketEnum;
    protected final Class<S1> oldServerboundPacketEnum;
    protected final Class<S2> newServerboundPacketEnum;

    protected Protocol() {
        this(null, null, null, null);
    }

    protected Protocol(@Nullable Class<C1> oldClientboundPacketEnum, @Nullable Class<C2> clientboundPacketEnum, @Nullable Class<S1> oldServerboundPacketEnum, @Nullable Class<S2> serverboundPacketEnum) {
        this.oldClientboundPacketEnum = oldClientboundPacketEnum;
        this.newClientboundPacketEnum = clientboundPacketEnum;
        this.oldServerboundPacketEnum = oldServerboundPacketEnum;
        this.newServerboundPacketEnum = serverboundPacketEnum;
        this.registerPackets();
        if (oldClientboundPacketEnum != null && clientboundPacketEnum != null && oldClientboundPacketEnum != clientboundPacketEnum) {
            this.registerOutgoingChannelIdChanges();
        }
        if (oldServerboundPacketEnum != null && serverboundPacketEnum != null && oldServerboundPacketEnum != serverboundPacketEnum) {
            this.registerIncomingChannelIdChanges();
        }
    }

    protected void registerOutgoingChannelIdChanges() {
        ClientboundPacketType[] newConstants = (ClientboundPacketType[])this.newClientboundPacketEnum.getEnumConstants();
        HashMap<String, ClientboundPacketType> newClientboundPackets = new HashMap<String, ClientboundPacketType>(newConstants.length);
        for (ClientboundPacketType newConstant : newConstants) {
            newClientboundPackets.put(newConstant.name(), newConstant);
        }
        for (ClientboundPacketType packet : (ClientboundPacketType[])this.oldClientboundPacketEnum.getEnumConstants()) {
            ClientboundPacketType mappedPacket = (ClientboundPacketType)newClientboundPackets.get(packet.name());
            int oldId = packet.ordinal();
            if (mappedPacket == null) {
                Preconditions.checkArgument((boolean)this.hasRegisteredOutgoing(State.PLAY, oldId), (Object)("Packet " + packet + " in " + this.getClass().getSimpleName() + " has no mapping - it needs to be manually cancelled or remapped!"));
                continue;
            }
            int newId = mappedPacket.ordinal();
            if (this.hasRegisteredOutgoing(State.PLAY, oldId)) continue;
            this.registerOutgoing(State.PLAY, oldId, newId);
        }
    }

    protected void registerIncomingChannelIdChanges() {
        ServerboundPacketType[] oldConstants = (ServerboundPacketType[])this.oldServerboundPacketEnum.getEnumConstants();
        HashMap<String, ServerboundPacketType> oldServerboundConstants = new HashMap<String, ServerboundPacketType>(oldConstants.length);
        for (ServerboundPacketType oldConstant : oldConstants) {
            oldServerboundConstants.put(oldConstant.name(), oldConstant);
        }
        for (ServerboundPacketType packet : (ServerboundPacketType[])this.newServerboundPacketEnum.getEnumConstants()) {
            ServerboundPacketType mappedPacket = (ServerboundPacketType)oldServerboundConstants.get(packet.name());
            int newId = packet.ordinal();
            if (mappedPacket == null) {
                Preconditions.checkArgument((boolean)this.hasRegisteredIncoming(State.PLAY, newId), (Object)("Packet " + packet + " in " + this.getClass().getSimpleName() + " has no mapping - it needs to be manually cancelled or remapped!"));
                continue;
            }
            int oldId = mappedPacket.ordinal();
            if (this.hasRegisteredIncoming(State.PLAY, newId)) continue;
            this.registerIncoming(State.PLAY, oldId, newId);
        }
    }

    public boolean isFiltered(Class packetClass) {
        return false;
    }

    protected void filterPacket(UserConnection info, Object packet, List output) throws Exception {
        output.add(packet);
    }

    protected void registerPackets() {
    }

    protected final void loadMappingData() {
        this.getMappingData().load();
        this.onMappingDataLoaded();
    }

    protected void onMappingDataLoaded() {
    }

    protected void register(ViaProviders providers) {
    }

    public void init(UserConnection userConnection) {
    }

    public void registerIncoming(State state, int oldPacketID, int newPacketID) {
        this.registerIncoming(state, oldPacketID, newPacketID, null);
    }

    public void registerIncoming(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper) {
        this.registerIncoming(state, oldPacketID, newPacketID, packetRemapper, false);
    }

    public void registerIncoming(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper, boolean override) {
        ProtocolPacket protocolPacket = new ProtocolPacket(state, oldPacketID, newPacketID, packetRemapper);
        Packet packet = new Packet(state, newPacketID);
        if (!override && this.incoming.containsKey(packet)) {
            Via.getPlatform().getLogger().log(Level.WARNING, packet + " already registered! If this override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        this.incoming.put(packet, protocolPacket);
    }

    public void cancelIncoming(State state, int oldPacketID, int newPacketID) {
        this.registerIncoming(state, oldPacketID, newPacketID, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(PacketWrapper::cancel);
            }
        });
    }

    public void cancelIncoming(State state, int newPacketID) {
        this.cancelIncoming(state, -1, newPacketID);
    }

    public void registerOutgoing(State state, int oldPacketID, int newPacketID) {
        this.registerOutgoing(state, oldPacketID, newPacketID, null);
    }

    public void registerOutgoing(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper) {
        this.registerOutgoing(state, oldPacketID, newPacketID, packetRemapper, false);
    }

    public void cancelOutgoing(State state, int oldPacketID, int newPacketID) {
        this.registerOutgoing(state, oldPacketID, newPacketID, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(PacketWrapper::cancel);
            }
        });
    }

    public void cancelOutgoing(State state, int oldPacketID) {
        this.cancelOutgoing(state, oldPacketID, -1);
    }

    public void registerOutgoing(State state, int oldPacketID, int newPacketID, PacketRemapper packetRemapper, boolean override) {
        ProtocolPacket protocolPacket = new ProtocolPacket(state, oldPacketID, newPacketID, packetRemapper);
        Packet packet = new Packet(state, oldPacketID);
        if (!override && this.outgoing.containsKey(packet)) {
            Via.getPlatform().getLogger().log(Level.WARNING, packet + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        this.outgoing.put(packet, protocolPacket);
    }

    public void registerOutgoing(C1 packetType, @Nullable PacketRemapper packetRemapper) {
        this.checkPacketType((PacketType)packetType, packetType.getClass() == this.oldClientboundPacketEnum);
        Object mappedPacket = this.oldClientboundPacketEnum == this.newClientboundPacketEnum ? packetType : (ClientboundPacketType)Arrays.stream(this.newClientboundPacketEnum.getEnumConstants()).filter(en -> en.name().equals(packetType.name())).findAny().orElse(null);
        Preconditions.checkNotNull(mappedPacket, (Object)("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " could not be automatically mapped!"));
        int oldId = packetType.ordinal();
        int newId = mappedPacket.ordinal();
        this.registerOutgoing(State.PLAY, oldId, newId, packetRemapper);
    }

    public void registerOutgoing(C1 packetType, @Nullable C2 mappedPacketType, @Nullable PacketRemapper packetRemapper) {
        this.checkPacketType((PacketType)packetType, packetType.getClass() == this.oldClientboundPacketEnum);
        this.checkPacketType((PacketType)mappedPacketType, mappedPacketType == null || mappedPacketType.getClass() == this.newClientboundPacketEnum);
        this.registerOutgoing(State.PLAY, packetType.ordinal(), mappedPacketType != null ? mappedPacketType.ordinal() : -1, packetRemapper);
    }

    public void registerOutgoing(C1 packetType, @Nullable C2 mappedPacketType) {
        this.registerOutgoing(packetType, mappedPacketType, null);
    }

    public void cancelOutgoing(C1 packetType) {
        this.cancelOutgoing(State.PLAY, packetType.ordinal(), packetType.ordinal());
    }

    public void registerIncoming(S2 packetType, @Nullable PacketRemapper packetRemapper) {
        this.checkPacketType((PacketType)packetType, packetType.getClass() == this.newServerboundPacketEnum);
        Object mappedPacket = this.oldServerboundPacketEnum == this.newServerboundPacketEnum ? packetType : (ServerboundPacketType)Arrays.stream(this.oldServerboundPacketEnum.getEnumConstants()).filter(en -> en.name().equals(packetType.name())).findAny().orElse(null);
        Preconditions.checkNotNull(mappedPacket, (Object)("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " could not be automatically mapped!"));
        int oldId = mappedPacket.ordinal();
        int newId = packetType.ordinal();
        this.registerIncoming(State.PLAY, oldId, newId, packetRemapper);
    }

    public void registerIncoming(S2 packetType, @Nullable S1 mappedPacketType, @Nullable PacketRemapper packetRemapper) {
        this.checkPacketType((PacketType)packetType, packetType.getClass() == this.newServerboundPacketEnum);
        this.checkPacketType((PacketType)mappedPacketType, mappedPacketType == null || mappedPacketType.getClass() == this.oldServerboundPacketEnum);
        this.registerIncoming(State.PLAY, mappedPacketType != null ? mappedPacketType.ordinal() : -1, packetType.ordinal(), packetRemapper);
    }

    public void cancelIncoming(S2 packetType) {
        Preconditions.checkArgument((packetType.getClass() == this.newServerboundPacketEnum ? 1 : 0) != 0);
        this.cancelIncoming(State.PLAY, -1, packetType.ordinal());
    }

    public boolean hasRegisteredOutgoing(State state, int oldPacketID) {
        Packet packet = new Packet(state, oldPacketID);
        return this.outgoing.containsKey(packet);
    }

    public boolean hasRegisteredIncoming(State state, int newPacketId) {
        Packet packet = new Packet(state, newPacketId);
        return this.incoming.containsKey(packet);
    }

    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        Packet statePacket;
        Map<Packet, ProtocolPacket> packetMap = direction == Direction.OUTGOING ? this.outgoing : this.incoming;
        ProtocolPacket protocolPacket = packetMap.get(statePacket = new Packet(state, packetWrapper.getId()));
        if (protocolPacket == null) {
            return;
        }
        int oldId = packetWrapper.getId();
        int newId = direction == Direction.OUTGOING ? protocolPacket.getNewID() : protocolPacket.getOldID();
        packetWrapper.setId(newId);
        PacketRemapper remapper = protocolPacket.getRemapper();
        if (remapper != null) {
            try {
                remapper.remap(packetWrapper);
            }
            catch (InformativeException e) {
                this.throwRemapError(direction, state, oldId, newId, e);
                return;
            }
            if (packetWrapper.isCancelled()) {
                throw CancelException.generate();
            }
        }
    }

    private void throwRemapError(Direction direction, State state, int oldId, int newId, InformativeException e) throws InformativeException {
        Class<C1> packetTypeClass;
        if (state == State.HANDSHAKE) {
            throw e;
        }
        Class<Object> class_ = state == State.PLAY ? (direction == Direction.OUTGOING ? this.oldClientboundPacketEnum : this.newServerboundPacketEnum) : (packetTypeClass = null);
        if (packetTypeClass != null) {
            PacketType[] enumConstants = (PacketType[])packetTypeClass.getEnumConstants();
            PacketType packetType = oldId < enumConstants.length && oldId >= 0 ? enumConstants[oldId] : null;
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + packetType + " (" + this.toNiceHex(oldId) + ")");
        } else {
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + this.toNiceHex(oldId) + "->" + this.toNiceHex(newId));
        }
        throw e;
    }

    private String toNiceHex(int id) {
        String hex = Integer.toHexString(id).toUpperCase();
        return (hex.length() == 1 ? "0x0" : "0x") + hex;
    }

    private void checkPacketType(PacketType packetType, boolean isValid) {
        if (!isValid) {
            throw new IllegalArgumentException("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " is taken from the wrong enum");
        }
    }

    @Nullable
    public <T> T get(Class<T> objectClass) {
        return (T)this.storedObjects.get(objectClass);
    }

    public void put(Object object) {
        this.storedObjects.put(object.getClass(), object);
    }

    public boolean hasMappingDataToLoad() {
        return this.getMappingData() != null;
    }

    @Nullable
    public MappingData getMappingData() {
        return null;
    }

    public String toString() {
        return "Protocol:" + this.getClass().getSimpleName();
    }

    public static class ProtocolPacket {
        private final State state;
        private final int oldID;
        private final int newID;
        private final PacketRemapper remapper;

        public ProtocolPacket(State state, int oldID, int newID, @Nullable PacketRemapper remapper) {
            this.state = state;
            this.oldID = oldID;
            this.newID = newID;
            this.remapper = remapper;
        }

        public State getState() {
            return this.state;
        }

        public int getOldID() {
            return this.oldID;
        }

        public int getNewID() {
            return this.newID;
        }

        @Nullable
        public PacketRemapper getRemapper() {
            return this.remapper;
        }
    }

    public static class Packet {
        private final State state;
        private final int packetId;

        public Packet(State state, int packetId) {
            this.state = state;
            this.packetId = packetId;
        }

        public State getState() {
            return this.state;
        }

        public int getPacketId() {
            return this.packetId;
        }

        public String toString() {
            return "Packet{state=" + (Object)((Object)this.state) + ", packetId=" + this.packetId + '}';
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Packet that = (Packet)o;
            return this.packetId == that.packetId && this.state == that.state;
        }

        public int hashCode() {
            int result = this.state != null ? this.state.hashCode() : 0;
            result = 31 * result + this.packetId;
            return result;
        }
    }
}

