/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.minecraft.ParticleType;
import com.viaversion.viaversion.api.type.types.version.Types1_19;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ServerboundLoginPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ClientboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.data.MappingData;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.DimensionRegistryStorage;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.NonceStorage;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.SequenceStorage;
import com.viaversion.viaversion.rewriter.CommandRewriter;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.CipherUtil;
import java.util.concurrent.ThreadLocalRandom;

public final class Protocol1_19To1_18_2
extends AbstractProtocol<ClientboundPackets1_18, ClientboundPackets1_19, ServerboundPackets1_17, ServerboundPackets1_19> {
    public static final MappingData MAPPINGS = new MappingData();
    private final EntityPackets entityRewriter = new EntityPackets(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);

    public Protocol1_19To1_18_2() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_19.class, ServerboundPackets1_17.class, ServerboundPackets1_19.class);
    }

    public static boolean isTextComponentNull(JsonElement element) {
        return element == null || element.isJsonNull() || element.isJsonArray() && element.getAsJsonArray().size() == 0;
    }

    public static JsonElement mapTextComponentIfNull(JsonElement component) {
        if (!Protocol1_19To1_18_2.isTextComponentNull(component)) {
            return component;
        }
        return ChatRewriter.emptyComponent();
    }

    @Override
    protected void registerPackets() {
        TagRewriter<ClientboundPackets1_18> tagRewriter = new TagRewriter<ClientboundPackets1_18>(this);
        tagRewriter.registerGeneric(ClientboundPackets1_18.TAGS);
        this.entityRewriter.register();
        this.itemRewriter.register();
        WorldPackets.register(this);
        this.cancelClientbound(ClientboundPackets1_18.ADD_VIBRATION_SIGNAL);
        final SoundRewriter<ClientboundPackets1_18> soundRewriter = new SoundRewriter<ClientboundPackets1_18>(this);
        this.registerClientbound(ClientboundPackets1_18.SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(wrapper -> wrapper.write(Type.LONG, Protocol1_19To1_18_2.randomLong()));
                this.handler(soundRewriter.getSoundHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_18.ENTITY_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(wrapper -> wrapper.write(Type.LONG, Protocol1_19To1_18_2.randomLong()));
                this.handler(soundRewriter.getSoundHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_18.NAMED_SOUND, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(wrapper -> wrapper.write(Type.LONG, Protocol1_19To1_18_2.randomLong()));
            }
        });
        new StatisticsRewriter<ClientboundPackets1_18>(this).register(ClientboundPackets1_18.STATISTICS);
        PacketHandler singleNullTextComponentMapper = wrapper -> wrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(wrapper.read(Type.COMPONENT)));
        this.registerClientbound(ClientboundPackets1_18.TITLE_TEXT, singleNullTextComponentMapper);
        this.registerClientbound(ClientboundPackets1_18.TITLE_SUBTITLE, singleNullTextComponentMapper);
        this.registerClientbound(ClientboundPackets1_18.ACTIONBAR, singleNullTextComponentMapper);
        this.registerClientbound(ClientboundPackets1_18.SCOREBOARD_OBJECTIVE, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.STRING);
            byte action = wrapper.passthrough(Type.BYTE);
            if (action == 0 || action == 2) {
                wrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(wrapper.read(Type.COMPONENT)));
            }
        });
        this.registerClientbound(ClientboundPackets1_18.TEAMS, (PacketWrapper wrapper) -> {
            wrapper.passthrough(Type.STRING);
            byte action = wrapper.passthrough(Type.BYTE);
            if (action == 0 || action == 2) {
                wrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(wrapper.read(Type.COMPONENT)));
                wrapper.passthrough(Type.BYTE);
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.VAR_INT);
                wrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(wrapper.read(Type.COMPONENT)));
                wrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(wrapper.read(Type.COMPONENT)));
            }
        });
        CommandRewriter<ClientboundPackets1_18> commandRewriter = new CommandRewriter<ClientboundPackets1_18>(this);
        this.registerClientbound(ClientboundPackets1_18.DECLARE_COMMANDS, (PacketWrapper wrapper) -> {
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
                String argumentType = wrapper.read(Type.STRING);
                int argumentTypeId = MAPPINGS.getArgumentTypeMappings().mappedId(argumentType);
                if (argumentTypeId == -1) {
                    Via.getPlatform().getLogger().warning("Unknown command argument type: " + argumentType);
                }
                wrapper.write(Type.VAR_INT, argumentTypeId);
                commandRewriter.handleArgument(wrapper, argumentType);
                if ((flags & 0x10) == 0) continue;
                wrapper.passthrough(Type.STRING);
            }
            wrapper.passthrough(Type.VAR_INT);
        });
        this.registerClientbound(ClientboundPackets1_18.CHAT_MESSAGE, ClientboundPackets1_19.SYSTEM_CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.COMPONENT);
                this.handler(wrapper -> {
                    byte type2 = wrapper.read(Type.BYTE);
                    wrapper.write(Type.VAR_INT, Integer.valueOf(type2 == 0 ? (byte)1 : type2));
                });
                this.read(Type.UUID);
            }
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.read(Type.LONG);
                this.read(Type.LONG);
                this.read(Type.BYTE_ARRAY_PRIMITIVE);
                this.read(Type.BOOLEAN);
            }
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT_COMMAND, ServerboundPackets1_17.CHAT_MESSAGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.read(Type.LONG);
                this.read(Type.LONG);
                this.handler(wrapper -> {
                    String command = wrapper.get(Type.STRING, 0);
                    wrapper.set(Type.STRING, 0, "/" + command);
                    int signatures = wrapper.read(Type.VAR_INT);
                    for (int i = 0; i < signatures; ++i) {
                        wrapper.read(Type.STRING);
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    }
                });
                this.read(Type.BOOLEAN);
            }
        });
        this.cancelServerbound(ServerboundPackets1_19.CHAT_PREVIEW);
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UUID);
                this.map(Type.STRING);
                this.create(Type.VAR_INT, 0);
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(wrapper -> {
                    byte[] publicKey = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    byte[] nonce = wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    wrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(publicKey, nonce)));
                });
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.read(Type.OPTIONAL_PROFILE_KEY);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.handler(wrapper -> {
                    if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                        wrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                    } else {
                        NonceStorage nonceStorage = wrapper.user().remove(NonceStorage.class);
                        if (nonceStorage == null) {
                            throw new IllegalArgumentException("Server sent nonce is missing");
                        }
                        wrapper.read(Type.LONG);
                        wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
                    }
                });
            }
        });
    }

    private static long randomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    @Override
    protected void onMappingDataLoaded() {
        super.onMappingDataLoaded();
        Types1_19.PARTICLE.filler(this).reader("block", ParticleType.Readers.BLOCK).reader("block_marker", ParticleType.Readers.BLOCK).reader("dust", ParticleType.Readers.DUST).reader("falling_dust", ParticleType.Readers.BLOCK).reader("dust_color_transition", ParticleType.Readers.DUST_TRANSITION).reader("item", ParticleType.Readers.VAR_INT_ITEM).reader("vibration", ParticleType.Readers.VIBRATION1_19).reader("sculk_charge", ParticleType.Readers.SCULK_CHARGE).reader("shriek", ParticleType.Readers.SHRIEK);
        Entity1_19Types.initialize(this);
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(AckSequenceProvider.class, new AckSequenceProvider());
    }

    @Override
    public void init(UserConnection user) {
        if (!user.has(DimensionRegistryStorage.class)) {
            user.put(new DimensionRegistryStorage());
        }
        user.put(new SequenceStorage());
        this.addEntityTracker(user, new EntityTrackerBase(user, Entity1_19Types.PLAYER));
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

