package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.google.common.primitives.Longs;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data.CommandRewriter1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets.BlockItemPackets1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets.EntityPackets1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.NonceStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19;
import com.viaversion.viaversion.api.minecraft.signature.SignableCommandArgumentsProvider;
import com.viaversion.viaversion.api.minecraft.signature.model.DecoratableMessage;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_0;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ChatDecorationResult;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.Protocol1_19_1To1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.Pair;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public final class Protocol1_18_2To1_19
   extends BackwardsProtocol<ClientboundPackets1_19, ClientboundPackets1_18, ServerboundPackets1_19, ServerboundPackets1_17> {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
   private static final UUID ZERO_UUID = new UUID(0L, 0L);
   private static final byte[] EMPTY_BYTES = new byte[0];
   private final EntityPackets1_19 entityRewriter = new EntityPackets1_19(this);
   private final BlockItemPackets1_19 blockItemPackets = new BlockItemPackets1_19(this);
   private final TranslatableRewriter<ClientboundPackets1_19> translatableRewriter = new TranslatableRewriter<>(this, ComponentRewriter.ReadType.JSON);

   public Protocol1_18_2To1_19() {
      super(ClientboundPackets1_19.class, ClientboundPackets1_18.class, ServerboundPackets1_19.class, ServerboundPackets1_17.class);
   }

   @Override
   protected void registerPackets() {
      super.registerPackets();
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19.ACTIONBAR);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19.TITLE_TEXT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19.TITLE_SUBTITLE);
      this.translatableRewriter.registerBossBar(ClientboundPackets1_19.BOSSBAR);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19.DISCONNECT);
      this.translatableRewriter.registerTabList(ClientboundPackets1_19.TAB_LIST);
      this.translatableRewriter.registerOpenWindow(ClientboundPackets1_19.OPEN_WINDOW);
      this.translatableRewriter.registerCombatKill(ClientboundPackets1_19.COMBAT_KILL);
      this.translatableRewriter.registerPing();
      final SoundRewriter<ClientboundPackets1_19> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.registerStopSound(ClientboundPackets1_19.STOP_SOUND);
      this.registerClientbound(ClientboundPackets1_19.SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.read(Type.LONG);
            this.handler(soundRewriter.getSoundHandler());
         }
      });
      this.registerClientbound(ClientboundPackets1_19.ENTITY_SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.read(Type.LONG);
            this.handler(soundRewriter.getSoundHandler());
         }
      });
      this.registerClientbound(ClientboundPackets1_19.NAMED_SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.read(Type.LONG);
            this.handler(soundRewriter.getNamedSoundHandler());
         }
      });
      TagRewriter<ClientboundPackets1_19> tagRewriter = new TagRewriter<>(this);
      tagRewriter.removeTags("minecraft:banner_pattern");
      tagRewriter.removeTags("minecraft:instrument");
      tagRewriter.removeTags("minecraft:cat_variant");
      tagRewriter.removeTags("minecraft:painting_variant");
      tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:polar_bears_spawnable_on_in_frozen_ocean");
      tagRewriter.renameTag(RegistryType.BLOCK, "minecraft:wool_carpets", "minecraft:carpets");
      tagRewriter.renameTag(RegistryType.ITEM, "minecraft:wool_carpets", "minecraft:carpets");
      tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:occludes_vibration_signals");
      tagRewriter.registerGeneric(ClientboundPackets1_19.TAGS);
      new StatisticsRewriter<>(this).register(ClientboundPackets1_19.STATISTICS);
      CommandRewriter<ClientboundPackets1_19> commandRewriter = new CommandRewriter1_19(this);
      this.registerClientbound(ClientboundPackets1_19.DECLARE_COMMANDS, wrapper -> {
         int size = wrapper.passthrough(Type.VAR_INT);

         for (int i = 0; i < size; i++) {
            byte flags = wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            if ((flags & 8) != 0) {
               wrapper.passthrough(Type.VAR_INT);
            }

            int nodeType = flags & 3;
            if (nodeType == 1 || nodeType == 2) {
               wrapper.passthrough(Type.STRING);
            }

            if (nodeType == 2) {
               int argumentTypeId = wrapper.read(Type.VAR_INT);
               String argumentType = MAPPINGS.getArgumentTypeMappings().identifier(argumentTypeId);
               if (argumentType == null) {
                  ViaBackwards.getPlatform().getLogger().warning("Unknown command argument type id: " + argumentTypeId);
                  argumentType = "minecraft:no";
               }

               wrapper.write(Type.STRING, commandRewriter.handleArgumentType(argumentType));
               commandRewriter.handleArgument(wrapper, argumentType);
               if ((flags & 16) != 0) {
                  wrapper.passthrough(Type.STRING);
               }
            }
         }

         wrapper.passthrough(Type.VAR_INT);
      });
      this.cancelClientbound(ClientboundPackets1_19.SERVER_DATA);
      this.cancelClientbound(ClientboundPackets1_19.CHAT_PREVIEW);
      this.cancelClientbound(ClientboundPackets1_19.SET_DISPLAY_CHAT_PREVIEW);
      this.registerClientbound(
         ClientboundPackets1_19.PLAYER_CHAT,
         ClientboundPackets1_18.CHAT_MESSAGE,
         new PacketHandlers() {
            @Override
            public void register() {
               this.handler(
                  wrapper -> {
                     JsonElement signedContent = wrapper.read(Type.COMPONENT);
                     JsonElement unsignedContent = wrapper.read(Type.OPTIONAL_COMPONENT);
                     int chatTypeId = wrapper.read(Type.VAR_INT);
                     UUID sender = wrapper.read(Type.UUID);
                     JsonElement senderName = wrapper.read(Type.COMPONENT);
                     JsonElement teamName = wrapper.read(Type.OPTIONAL_COMPONENT);
                     CompoundTag chatType = wrapper.user().get(DimensionRegistryStorage.class).chatType(chatTypeId);
                     ChatDecorationResult decorationResult = Protocol1_19_1To1_19.decorateChatMessage(
                        chatType, chatTypeId, senderName, teamName, unsignedContent != null ? unsignedContent : signedContent
                     );
                     if (decorationResult == null) {
                        wrapper.cancel();
                     } else {
                        Protocol1_18_2To1_19.this.translatableRewriter.processText(decorationResult.content());
                        wrapper.write(Type.COMPONENT, decorationResult.content());
                        wrapper.write(Type.BYTE, Byte.valueOf((byte)(decorationResult.overlay() ? 2 : 1)));
                        wrapper.write(Type.UUID, sender);
                     }
                  }
               );
               this.read(Type.LONG);
               this.read(Type.LONG);
               this.read(Type.BYTE_ARRAY_PRIMITIVE);
            }
         }
      );
      this.registerClientbound(ClientboundPackets1_19.SYSTEM_CHAT, ClientboundPackets1_18.CHAT_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> {
               JsonElement content = wrapper.passthrough(Type.COMPONENT);
               Protocol1_18_2To1_19.this.translatableRewriter.processText(content);
               int typeId = wrapper.read(Type.VAR_INT);
               wrapper.write(Type.BYTE, Byte.valueOf((byte)(typeId == 2 ? 2 : 0)));
            });
            this.create(Type.UUID, Protocol1_18_2To1_19.ZERO_UUID);
         }
      });
      this.registerServerbound(ServerboundPackets1_17.CHAT_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               ChatSession1_19_0 chatSession = wrapper.user().get(ChatSession1_19_0.class);
               UUID sender = wrapper.user().getProtocolInfo().getUuid();
               Instant timestamp = Instant.now();
               long salt = ThreadLocalRandom.current().nextLong();
               wrapper.write(Type.LONG, timestamp.toEpochMilli());
               wrapper.write(Type.LONG, chatSession != null ? salt : 0L);
               String message = wrapper.get(Type.STRING, 0);
               if (!message.isEmpty() && message.charAt(0) == '/') {
                  String command = message.substring(1);
                  wrapper.setPacketType(ServerboundPackets1_19.CHAT_COMMAND);
                  wrapper.set(Type.STRING, 0, command);
                  SignableCommandArgumentsProvider argumentsProvider = Via.getManager().getProviders().get(SignableCommandArgumentsProvider.class);
                  if (chatSession != null && argumentsProvider != null) {
                     MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                     List<Pair<String, String>> arguments = argumentsProvider.getSignableArguments(command);
                     wrapper.write(Type.VAR_INT, arguments.size());

                     for (Pair<String, String> argument : arguments) {
                        byte[] signature = chatSession.signChatMessage(metadata, new DecoratableMessage(argument.value()));
                        wrapper.write(Type.STRING, argument.key());
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, signature);
                     }
                  } else {
                     wrapper.write(Type.VAR_INT, 0);
                  }
               } else if (chatSession != null) {
                  MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                  DecoratableMessage decoratableMessage = new DecoratableMessage(message);
                  byte[] signature = chatSession.signChatMessage(metadata, decoratableMessage);
                  wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, signature);
               } else {
                  wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, Protocol1_18_2To1_19.EMPTY_BYTES);
               }

               wrapper.write(Type.BOOLEAN, false);
            });
         }
      });
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UUID);
            this.map(Type.STRING);
            this.handler(wrapper -> {
               int properties = wrapper.read(Type.VAR_INT);

               for (int i = 0; i < properties; i++) {
                  wrapper.read(Type.STRING);
                  wrapper.read(Type.STRING);
                  if (wrapper.read(Type.BOOLEAN)) {
                     wrapper.read(Type.STRING);
                  }
               }
            });
         }
      });
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.BYTE_ARRAY_PRIMITIVE);
            this.handler(wrapper -> {
               if (wrapper.user().has(ChatSession1_19_0.class)) {
                  wrapper.user().put(new NonceStorage(wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE)));
               }
            });
         }
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               ChatSession1_19_0 chatSession = wrapper.user().get(ChatSession1_19_0.class);
               wrapper.write(Type.OPTIONAL_PROFILE_KEY, chatSession == null ? null : chatSession.getProfileKey());
            });
         }
      });
      this.registerServerbound(
         State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.BYTE_ARRAY_PRIMITIVE);
               this.handler(wrapper -> {
                  ChatSession1_19_0 chatSession = wrapper.user().get(ChatSession1_19_0.class);
                  byte[] verifyToken = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                  wrapper.write(Type.BOOLEAN, chatSession == null);
                  if (chatSession != null) {
                     long salt = ThreadLocalRandom.current().nextLong();
                     byte[] signature = chatSession.sign(signer -> {
                        signer.accept(wrapper.user().remove(NonceStorage.class).nonce());
                        signer.accept(Longs.toByteArray(salt));
                     });
                     wrapper.write(Type.LONG, salt);
                     wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, signature);
                  } else {
                     wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, verifyToken);
                  }
               });
            }
         }
      );
   }

   @Override
   public void init(UserConnection user) {
      user.put(new DimensionRegistryStorage());
      this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19.PLAYER));
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   @Override
   public TranslatableRewriter<ClientboundPackets1_19> getTranslatableRewriter() {
      return this.translatableRewriter;
   }

   public EntityPackets1_19 getEntityRewriter() {
      return this.entityRewriter;
   }

   public BlockItemPackets1_19 getItemRewriter() {
      return this.blockItemPackets;
   }
}
