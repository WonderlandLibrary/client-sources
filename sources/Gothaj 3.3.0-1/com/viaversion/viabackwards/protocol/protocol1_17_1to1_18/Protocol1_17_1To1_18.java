package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.BlockItemPackets1_18;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets.EntityPackets1_18;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_17;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public final class Protocol1_17_1To1_18
   extends BackwardsProtocol<ClientboundPackets1_18, ClientboundPackets1_17_1, ServerboundPackets1_17, ServerboundPackets1_17> {
   private static final BackwardsMappings MAPPINGS = new BackwardsMappings();
   private final EntityPackets1_18 entityRewriter = new EntityPackets1_18(this);
   private final BlockItemPackets1_18 itemRewriter = new BlockItemPackets1_18(this);
   private final TranslatableRewriter<ClientboundPackets1_18> translatableRewriter = new TranslatableRewriter<>(this, ComponentRewriter.ReadType.JSON);

   public Protocol1_17_1To1_18() {
      super(ClientboundPackets1_18.class, ClientboundPackets1_17_1.class, ServerboundPackets1_17.class, ServerboundPackets1_17.class);
   }

   @Override
   protected void registerPackets() {
      super.registerPackets();
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.CHAT_MESSAGE);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.ACTIONBAR);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.TITLE_TEXT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.TITLE_SUBTITLE);
      this.translatableRewriter.registerBossBar(ClientboundPackets1_18.BOSSBAR);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_18.DISCONNECT);
      this.translatableRewriter.registerTabList(ClientboundPackets1_18.TAB_LIST);
      this.translatableRewriter.registerOpenWindow(ClientboundPackets1_18.OPEN_WINDOW);
      this.translatableRewriter.registerCombatKill(ClientboundPackets1_18.COMBAT_KILL);
      this.translatableRewriter.registerPing();
      SoundRewriter<ClientboundPackets1_18> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.registerSound(ClientboundPackets1_18.SOUND);
      soundRewriter.registerSound(ClientboundPackets1_18.ENTITY_SOUND);
      soundRewriter.registerStopSound(ClientboundPackets1_18.STOP_SOUND);
      soundRewriter.registerNamedSound(ClientboundPackets1_18.NAMED_SOUND);
      TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter<>(this);
      tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:lava_pool_stone_replaceables");
      tagRewriter.registerGeneric(ClientboundPackets1_18.TAGS);
      this.registerServerbound(ServerboundPackets1_17.CLIENT_SETTINGS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.create(Type.BOOLEAN, Boolean.valueOf(true));
         }
      });
      this.registerClientbound(ClientboundPackets1_18.SCOREBOARD_OBJECTIVE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(Protocol1_17_1To1_18.this.cutName(0, 16));
         }
      });
      this.registerClientbound(ClientboundPackets1_18.DISPLAY_SCOREBOARD, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.BYTE);
            this.map(Type.STRING);
            this.handler(Protocol1_17_1To1_18.this.cutName(0, 16));
         }
      });
      this.registerClientbound(ClientboundPackets1_18.TEAMS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(Protocol1_17_1To1_18.this.cutName(0, 16));
         }
      });
      this.registerClientbound(ClientboundPackets1_18.UPDATE_SCORE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.VAR_INT);
            this.map(Type.STRING);
            this.handler(Protocol1_17_1To1_18.this.cutName(0, 40));
            this.handler(Protocol1_17_1To1_18.this.cutName(1, 16));
         }
      });
   }

   private PacketHandler cutName(int index, int maxLength) {
      return wrapper -> {
         String s = wrapper.get(Type.STRING, index);
         if (s.length() > maxLength) {
            wrapper.set(Type.STRING, index, s.substring(0, maxLength));
         }
      };
   }

   @Override
   public void init(UserConnection connection) {
      this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_17.PLAYER));
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   public EntityPackets1_18 getEntityRewriter() {
      return this.entityRewriter;
   }

   public BlockItemPackets1_18 getItemRewriter() {
      return this.itemRewriter;
   }

   @Override
   public TranslatableRewriter<ClientboundPackets1_18> getTranslatableRewriter() {
      return this.translatableRewriter;
   }
}
