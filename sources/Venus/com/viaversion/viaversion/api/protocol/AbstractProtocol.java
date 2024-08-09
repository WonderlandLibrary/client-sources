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

    protected AbstractProtocol(@Nullable Class<CU> clazz, @Nullable Class<CM> clazz2, @Nullable Class<SM> clazz3, @Nullable Class<SU> clazz4) {
        this.unmappedClientboundPacketType = clazz;
        this.mappedClientboundPacketType = clazz2;
        this.mappedServerboundPacketType = clazz3;
        this.unmappedServerboundPacketType = clazz4;
        this.packetTypesProvider = this.createPacketTypesProvider();
        this.clientboundMappings = this.createClientboundPacketMappings();
        this.serverboundMappings = this.createServerboundPacketMappings();
    }

    @Override
    public final void initialize() {
        Preconditions.checkArgument(!this.initialized, "Protocol has already been initialized");
        this.initialized = true;
        this.registerPackets();
        if (this.unmappedClientboundPacketType != null && this.mappedClientboundPacketType != null && this.unmappedClientboundPacketType != this.mappedClientboundPacketType) {
            this.registerPacketIdChanges(this.packetTypesProvider.unmappedClientboundPacketTypes(), this.packetTypesProvider.mappedClientboundPacketTypes(), this::hasRegisteredClientbound, this::registerClientbound);
        }
        if (this.mappedServerboundPacketType != null && this.unmappedServerboundPacketType != null && this.mappedServerboundPacketType != this.unmappedServerboundPacketType) {
            this.registerPacketIdChanges(this.packetTypesProvider.unmappedServerboundPacketTypes(), this.packetTypesProvider.mappedServerboundPacketTypes(), this::hasRegisteredServerbound, this::registerServerbound);
        }
    }

    private <U extends PacketType, M extends PacketType> void registerPacketIdChanges(Map<State, PacketTypeMap<U>> map, Map<State, PacketTypeMap<M>> map2, Predicate<U> predicate, BiConsumer<U, M> biConsumer) {
        for (Map.Entry<State, PacketTypeMap<M>> entry : map2.entrySet()) {
            PacketTypeMap<M> packetTypeMap = entry.getValue();
            for (PacketType packetType : map.get((Object)entry.getKey()).types()) {
                PacketType packetType2 = (PacketType)packetTypeMap.typeByName(packetType.getName());
                if (packetType2 == null) {
                    Preconditions.checkArgument(predicate.test(packetType), "Packet %s in %s has no mapping - it needs to be manually cancelled or remapped", new Object[]{packetType, this.getClass()});
                    continue;
                }
                if (packetType.getId() == packetType2.getId() || predicate.test(packetType)) continue;
                biConsumer.accept(packetType, packetType2);
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

    protected void addEntityTracker(UserConnection userConnection, EntityTracker entityTracker) {
        userConnection.addEntityTracker(this.getClass(), entityTracker);
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

    private <P extends PacketType> Map<State, PacketTypeMap<P>> packetTypeMap(Class<P> clazz) {
        if (clazz != null) {
            EnumMap<State, PacketTypeMap<P>> enumMap = new EnumMap<State, PacketTypeMap<P>>(State.class);
            enumMap.put(State.PLAY, PacketTypeMap.of(clazz));
            return enumMap;
        }
        return Collections.emptyMap();
    }

    @Override
    public void registerServerbound(State state, int n, int n2, PacketHandler packetHandler, boolean bl) {
        Preconditions.checkArgument(n != -1, "Unmapped packet id cannot be -1");
        PacketMapping packetMapping = PacketMapping.of(n2, packetHandler);
        if (!bl && this.serverboundMappings.hasMapping(state, n)) {
            Via.getPlatform().getLogger().log(Level.WARNING, n + " already registered! If this override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        this.serverboundMappings.addMapping(state, n, packetMapping);
    }

    @Override
    public void cancelServerbound(State state, int n) {
        this.registerServerbound(state, n, n, PacketWrapper::cancel);
    }

    @Override
    public void registerClientbound(State state, int n, int n2, PacketHandler packetHandler, boolean bl) {
        Preconditions.checkArgument(n != -1, "Unmapped packet id cannot be -1");
        PacketMapping packetMapping = PacketMapping.of(n2, packetHandler);
        if (!bl && this.clientboundMappings.hasMapping(state, n)) {
            Via.getPlatform().getLogger().log(Level.WARNING, n + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        this.clientboundMappings.addMapping(state, n, packetMapping);
    }

    @Override
    public void cancelClientbound(State state, int n) {
        this.registerClientbound(state, n, n, PacketWrapper::cancel);
    }

    @Override
    public void registerClientbound(CU CU, @Nullable PacketHandler packetHandler) {
        PacketTypeMap<CM> packetTypeMap = this.packetTypesProvider.mappedClientboundPacketTypes().get((Object)CU.state());
        ClientboundPacketType clientboundPacketType = (ClientboundPacketType)AbstractProtocol.mappedPacketType(CU, packetTypeMap, this.unmappedClientboundPacketType, this.mappedClientboundPacketType);
        this.registerClientbound(CU, clientboundPacketType, packetHandler);
    }

    @Override
    public void registerClientbound(CU CU, @Nullable CM CM, @Nullable PacketHandler packetHandler, boolean bl) {
        this.register(this.clientboundMappings, (PacketType)CU, (PacketType)CM, (Class<? extends PacketType>)this.unmappedClientboundPacketType, (Class<? extends PacketType>)this.mappedClientboundPacketType, packetHandler, bl);
    }

    @Override
    public void cancelClientbound(CU CU) {
        this.registerClientbound(CU, null, PacketWrapper::cancel);
    }

    @Override
    public void registerServerbound(SU SU, @Nullable PacketHandler packetHandler) {
        PacketTypeMap<SM> packetTypeMap = this.packetTypesProvider.mappedServerboundPacketTypes().get((Object)SU.state());
        ServerboundPacketType serverboundPacketType = (ServerboundPacketType)AbstractProtocol.mappedPacketType(SU, packetTypeMap, this.unmappedServerboundPacketType, this.mappedServerboundPacketType);
        this.registerServerbound(SU, serverboundPacketType, packetHandler);
    }

    @Override
    public void registerServerbound(SU SU, @Nullable SM SM2, @Nullable PacketHandler packetHandler, boolean bl) {
        this.register(this.serverboundMappings, (PacketType)SU, (PacketType)SM2, (Class<? extends PacketType>)this.unmappedServerboundPacketType, (Class<? extends PacketType>)this.mappedServerboundPacketType, packetHandler, bl);
    }

    @Override
    public void cancelServerbound(SU SU) {
        this.registerServerbound(SU, null, PacketWrapper::cancel);
    }

    private void register(PacketMappings packetMappings, PacketType packetType, @Nullable PacketType packetType2, Class<? extends PacketType> clazz, Class<? extends PacketType> clazz2, @Nullable PacketHandler packetHandler, boolean bl) {
        AbstractProtocol.checkPacketType(packetType, clazz == null || clazz.isInstance(packetType));
        if (packetType2 != null) {
            AbstractProtocol.checkPacketType(packetType2, clazz2 == null || clazz2.isInstance(packetType2));
            Preconditions.checkArgument(packetType.state() == packetType2.state(), "Packet type state does not match mapped packet type state");
            Preconditions.checkArgument(packetType.direction() == packetType2.direction(), "Packet type direction does not match mapped packet type state");
        }
        PacketMapping packetMapping = PacketMapping.of(packetType2, packetHandler);
        if (!bl && packetMappings.hasMapping(packetType)) {
            Via.getPlatform().getLogger().log(Level.WARNING, packetType + " already registered! If override is intentional, set override to true. Stacktrace: ", new Exception());
        }
        packetMappings.addMapping(packetType, packetMapping);
    }

    private static <U extends PacketType, M extends PacketType> M mappedPacketType(U u, PacketTypeMap<M> packetTypeMap, Class<U> clazz, Class<M> clazz2) {
        Preconditions.checkNotNull(u);
        AbstractProtocol.checkPacketType(u, clazz == null || clazz.isInstance(u));
        if (clazz == clazz2) {
            return (M)u;
        }
        Preconditions.checkNotNull(packetTypeMap, "Mapped packet types not provided for state %s of type class %s", new Object[]{u.state(), clazz2});
        PacketType packetType = (PacketType)packetTypeMap.typeByName(u.getName());
        if (packetType != null) {
            return (M)packetType;
        }
        throw new IllegalArgumentException("Packet type " + u + " in " + u.getClass().getSimpleName() + " could not be automatically mapped!");
    }

    @Override
    public boolean hasRegisteredClientbound(State state, int n) {
        return this.clientboundMappings.hasMapping(state, n);
    }

    @Override
    public boolean hasRegisteredServerbound(State state, int n) {
        return this.serverboundMappings.hasMapping(state, n);
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        int n;
        PacketMappings packetMappings = direction == Direction.CLIENTBOUND ? this.clientboundMappings : this.serverboundMappings;
        PacketMapping packetMapping = packetMappings.mappedPacket(state, n = packetWrapper.getId());
        if (packetMapping == null) {
            return;
        }
        packetMapping.applyType(packetWrapper);
        PacketHandler packetHandler = packetMapping.handler();
        if (packetHandler != null) {
            try {
                packetHandler.handle(packetWrapper);
            } catch (CancelException cancelException) {
                throw cancelException;
            } catch (InformativeException informativeException) {
                informativeException.addSource(packetHandler.getClass());
                this.throwRemapError(direction, state, n, packetWrapper.getId(), informativeException);
                return;
            } catch (Exception exception) {
                InformativeException informativeException = new InformativeException(exception);
                informativeException.addSource(packetHandler.getClass());
                this.throwRemapError(direction, state, n, packetWrapper.getId(), informativeException);
                return;
            }
            if (packetWrapper.isCancelled()) {
                throw CancelException.generate();
            }
        }
    }

    private void throwRemapError(Direction direction, State state, int n, int n2, InformativeException informativeException) throws InformativeException {
        Object object;
        if (state != State.PLAY && direction == Direction.SERVERBOUND && !Via.getManager().debugHandler().enabled()) {
            informativeException.setShouldBePrinted(true);
            throw informativeException;
        }
        Object object2 = object = direction == Direction.CLIENTBOUND ? this.unmappedClientboundPacketType(state, n) : this.unmappedServerboundPacketType(state, n);
        if (object != null) {
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + object + " (" + AbstractProtocol.toNiceHex(n) + ")");
        } else {
            Via.getPlatform().getLogger().warning("ERROR IN " + this.getClass().getSimpleName() + " IN REMAP OF " + AbstractProtocol.toNiceHex(n) + "->" + AbstractProtocol.toNiceHex(n2));
        }
        throw informativeException;
    }

    private @Nullable CU unmappedClientboundPacketType(State state, int n) {
        PacketTypeMap<CU> packetTypeMap = this.packetTypesProvider.unmappedClientboundPacketTypes().get((Object)state);
        return (CU)(packetTypeMap != null ? (ClientboundPacketType)packetTypeMap.typeById(n) : null);
    }

    private @Nullable SU unmappedServerboundPacketType(State state, int n) {
        PacketTypeMap<SU> packetTypeMap = this.packetTypesProvider.unmappedServerboundPacketTypes().get((Object)state);
        return (SU)(packetTypeMap != null ? (ServerboundPacketType)packetTypeMap.typeById(n) : null);
    }

    public static String toNiceHex(int n) {
        String string = Integer.toHexString(n).toUpperCase();
        return (string.length() == 1 ? "0x0" : "0x") + string;
    }

    private static void checkPacketType(PacketType packetType, boolean bl) {
        if (!bl) {
            throw new IllegalArgumentException("Packet type " + packetType + " in " + packetType.getClass().getSimpleName() + " is taken from the wrong packet types class");
        }
    }

    @Override
    public PacketTypesProvider<CU, CM, SM, SU> getPacketTypesProvider() {
        return this.packetTypesProvider;
    }

    @Override
    public <T> @Nullable T get(Class<T> clazz) {
        return (T)this.storedObjects.get(clazz);
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

