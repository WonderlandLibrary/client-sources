/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  io.netty.buffer.ByteBuf
 *  io.netty.channel.ChannelFuture
 */
package us.myles.ViaVersion.api;

import com.google.common.base.Preconditions;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.TypeConverter;
import us.myles.ViaVersion.exception.CancelException;
import us.myles.ViaVersion.exception.InformativeException;
import us.myles.ViaVersion.packets.Direction;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.util.PipelineUtil;

public class PacketWrapper {
    public static final int PASSTHROUGH_ID = 1000;
    private static final Protocol[] PROTOCOL_ARRAY = new Protocol[0];
    private final ByteBuf inputBuffer;
    private final UserConnection userConnection;
    private boolean send = true;
    private int id = -1;
    private final LinkedList<Pair<Type, Object>> readableObjects = new LinkedList();
    private final List<Pair<Type, Object>> packetValues = new ArrayList<Pair<Type, Object>>();

    public PacketWrapper(int packetID, ByteBuf inputBuffer, UserConnection userConnection) {
        this.id = packetID;
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }

    public <T> T get(Type<T> type, int index) throws Exception {
        int currentIndex = 0;
        for (Pair<Type, Object> packetValue : this.packetValues) {
            if (packetValue.getKey() != type) continue;
            if (currentIndex == index) {
                return (T)packetValue.getValue();
            }
            ++currentIndex;
        }
        ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index);
        throw new InformativeException(e).set("Type", type.getTypeName()).set("Index", index).set("Packet ID", this.getId()).set("Data", this.packetValues);
    }

    public boolean is(Type type, int index) {
        int currentIndex = 0;
        for (Pair<Type, Object> packetValue : this.packetValues) {
            if (packetValue.getKey() != type) continue;
            if (currentIndex == index) {
                return true;
            }
            ++currentIndex;
        }
        return false;
    }

    public boolean isReadable(Type type, int index) {
        int currentIndex = 0;
        for (Pair pair : this.readableObjects) {
            if (((Type)pair.getKey()).getBaseClass() != type.getBaseClass()) continue;
            if (currentIndex == index) {
                return true;
            }
            ++currentIndex;
        }
        return false;
    }

    public <T> void set(Type<T> type, int index, T value) throws Exception {
        int currentIndex = 0;
        for (Pair<Type, Object> packetValue : this.packetValues) {
            if (packetValue.getKey() != type) continue;
            if (currentIndex == index) {
                packetValue.setValue(value);
                return;
            }
            ++currentIndex;
        }
        ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index);
        throw new InformativeException(e).set("Type", type.getTypeName()).set("Index", index).set("Packet ID", this.getId());
    }

    public <T> T read(Type<T> type) throws Exception {
        if (type == Type.NOTHING) {
            return null;
        }
        if (this.readableObjects.isEmpty()) {
            Preconditions.checkNotNull((Object)this.inputBuffer, (Object)"This packet does not have an input buffer.");
            try {
                return type.read(this.inputBuffer);
            }
            catch (Exception e) {
                throw new InformativeException(e).set("Type", type.getTypeName()).set("Packet ID", this.getId()).set("Data", this.packetValues);
            }
        }
        Pair<Type, Object> read = this.readableObjects.poll();
        Type rtype = read.getKey();
        if (rtype.equals(type) || type.getBaseClass().equals(rtype.getBaseClass()) && type.getOutputClass().equals(rtype.getOutputClass())) {
            return (T)read.getValue();
        }
        if (rtype == Type.NOTHING) {
            return this.read(type);
        }
        IOException e = new IOException("Unable to read type " + type.getTypeName() + ", found " + read.getKey().getTypeName());
        throw new InformativeException(e).set("Type", type.getTypeName()).set("Packet ID", this.getId()).set("Data", this.packetValues);
    }

    public <T> void write(Type<T> type, T value) {
        if (value != null && !type.getOutputClass().isAssignableFrom(value.getClass())) {
            if (type instanceof TypeConverter) {
                value = ((TypeConverter)((Object)type)).from(value);
            } else {
                Via.getPlatform().getLogger().warning("Possible type mismatch: " + value.getClass().getName() + " -> " + type.getOutputClass());
            }
        }
        this.packetValues.add(new Pair<Type<T>, T>(type, value));
    }

    public <T> T passthrough(Type<T> type) throws Exception {
        T value = this.read(type);
        this.write(type, value);
        return value;
    }

    public void passthroughAll() throws Exception {
        this.packetValues.addAll(this.readableObjects);
        this.readableObjects.clear();
        if (this.inputBuffer.readableBytes() > 0) {
            this.passthrough(Type.REMAINING_BYTES);
        }
    }

    public void writeToBuffer(ByteBuf buffer) throws Exception {
        if (this.id != -1) {
            Type.VAR_INT.writePrimitive(buffer, this.id);
        }
        if (!this.readableObjects.isEmpty()) {
            this.packetValues.addAll(this.readableObjects);
            this.readableObjects.clear();
        }
        int index = 0;
        for (Pair<Type, Object> packetValue : this.packetValues) {
            try {
                Object value = packetValue.getValue();
                if (value != null && !packetValue.getKey().getOutputClass().isAssignableFrom(value.getClass())) {
                    if (packetValue.getKey() instanceof TypeConverter) {
                        value = ((TypeConverter)((Object)packetValue.getKey())).from(value);
                    } else {
                        Via.getPlatform().getLogger().warning("Possible type mismatch: " + value.getClass().getName() + " -> " + packetValue.getKey().getOutputClass());
                    }
                }
                packetValue.getKey().write(buffer, value);
            }
            catch (Exception e) {
                throw new InformativeException(e).set("Index", index).set("Type", packetValue.getKey().getTypeName()).set("Packet ID", this.getId()).set("Data", this.packetValues);
            }
            ++index;
        }
        this.writeRemaining(buffer);
    }

    public void clearInputBuffer() {
        if (this.inputBuffer != null) {
            this.inputBuffer.clear();
        }
        this.readableObjects.clear();
    }

    public void clearPacket() {
        this.clearInputBuffer();
        this.packetValues.clear();
    }

    private void writeRemaining(ByteBuf output) {
        if (this.inputBuffer != null) {
            output.writeBytes(this.inputBuffer, this.inputBuffer.readableBytes());
        }
    }

    public void send(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) throws Exception {
        this.send(packetProtocol, skipCurrentPipeline, false);
    }

    public void send(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
        block3: {
            if (!this.isCancelled()) {
                try {
                    ByteBuf output = this.constructPacket(packetProtocol, skipCurrentPipeline, Direction.OUTGOING);
                    this.user().sendRawPacket(output, currentThread);
                }
                catch (Exception e) {
                    if (PipelineUtil.containsCause(e, CancelException.class)) break block3;
                    throw e;
                }
            }
        }
    }

    private ByteBuf constructPacket(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, Direction direction) throws Exception {
        Protocol[] protocols = this.user().getProtocolInfo().getPipeline().pipes().toArray(PROTOCOL_ARRAY);
        boolean reverse = direction == Direction.OUTGOING;
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
        this.writeToBuffer(output);
        return output;
    }

    public void send(Class<? extends Protocol> packetProtocol) throws Exception {
        this.send(packetProtocol, true);
    }

    public ChannelFuture sendFuture(Class<? extends Protocol> packetProtocol) throws Exception {
        if (!this.isCancelled()) {
            ByteBuf output = this.constructPacket(packetProtocol, true, Direction.OUTGOING);
            return this.user().sendRawPacketFuture(output);
        }
        return this.user().getChannel().newFailedFuture((Throwable)new Exception("Cancelled packet"));
    }

    @Deprecated
    public void send() throws Exception {
        if (!this.isCancelled()) {
            ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
            this.writeToBuffer(output);
            this.user().sendRawPacket(output);
        }
    }

    public PacketWrapper create(int packetID) {
        return new PacketWrapper(packetID, null, this.user());
    }

    public PacketWrapper create(int packetID, ValueCreator init) throws Exception {
        PacketWrapper wrapper = this.create(packetID);
        init.write(wrapper);
        return wrapper;
    }

    public PacketWrapper apply(Direction direction, State state, int index, List<Protocol> pipeline, boolean reverse) throws Exception {
        Protocol[] array = pipeline.toArray(PROTOCOL_ARRAY);
        return this.apply(direction, state, reverse ? array.length - 1 : index, array, reverse);
    }

    public PacketWrapper apply(Direction direction, State state, int index, List<Protocol> pipeline) throws Exception {
        return this.apply(direction, state, index, pipeline.toArray(PROTOCOL_ARRAY), false);
    }

    private PacketWrapper apply(Direction direction, State state, int index, Protocol[] pipeline, boolean reverse) throws Exception {
        if (reverse) {
            for (int i = index; i >= 0; --i) {
                pipeline[i].transform(direction, state, this);
                this.resetReader();
            }
        } else {
            for (int i = index; i < pipeline.length; ++i) {
                pipeline[i].transform(direction, state, this);
                this.resetReader();
            }
        }
        return this;
    }

    public void cancel() {
        this.send = false;
    }

    public boolean isCancelled() {
        return !this.send;
    }

    public UserConnection user() {
        return this.userConnection;
    }

    public void resetReader() {
        this.packetValues.addAll(this.readableObjects);
        this.readableObjects.clear();
        this.readableObjects.addAll(this.packetValues);
        this.packetValues.clear();
    }

    @Deprecated
    public void sendToServer() throws Exception {
        if (!this.isCancelled()) {
            ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
            this.writeToBuffer(output);
            this.user().sendRawPacketToServer(output, true);
        }
    }

    public void sendToServer(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
        block3: {
            if (!this.isCancelled()) {
                try {
                    ByteBuf output = this.constructPacket(packetProtocol, skipCurrentPipeline, Direction.INCOMING);
                    this.user().sendRawPacketToServer(output, currentThread);
                }
                catch (Exception e) {
                    if (PipelineUtil.containsCause(e, CancelException.class)) break block3;
                    throw e;
                }
            }
        }
    }

    public void sendToServer(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) throws Exception {
        this.sendToServer(packetProtocol, skipCurrentPipeline, false);
    }

    public void sendToServer(Class<? extends Protocol> packetProtocol) throws Exception {
        this.sendToServer(packetProtocol, true);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "PacketWrapper{packetValues=" + this.packetValues + ", readableObjects=" + this.readableObjects + ", id=" + this.id + '}';
    }
}

