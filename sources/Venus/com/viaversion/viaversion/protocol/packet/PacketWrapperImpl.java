/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocol.packet;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PacketWrapperImpl
implements PacketWrapper {
    private static final Protocol[] PROTOCOL_ARRAY = new Protocol[0];
    private final Deque<PacketValue> readableObjects = new ArrayDeque<PacketValue>();
    private final List<PacketValue> packetValues = new ArrayList<PacketValue>();
    private final ByteBuf inputBuffer;
    private final UserConnection userConnection;
    private boolean send = true;
    private PacketType packetType;
    private int id;

    public PacketWrapperImpl(int n, @Nullable ByteBuf byteBuf, UserConnection userConnection) {
        this.id = n;
        this.inputBuffer = byteBuf;
        this.userConnection = userConnection;
    }

    public PacketWrapperImpl(@Nullable PacketType packetType, @Nullable ByteBuf byteBuf, UserConnection userConnection) {
        this.packetType = packetType;
        this.id = packetType != null ? packetType.getId() : -1;
        this.inputBuffer = byteBuf;
        this.userConnection = userConnection;
    }

    @Override
    public <T> T get(Type<T> type, int n) throws Exception {
        int n2 = 0;
        for (PacketValue packetValue : this.packetValues) {
            if (packetValue.type() != type) continue;
            if (n2 == n) {
                return (T)packetValue.value();
            }
            ++n2;
        }
        throw this.createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + n), type, n);
    }

    @Override
    public boolean is(Type type, int n) {
        int n2 = 0;
        for (PacketValue packetValue : this.packetValues) {
            if (packetValue.type() != type) continue;
            if (n2 == n) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    @Override
    public boolean isReadable(Type type, int n) {
        int n2 = 0;
        for (PacketValue packetValue : this.readableObjects) {
            if (packetValue.type().getBaseClass() != type.getBaseClass()) continue;
            if (n2 == n) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    @Override
    public <T> void set(Type<T> type, int n, T t) throws Exception {
        int n2 = 0;
        for (PacketValue packetValue : this.packetValues) {
            if (packetValue.type() != type) continue;
            if (n2 == n) {
                packetValue.setValue(this.attemptTransform(type, t));
                return;
            }
            ++n2;
        }
        throw this.createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + n), type, n);
    }

    @Override
    public <T> T read(Type<T> type) throws Exception {
        if (type == Type.NOTHING) {
            return null;
        }
        if (this.readableObjects.isEmpty()) {
            Preconditions.checkNotNull(this.inputBuffer, "This packet does not have an input buffer.");
            try {
                return type.read(this.inputBuffer);
            } catch (Exception exception) {
                throw this.createInformativeException(exception, type, this.packetValues.size() + 1);
            }
        }
        PacketValue packetValue = this.readableObjects.poll();
        Type type2 = packetValue.type();
        if (type2 == type || type.getBaseClass() == type2.getBaseClass() && type.getOutputClass() == type2.getOutputClass()) {
            return (T)packetValue.value();
        }
        if (type2 == Type.NOTHING) {
            return this.read(type);
        }
        throw this.createInformativeException(new IOException("Unable to read type " + type.getTypeName() + ", found " + packetValue.type().getTypeName()), type, this.readableObjects.size());
    }

    @Override
    public <T> void write(Type<T> type, T t) {
        this.packetValues.add(new PacketValue(type, this.attemptTransform(type, t), null));
    }

    private @Nullable Object attemptTransform(Type<?> type, @Nullable Object object) {
        if (object != null && !type.getOutputClass().isAssignableFrom(object.getClass())) {
            if (type instanceof TypeConverter) {
                return ((TypeConverter)((Object)type)).from(object);
            }
            Via.getPlatform().getLogger().warning("Possible type mismatch: " + object.getClass().getName() + " -> " + type.getOutputClass());
        }
        return object;
    }

    @Override
    public <T> T passthrough(Type<T> type) throws Exception {
        T t = this.read(type);
        this.write(type, t);
        return t;
    }

    @Override
    public void passthroughAll() throws Exception {
        this.packetValues.addAll(this.readableObjects);
        this.readableObjects.clear();
        if (this.inputBuffer.isReadable()) {
            this.passthrough(Type.REMAINING_BYTES);
        }
    }

    @Override
    public void writeToBuffer(ByteBuf byteBuf) throws Exception {
        if (this.id != -1) {
            Type.VAR_INT.writePrimitive(byteBuf, this.id);
        }
        if (!this.readableObjects.isEmpty()) {
            this.packetValues.addAll(this.readableObjects);
            this.readableObjects.clear();
        }
        int n = 0;
        for (PacketValue packetValue : this.packetValues) {
            try {
                packetValue.type().write(byteBuf, packetValue.value());
            } catch (Exception exception) {
                throw this.createInformativeException(exception, packetValue.type(), n);
            }
            ++n;
        }
        this.writeRemaining(byteBuf);
    }

    private InformativeException createInformativeException(Exception exception, Type<?> type, int n) {
        return new InformativeException(exception).set("Index", n).set("Type", type.getTypeName()).set("Packet ID", this.id).set("Packet Type", this.packetType).set("Data", this.packetValues);
    }

    @Override
    public void clearInputBuffer() {
        if (this.inputBuffer != null) {
            this.inputBuffer.clear();
        }
        this.readableObjects.clear();
    }

    @Override
    public void clearPacket() {
        this.clearInputBuffer();
        this.packetValues.clear();
    }

    private void writeRemaining(ByteBuf byteBuf) {
        if (this.inputBuffer != null) {
            byteBuf.writeBytes(this.inputBuffer);
        }
    }

    @Override
    public void send(Class<? extends Protocol> clazz, boolean bl) throws Exception {
        this.send0(clazz, bl, true);
    }

    @Override
    public void scheduleSend(Class<? extends Protocol> clazz, boolean bl) throws Exception {
        this.send0(clazz, bl, false);
    }

    private void send0(Class<? extends Protocol> clazz, boolean bl, boolean bl2) throws Exception {
        if (this.isCancelled()) {
            return;
        }
        UserConnection userConnection = this.user();
        if (bl2) {
            block4: {
                try {
                    ByteBuf byteBuf = this.constructPacket(clazz, bl, Direction.CLIENTBOUND);
                    userConnection.sendRawPacket(byteBuf);
                } catch (Exception exception) {
                    if (PipelineUtil.containsCause(exception, CancelException.class)) break block4;
                    throw exception;
                }
            }
            return;
        }
        userConnection.getChannel().eventLoop().submit(() -> this.lambda$send0$0(clazz, bl, userConnection));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ByteBuf constructPacket(Class<? extends Protocol> clazz, boolean bl, Direction direction) throws Exception {
        Protocol[] protocolArray = this.user().getProtocolInfo().getPipeline().pipes().toArray(PROTOCOL_ARRAY);
        boolean bl2 = direction == Direction.CLIENTBOUND;
        int n = -1;
        for (int i = 0; i < protocolArray.length; ++i) {
            if (protocolArray[i].getClass() != clazz) continue;
            n = i;
            break;
        }
        if (n == -1) {
            throw new NoSuchElementException(clazz.getCanonicalName());
        }
        if (bl) {
            n = bl2 ? n - 1 : n + 1;
        }
        this.resetReader();
        this.apply(direction, this.user().getProtocolInfo().getState(), n, protocolArray, bl2);
        ByteBuf byteBuf = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(byteBuf);
            ByteBuf byteBuf2 = byteBuf.retain();
            return byteBuf2;
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public ChannelFuture sendFuture(Class<? extends Protocol> clazz) throws Exception {
        if (!this.isCancelled()) {
            ByteBuf byteBuf = this.constructPacket(clazz, true, Direction.CLIENTBOUND);
            return this.user().sendRawPacketFuture(byteBuf);
        }
        return this.user().getChannel().newFailedFuture(new Exception("Cancelled packet"));
    }

    @Override
    public void sendRaw() throws Exception {
        this.sendRaw(true);
    }

    @Override
    public void scheduleSendRaw() throws Exception {
        this.sendRaw(false);
    }

    private void sendRaw(boolean bl) throws Exception {
        if (this.isCancelled()) {
            return;
        }
        ByteBuf byteBuf = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(byteBuf);
            if (bl) {
                this.user().sendRawPacket(byteBuf.retain());
            } else {
                this.user().scheduleSendRawPacket(byteBuf.retain());
            }
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public PacketWrapperImpl create(int n) {
        return new PacketWrapperImpl(n, null, this.user());
    }

    @Override
    public PacketWrapperImpl create(int n, PacketHandler packetHandler) throws Exception {
        PacketWrapperImpl packetWrapperImpl = this.create(n);
        packetHandler.handle(packetWrapperImpl);
        return packetWrapperImpl;
    }

    @Override
    public PacketWrapperImpl apply(Direction direction, State state, int n, List<Protocol> list, boolean bl) throws Exception {
        Protocol[] protocolArray = list.toArray(PROTOCOL_ARRAY);
        return this.apply(direction, state, bl ? protocolArray.length - 1 : n, protocolArray, bl);
    }

    @Override
    public PacketWrapperImpl apply(Direction direction, State state, int n, List<Protocol> list) throws Exception {
        return this.apply(direction, state, n, list.toArray(PROTOCOL_ARRAY), false);
    }

    private PacketWrapperImpl apply(Direction direction, State state, int n, Protocol[] protocolArray, boolean bl) throws Exception {
        if (bl) {
            for (int i = n; i >= 0; --i) {
                protocolArray[i].transform(direction, state, this);
                this.resetReader();
            }
        } else {
            for (int i = n; i < protocolArray.length; ++i) {
                protocolArray[i].transform(direction, state, this);
                this.resetReader();
            }
        }
        return this;
    }

    @Override
    public boolean isCancelled() {
        return !this.send;
    }

    @Override
    public void setCancelled(boolean bl) {
        this.send = !bl;
    }

    @Override
    public UserConnection user() {
        return this.userConnection;
    }

    @Override
    public void resetReader() {
        for (int i = this.packetValues.size() - 1; i >= 0; --i) {
            this.readableObjects.addFirst(this.packetValues.get(i));
        }
        this.packetValues.clear();
    }

    @Override
    public void sendToServerRaw() throws Exception {
        this.sendToServerRaw(true);
    }

    @Override
    public void scheduleSendToServerRaw() throws Exception {
        this.sendToServerRaw(false);
    }

    private void sendToServerRaw(boolean bl) throws Exception {
        if (this.isCancelled()) {
            return;
        }
        ByteBuf byteBuf = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(byteBuf);
            if (bl) {
                this.user().sendRawPacketToServer(byteBuf.retain());
            } else {
                this.user().scheduleSendRawPacketToServer(byteBuf.retain());
            }
        } finally {
            byteBuf.release();
        }
    }

    @Override
    public void sendToServer(Class<? extends Protocol> clazz, boolean bl) throws Exception {
        this.sendToServer0(clazz, bl, true);
    }

    @Override
    public void scheduleSendToServer(Class<? extends Protocol> clazz, boolean bl) throws Exception {
        this.sendToServer0(clazz, bl, false);
    }

    private void sendToServer0(Class<? extends Protocol> clazz, boolean bl, boolean bl2) throws Exception {
        if (this.isCancelled()) {
            return;
        }
        UserConnection userConnection = this.user();
        if (bl2) {
            block4: {
                try {
                    ByteBuf byteBuf = this.constructPacket(clazz, bl, Direction.SERVERBOUND);
                    userConnection.sendRawPacketToServer(byteBuf);
                } catch (Exception exception) {
                    if (PipelineUtil.containsCause(exception, CancelException.class)) break block4;
                    throw exception;
                }
            }
            return;
        }
        userConnection.getChannel().eventLoop().submit(() -> this.lambda$sendToServer0$1(clazz, bl, userConnection));
    }

    @Override
    public @Nullable PacketType getPacketType() {
        return this.packetType;
    }

    @Override
    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
        this.id = packetType != null ? packetType.getId() : -1;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    @Deprecated
    public void setId(int n) {
        this.packetType = null;
        this.id = n;
    }

    public @Nullable ByteBuf getInputBuffer() {
        return this.inputBuffer;
    }

    public String toString() {
        return "PacketWrapper{type=" + this.packetType + ", id=" + this.id + ", values=" + this.packetValues + ", readable=" + this.readableObjects + '}';
    }

    public PacketWrapper apply(Direction direction, State state, int n, List list) throws Exception {
        return this.apply(direction, state, n, list);
    }

    public PacketWrapper apply(Direction direction, State state, int n, List list, boolean bl) throws Exception {
        return this.apply(direction, state, n, list, bl);
    }

    @Override
    public PacketWrapper create(int n, PacketHandler packetHandler) throws Exception {
        return this.create(n, packetHandler);
    }

    @Override
    public PacketWrapper create(int n) {
        return this.create(n);
    }

    private void lambda$sendToServer0$1(Class clazz, boolean bl, UserConnection userConnection) {
        block4: {
            try {
                ByteBuf byteBuf = this.constructPacket(clazz, bl, Direction.SERVERBOUND);
                userConnection.sendRawPacketToServer(byteBuf);
            } catch (RuntimeException runtimeException) {
                if (!PipelineUtil.containsCause(runtimeException, CancelException.class)) {
                    throw runtimeException;
                }
            } catch (Exception exception) {
                if (PipelineUtil.containsCause(exception, CancelException.class)) break block4;
                throw new RuntimeException(exception);
            }
        }
    }

    private void lambda$send0$0(Class clazz, boolean bl, UserConnection userConnection) {
        block4: {
            try {
                ByteBuf byteBuf = this.constructPacket(clazz, bl, Direction.CLIENTBOUND);
                userConnection.sendRawPacket(byteBuf);
            } catch (RuntimeException runtimeException) {
                if (!PipelineUtil.containsCause(runtimeException, CancelException.class)) {
                    throw runtimeException;
                }
            } catch (Exception exception) {
                if (PipelineUtil.containsCause(exception, CancelException.class)) break block4;
                throw new RuntimeException(exception);
            }
        }
    }

    public static final class PacketValue {
        private final Type type;
        private Object value;

        private PacketValue(Type type, @Nullable Object object) {
            this.type = type;
            this.value = object;
        }

        public Type type() {
            return this.type;
        }

        public @Nullable Object value() {
            return this.value;
        }

        public void setValue(@Nullable Object object) {
            this.value = object;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            PacketValue packetValue = (PacketValue)object;
            if (!this.type.equals(packetValue.type)) {
                return true;
            }
            return Objects.equals(this.value, packetValue.value);
        }

        public int hashCode() {
            int n = this.type.hashCode();
            n = 31 * n + (this.value != null ? this.value.hashCode() : 0);
            return n;
        }

        public String toString() {
            return "{" + this.type + ": " + this.value + "}";
        }

        PacketValue(Type type, Object object, 1 var3_3) {
            this(type, object);
        }
    }
}

