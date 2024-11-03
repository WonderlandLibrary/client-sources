package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.BlockItemPackets1_10;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets.EntityPackets1_10;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

public class Protocol1_9_4To1_10 extends BackwardsProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.10", "1.9.4");
   private static final ValueTransformer<Float, Short> TO_OLD_PITCH = new ValueTransformer<Float, Short>(Type.UNSIGNED_BYTE) {
      public Short transform(PacketWrapper packetWrapper, Float inputValue) throws Exception {
         return (short)Math.round(inputValue * 63.5F);
      }
   };
   private final EntityPackets1_10 entityPackets = new EntityPackets1_10(this);
   private final BlockItemPackets1_10 blockItemPackets = new BlockItemPackets1_10(this);

   public Protocol1_9_4To1_10() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
   }

   @Override
   protected void registerPackets() {
      this.entityPackets.register();
      this.blockItemPackets.register();
      final SoundRewriter<ClientboundPackets1_9_3> soundRewriter = new SoundRewriter<>(this);
      this.registerClientbound(ClientboundPackets1_9_3.NAMED_SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT, Protocol1_9_4To1_10.TO_OLD_PITCH);
            this.handler(soundRewriter.getNamedSoundHandler());
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT, Protocol1_9_4To1_10.TO_OLD_PITCH);
            this.handler(soundRewriter.getSoundHandler());
         }
      });
      this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketHandlers() {
         @Override
         public void register() {
            this.read(Type.STRING);
            this.map(Type.VAR_INT);
         }
      });
   }

   @Override
   public void init(UserConnection user) {
      if (!user.has(ClientWorld.class)) {
         user.put(new ClientWorld());
      }

      user.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(user, EntityTypes1_10.EntityType.PLAYER));
   }

   @Override
   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   public EntityPackets1_10 getEntityRewriter() {
      return this.entityPackets;
   }

   public BlockItemPackets1_10 getItemRewriter() {
      return this.blockItemPackets;
   }

   @Override
   public boolean hasMappingDataToLoad() {
      return true;
   }
}
