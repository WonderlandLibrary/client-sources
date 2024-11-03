package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets.BlockItemPackets1_19_3;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.packets.EntityPackets1_19_3;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.ChatSessionStorage;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.ChatTypeStorage1_19_3;
import com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.storage.NonceStorage;
import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.Protocol1_19To1_19_1;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_19_3;
import com.viaversion.viaversion.api.minecraft.signature.SignableCommandArgumentsProvider;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_3;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.ByteArrayType;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.CipherUtil;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.Pair;
import java.util.BitSet;
import java.util.List;

public final class Protocol1_19_1To1_19_3
   extends BackwardsProtocol<ClientboundPackets1_19_3, ClientboundPackets1_19_1, ServerboundPackets1_19_3, ServerboundPackets1_19_1> {
   public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
   public static final ByteArrayType.OptionalByteArrayType OPTIONAL_SIGNATURE_BYTES_TYPE = new ByteArrayType.OptionalByteArrayType(256);
   public static final ByteArrayType SIGNATURE_BYTES_TYPE = new ByteArrayType(256);
   private final EntityPackets1_19_3 entityRewriter = new EntityPackets1_19_3(this);
   private final BlockItemPackets1_19_3 itemRewriter = new BlockItemPackets1_19_3(this);
   private final TranslatableRewriter<ClientboundPackets1_19_3> translatableRewriter = new TranslatableRewriter<>(this, ComponentRewriter.ReadType.JSON);

   public Protocol1_19_1To1_19_3() {
      super(ClientboundPackets1_19_3.class, ClientboundPackets1_19_1.class, ServerboundPackets1_19_3.class, ServerboundPackets1_19_1.class);
   }

   @Override
   protected void registerPackets() {
      super.registerPackets();
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.SYSTEM_CHAT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.ACTIONBAR);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.TITLE_TEXT);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.TITLE_SUBTITLE);
      this.translatableRewriter.registerBossBar(ClientboundPackets1_19_3.BOSSBAR);
      this.translatableRewriter.registerComponentPacket(ClientboundPackets1_19_3.DISCONNECT);
      this.translatableRewriter.registerTabList(ClientboundPackets1_19_3.TAB_LIST);
      this.translatableRewriter.registerOpenWindow(ClientboundPackets1_19_3.OPEN_WINDOW);
      this.translatableRewriter.registerCombatKill(ClientboundPackets1_19_3.COMBAT_KILL);
      this.translatableRewriter.registerPing();
      SoundRewriter<ClientboundPackets1_19_3> soundRewriter = new SoundRewriter<>(this);
      soundRewriter.registerStopSound(ClientboundPackets1_19_3.STOP_SOUND);
      this.registerClientbound(ClientboundPackets1_19_3.SOUND, wrapper -> {
         int soundId = wrapper.read(Type.VAR_INT) - 1;
         if (soundId != -1) {
            int mappedId = MAPPINGS.getSoundMappings().getNewId(soundId);
            if (mappedId == -1) {
               wrapper.cancel();
            } else {
               wrapper.write(Type.VAR_INT, mappedId);
            }
         } else {
            String soundIdentifier = wrapper.read(Type.STRING);
            wrapper.read(Type.OPTIONAL_FLOAT);
            String mappedIdentifier = MAPPINGS.getMappedNamedSound(soundIdentifier);
            if (mappedIdentifier != null) {
               if (mappedIdentifier.isEmpty()) {
                  wrapper.cancel();
                  return;
               }

               soundIdentifier = mappedIdentifier;
            }

            wrapper.write(Type.STRING, soundIdentifier);
            wrapper.setPacketType(ClientboundPackets1_19_1.NAMED_SOUND);
         }
      });
      this.registerClientbound(ClientboundPackets1_19_3.ENTITY_SOUND, wrapper -> {
         int soundId = wrapper.read(Type.VAR_INT) - 1;
         if (soundId != -1) {
            int mappedId = MAPPINGS.getSoundMappings().getNewId(soundId);
            if (mappedId == -1) {
               wrapper.cancel();
            } else {
               wrapper.write(Type.VAR_INT, mappedId);
            }
         } else {
            String soundIdentifier = wrapper.read(Type.STRING);
            wrapper.read(Type.OPTIONAL_FLOAT);
            String mappedIdentifier = MAPPINGS.getMappedNamedSound(soundIdentifier);
            if (mappedIdentifier != null) {
               if (mappedIdentifier.isEmpty()) {
                  wrapper.cancel();
                  return;
               }

               soundIdentifier = mappedIdentifier;
            }

            int mappedId = MAPPINGS.mappedSound(soundIdentifier);
            if (mappedId == -1) {
               wrapper.cancel();
            } else {
               wrapper.write(Type.VAR_INT, mappedId);
            }
         }
      });
      TagRewriter<ClientboundPackets1_19_3> tagRewriter = new TagRewriter<>(this);
      tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
      tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:overworld_natural_logs");
      tagRewriter.registerGeneric(ClientboundPackets1_19_3.TAGS);
      new StatisticsRewriter<>(this).register(ClientboundPackets1_19_3.STATISTICS);
      CommandRewriter<ClientboundPackets1_19_3> commandRewriter = new CommandRewriter<>(this);
      this.registerClientbound(ClientboundPackets1_19_3.DECLARE_COMMANDS, wrapper -> {
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
               int mappedArgumentTypeId = MAPPINGS.getArgumentTypeMappings().mappings().getNewId(argumentTypeId);
               Preconditions.checkArgument(mappedArgumentTypeId != -1, "Unknown command argument type id: " + argumentTypeId);
               wrapper.write(Type.VAR_INT, mappedArgumentTypeId);
               String identifier = MAPPINGS.getArgumentTypeMappings().identifier(argumentTypeId);
               commandRewriter.handleArgument(wrapper, identifier);
               if (identifier.equals("minecraft:gamemode")) {
                  wrapper.write(Type.VAR_INT, 0);
               }

               if ((flags & 16) != 0) {
                  wrapper.passthrough(Type.STRING);
               }
            }
         }

         wrapper.passthrough(Type.VAR_INT);
      });
      this.registerClientbound(ClientboundPackets1_19_3.SERVER_DATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.OPTIONAL_COMPONENT);
            this.map(Type.OPTIONAL_STRING);
            this.create(Type.BOOLEAN, Boolean.valueOf(false));
         }
      });
      this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               ProfileKey profileKey = wrapper.read(Type.OPTIONAL_PROFILE_KEY);
               if (profileKey == null) {
                  wrapper.user().put(new NonceStorage(null));
               }
            });
         }
      });
      this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               if (!wrapper.user().has(NonceStorage.class)) {
                  byte[] publicKey = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                  byte[] nonce = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                  wrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
               }
            });
         }
      });
      this.registerServerbound(
         State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.BYTE_ARRAY_PRIMITIVE);
               this.handler(wrapper -> {
                  NonceStorage nonceStorage = wrapper.user().remove(NonceStorage.class);
                  boolean isNonce = wrapper.read(Type.BOOLEAN);
                  if (!isNonce) {
                     wrapper.read(Type.LONG);
                     wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                     wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce() != null ? nonceStorage.nonce() : new byte[0]);
                  }
               });
            }
         }
      );
      this.registerServerbound(ServerboundPackets1_19_1.CHAT_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.LONG);
            this.map(Type.LONG);
            this.read(Type.BYTE_ARRAY_PRIMITIVE);
            this.read(Type.BOOLEAN);
            this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
            this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
            this.handler(wrapper -> {
               ChatSession1_19_3 chatSession = wrapper.user().get(ChatSession1_19_3.class);
               if (chatSession != null) {
                  String message = wrapper.get(Type.STRING, 0);
                  long timestamp = wrapper.get(Type.LONG, 0);
                  long salt = wrapper.get(Type.LONG, 1);
                  MessageMetadata metadata = new MessageMetadata(null, timestamp, salt);
                  byte[] signature = chatSession.signChatMessage(metadata, message, new PlayerMessageSignature[0]);
                  wrapper.write(Protocol1_19_1To1_19_3.OPTIONAL_SIGNATURE_BYTES_TYPE, signature);
               } else {
                  wrapper.write(Protocol1_19_1To1_19_3.OPTIONAL_SIGNATURE_BYTES_TYPE, null);
               }

               wrapper.write(Type.VAR_INT, 0);
               wrapper.write(new BitSetType(20), new BitSet(20));
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_19_1.CHAT_COMMAND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.LONG);
            this.map(Type.LONG);
            this.handler(wrapper -> {
               ChatSession1_19_3 chatSession = wrapper.user().get(ChatSession1_19_3.class);
               SignableCommandArgumentsProvider argumentsProvider = Via.getManager().getProviders().get(SignableCommandArgumentsProvider.class);
               String command = wrapper.get(Type.STRING, 0);
               long timestamp = wrapper.get(Type.LONG, 0);
               long salt = wrapper.get(Type.LONG, 1);
               int signatures = wrapper.read(Type.VAR_INT);

               for (int i = 0; i < signatures; i++) {
                  wrapper.read(Type.STRING);
                  wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
               }

               wrapper.read(Type.BOOLEAN);
               if (chatSession != null && argumentsProvider != null) {
                  MessageMetadata metadata = new MessageMetadata(null, timestamp, salt);
                  List<Pair<String, String>> arguments = argumentsProvider.getSignableArguments(command);
                  wrapper.write(Type.VAR_INT, arguments.size());

                  for (Pair<String, String> argument : arguments) {
                     byte[] signature = chatSession.signChatMessage(metadata, argument.value(), new PlayerMessageSignature[0]);
                     wrapper.write(Type.STRING, argument.key());
                     wrapper.write(Protocol1_19_1To1_19_3.SIGNATURE_BYTES_TYPE, signature);
                  }
               } else {
                  wrapper.write(Type.VAR_INT, 0);
               }

               int offset = 0;
               BitSet acknowledged = new BitSet(20);
               wrapper.write(Type.VAR_INT, 0);
               wrapper.write(new BitSetType(20), acknowledged);
            });
            this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
            this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
         }
      });
      this.registerClientbound(
         ClientboundPackets1_19_3.PLAYER_CHAT,
         ClientboundPackets1_19_1.SYSTEM_CHAT,
         new PacketHandlers() {
            @Override
            public void register() {
               this.read(Type.UUID);
               this.read(Type.VAR_INT);
               this.read(Protocol1_19_1To1_19_3.OPTIONAL_SIGNATURE_BYTES_TYPE);
               this.handler(
                  wrapper -> {
                     String plainContent = wrapper.read(Type.STRING);
                     wrapper.read(Type.LONG);
                     wrapper.read(Type.LONG);
                     int lastSeen = wrapper.read(Type.VAR_INT);

                     for (int i = 0; i < lastSeen; i++) {
                        int index = wrapper.read(Type.VAR_INT);
                        if (index == 0) {
                           wrapper.read(Protocol1_19_1To1_19_3.SIGNATURE_BYTES_TYPE);
                        }
                     }

                     JsonElement unsignedContent = wrapper.read(Type.OPTIONAL_COMPONENT);
                     JsonElement content = (JsonElement)(unsignedContent != null ? unsignedContent : ComponentUtil.plainToJson(plainContent));
                     Protocol1_19_1To1_19_3.this.translatableRewriter.processText(content);
                     int filterMaskType = wrapper.read(Type.VAR_INT);
                     if (filterMaskType == 2) {
                        wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                     }

                     int chatTypeId = wrapper.read(Type.VAR_INT);
                     JsonElement senderName = wrapper.read(Type.COMPONENT);
                     JsonElement targetName = wrapper.read(Type.OPTIONAL_COMPONENT);
                     JsonElement result = Protocol1_19To1_19_1.decorateChatMessage(
                        wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content
                     );
                     if (result == null) {
                        wrapper.cancel();
                     } else {
                        wrapper.write(Type.COMPONENT, result);
                        wrapper.write(Type.BOOLEAN, false);
                     }
                  }
               );
            }
         }
      );
      this.registerClientbound(
         ClientboundPackets1_19_3.DISGUISED_CHAT,
         ClientboundPackets1_19_1.SYSTEM_CHAT,
         wrapper -> {
            JsonElement content = wrapper.read(Type.COMPONENT);
            this.translatableRewriter.processText(content);
            int chatTypeId = wrapper.read(Type.VAR_INT);
            JsonElement senderName = wrapper.read(Type.COMPONENT);
            JsonElement targetName = wrapper.read(Type.OPTIONAL_COMPONENT);
            JsonElement result = Protocol1_19To1_19_1.decorateChatMessage(
               wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content
            );
            if (result == null) {
               wrapper.cancel();
            } else {
               wrapper.write(Type.COMPONENT, result);
               wrapper.write(Type.BOOLEAN, false);
            }
         }
      );
      this.cancelClientbound(ClientboundPackets1_19_3.UPDATE_ENABLED_FEATURES);
      this.cancelServerbound(ServerboundPackets1_19_1.CHAT_PREVIEW);
      this.cancelServerbound(ServerboundPackets1_19_1.CHAT_ACK);
   }

   @Override
   public void init(UserConnection user) {
      user.put(new ChatSessionStorage());
      user.put(new ChatTypeStorage1_19_3());
      this.addEntityTracker(user, new EntityTrackerBase(user, EntityTypes1_19_3.PLAYER));
   }

   public BackwardsMappings getMappingData() {
      return MAPPINGS;
   }

   @Override
   public TranslatableRewriter<ClientboundPackets1_19_3> getTranslatableRewriter() {
      return this.translatableRewriter;
   }

   public BlockItemPackets1_19_3 getItemRewriter() {
      return this.itemRewriter;
   }

   public EntityPackets1_19_3 getEntityRewriter() {
      return this.entityRewriter;
   }
}
