/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMapping;
import com.viaversion.viaversion.api.protocol.packet.mapping.PacketMappings;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMap;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.packet.provider.SimplePacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.rewriter.Rewriter;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import org.checkerframework.checker.nullness.qual.Nullable;

public abstract class AbstractProtocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType>
implements Protocol<CU, CM, SM, SU> {
    protected final Class<CU> unmappedClientboundPacketType;
    protected final Class<CM> mappedClientboundPacketType;
    protected final Class<SM> mappedServerboundPacketType;
    protected final Class<SU> unmappedServerboundPacketType;
    protected final PacketTypesProvider<CU, CM, SM, SU> packetTypesProvider;
    protected final PacketMappings clientboundMappings;
    protected final PacketMappings serverboundMappings;
    private final Map<Class<?>, Object> storedObjects = new HashMap();
    private boolean initialized;

    @Deprecated
    protected AbstractProtocol() {
        this(null, null, null, null);
    }

    protected AbstractProtocol(@Nullable Class<CU> unmappedClientboundPacketType, @Nullable Class<CM> mappedClientboundPacketType, @Nullable Class<SM> mappedServerboundPacketType, @Nullable Class<SU> unmappedServerboundPacketType) {
        this.unmappedClientboundPacketType = unmappedClientboundPacketType;
        this.mappedClientboundPacketType = mappedClientboundPacketType;
        this.mappedServerboundPacketType = mappedServerboundPacketType;
        this.unmappedServerboundPacketType = unmappedServerboundPacketType;
        this.packetTypesProvider = this.createPacketTypesProvider();
        this.clientboundMappings = this.createClientboundPacketMappings();
        this.serverboundMappings = this.createServerboundPacketMappings();
    }

    @Override
    public final void initialize() {
        Preconditions.checkArgument(!this.initialized, "Protocol has already been initialized");
        this.initialized = true;
        this.registerPackets();
        this.registerConfigurationChangeHandlers();
        if (this.unmappedClientboundPacketType != null && this.mappedClientboundPacketType != null && this.unmappedClientboundPacketType != this.mappedClientboundPacketType) {
            this.registerPacketIdChanges(this.packetTypesProvider.unmappedClientboundPacketTypes(), this.packetTypesProvider.mappedClientboundPacketTypes(), this::hasRegisteredClientbound, this::registerClientbound);
        }
        if (this.mappedServerboundPacketType != null && this.unmappedServerboundPacketType != null && this.mappedServerboundPacketType != this.unmappedServerboundPacketType) {
            this.registerPacketIdChanges(this.packetTypesProvider.unmappedServerboundPacketTypes(), this.packetTypesProvider.mappedServerboundPacketTypes(), this::hasRegisteredServerbound, this::registerServerbound);
        }
    }

    protected void registerConfigurationChangeHandlers() {
        ClientboundPacketType clientboundFinishConfigurationPacket;
        ServerboundPacketType finishConfigurationPacket;
        CU startConfigurationPacket;
        SU configurationAcknowledgedPacket = this.configurationAcknowledgedPacket();
        if (configurationAcknowledgedPacket != null) {
            this.registerServerbound(configurationAcknowledgedPacket, this.setClientStateHandler(State.CONFIGURATION));
        }
        if ((startConfigurationPacket = this.startConfigurationPacket()) != null) {
            this.registerClientbound(startConfigurationPacket, this.setServerStateHandler(State.CONFIGURATION));
        }
        if ((finishConfigurationPacket = this.serverboundFinishConfigurationPacket()) != null) {
            int id = finishConfigurationPacket.getId();
            this.registerServerbound(State.CONFIGURATION, id, id, this.setClientStateHandler(State.PLAY));
        }
        if ((clientboundFinishConfigurationPacket = this.clientboundFinishConfigurationPacket()) != null) {
            int id = clientboundFinishConfigurationPacket.getId();
            this.registerClientbound(State.CONFIGURATION, id, id, this.setServerStateHandler(State.PLAY));
        }
    }

    private <U extends PacketType, M extends PacketType> void registerPacketIdChanges(Map<State, PacketTypeMap<U>> unmappedPacketTypes, Map<State, PacketTypeMap<M>> mappedPacketTypes, Predicate<U> registeredPredicate, BiConsumer<U, M> registerConsumer) {
        for (Map.Entry<State, PacketTypeMap<M>> entry : mappedPacketTypes.entrySet()) {
            PacketTypeMap<M> mappedTypes = entry.getValue();
            for (PacketType unmappedType : unmappedPacketTypes.get((Object)entry.getKey()).types()) {
                M mappedType = mappedTypes.typeByName(unmappedType.getName());
                if (mappedType == null) {
                    Preconditions.checkArgument(registeredPredicate.test(unmappedType), "Packet %s in %s has no mapping - it needs to be manually cancelled or remapped", new Object[]{unmappedType, this.getClass()});
                    continue;
                }
                if (unmappedType.getId() == mappedType.getId() || registeredPredicate.test(unmappedType)) continue;
                registerConsumer.accept(unmappedType, mappedType);
            }
        }
    }

    @Override
    public final void loadMappingData() {
        this.getMappingData().load();
        this.onMappingDataLoaded();
    }

    protected void registerPackets() {
        this.callRegister(this.getEntityRewriter());
        this.callRegister(this.getItemRewriter());
    }

    protected void onMappingDataLoaded() {
        this.callOnMappingDataLoaded(this.getEntityRewriter());
        this.callOnMappingDataLoaded(this.getItemRewriter());
    }

    private void callRegister(@Nullable Rewriter<?> rewriter) {
        if (rewriter != null) {
            rewriter.register();
        }
    }

    private void callOnMappingDataLoaded(@Nullable Rewriter<?> rewriter) {
        if (rewriter != null) {
            rewriter.onMappingDataLoaded();
        }
    }

    protected void addEntityTracker(UserConnection connection, EntityTracker tracker) {
        connection.addEntityTracker(this.getClass(), tracker);
    }

    protected PacketTypesProvider<CU, CM, SM, SU> createPacketTypesProvider() {
        return new SimplePacketTypesProvider<CU, CM, SM, SU>(this.packetTypeMap(this.unmappedClientboundPacketType), this.packetTypeMap(this.mappedClientboundPacketType), this.packetTypeMap(this.mappedServerboundPacketType), this.packetTypeMap(this.unmappedServerboundPacketType));
    }

    protected PacketMappings createClientboundPacketMappings() {
        return PacketMappings.arrayMappings();
    }

    protected PacketMappings createServerboundPacketMappings() {
        return PacketMappings.arrayMappings();
    }

    private <P extends PacketType> Map<State, PacketTypeMap<P>> packetTypeMap(Class<P> packetTypeClass) {
        if (packetTypeClass != null) {
            EnumMap<State, PacketTypeMap<P>> map = new EnumMap<State, PacketTypeMap<P>>(State.class);
            map.put(State.PLAY, PacketTypeMap.of(packetTypeClass));
            return map;
        }
        return Collections.emptyMap();
    }

    protected @Nullable SU configurationAcknowledgedPacket() {
        Map<State, PacketTypeMap<SU>> packetTypes = this.packetTypesProvider.unmappedServerboundPacketTypes();
        PacketTypeMap<SU> packetTypeMap = packetTypes.get((Object)State.PLAY);
        return (SU)(packetTypeMap != null ? (ServerboundPacketType)packetTypeMap.typeByName("CONFIGURATION_ACKNOWLEDGED") : null);
    }

    protected @Nullable CU startConfigurationPacket() {
        Map<State, PacketTypeMap<CU>> packetTypes = this.packetTypesProvider.unmappedClientboundPacketTypes();
        PacketTypeMap<CU> packetTypeMap = packetTypes.get((Object)State.PLAY);
        return (CU)(packetTypeMap != null ? (ClientboundPacketType)packetTypeMap.typeByName("START_CONFIGURATION") : null);
    }

    protected @Nullable ServerboundPacketType serverboundFinishConfigurationPacket() {
        return null;
    }

    protected @Nullable ClientboundPacketType clientboundFinishConfigurationPacket() {
        return null;
    }

    @Override
    public void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler, boolean override) {
        Preconditions.checkArgument(unmappedPacketId != -1, "Unmapped packet id cannot be -1");
        PacketMapping packetMapping = PacketMapping.of(mappedPacketId, handler);
        if (!override && this.serverboundMappings.hasMapping(state, unmappedPacketId)) {
            Via.getPlatform().getLogger().log(Level.WARNING, unmappedPacketId + " already registered! If this override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        this.serverboundMappings.addMapping(state, unmappedPacketId, packetMapping);
    }

    @Override
    public void cancelServerbound(State state, int unmappedPacketId) {
        this.registerServerbound(state, unmappedPacketId, unmappedPacketId, PacketWrapper::cancel);
    }

    @Override
    public void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler, boolean override) {
        Preconditions.checkArgument(unmappedPacketId != -1, "Unmapped packet id cannot be -1");
        PacketMapping packetMapping = PacketMapping.of(mappedPacketId, handler);
        if (!override && this.clientboundMappings.hasMapping(state, unmappedPacketId)) {
            Via.getPlatform().getLogger().log(Level.WARNING, unmappedPacketId + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        this.clientboundMappings.addMapping(state, unmappedPacketId, packetMapping);
    }

    @Override
    public void cancelClientbound(State state, int unmappedPacketId) {
        this.registerClientbound(state, unmappedPacketId, unmappedPacketId, PacketWrapper::cancel);
    }

    @Override
    public void registerClientbound(CU packetType, @Nullable PacketHandler handler) {
        PacketTypeMap<CM> mappedPacketTypes = this.packetTypesProvider.mappedClientboundPacketTypes().get((Object)packetType.state());
        ClientboundPacketType mappedPacketType = (ClientboundPacketType)AbstractProtocol.mappedPacketType(packetType, mappedPacketTypes, this.unmappedClientboundPacketType, this.mappedClientboundPacketType);
        this.registerClientbound(packetType, mappedPacketType, handler);
    }

    @Override
    public void registerClientbound(CU packetType, @Nullable CM mappedPacketType, @Nullable PacketHandler handler, boolean override) {
        this.register(this.clientboundMappings, (PacketType)packetType, (PacketType)mappedPacketType, (Class<? extends PacketType>)this.unmappedClientboundPacketType, (Class<? extends PacketType>)this.mappedClientboundPacketType, handler, override);
    }

    @Override
    public void cancelClientbound(CU packetType) {
        this.registerClientbound(packetType, null, PacketWrapper::cancel);
    }

    @Override
    public void registerServerbound(SU packetType, @Nullable PacketHandler handler) {
        PacketTypeMap<SM> mappedPacketTypes = this.packetTypesProvider.mappedServerboundPacketTypes().get((Object)packetType.state());
        ServerboundPacketType mappedPacketType = (ServerboundPacketType)AbstractProtocol.mappedPacketType(packetType, mappedPacketTypes, this.unmappedServerboundPacketType, this.mappedServerboundPacketType);
        this.registerServerbound(packetType, mappedPacketType, handler);
    }

    @Override
    public void registerServerbound(SU packetType, @Nullable SM mappedPacketType, @Nullable PacketHandler handler, boolean override) {
        this.register(this.serverboundMappings, (PacketType)packetType, (PacketType)mappedPacketType, (Class<? extends PacketType>)this.unmappedServerboundPacketType, (Class<? extends PacketType>)this.mappedServerboundPacketType, handler, override);
    }

    @Override
    public void cancelServerbound(SU packetType) {
        this.registerServerbound(packetType, null, PacketWrapper::cancel);
    }

    private void register(PacketMappings packetMappings, PacketType packetType, @Nullable PacketType mappedPacketType, Class<? extends PacketType> unmappedPacketClass, Class<? extends PacketType> mappedPacketClass, @Nullable PacketHandler handler, boolean override) {
        AbstractProtocol.checkPacketType(packetType, unmappedPacketClass == null || unmappedPacketClass.isInstance(packetType));
        if (mappedPacketType != null) {
            AbstractProtocol.checkPacketType(mappedPacketType, mappedPacketClass == null || mappedPacketClass.isInstance(mappedPacketType));
            Preconditions.checkArgument(packetType.state() == mappedPacketType.state(), "Packet type state does not match mapped packet type state");
            Preconditions.checkArgument(packetType.direction() == mappedPacketType.direction(), "Packet type direction does not match mapped packet type state");
        }
        PacketMapping packetMapping = PacketMapping.of(mappedPacketType, handler);
        if (!override && packetMappings.hasMapping(packetType)) {
            Via.getPlatform().getLogger().log(Level.WARNING, packetType + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        packetMappings.addMapping(packetType, packetMapping);
    }

    private static <U extends PacketType, M extends PacketType> M mappedPacketType(U packetType, PacketTypeMap<M> mappedTypes, Class<U> unmappedPacketTypeClass, Class<M> mappedPacketTypeClass) {
        Preconditions.checkNotNull(packetType);
        AbstractProtocol.checkPacketType(packetType, unmappedPacketTypeClass == null || unmappedPacketTypeClass.isInstance(packetType));
        if (unmappedPacketTypeClass == mappedPacketTypeClass) {
            return (M)packetType;
        }
        Preconditions.checkNotNull(mappedTypes, "Mapped packet types not provided for state %s of type class %s", new Object[]{packetType.state(), mappedPacketTypeClass});
        M mappedType = mappedTypes.typeByName(packetType.getName());
        if (mappedType != null) {
            return mappedType;
        }
        throw new IllegalArgumentException("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " could not be automatically mapped!");
    }

    @Override
    public boolean hasRegisteredClientbound(State state, int unmappedPacketId) {
        return this.clientboundMappings.hasMapping(state, unmappedPacketId);
    }

    @Override
    public boolean hasRegisteredServerbound(State state, int unmappedPacketId) {
        return this.serverboundMappings.hasMapping(state, unmappedPacketId);
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        int unmappedId;
        PacketMappings mappings = direction == Direction.CLIENTBOUND ? this.clientboundMappings : this.serverboundMappings;
        PacketMapping packetMapping = mappings.mappedPacket(state, unmappedId = packetWrapper.getId());
        if (packetMapping == null) {
            return;
        }
        packetMapping.applyType(packetWrapper);
        PacketHandler handler = packetMapping.handler();
        if (handler != null) {
            try {
                handler.handle(packetWrapper);
            } catch (CancelException e) {
                throw e;
            } catch (InformativeException e) {
                e.addSource(handler.getClass());
                this.throwRemapError(direction, state, unmappedId, packetWrapper.getId(), e);
                return;
            } catch (Exception e) {
                InformativeException ex = new InformativeException(e);
                ex.addSource(handler.getClass());
                this.throwRemapError(direction, state, unmappedId, packetWrapper.getId(), ex);
                return;
            }
            if (packetWrapper.isCancelled()) {
                throw CancelException.generate();
            }
        }
    }

    protected void throwRemapError(Direction direction, State state, int unmappedPacketId, int mappedPacketId, InformativeException e) throws InformativeException {
        Object packetType;
        if (state != State.PLAY && direction == Direction.SERVERBOUND && !Via.getManager().debugHandler().enabled()) {
            e.setShouldBePrinted(false);
            throw e;
        }
        Object object = packetType = direction == Direction.CLIENTBOUND ? this.unmappedClientboundPacketType(state, unmappedPacketId) : this.unmappedServerboundPacketType(state, unmappedPacketId);
        if (packetType != null) {
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + packetType + " (" + AbstractProtocol.toNiceHex(unmappedPacketId) + ")");
        } else {
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + AbstractProtocol.toNiceHex(unmappedPacketId) + "->" + AbstractProtocol.toNiceHex(mappedPacketId));
        }
        throw e;
    }

    private @Nullable CU unmappedClientboundPacketType(State state, int packetId) {
        PacketTypeMap<CU> map = this.packetTypesProvider.unmappedClientboundPacketTypes().get((Object)state);
        return (CU)(map != null ? (ClientboundPacketType)map.typeById(packetId) : null);
    }

    private @Nullable SU unmappedServerboundPacketType(State state, int packetId) {
        PacketTypeMap<SU> map = this.packetTypesProvider.unmappedServerboundPacketTypes().get((Object)state);
        return (SU)(map != null ? (ServerboundPacketType)map.typeById(packetId) : null);
    }

    public static String toNiceHex(int id) {
        String hex = Integer.toHexString(id).toUpperCase();
        return (hex.length() == 1 ? "0x0" : "0x") + hex;
    }

    private static void checkPacketType(PacketType packetType, boolean isValid) {
        if (!isValid) {
            throw new IllegalArgumentException("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " is taken from the wrong packet types class");
        }
    }

    protected PacketHandler setClientStateHandler(State state) {
        return wrapper -> wrapper.user().getProtocolInfo().setClientState(state);
    }

    protected PacketHandler setServerStateHandler(State state) {
        return wrapper -> wrapper.user().getProtocolInfo().setClientState(state);
    }

    @Override
    public PacketTypesProvider<CU, CM, SM, SU> getPacketTypesProvider() {
        return this.packetTypesProvider;
    }

    @Override
    public <T> @Nullable T get(Class<T> objectClass) {
        return (T)this.storedObjects.get(objectClass);
    }

    @Override
    public void put(Object object) {
        this.storedObjects.put(object.getClass(), object);
    }

    public PacketTypesProvider<CU, CM, SM, SU> packetTypesProvider() {
        return this.packetTypesProvider;
    }

    public String toString() {
        return "Protocol:" + this.getClass().getSimpleName();
    }
}

