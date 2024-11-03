package com.viaversion.viaversion.protocol.packet;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
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

public class PacketWrapperImpl implements PacketWrapper {
   private static final Protocol[] PROTOCOL_ARRAY = new Protocol[0];
   private final Deque<PacketWrapperImpl.PacketValue<?>> readableObjects = new ArrayDeque<>();
   private final List<PacketWrapperImpl.PacketValue<?>> packetValues = new ArrayList<>();
   private final ByteBuf inputBuffer;
   private final UserConnection userConnection;
   private boolean send = true;
   private PacketType packetType;
   private int id;

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

      for (PacketWrapperImpl.PacketValue<?> packetValue : this.packetValues) {
         if (packetValue.type() == type) {
            if (currentIndex == index) {
               return (T)packetValue.value();
            }

            currentIndex++;
         }
      }

      throw this.createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index), type, index);
   }

   @Override
   public boolean is(Type type, int index) {
      int currentIndex = 0;

      for (PacketWrapperImpl.PacketValue<?> packetValue : this.packetValues) {
         if (packetValue.type() == type) {
            if (currentIndex == index) {
               return true;
            }

            currentIndex++;
         }
      }

      return false;
   }

   @Override
   public boolean isReadable(Type type, int index) {
      int currentIndex = 0;

      for (PacketWrapperImpl.PacketValue<?> packetValue : this.readableObjects) {
         if (packetValue.type().getBaseClass() == type.getBaseClass()) {
            if (currentIndex == index) {
               return true;
            }

            currentIndex++;
         }
      }

      return false;
   }

   @Override
   public <T> void set(Type<T> type, int index, T value) throws Exception {
      int currentIndex = 0;

      for (PacketWrapperImpl.PacketValue packetValue : this.packetValues) {
         if (packetValue.type() == type) {
            if (currentIndex == index) {
               packetValue.setValue(this.attemptTransform(type, value));
               return;
            }

            currentIndex++;
         }
      }

      throw this.createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + type.getTypeName() + " at " + index), type, index);
   }

   @Override
   public <T> T read(Type<T> type) throws Exception {
      if (this.readableObjects.isEmpty()) {
         Preconditions.checkNotNull(this.inputBuffer, "This packet does not have an input buffer.");

         try {
            return type.read(this.inputBuffer);
         } catch (Exception var4) {
            throw this.createInformativeException(var4, type, this.packetValues.size() + 1);
         }
      } else {
         PacketWrapperImpl.PacketValue readValue = this.readableObjects.poll();
         Type<?> readType = readValue.type();
         if (readType != type && (type.getBaseClass() != readType.getBaseClass() || type.getOutputClass() != readType.getOutputClass())) {
            throw this.createInformativeException(
               new IOException("Unable to read type " + type.getTypeName() + ", found " + readValue.type().getTypeName()), type, this.readableObjects.size()
            );
         } else {
            return (T)readValue.value();
         }
      }
   }

   @Override
   public <T> void write(Type<T> type, T value) {
      this.packetValues.add(new PacketWrapperImpl.PacketValue(type, this.attemptTransform(type, value)));
   }

   @Nullable
   private <T> T attemptTransform(Type<T> expectedType, @Nullable T value) {
      if (value != null && !expectedType.getOutputClass().isAssignableFrom(value.getClass())) {
         if (expectedType instanceof TypeConverter) {
            return ((TypeConverter)expectedType).from(value);
         }

         Via.getPlatform().getLogger().warning("Possible type mismatch: " + value.getClass().getName() + " -> " + expectedType.getOutputClass());
      }

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
      if (this.inputBuffer.isReadable()) {
         this.passthrough(Type.REMAINING_BYTES);
      }
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

      for (PacketWrapperImpl.PacketValue<?> packetValue : this.packetValues) {
         try {
            packetValue.write(buffer);
         } catch (Exception var6) {
            throw this.createInformativeException(var6, packetValue.type(), index);
         }

         index++;
      }

      this.writeRemaining(buffer);
   }

   private InformativeException createInformativeException(Exception cause, Type<?> type, int index) {
      return new InformativeException(cause)
         .set("Index", index)
         .set("Type", type.getTypeName())
         .set("Packet ID", this.id)
         .set("Packet Type", this.packetType)
         .set("Data", this.packetValues);
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
      if (this.inputBuffer != null) {
         output.writeBytes(this.inputBuffer);
      }
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
      if (!this.isCancelled()) {
         UserConnection connection = this.user();
         if (currentThread) {
            try {
               ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
               connection.sendRawPacket(output);
            } catch (Exception var6) {
               if (!PipelineUtil.containsCause(var6, CancelException.class)) {
                  throw var6;
               }
            }
         } else {
            connection.getChannel().eventLoop().submit(() -> {
               try {
                  ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
                  connection.sendRawPacket(output);
               } catch (RuntimeException var5x) {
                  if (!PipelineUtil.containsCause(var5x, CancelException.class)) {
                     throw var5x;
                  }
               } catch (Exception var6x) {
                  if (!PipelineUtil.containsCause(var6x, CancelException.class)) {
                     throw new RuntimeException(var6x);
                  }
               }
            });
         }
      }
   }

   private ByteBuf constructPacket(Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, Direction direction) throws Exception {
      ProtocolInfo protocolInfo = this.user().getProtocolInfo();
      List<Protocol> pipes = direction == Direction.SERVERBOUND ? protocolInfo.getPipeline().pipes() : protocolInfo.getPipeline().reversedPipes();
      List<Protocol> protocols = new ArrayList<>();
      int index = -1;

      for (int i = 0; i < pipes.size(); i++) {
         Protocol protocol = pipes.get(i);
         if (protocol.isBaseProtocol()) {
            protocols.add(protocol);
         }

         if (protocol.getClass() == packetProtocol) {
            index = i;
            break;
         }
      }

      if (index == -1) {
         throw new NoSuchElementException(packetProtocol.getCanonicalName());
      } else {
         if (skipCurrentPipeline) {
            index = Math.min(index + 1, pipes.size());
         }

         protocols.addAll(pipes.subList(index, pipes.size()));
         this.resetReader();
         this.apply(direction, protocolInfo.getState(direction), 0, protocols);
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         ByteBuf var14;
         try {
            this.writeToBuffer(output);
            var14 = output.retain();
         } finally {
            output.release();
         }

         return var14;
      }
   }

   @Override
   public ChannelFuture sendFuture(Class<? extends Protocol> packetProtocol) throws Exception {
      if (!this.isCancelled()) {
         ByteBuf output = this.constructPacket(packetProtocol, true, Direction.CLIENTBOUND);
         return this.user().sendRawPacketFuture(output);
      } else {
         return this.user().getChannel().newFailedFuture(new Exception("Cancelled packet"));
      }
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
      if (!this.isCancelled()) {
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         try {
            this.writeToBuffer(output);
            if (currentThread) {
               this.user().sendRawPacket(output.retain());
            } else {
               this.user().scheduleSendRawPacket(output.retain());
            }
         } finally {
            output.release();
         }
      }
   }

   public PacketWrapperImpl create(int packetId) {
      return new PacketWrapperImpl(packetId, null, this.user());
   }

   public PacketWrapperImpl create(int packetId, PacketHandler handler) throws Exception {
      PacketWrapperImpl wrapper = this.create(packetId);
      handler.handle(wrapper);
      return wrapper;
   }

   public PacketWrapperImpl apply(Direction direction, State state, int index, List<Protocol> pipeline, boolean reverse) throws Exception {
      Protocol[] array = pipeline.toArray(PROTOCOL_ARRAY);
      return this.apply(direction, state, reverse ? array.length - 1 : index, array, reverse);
   }

   public PacketWrapperImpl apply(Direction direction, State state, int index, List<Protocol> pipeline) throws Exception {
      return this.apply(direction, state, index, pipeline.toArray(PROTOCOL_ARRAY), false);
   }

   private PacketWrapperImpl apply(Direction direction, State state, int index, Protocol[] pipeline, boolean reverse) throws Exception {
      State updatedState = state;
      if (reverse) {
         for (int i = index; i >= 0; i--) {
            pipeline[i].transform(direction, updatedState, this);
            this.resetReader();
            if (this.packetType != null) {
               updatedState = this.packetType.state();
            }
         }
      } else {
         for (int ix = index; ix < pipeline.length; ix++) {
            pipeline[ix].transform(direction, updatedState, this);
            this.resetReader();
            if (this.packetType != null) {
               updatedState = this.packetType.state();
            }
         }
      }

      return this;
   }

   @Override
   public boolean isCancelled() {
      return !this.send;
   }

   @Override
   public void setCancelled(boolean cancel) {
      this.send = !cancel;
   }

   @Override
   public UserConnection user() {
      return this.userConnection;
   }

   @Override
   public void resetReader() {
      for (int i = this.packetValues.size() - 1; i >= 0; i--) {
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

   private void sendToServerRaw(boolean currentThread) throws Exception {
      if (!this.isCancelled()) {
         ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();

         try {
            this.writeToBuffer(output);
            if (currentThread) {
               this.user().sendRawPacketToServer(output.retain());
            } else {
               this.user().scheduleSendRawPacketToServer(output.retain());
            }
         } finally {
            output.release();
         }
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
      if (!this.isCancelled()) {
         UserConnection connection = this.user();
         if (currentThread) {
            try {
               ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
               connection.sendRawPacketToServer(output);
            } catch (Exception var6) {
               if (!PipelineUtil.containsCause(var6, CancelException.class)) {
                  throw var6;
               }
            }
         } else {
            connection.getChannel().eventLoop().submit(() -> {
               try {
                  ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
                  connection.sendRawPacketToServer(output);
               } catch (RuntimeException var5x) {
                  if (!PipelineUtil.containsCause(var5x, CancelException.class)) {
                     throw var5x;
                  }
               } catch (Exception var6x) {
                  if (!PipelineUtil.containsCause(var6x, CancelException.class)) {
                     throw new RuntimeException(var6x);
                  }
               }
            });
         }
      }
   }

   @Nullable
   @Override
   public PacketType getPacketType() {
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

   @Deprecated
   @Override
   public void setId(int id) {
      this.packetType = null;
      this.id = id;
   }

   @Nullable
   public ByteBuf getInputBuffer() {
      return this.inputBuffer;
   }

   @Override
   public String toString() {
      return "PacketWrapper{type=" + this.packetType + ", id=" + this.id + ", values=" + this.packetValues + ", readable=" + this.readableObjects + '}';
   }

   public static final class PacketValue<T> {
      private final Type<T> type;
      private T value;

      private PacketValue(Type<T> type, @Nullable T value) {
         this.type = type;
         this.value = value;
      }

      public Type<T> type() {
         return this.type;
      }

      @Nullable
      public Object value() {
         return this.value;
      }

      public void write(ByteBuf buffer) throws Exception {
         this.type.write(buffer, this.value);
      }

      public void setValue(@Nullable T value) {
         this.value = value;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            PacketWrapperImpl.PacketValue<?> that = (PacketWrapperImpl.PacketValue<?>)o;
            return !this.type.equals(that.type) ? false : Objects.equals(this.value, that.value);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int result = this.type.hashCode();
         return 31 * result + (this.value != null ? this.value.hashCode() : 0);
      }

      @Override
      public String toString() {
         return "{" + this.type + ": " + this.value + "}";
      }
   }
}
