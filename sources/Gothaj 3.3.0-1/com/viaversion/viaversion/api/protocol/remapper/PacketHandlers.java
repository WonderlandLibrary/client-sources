package com.viaversion.viaversion.api.protocol.remapper;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class PacketHandlers implements PacketHandler {
   private final List<PacketHandler> packetHandlers = new ArrayList<>();

   protected PacketHandlers() {
      this.register();
   }

   static PacketHandler fromRemapper(List<PacketHandler> valueRemappers) {
      PacketHandlers handlers = new PacketHandlers() {
         @Override
         public void register() {
         }
      };
      handlers.packetHandlers.addAll(valueRemappers);
      return handlers;
   }

   public <T> void map(Type<T> type) {
      this.handler(wrapper -> wrapper.write(type, wrapper.read(type)));
   }

   public void map(Type oldType, Type newType) {
      this.handler(wrapper -> wrapper.write(newType, wrapper.read(oldType)));
   }

   public <T1, T2> void map(Type<T1> oldType, Type<T2> newType, final Function<T1, T2> transformer) {
      this.map(oldType, new ValueTransformer<T1, T2>(newType) {
         @Override
         public T2 transform(PacketWrapper wrapper, T1 inputValue) {
            return transformer.apply(inputValue);
         }
      });
   }

   public <T1, T2> void map(ValueTransformer<T1, T2> transformer) {
      if (transformer.getInputType() == null) {
         throw new IllegalArgumentException("Use map(Type<T1>, ValueTransformer<T1, T2>) for value transformers without specified input type!");
      } else {
         this.map(transformer.getInputType(), transformer);
      }
   }

   public <T1, T2> void map(Type<T1> oldType, ValueTransformer<T1, T2> transformer) {
      this.map(new TypeRemapper<>(oldType), transformer);
   }

   public <T> void map(ValueReader<T> inputReader, ValueWriter<T> outputWriter) {
      this.handler(wrapper -> outputWriter.write(wrapper, inputReader.read(wrapper)));
   }

   public void handler(PacketHandler handler) {
      this.packetHandlers.add(handler);
   }

   public <T> void create(Type<T> type, T value) {
      this.handler(wrapper -> wrapper.write(type, value));
   }

   public void read(Type<?> type) {
      this.handler(wrapper -> wrapper.read(type));
   }

   protected abstract void register();

   @Override
   public final void handle(PacketWrapper wrapper) throws Exception {
      for (PacketHandler handler : this.packetHandlers) {
         handler.handle(wrapper);
      }
   }

   public int handlersSize() {
      return this.packetHandlers.size();
   }
}
