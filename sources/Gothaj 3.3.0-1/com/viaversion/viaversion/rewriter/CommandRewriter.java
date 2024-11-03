package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.HashMap;
import java.util.Map;

public class CommandRewriter<C extends ClientboundPacketType> {
   protected final Protocol<C, ?, ?, ?> protocol;
   protected final Map<String, CommandRewriter.CommandArgumentConsumer> parserHandlers = new HashMap<>();

   public CommandRewriter(Protocol<C, ?, ?, ?> protocol) {
      this.protocol = protocol;
      this.parserHandlers.put("brigadier:double", wrapper -> {
         byte propertyFlags = wrapper.passthrough(Type.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Type.DOUBLE);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Type.DOUBLE);
         }
      });
      this.parserHandlers.put("brigadier:float", wrapper -> {
         byte propertyFlags = wrapper.passthrough(Type.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Type.FLOAT);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Type.FLOAT);
         }
      });
      this.parserHandlers.put("brigadier:integer", wrapper -> {
         byte propertyFlags = wrapper.passthrough(Type.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Type.INT);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Type.INT);
         }
      });
      this.parserHandlers.put("brigadier:long", wrapper -> {
         byte propertyFlags = wrapper.passthrough(Type.BYTE);
         if ((propertyFlags & 1) != 0) {
            wrapper.passthrough(Type.LONG);
         }

         if ((propertyFlags & 2) != 0) {
            wrapper.passthrough(Type.LONG);
         }
      });
      this.parserHandlers.put("brigadier:string", wrapper -> {
         Integer var10000 = wrapper.passthrough(Type.VAR_INT);
      });
      this.parserHandlers.put("minecraft:entity", wrapper -> {
         Byte var10000 = wrapper.passthrough(Type.BYTE);
      });
      this.parserHandlers.put("minecraft:score_holder", wrapper -> {
         Byte var10000 = wrapper.passthrough(Type.BYTE);
      });
      this.parserHandlers.put("minecraft:resource", wrapper -> {
         String var10000 = wrapper.passthrough(Type.STRING);
      });
      this.parserHandlers.put("minecraft:resource_or_tag", wrapper -> {
         String var10000 = wrapper.passthrough(Type.STRING);
      });
      this.parserHandlers.put("minecraft:resource_or_tag_key", wrapper -> {
         String var10000 = wrapper.passthrough(Type.STRING);
      });
      this.parserHandlers.put("minecraft:resource_key", wrapper -> {
         String var10000 = wrapper.passthrough(Type.STRING);
      });
   }

   public void registerDeclareCommands(C packetType) {
      this.protocol.registerClientbound(packetType, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            byte flags = wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            if ((flags & 8) != 0) {
               wrapper.passthrough(Type.VAR_INT);
            }

            byte nodeType = (byte)(flags & 3);
            if (nodeType == 1 || nodeType == 2) {
               wrapper.passthrough(Type.STRING);
            }

            if (nodeType == 2) {
               String argumentType = wrapper.read(Type.STRING);
               String newArgumentType = this.handleArgumentType(argumentType);
               if (newArgumentType != null) {
                  wrapper.write(Type.STRING, newArgumentType);
               }

               this.handleArgument(wrapper, argumentType);
            }

            if ((flags & 16) != 0) {
               wrapper.passthrough(Type.STRING);
            }
         }

         wrapper.passthrough(Type.VAR_INT);
      });
   }

   public void registerDeclareCommands1_19(C packetType) {
      this.protocol.registerClientbound(packetType, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            byte flags = wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            if ((flags & 8) != 0) {
               wrapper.passthrough(Type.VAR_INT);
            }

            byte nodeType = (byte)(flags & 3);
            if (nodeType == 1 || nodeType == 2) {
               wrapper.passthrough(Type.STRING);
            }

            if (nodeType == 2) {
               int argumentTypeId = wrapper.read(Type.VAR_INT);
               String argumentType = this.argumentType(argumentTypeId);
               String newArgumentType = this.handleArgumentType(argumentType);
               Preconditions.checkNotNull(newArgumentType, "No mapping for argument type %s", new Object[]{argumentType});
               wrapper.write(Type.VAR_INT, this.mappedArgumentTypeId(newArgumentType));
               this.handleArgument(wrapper, argumentType);
            }

            if ((flags & 16) != 0) {
               wrapper.passthrough(Type.STRING);
            }
         }

         wrapper.passthrough(Type.VAR_INT);
      });
   }

   public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
      CommandRewriter.CommandArgumentConsumer handler = this.parserHandlers.get(argumentType);
      if (handler != null) {
         handler.accept(wrapper);
      }
   }

   public String handleArgumentType(String argumentType) {
      return this.protocol.getMappingData() != null && this.protocol.getMappingData().getArgumentTypeMappings() != null
         ? this.protocol.getMappingData().getArgumentTypeMappings().mappedIdentifier(argumentType)
         : argumentType;
   }

   protected String argumentType(int argumentTypeId) {
      return this.protocol.getMappingData().getArgumentTypeMappings().identifier(argumentTypeId);
   }

   protected int mappedArgumentTypeId(String mappedArgumentType) {
      return this.protocol.getMappingData().getArgumentTypeMappings().mappedId(mappedArgumentType);
   }

   @FunctionalInterface
   public interface CommandArgumentConsumer {
      void accept(PacketWrapper var1) throws Exception;
   }
}
