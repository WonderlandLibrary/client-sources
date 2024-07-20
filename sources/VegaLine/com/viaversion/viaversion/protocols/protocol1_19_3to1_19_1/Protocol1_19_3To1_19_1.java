/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.api.type.types.ByteArrayType;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_19_3;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ClientboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_1to1_19.ServerboundPackets1_19_1;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ClientboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.storage.ReceivedMessagesStorage;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.util.UUID;

public final class Protocol1_19_3To1_19_1
extends AbstractProtocol<ClientboundPackets1_19_1, ClientboundPackets1_19_3, ServerboundPackets1_19_1, ServerboundPackets1_19_3> {
    public static final MappingData MAPPINGS = new MappingDataBase("1.19", "1.19.3");
    private static final ByteArrayType.OptionalByteArrayType OPTIONAL_MESSAGE_SIGNATURE_BYTES_TYPE = new ByteArrayType.OptionalByteArrayType(256);
    private static final ByteArrayType MESSAGE_SIGNATURE_BYTES_TYPE = new ByteArrayType(256);
    private static final BitSetType ACKNOWLEDGED_BIT_SET_TYPE = new BitSetType(20);
    private static final UUID ZERO_UUID = new UUID(0L, 0L);
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final EntityPackets entityRewriter = new EntityPackets(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);

    public Protocol1_19_3To1_19_1() {
        super(ClientboundPackets1_19_1.class, ClientboundPackets1_19_3.class, ServerboundPackets1_19_1.class, ServerboundPackets1_19_3.class);
    }

    @Override
    protected void registerPackets() {
        TagRewriter<ClientboundPackets1_19_1> tagRewriter = new TagRewriter<ClientboundPackets1_19_1>(this);
        tagRewriter.addTagRaw(RegistryType.ITEM, "minecraft:creeper_igniters", 733);
        tagRewriter.addEmptyTags(RegistryType.ITEM, "minecraft:bookshelf_books", "minecraft:hanging_signs", "minecraft:stripped_logs");
        tagRewriter.addEmptyTags(RegistryType.BLOCK, "minecraft:all_hanging_signs", "minecraft:ceiling_hanging_signs", "minecraft:invalid_spawn_inside", "minecraft:stripped_logs", "minecraft:wall_hanging_signs");
        tagRewriter.registerGeneric(ClientboundPackets1_19_1.TAGS);
        this.entityRewriter.register();
        this.itemRewriter.register();
        final SoundRewriter<ClientboundPackets1_19_1> soundRewriter = new SoundRewriter<ClientboundPackets1_19_1>(this);
        this.registerClientbound(ClientboundPackets1_19_1.ENTITY_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(soundRewriter.getSoundHandler());
                this.handler(wrapper -> {
                    int soundId = wrapper.get(Type.VAR_INT, 0);
                    wrapper.set(Type.VAR_INT, 0, soundId + 1);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_19_1.SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(soundRewriter.getSoundHandler());
                this.handler(wrapper -> {
                    int soundId = wrapper.get(Type.VAR_INT, 0);
                    wrapper.set(Type.VAR_INT, 0, soundId + 1);
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_19_1.NAMED_SOUND, ClientboundPackets1_19_3.SOUND, (PacketWrapper wrapper) -> {
            wrapper.write(Type.VAR_INT, 0);
            wrapper.passthrough(Type.STRING);
            wrapper.write(Type.OPTIONAL_FLOAT, null);
        });
        new StatisticsRewriter<ClientboundPackets1_19_1>(this).register(ClientboundPackets1_19_1.STATISTICS);
        CommandRewriter<ClientboundPackets1_19_1> commandRewriter = new CommandRewriter<ClientboundPackets1_19_1>((Protocol)this){

            @Override
            public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
                switch (argumentType) {
                    case "minecraft:item_enchantment": {
                        wrapper.write(Type.STRING, "minecraft:enchantment");
                        break;
                    }
                    case "minecraft:mob_effect": {
                        wrapper.write(Type.STRING, "minecraft:mob_effect");
                        break;
                    }
                    case "minecraft:entity_summon": {
                        wrapper.write(Type.STRING, "minecraft:entity_type");
                        break;
                    }
                    default: {
                        super.handleArgument(wrapper, argumentType);
                    }
                }
            }

            @Override
            public String handleArgumentType(String argumentType) {
                switch (argumentType) {
                    case "minecraft:resource": {
                        return "minecraft:resource_key";
                    }
                    case "minecraft:resource_or_tag": {
                        return "minecraft:resource_or_tag_key";
                    }
                    case "minecraft:entity_summon": 
                    case "minecraft:item_enchantment": 
                    case "minecraft:mob_effect": {
                        return "minecraft:resource";
                    }
                }
                return argumentType;
            }
        };
        commandRewriter.registerDeclareCommands1_19(ClientboundPackets1_19_1.DECLARE_COMMANDS);
        this.registerClientbound(ClientboundPackets1_19_1.SERVER_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.OPTIONAL_COMPONENT);
                this.map(Type.OPTIONAL_STRING);
                this.read(Type.BOOLEAN);
            }
        });
        this.registerClientbound(ClientboundPackets1_19_1.PLAYER_CHAT, ClientboundPackets1_19_3.DISGUISED_CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.read(Type.OPTIONAL_BYTE_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    int filterMaskType;
                    PlayerMessageSignature signature = wrapper.read(Type.PLAYER_MESSAGE_SIGNATURE);
                    if (!signature.uuid().equals(ZERO_UUID) && signature.signatureBytes().length != 0) {
                        ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                        messagesStorage.add(signature);
                        if (messagesStorage.tickUnacknowledged() > 64) {
                            messagesStorage.resetUnacknowledgedCount();
                            PacketWrapper chatAckPacket = wrapper.create(ServerboundPackets1_19_1.CHAT_ACK);
                            chatAckPacket.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                            chatAckPacket.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                            chatAckPacket.sendToServer(Protocol1_19_3To1_19_1.class);
                        }
                    }
                    String plainMessage = wrapper.read(Type.STRING);
                    JsonElement decoratedMessage = wrapper.read(Type.OPTIONAL_COMPONENT);
                    wrapper.read(Type.LONG);
                    wrapper.read(Type.LONG);
                    wrapper.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
                    JsonElement unsignedMessage = wrapper.read(Type.OPTIONAL_COMPONENT);
                    if (unsignedMessage != null) {
                        decoratedMessage = unsignedMessage;
                    }
                    if (decoratedMessage == null) {
                        decoratedMessage = GsonComponentSerializer.gson().serializeToTree(Component.text(plainMessage));
                    }
                    if ((filterMaskType = wrapper.read(Type.VAR_INT).intValue()) == 2) {
                        wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                    }
                    wrapper.write(Type.COMPONENT, decoratedMessage);
                });
            }
        });
        this.registerServerbound(ServerboundPackets1_19_3.CHAT_COMMAND, new PacketHandlers(){

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
                        wrapper.read(MESSAGE_SIGNATURE_BYTES_TYPE);
                    }
                    wrapper.write(Type.BOOLEAN, false);
                    ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                    messagesStorage.resetUnacknowledgedCount();
                    wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                    wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                });
                this.read(Type.VAR_INT);
                this.read(ACKNOWLEDGED_BIT_SET_TYPE);
            }
        });
        this.registerServerbound(ServerboundPackets1_19_3.CHAT_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.read(Type.LONG);
                this.create(Type.LONG, 0L);
                this.handler(wrapper -> {
                    wrapper.read(OPTIONAL_MESSAGE_SIGNATURE_BYTES_TYPE);
                    wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, EMPTY_BYTES);
                    wrapper.write(Type.BOOLEAN, false);
                    ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
                    messagesStorage.resetUnacknowledgedCount();
                    wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
                    wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
                });
                this.read(Type.VAR_INT);
                this.read(ACKNOWLEDGED_BIT_SET_TYPE);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.create(Type.OPTIONAL_PROFILE_KEY, null);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.create(Type.BOOLEAN, true);
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
            }
        });
        this.cancelServerbound(ServerboundPackets1_19_3.CHAT_SESSION_UPDATE);
        this.cancelClientbound(ClientboundPackets1_19_1.DELETE_CHAT_MESSAGE);
        this.cancelClientbound(ClientboundPackets1_19_1.PLAYER_CHAT_HEADER);
        this.cancelClientbound(ClientboundPackets1_19_1.CHAT_PREVIEW);
        this.cancelClientbound(ClientboundPackets1_19_1.SET_DISPLAY_CHAT_PREVIEW);
        this.cancelServerbound(ServerboundPackets1_19_3.CHAT_ACK);
    }

    @Override
    protected void onMappingDataLoaded() {
        super.onMappingDataLoaded();
        Types1_19_3.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.VAR_INT_ITEM).reader("vibration", ParticleType.Readers.VIBRATION1_19).reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE).reader("shriek", ParticleType.Readers.SHRIEK);
        Entity1_19_3Types.initialize(this);
    }

    @Override
    public void init(UserConnection user) {
        user.put(new ReceivedMessagesStorage());
        this.addEntityTracker(user, new EntityTrackerBase(user, Entity1_19_3Types.PLAYER));
    }

    @Override
    public MappingData getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets getEntityRewriter() {
        return this.entityRewriter;
    }

    public InventoryPackets getItemRewriter() {
        return this.itemRewriter;
    }
}

