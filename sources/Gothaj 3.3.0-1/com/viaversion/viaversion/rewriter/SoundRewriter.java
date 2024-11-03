package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

public class SoundRewriter<C extends ClientboundPacketType> {
   protected final Protocol<C, ?, ?, ?> protocol;
   protected final IdRewriteFunction idRewriter;

   public SoundRewriter(Protocol<C, ?, ?, ?> protocol) {
      this.protocol = protocol;
      this.idRewriter = id -> protocol.getMappingData().getSoundMappings().getNewId(id);
   }

   public SoundRewriter(Protocol<C, ?, ?, ?> protocol, IdRewriteFunction idRewriter) {
      this.protocol = protocol;
      this.idRewriter = idRewriter;
   }

   public void registerSound(C packetType) {
      this.protocol.registerClientbound(packetType, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(SoundRewriter.this.getSoundHandler());
         }
      });
   }

   public void registerEntitySound(C packetType) {
      this.registerSound(packetType);
   }

   public void register1_19_3Sound(C packetType) {
      this.protocol.registerClientbound(packetType, wrapper -> {
         int soundId = wrapper.read(Type.VAR_INT);
         if (soundId == 0) {
            wrapper.write(Type.VAR_INT, 0);
         } else {
            int mappedId = this.idRewriter.rewrite(soundId - 1);
            if (mappedId == -1) {
               wrapper.cancel();
            } else {
               wrapper.write(Type.VAR_INT, mappedId + 1);
            }
         }
      });
   }

   public PacketHandler getSoundHandler() {
      return wrapper -> {
         int soundId = wrapper.get(Type.VAR_INT, 0);
         int mappedId = this.idRewriter.rewrite(soundId);
         if (mappedId == -1) {
            wrapper.cancel();
         } else if (soundId != mappedId) {
            wrapper.set(Type.VAR_INT, 0, mappedId);
         }
      };
   }
}
