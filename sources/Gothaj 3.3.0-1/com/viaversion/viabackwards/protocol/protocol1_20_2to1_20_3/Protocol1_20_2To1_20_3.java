package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.rewriter.BlockItemPacketRewriter1_20_3;
import com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.rewriter.EntityPacketRewriter1_20_3;
import com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.storage.ResourcepackIDStorage;
import com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.storage.SpawnPositionStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_20_3;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.fastutil.Pair;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.CommandRewriter1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundConfigurationPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.Protocol1_20_3To1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundConfigurationPackets1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundPackets1_20_3;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ServerboundPackets1_20_3;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.BitSet;
import java.util.UUID;

public final class Protocol1_20_2To1_20_3
   extends BackwardsProtocol<ClientboundPackets1_20_3, ClientboundPackets1_20_2, ServerboundPackets1_20_3, ServerboundPackets1_20_2> {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.20.3", "1.20.2", Protocol1_20_3To1_20_2.class);
   private final EntityPacketRewriter1_20_3 entityRewriter = new EntityPacketRewriter1_20_3(this);
   private final BlockItemPacketRewriter1_20_3 itemRewriter = new BlockItemPacketRewriter1_20_3(this);
   private final TranslatableRewriter<ClientboundPackets1_20_3> translatableRewriter = new TranslatableRewriter<>(this, ComponentRewriter.ReadType.NBT);

   public Protocol1_20_2To1_20_3() {
      super(ClientboundPackets1_20_3.class, ClientboundPackets1_20_2.class, ServerboundPackets1_20_3.class, ServerboundPackets1_20_2.class);
   }

   @Override
   protected void registerPackets() {
      super.registerPackets();
      TagRewriter<ClientboundPackets1_20_3> tagRewriter = new TagRewriter<>(this);
      tagRewriter.registerGeneric(ClientboundPackets1_20_3.TAGS);
      SoundRewriter<ClientboundPackets1_20_3> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.register1_19_3Sound(ClientboundPackets1_20_3.SOUND);
      soundRewriter.registerEntitySound(ClientboundPackets1_20_3.ENTITY_SOUND);
      soundRewriter.registerStopSound(ClientboundPackets1_20_3.STOP_SOUND);
      new StatisticsRewriter<>(this).register(ClientboundPackets1_20_3.STATISTICS);
      (new CommandRewriter1_19_4<ClientboundPackets1_20_3>(this) {
         @Override
         public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
            if (argumentType.equals("minecraft:style")) {
               wrapper.write(Type.VAR_INT, 1);
            } else {
               super.handleArgument(wrapper, argumentType);
            }
         }
      }).registerDeclareCommands1_19(ClientboundPackets1_20_3.DECLARE_COMMANDS);
      this.registerClientbound(ClientboundPackets1_20_3.RESET_SCORE, ClientboundPackets1_20_2.UPDATE_SCORE, wrapper -> {
         wrapper.passthrough(Type.STRING);
         wrapper.write(Type.VAR_INT, 1);
         String objectiveName = wrapper.read(Type.OPTIONAL_STRING);
         wrapper.write(Type.STRING, objectiveName != null ? objectiveName : "");
      });
      this.registerClientbound(ClientboundPackets1_20_3.UPDATE_SCORE, wrapper -> {
         wrapper.passthrough(Type.STRING);
         wrapper.write(Type.VAR_INT, 0);
         wrapper.passthrough(Type.STRING);
         wrapper.passthrough(Type.VAR_INT);
         wrapper.clearInputBuffer();
      });
      this.registerClientbound(ClientboundPackets1_20_3.SCOREBOARD_OBJECTIVE, wrapper -> {
         wrapper.passthrough(Type.STRING);
         byte action = wrapper.passthrough(Type.BYTE);
         if (action == 0 || action == 2) {
            this.convertComponent(wrapper);
            wrapper.passthrough(Type.VAR_INT);
            wrapper.clearInputBuffer();
         }
      });
      this.cancelClientbound(ClientboundPackets1_20_3.TICKING_STATE);
      this.cancelClientbound(ClientboundPackets1_20_3.TICKING_STEP);
      this.registerServerbound(ServerboundPackets1_20_2.UPDATE_JIGSAW_BLOCK, wrapper -> {
         wrapper.passthrough(Type.POSITION1_14);
         wrapper.passthrough(Type.STRING);
         wrapper.passthrough(Type.STRING);
         wrapper.passthrough(Type.STRING);
         wrapper.passthrough(Type.STRING);
         wrapper.passthrough(Type.STRING);
         wrapper.write(Type.VAR_INT, 0);
         wrapper.write(Type.VAR_INT, 0);
      });
      this.registerClientbound(ClientboundPackets1_20_3.ADVANCEMENTS, wrapper -> {
         wrapper.passthrough(Type.BOOLEAN);
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            wrapper.passthrough(Type.STRING);
            if (wrapper.passthrough(Type.BOOLEAN)) {
               wrapper.passthrough(Type.STRING);
            }

            if (wrapper.passthrough(Type.BOOLEAN)) {
               this.convertComponent(wrapper);
               this.convertComponent(wrapper);
               this.itemRewriter.handleItemToClient(wrapper.passthrough(Type.ITEM1_20_2));
               wrapper.passthrough(Type.VAR_INT);
               int flags = wrapper.passthrough(Type.INT);
               if ((flags & 1) != 0) {
                  wrapper.passthrough(Type.STRING);
               }

               wrapper.passthrough(Type.FLOAT);
               wrapper.passthrough(Type.FLOAT);
            }

            int requirements = wrapper.passthrough(Type.VAR_INT);

            for (int array = 0; array < requirements; array++) {
               wrapper.passthrough(Type.STRING_ARRAY);
            }

            wrapper.passthrough(Type.BOOLEAN);
         }
      });
      this.registerClientbound(ClientboundPackets1_20_3.TAB_COMPLETE, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         wrapper.passthrough(Type.VAR_INT);
         wrapper.passthrough(Type.VAR_INT);
         int suggestions = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < suggestions; i++) {
            wrapper.passthrough(Type.STRING);
            this.convertOptionalComponent(wrapper);
         }
      });
      this.registerClientbound(ClientboundPackets1_20_3.MAP_DATA, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         wrapper.passthrough(Type.BYTE);
         wrapper.passthrough(Type.BOOLEAN);
         if (wrapper.passthrough(Type.BOOLEAN)) {
            int icons = wrapper.passthrough(Type.VAR_INT);

            for (int i = 0; i < icons; i++) {
               wrapper.passthrough(Type.VAR_INT);
               wrapper.passthrough(Type.BYTE);
               wrapper.passthrough(Type.BYTE);
               wrapper.passthrough(Type.BYTE);
               this.convertOptionalComponent(wrapper);
            }
         }
      });
      this.registerClientbound(ClientboundPackets1_20_3.BOSSBAR, wrapper -> {
         wrapper.passthrough(Type.UUID);
         int action = wrapper.passthrough(Type.VAR_INT);
         if (action == 0 || action == 3) {
            this.convertComponent(wrapper);
         }
      });
      this.registerClientbound(ClientboundPackets1_20_3.PLAYER_CHAT, wrapper -> {
         wrapper.passthrough(Type.UUID);
         wrapper.passthrough(Type.VAR_INT);
         wrapper.passthrough(Type.OPTIONAL_SIGNATURE_BYTES);
         wrapper.passthrough(Type.STRING);
         wrapper.passthrough(Type.LONG);
         wrapper.passthrough(Type.LONG);
         int lastSeen = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < lastSeen; i++) {
            int index = wrapper.passthrough(Type.VAR_INT);
            if (index == 0) {
               wrapper.passthrough(Type.SIGNATURE_BYTES);
            }
         }

         this.convertOptionalComponent(wrapper);
         int filterMaskType = wrapper.passthrough(Type.VAR_INT);
         if (filterMaskType == 2) {
            wrapper.passthrough(Type.LONG_ARRAY_PRIMITIVE);
         }

         wrapper.passthrough(Type.VAR_INT);
         this.convertComponent(wrapper);
         this.convertOptionalComponent(wrapper);
      });
      this.registerClientbound(ClientboundPackets1_20_3.TEAMS, wrapper -> {
         wrapper.passthrough(Type.STRING);
         byte action = wrapper.passthrough(Type.BYTE);
         if (action == 0 || action == 2) {
            this.convertComponent(wrapper);
            wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.STRING);
            wrapper.passthrough(Type.STRING);
            wrapper.passthrough(Type.VAR_INT);
            this.convertComponent(wrapper);
            this.convertComponent(wrapper);
         }
      });
      this.registerClientbound(
         State.CONFIGURATION,
         ClientboundConfigurationPackets1_20_2.DISCONNECT.getId(),
         ClientboundConfigurationPackets1_20_2.DISCONNECT.getId(),
         this::convertComponent
      );
      this.registerClientbound(ClientboundPackets1_20_3.DISCONNECT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_3.RESOURCE_PACK_PUSH, ClientboundPackets1_20_2.RESOURCE_PACK, this.resourcePackHandler());
      this.registerClientbound(ClientboundPackets1_20_3.SERVER_DATA, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_3.ACTIONBAR, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_3.TITLE_TEXT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_3.TITLE_SUBTITLE, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_3.DISGUISED_CHAT, wrapper -> {
         this.convertComponent(wrapper);
         wrapper.passthrough(Type.VAR_INT);
         this.convertComponent(wrapper);
         this.convertOptionalComponent(wrapper);
      });
      this.registerClientbound(ClientboundPackets1_20_3.SYSTEM_CHAT, this::convertComponent);
      this.registerClientbound(ClientboundPackets1_20_3.OPEN_WINDOW, wrapper -> {
         wrapper.passthrough(Type.VAR_INT);
         int containerTypeId = wrapper.read(Type.VAR_INT);
         int mappedContainerTypeId = MAPPINGS.getMenuMappings().getNewId(containerTypeId);
         if (mappedContainerTypeId == -1) {
            wrapper.cancel();
         } else {
            wrapper.write(Type.VAR_INT, mappedContainerTypeId);
            this.convertComponent(wrapper);
         }
      });
      this.registerClientbound(ClientboundPackets1_20_3.TAB_LIST, wrapper -> {
         this.convertComponent(wrapper);
         this.convertComponent(wrapper);
      });
      this.registerClientbound(ClientboundPackets1_20_3.COMBAT_KILL, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.handler(wrapper -> Protocol1_20_2To1_20_3.this.convertComponent(wrapper));
         }
      });
      this.registerClientbound(ClientboundPackets1_20_3.PLAYER_INFO_UPDATE, wrapper -> {
         BitSet actions = wrapper.passthrough(Type.PROFILE_ACTIONS_ENUM);
         int entries = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < entries; i++) {
            wrapper.passthrough(Type.UUID);
            if (actions.get(0)) {
               wrapper.passthrough(Type.STRING);
               int properties = wrapper.passthrough(Type.VAR_INT);

               for (int j = 0; j < properties; j++) {
                  wrapper.passthrough(Type.STRING);
                  wrapper.passthrough(Type.STRING);
                  wrapper.passthrough(Type.OPTIONAL_STRING);
               }
            }

            if (actions.get(1) && wrapper.passthrough(Type.BOOLEAN)) {
               wrapper.passthrough(Type.UUID);
               wrapper.passthrough(Type.PROFILE_KEY);
            }

            if (actions.get(2)) {
               wrapper.passthrough(Type.VAR_INT);
            }

            if (actions.get(3)) {
               wrapper.passthrough(Type.BOOLEAN);
            }

            if (actions.get(4)) {
               wrapper.passthrough(Type.VAR_INT);
            }

            if (actions.get(5)) {
               this.convertOptionalComponent(wrapper);
            }
         }
      });
      this.registerClientbound(ClientboundPackets1_20_3.SPAWN_POSITION, wrapper -> {
         Position position = wrapper.passthrough(Type.POSITION1_14);
         float angle = wrapper.passthrough(Type.FLOAT);
         wrapper.user().get(SpawnPositionStorage.class).setSpawnPosition(Pair.of(position, angle));
      });
      this.registerClientbound(ClientboundPackets1_20_3.GAME_EVENT, wrapper -> {
         short reason = wrapper.passthrough(Type.UNSIGNED_BYTE);
         if (reason == 13) {
            wrapper.cancel();
            Pair<Position, Float> spawnPositionAndAngle = wrapper.user().get(SpawnPositionStorage.class).getSpawnPosition();
            PacketWrapper spawnPosition = wrapper.create(ClientboundPackets1_20_2.SPAWN_POSITION);
            spawnPosition.write(Type.POSITION1_14, spawnPositionAndAngle.first());
            spawnPosition.write(Type.FLOAT, spawnPositionAndAngle.second());
            spawnPosition.send(Protocol1_20_2To1_20_3.class, true);
         }
      });
      this.cancelClientbound(ClientboundPackets1_20_3.RESOURCE_PACK_POP);
      this.registerServerbound(ServerboundPackets1_20_2.RESOURCE_PACK_STATUS, this.resourcePackStatusHandler());
      this.cancelClientbound(State.CONFIGURATION, ClientboundConfigurationPackets1_20_3.RESOURCE_PACK_POP.getId());
      this.registerServerbound(State.CONFIGURATION, ServerboundConfigurationPackets1_20_2.RESOURCE_PACK, this.resourcePackStatusHandler());
      this.registerClientbound(
         State.CONFIGURATION,
         ClientboundConfigurationPackets1_20_3.RESOURCE_PACK_PUSH.getId(),
         ClientboundConfigurationPackets1_20_2.RESOURCE_PACK.getId(),
         this.resourcePackHandler()
      );
      this.registerClientbound(
         State.CONFIGURATION,
         ClientboundConfigurationPackets1_20_3.UPDATE_TAGS.getId(),
         ClientboundConfigurationPackets1_20_2.UPDATE_TAGS.getId(),
         tagRewriter.getGenericHandler()
      );
      this.registerClientbound(
         State.CONFIGURATION,
         ClientboundConfigurationPackets1_20_3.UPDATE_ENABLED_FEATURES.getId(),
         ClientboundConfigurationPackets1_20_2.UPDATE_ENABLED_FEATURES.getId()
      );
   }

   private PacketHandler resourcePackStatusHandler() {
      return wrapper -> {
         ResourcepackIDStorage storage = wrapper.user().get(ResourcepackIDStorage.class);
         wrapper.write(Type.UUID, storage != null ? storage.uuid() : UUID.randomUUID());
      };
   }

   private PacketHandler resourcePackHandler() {
      return wrapper -> {
         UUID uuid = wrapper.read(Type.UUID);
         wrapper.user().put(new ResourcepackIDStorage(uuid));
         wrapper.passthrough(Type.STRING);
         wrapper.passthrough(Type.STRING);
         wrapper.passthrough(Type.BOOLEAN);
         this.convertOptionalComponent(wrapper);
      };
   }

   private void convertComponent(PacketWrapper wrapper) throws Exception {
      Tag tag = wrapper.read(Type.TAG);
      this.translatableRewriter.processTag(tag);
      wrapper.write(Type.COMPONENT, ComponentUtil.tagToJson(tag));
   }

   private void convertOptionalComponent(PacketWrapper wrapper) throws Exception {
      Tag tag = wrapper.read(Type.OPTIONAL_TAG);
      this.translatableRewriter.processTag(tag);
      wrapper.write(Type.OPTIONAL_COMPONENT, ComponentUtil.tagToJson(tag));
   }

   @Override
   public void init(UserConnection connection) {
      connection.put(new SpawnPositionStorage());
      this.addEntityTracker(connection, new EntityTrackerBase(connection, EntityTypes1_20_3.PLAYER));
   }

   @Override
   protected ServerboundPacketType serverboundFinishConfigurationPacket() {
      return ServerboundConfigurationPackets1_20_2.FINISH_CONFIGURATION;
   }

   @Override
   protected ClientboundPacketType clientboundFinishConfigurationPacket() {
      return ClientboundConfigurationPackets1_20_3.FINISH_CONFIGURATION;
   }

   @Override
   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   public BlockItemPacketRewriter1_20_3 getItemRewriter() {
      return this.itemRewriter;
   }

   public EntityPacketRewriter1_20_3 getEntityRewriter() {
      return this.entityRewriter;
   }

   @Override
   public TranslatableRewriter<ClientboundPackets1_20_3> getTranslatableRewriter() {
      return this.translatableRewriter;
   }
}
