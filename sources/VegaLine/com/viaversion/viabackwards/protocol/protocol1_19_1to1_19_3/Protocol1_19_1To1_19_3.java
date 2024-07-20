/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
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
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.ByteArrayType;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.CipherUtil;
import java.util.BitSet;

public final class Protocol1_19_1To1_19_3
extends BackwardsProtocol<ClientboundPackets1_19_3, ClientboundPackets1_19_1, ServerboundPackets1_19_3, ServerboundPackets1_19_1> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    public static final ByteArrayType.OptionalByteArrayType OPTIONAL_SIGNATURE_BYTES_TYPE = new ByteArrayType.OptionalByteArrayType(256);
    public static final ByteArrayType SIGNATURE_BYTES_TYPE = new ByteArrayType(256);
    private final EntityPackets1_19_3 entityRewriter = new EntityPackets1_19_3(this);
    private final BlockItemPackets1_19_3 itemRewriter = new BlockItemPackets1_19_3(this);
    private final TranslatableRewriter<ClientboundPackets1_19_3> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_19_3>(this);

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
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_19_3.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_19_3.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_19_3.OPEN_WINDOW);
        this.translatableRewriter.registerCombatKill(ClientboundPackets1_19_3.COMBAT_KILL);
        this.translatableRewriter.registerPing();
        SoundRewriter<ClientboundPackets1_19_3> soundRewriter = new SoundRewriter<ClientboundPackets1_19_3>(this);
        soundRewriter.registerStopSound(ClientboundPackets1_19_3.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_19_3.SOUND, (PacketWrapper wrapper) -> {
            int soundId = wrapper.read(Type.VAR_INT) - 1;
            if (soundId != -1) {
                int mappedId = MAPPINGS.getSoundMappings().getNewId(soundId);
                if (mappedId == -1) {
                    wrapper.cancel();
                    return;
                }
                wrapper.write(Type.VAR_INT, mappedId);
                return;
            }
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
        });
        this.registerClientbound(ClientboundPackets1_19_3.ENTITY_SOUND, (PacketWrapper wrapper) -> {
            int mappedId;
            int soundId = wrapper.read(Type.VAR_INT) - 1;
            if (soundId != -1) {
                int mappedId2 = MAPPINGS.getSoundMappings().getNewId(soundId);
                if (mappedId2 == -1) {
                    wrapper.cancel();
                    return;
                }
                wrapper.write(Type.VAR_INT, mappedId2);
                return;
            }
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
            if ((mappedId = MAPPINGS.mappedSound(soundIdentifier)) == -1) {
                wrapper.cancel();
                return;
            }
            wrapper.write(Type.VAR_INT, mappedId);
        });
        TagRewriter<ClientboundPackets1_19_3> tagRewriter = new TagRewriter<ClientboundPackets1_19_3>(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
        tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:overworld_natural_logs");
        tagRewriter.registerGeneric(ClientboundPackets1_19_3.TAGS);
        new StatisticsRewriter<ClientboundPackets1_19_3>(this).register(ClientboundPackets1_19_3.STATISTICS);
        CommandRewriter<ClientboundPackets1_19_3> commandRewriter = new CommandRewriter<ClientboundPackets1_19_3>(this);
        this.registerClientbound(ClientboundPackets1_19_3.DECLARE_COMMANDS, (PacketWrapper wrapper) -> {
            int size = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < size; ++i) {
                int nodeType;
                byte flags = wrapper.passthrough(Type.BYTE);
                wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                if ((flags & 8) != 0) {
                    wrapper.passthrough(Type.VAR_INT);
                }
                if ((nodeType = flags & 3) == 1 || nodeType == 2) {
                    wrapper.passthrough(Type.STRING);
                }
                if (nodeType != 2) continue;
                int argumentTypeId = wrapper.read(Type.VAR_INT);
                int mappedArgumentTypeId = MAPPINGS.getArgumentTypeMappings().mappings().getNewId(argumentTypeId);
                Preconditions.checkArgument(mappedArgumentTypeId != -1, "Unknown command argument type id: " + argumentTypeId);
                wrapper.write(Type.VAR_INT, mappedArgumentTypeId);
                String identifier = MAPPINGS.getArgumentTypeMappings().identifier(argumentTypeId);
                commandRewriter.handleArgument(wrapper, identifier);
                if (identifier.equals("minecraft:gamemode")) {
                    wrapper.write(Type.VAR_INT, 0);
                }
                if ((flags & 0x10) == 0) continue;
                wrapper.passthrough(Type.STRING);
            }
            wrapper.passthrough(Type.VAR_INT);
        });
        this.registerClientbound(ClientboundPackets1_19_3.SERVER_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.OPTIONAL_COMPONENT);
                this.map(Type.OPTIONAL_STRING);
                this.create(Type.BOOLEAN, false);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(){

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
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    if (wrapper.user().has(NonceStorage.class)) {
                        return;
                    }
                    byte[] publicKey = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    byte[] nonce = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    wrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
                });
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    NonceStorage nonceStorage = wrapper.user().remove(NonceStorage.class);
                    boolean isNonce = wrapper.read(Type.BOOLEAN);
                    if (!isNonce) {
                        wrapper.read(Type.LONG);
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce() != null ? nonceStorage.nonce() : new byte[]{});
                    }
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_19_1.CHAT_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.read(Type.BYTE_ARRAY_PRIMITIVE);
                this.create(OPTIONAL_SIGNATURE_BYTES_TYPE, null);
                this.read(Type.BOOLEAN);
                this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
                this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
                this.handler(wrapper -> {
                    boolean offset = false;
                    BitSet acknowledged = new BitSet(20);
                    wrapper.write(Type.VAR_INT, 0);
                    wrapper.write(new BitSetType(20), acknowledged);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_19_1.CHAT_COMMAND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.handler(wrapper -> {
                    int signatures = wrapper.read(Type.VAR_INT);
                    wrapper.write(Type.VAR_INT, 0);
                    for (int i = 0; i < signatures; ++i) {
                        wrapper.read(Type.STRING);
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    }
                    wrapper.read(Type.BOOLEAN);
                    boolean offset = false;
                    BitSet acknowledged = new BitSet(20);
                    wrapper.write(Type.VAR_INT, 0);
                    wrapper.write(new BitSetType(20), acknowledged);
                });
                this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
                this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
            }
        });
        this.registerClientbound(ClientboundPackets1_19_3.PLAYER_CHAT, ClientboundPackets1_19_1.SYSTEM_CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.read(Type.UUID);
                this.read(Type.VAR_INT);
                this.read(OPTIONAL_SIGNATURE_BYTES_TYPE);
                this.handler(wrapper -> {
                    String plainContent = wrapper.read(Type.STRING);
                    wrapper.read(Type.LONG);
                    wrapper.read(Type.LONG);
                    int lastSeen = wrapper.read(Type.VAR_INT);
                    for (int i = 0; i < lastSeen; ++i) {
                        int index = wrapper.read(Type.VAR_INT);
                        if (index != 0) continue;
                        wrapper.read(SIGNATURE_BYTES_TYPE);
                    }
                    JsonElement unsignedContent = wrapper.read(Type.OPTIONAL_COMPONENT);
                    JsonElement content = unsignedContent != null ? unsignedContent : GsonComponentSerializer.gson().serializeToTree(Component.text(plainContent));
                    Protocol1_19_1To1_19_3.this.translatableRewriter.processText(content);
                    int filterMaskType = wrapper.read(Type.VAR_INT);
                    if (filterMaskType == 2) {
                        wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    }
                    int chatTypeId = wrapper.read(Type.VAR_INT);
                    JsonElement senderName = wrapper.read(Type.COMPONENT);
                    JsonElement targetName = wrapper.read(Type.OPTIONAL_COMPONENT);
                    JsonElement result = Protocol1_19To1_19_1.decorateChatMessage(wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content);
                    if (result == null) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.write(Type.COMPONENT, result);
                    wrapper.write(Type.BOOLEAN, false);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_19_3.DISGUISED_CHAT, ClientboundPackets1_19_1.SYSTEM_CHAT, (PacketWrapper wrapper) -> {
            JsonElement content = wrapper.read(Type.COMPONENT);
            this.translatableRewriter.processText(content);
            int chatTypeId = wrapper.read(Type.VAR_INT);
            JsonElement senderName = wrapper.read(Type.COMPONENT);
            JsonElement targetName = wrapper.read(Type.OPTIONAL_COMPONENT);
            JsonElement result = Protocol1_19To1_19_1.decorateChatMessage(wrapper.user().get(ChatTypeStorage1_19_3.class), chatTypeId, senderName, targetName, content);
            if (result == null) {
                wrapper.cancel();
                return;
            }
            wrapper.write(Type.COMPONENT, result);
            wrapper.write(Type.BOOLEAN, false);
        });
        this.cancelClientbound(ClientboundPackets1_19_3.UPDATE_ENABLED_FEATURES);
        this.cancelServerbound(ServerboundPackets1_19_1.CHAT_PREVIEW);
        this.cancelServerbound(ServerboundPackets1_19_1.CHAT_ACK);
    }

    @Override
    public void init(UserConnection user) {
        user.put(new ChatSessionStorage());
        user.put(new ChatTypeStorage1_19_3());
        this.addEntityTracker(user, new EntityTrackerBase(user, Entity1_19_3Types.PLAYER));
    }

    @Override
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

