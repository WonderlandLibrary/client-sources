/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelFuture
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
import com.viaversion.viaversion.util.Pair;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PacketWrapperImpl
implements PacketWrapper {
    private static final Protocol[] PROTOCOL_ARRAY = new Protocol[0];
    private final ByteBuf inputBuffer;
    private final UserConnection userConnection;
    private boolean send = true;
    private PacketType packetType;
    private int id;
    private final Deque<Pair<Type, Object>> readableObjects = new ArrayDeque<Pair<Type, Object>>();
    private final List<Pair<Type, Object>> packetValues = new ArrayList<Pair<Type, Object>>();

    public PacketWrapperImpl(int packetId, @Nullable ByteBuf inputBuffer, UserConnection userConnection) {
        this.id = packetId;
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }

    public PacketWrapperImpl(@Nullable PacketType packetType, @Nullable ByteBuf inputBuffer, UserConnection userConnection) {
        this.packetType = packetType;
        this.id = packetType != null ? packetType.getId() : -1;
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }

    @Override
    public <T> T get(Type<T> type, int index) throws Exception {
        int currentIndex = 0;
        Iterator<Pair<Type, Object>> iterator = this.packetValues.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index);
                throw new InformativeException(e).set("Type", type.getTypeName()).set("Index", index).set("Packet ID", this.getId()).set("Packet Type", this.packetType).set("Data", this.packetValues);
            }
            Pair<Type, Object> packetValue = iterator.next();
            if (packetValue.key() != type) continue;
            if (currentIndex == index) {
                return (T)packetValue.value();
            }
            ++currentIndex;
        }
    }

    @Override
    public boolean is(Type type, int index) {
        int currentIndex = 0;
        Iterator<Pair<Type, Object>> iterator = this.packetValues.iterator();
        while (iterator.hasNext()) {
            Pair<Type, Object> packetValue = iterator.next();
            if (packetValue.key() != type) continue;
            if (currentIndex == index) {
                return true;
            }
            ++currentIndex;
        }
        return false;
    }

    @Override
    public boolean isReadable(Type type, int index) {
        int currentIndex = 0;
        Iterator<Pair<Type, Object>> iterator = this.readableObjects.iterator();
        while (iterator.hasNext()) {
            Pair<Type, Object> packetValue = iterator.next();
            if (packetValue.key().getBaseClass() != type.getBaseClass()) continue;
            if (currentIndex == index) {
                return true;
            }
            ++currentIndex;
        }
        return false;
    }

    @Override
    public <T> void set(Type<T> type, int index, T value) throws Exception {
        int currentIndex = 0;
        Iterator<Pair<Type, Object>> iterator = this.packetValues.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index);
                throw new InformativeException(e).set("Type", type.getTypeName()).set("Index", index).set("Packet ID", this.getId()).set("Packet Type", this.packetType);
            }
            Pair<Type, Object> packetValue = iterator.next();
            if (packetValue.key() != type) continue;
            if (currentIndex == index) {
                packetValue.setValue(this.attemptTransform(type, value));
                return;
            }
            ++currentIndex;
        }
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
            }
            catch (Exception e) {
                throw new InformativeException(e).set("Type", type.getTypeName()).set("Packet ID", this.getId()).set("Packet Type", this.packetType).set("Data", this.packetValues);
            }
        }
        Pair<Type, Object> read = this.readableObjects.poll();
        Type rtype = read.key();
        if (rtype == type) return (T)read.value();
        if (type.getBaseClass() == rtype.getBaseClass() && type.getOutputClass() == rtype.getOutputClass()) {
            return (T)read.value();
        }
        if (rtype == Type.NOTHING) {
            return this.read(type);
        }
        IOException e = new IOException("Unable to read type " + type.getTypeName() + ", found " + read.key().getTypeName());
        throw new InformativeException(e).set("Type", type.getTypeName()).set("Packet ID", this.getId()).set("Packet Type", this.packetType).set("Data", this.packetValues);
    }

    @Override
    public <T> void write(Type<T> type, T value) {
        this.packetValues.add(new Pair<Type<T>, Object>(type, this.attemptTransform(type, value)));
    }

    private @Nullable Object attemptTransform(Type<?> expectedType, @Nullable Object value) {
        if (value == null) return value;
        if (expectedType.getOutputClass().isAssignableFrom(value.getClass())) return value;
        if (expectedType instanceof TypeConverter) {
            return ((TypeConverter)((Object)expectedType)).from(value);
        }
        Via.getPlatform().getLogger().warning("Possible type mismatch: " + value.getClass().getName() + " -> " + expectedType.getOutputClass());
        return value;
    }

    @Override
    public <T> T passthrough(Type<T> type) throws Exception {
        T value = this.read(type);
        this.write(type, value);
        return value;
    }

    @Override
    public void passthroughAll() throws Exception {
        this.packetValues.addAll(this.readableObjects);
        this.readableObjects.clear();
        if (!this.inputBuffer.isReadable()) return;
        this.passthrough(Type.REMAINING_BYTES);
    }

    @Override
    public void writeToBuffer(ByteBuf buffer) throws Exception {
        if (this.id != -1) {
            Type.VAR_INT.writePrimitive(buffer, this.id);
        }
        if (!this.readableObjects.isEmpty()) {
            this.packetValues.addAll(this.readableObjects);
            this.readableObjects.clear();
        }
        int index = 0;
        Iterator<Pair<Type, Object>> iterator = this.packetValues.iterator();
        while (true) {
            if (!iterator.hasNext()) {
                this.writeRemaining(buffer);
                return;
            }
            Pair<Type, Object> packetValue = iterator.next();
            try {
                packetValue.key().write(buffer, packetValue.value());
            }
            catch (Exception e) {
                throw new InformativeException(e).set("Index", index).set("Type", packetValue.key().getTypeName()).set("Packet ID", this.getId()).set("Packet Type", this.packetType).set("Data", this.packetValues);
            }
            ++index;
        }
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

    private void writeRemaining(ByteBuf output) {
        if (this.inputBuffer == null) return;
        output.writeBytes(this.inputBuffer);
    }

    @Override
    public void send(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
        this.send0(protocol, skipCurrentPipeline, true);
    }

    @Override
    public void scheduleSend(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
        this.send0(protocol, skipCurrentPipeline, false);
    }

    private void send0(Class<? extends Protocol> protocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
        if (this.isCancelled()) {
            return;
        }
        try {
            ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
            if (currentThread) {
                this.user().sendRawPacket(output);
                return;
            }
            this.user().scheduleSendRawPacket(output);
            return;
        }
        catch (Exception e) {
            if (PipelineUtil.containsCause(e, CancelException.class)) return;
            throw e;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ByteBuf constructPacket(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, Direction direction) throws Exception {
        Protocol[] protocols = this.user().getProtocolInfo().getPipeline().pipes().toArray(PROTOCOL_ARRAY);
        boolean reverse = direction == Direction.CLIENTBOUND;
        int index = -1;
        for (int i = 0; i < protocols.length; ++i) {
            if (protocols[i].getClass() != packetProtocol) continue;
            index = i;
            break;
        }
        if (index == -1) {
            throw new NoSuchElementException(packetProtocol.getCanonicalName());
        }
        if (skipCurrentPipeline) {
            index = reverse ? index - 1 : index + 1;
        }
        this.resetReader();
        this.apply(direction, this.user().getProtocolInfo().getState(), index, protocols, reverse);
        ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(output);
            ByteBuf byteBuf = output.retain();
            return byteBuf;
        }
        finally {
            output.release();
        }
    }

    @Override
    public ChannelFuture sendFuture(Class<? extends Protocol> packetProtocol) throws Exception {
        if (this.isCancelled()) return this.user().getChannel().newFailedFuture((Throwable)new Exception("Cancelled packet"));
        ByteBuf output = this.constructPacket(packetProtocol, true, Direction.CLIENTBOUND);
        return this.user().sendRawPacketFuture(output);
    }

    @Override
    public void sendRaw() throws Exception {
        this.sendRaw(true);
    }

    @Override
    public void scheduleSendRaw() throws Exception {
        this.sendRaw(false);
    }

    private void sendRaw(boolean currentThread) throws Exception {
        if (this.isCancelled()) {
            return;
        }
        ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(output);
            if (currentThread) {
                this.user().sendRawPacket(output.retain());
                return;
            }
            this.user().scheduleSendRawPacket(output.retain());
            return;
        }
        finally {
            output.release();
        }
    }

    @Override
    public PacketWrapperImpl create(int packetId) {
        return new PacketWrapperImpl(packetId, null, this.user());
    }

    @Override
    public PacketWrapperImpl create(int packetId, PacketHandler handler) throws Exception {
        PacketWrapperImpl wrapper = this.create(packetId);
        handler.handle(wrapper);
        return wrapper;
    }

    @Override
    public PacketWrapperImpl apply(Direction direction, State state, int index, List<Protocol> pipeline, boolean reverse) throws Exception {
        int n;
        Protocol[] array = pipeline.toArray(PROTOCOL_ARRAY);
        if (reverse) {
            n = array.length - 1;
            return this.apply(direction, state, n, array, reverse);
        }
        n = index;
        return this.apply(direction, state, n, array, reverse);
    }

    @Override
    public PacketWrapperImpl apply(Direction direction, State state, int index, List<Protocol> pipeline) throws Exception {
        return this.apply(direction, state, index, pipeline.toArray(PROTOCOL_ARRAY), false);
    }

    private PacketWrapperImpl apply(Direction direction, State state, int index, Protocol[] pipeline, boolean reverse) throws Exception {
        if (reverse) {
            int i = index;
            while (i >= 0) {
                pipeline[i].transform(direction, state, this);
                this.resetReader();
                --i;
            }
            return this;
        }
        int i = index;
        while (i < pipeline.length) {
            pipeline[i].transform(direction, state, this);
            this.resetReader();
            ++i;
        }
        return this;
    }

    @Override
    public void cancel() {
        this.send = false;
    }

    @Override
    public boolean isCancelled() {
        if (this.send) return false;
        return true;
    }

    @Override
    public UserConnection user() {
        return this.userConnection;
    }

    @Override
    public void resetReader() {
        int i = this.packetValues.size() - 1;
        while (true) {
            if (i < 0) {
                this.packetValues.clear();
                return;
            }
            this.readableObjects.addFirst(this.packetValues.get(i));
            --i;
        }
    }

    @Override
    public void sendToServerRaw() throws Exception {
        this.sendToServerRaw(true);
    }

    @Override
    public void scheduleSendToServerRaw() throws Exception {
        this.sendToServerRaw(false);
    }

    private void sendToServerRaw(boolean currentThread) throws Exception {
        if (this.isCancelled()) {
            return;
        }
        ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(output);
            if (currentThread) {
                this.user().sendRawPacketToServer(output.retain());
                return;
            }
            this.user().scheduleSendRawPacketToServer(output.retain());
            return;
        }
        finally {
            output.release();
        }
    }

    @Override
    public void sendToServer(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
        this.sendToServer0(protocol, skipCurrentPipeline, true);
    }

    @Override
    public void scheduleSendToServer(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws Exception {
        this.sendToServer0(protocol, skipCurrentPipeline, false);
    }

    private void sendToServer0(Class<? extends Protocol> protocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
        if (this.isCancelled()) {
            return;
        }
        try {
            ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
            if (currentThread) {
                this.user().sendRawPacketToServer(output);
                return;
            }
            this.user().scheduleSendRawPacketToServer(output);
            return;
        }
        catch (Exception e) {
            if (PipelineUtil.containsCause(e, CancelException.class)) return;
            throw e;
        }
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
    public void setId(int id) {
        this.packetType = null;
        this.id = id;
    }

    public @Nullable ByteBuf getInputBuffer() {
        return this.inputBuffer;
    }

    public String toString() {
        return "PacketWrapper{packetValues=" + this.packetValues + ", readableObjects=" + this.readableObjects + ", id=" + this.id + ", packetType=" + this.packetType + '}';
    }
}

