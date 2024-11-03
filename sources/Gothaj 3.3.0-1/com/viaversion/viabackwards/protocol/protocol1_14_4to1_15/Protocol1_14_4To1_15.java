package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.data.ImmediateRespawn;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.EntityPackets1_15;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_15;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_14_4To1_15 extends BackwardsProtocol<ClientboundPackets1_15, ClientboundPackets1_14_4, ServerboundPackets1_14, ServerboundPackets1_14> {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.15", "1.14", Protocol1_15To1_14_4.class);
   private final EntityPackets1_15 entityRewriter = new EntityPackets1_15(this);
   private final BlockItemPackets1_15 blockItemPackets = new BlockItemPackets1_15(this);
   private final TranslatableRewriter<ClientboundPackets1_15> translatableRewriter = new TranslatableRewriter<>(this, ComponentRewriter.ReadType.JSON);

   public Protocol1_14_4To1_15() {
      super(ClientboundPackets1_15.class, ClientboundPackets1_14_4.class, ServerboundPackets1_14.class, ServerboundPackets1_14.class);
   }

   @Override
   protected void registerPackets() {
      super.registerPackets();
      this.translatableRewriter.registerBossBar(ClientboundPackets1_15.BOSSBAR);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_15.CHAT_MESSAGE);
      this.translatableRewriter.registerCombatEvent(ClientboundPackets1_15.COMBAT_EVENT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_15.DISCONNECT);
      this.translatableRewriter.registerOpenWindow(ClientboundPackets1_15.OPEN_WINDOW);
      this.translatableRewriter.registerTabList(ClientboundPackets1_15.TAB_LIST);
      this.translatableRewriter.registerTitle(ClientboundPackets1_15.TITLE);
      this.translatableRewriter.registerPing();
      SoundRewriter<ClientboundPackets1_15> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.registerSound(ClientboundPackets1_15.SOUND);
      soundRewriter.registerSound(ClientboundPackets1_15.ENTITY_SOUND);
      soundRewriter.registerNamedSound(ClientboundPackets1_15.NAMED_SOUND);
      soundRewriter.registerStopSound(ClientboundPackets1_15.STOP_SOUND);
      this.registerClientbound(ClientboundPackets1_15.EXPLOSION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.handler(wrapper -> {
               PacketWrapper soundPacket = wrapper.create(ClientboundPackets1_14_4.SOUND);
               soundPacket.write(Type.VAR_INT, 243);
               soundPacket.write(Type.VAR_INT, 4);
               soundPacket.write(Type.INT, this.toEffectCoordinate(wrapper.get(Type.FLOAT, 0)));
               soundPacket.write(Type.INT, this.toEffectCoordinate(wrapper.get(Type.FLOAT, 1)));
               soundPacket.write(Type.INT, this.toEffectCoordinate(wrapper.get(Type.FLOAT, 2)));
               soundPacket.write(Type.FLOAT, 4.0F);
               soundPacket.write(Type.FLOAT, 1.0F);
               soundPacket.send(Protocol1_14_4To1_15.class);
            });
         }

         private int toEffectCoordinate(float coordinate) {
            return (int)(coordinate * 8.0F);
         }
      });
      new TagRewriter<>(this).register(ClientboundPackets1_15.TAGS, RegistryType.ENTITY);
      new StatisticsRewriter<>(this).register(ClientboundPackets1_15.STATISTICS);
   }

   @Override
   public void init(UserConnection user) {
      user.put(new ImmediateRespawn());
      user.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(user, EntityTypes1_15.PLAYER));
   }

   @Override
   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   public EntityPackets1_15 getEntityRewriter() {
      return this.entityRewriter;
   }

   public BlockItemPackets1_15 getItemRewriter() {
      return this.blockItemPackets;
   }

   @Override
   public TranslatableRewriter<ClientboundPackets1_15> getTranslatableRewriter() {
      return this.translatableRewriter;
   }
}
