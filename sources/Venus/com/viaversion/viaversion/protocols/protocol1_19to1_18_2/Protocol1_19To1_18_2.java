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
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Protocol1_19To1_18_2
extends AbstractProtocol<ClientboundPackets1_18, ClientboundPackets1_19, ServerboundPackets1_17, ServerboundPackets1_19> {
    public static final MappingData MAPPINGS = new MappingData();
    private final EntityPackets entityRewriter = new EntityPackets(this);
    private final InventoryPackets itemRewriter = new InventoryPackets(this);

    public Protocol1_19To1_18_2() {
        super(ClientboundPackets1_18.class, ClientboundPackets1_19.class, ServerboundPackets1_17.class, ServerboundPackets1_19.class);
    }

    public static boolean isTextComponentNull(JsonElement jsonElement) {
        return jsonElement == null || jsonElement.isJsonNull() || jsonElement.isJsonArray() && jsonElement.getAsJsonArray().size() == 0;
    }

    public static JsonElement mapTextComponentIfNull(JsonElement jsonElement) {
        if (!Protocol1_19To1_18_2.isTextComponentNull(jsonElement)) {
            return jsonElement;
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
        SoundRewriter<ClientboundPackets1_18> soundRewriter = new SoundRewriter<ClientboundPackets1_18>(this);
        this.registerClientbound(ClientboundPackets1_18.SOUND, new PacketHandlers(this, soundRewriter){
            final SoundRewriter val$soundRewriter;
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
                this.val$soundRewriter = soundRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(1::lambda$register$0);
                this.handler(this.val$soundRewriter.getSoundHandler());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.LONG, Protocol1_19To1_18_2.access$000());
            }
        });
        this.registerClientbound(ClientboundPackets1_18.ENTITY_SOUND, new PacketHandlers(this, soundRewriter){
            final SoundRewriter val$soundRewriter;
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
                this.val$soundRewriter = soundRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(2::lambda$register$0);
                this.handler(this.val$soundRewriter.getSoundHandler());
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.LONG, Protocol1_19To1_18_2.access$000());
            }
        });
        this.registerClientbound(ClientboundPackets1_18.NAMED_SOUND, new PacketHandlers(this){
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.VAR_INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.LONG, Protocol1_19To1_18_2.access$000());
            }
        });
        new StatisticsRewriter<ClientboundPackets1_18>(this).register(ClientboundPackets1_18.STATISTICS);
        PacketHandler packetHandler = Protocol1_19To1_18_2::lambda$registerPackets$0;
        this.registerClientbound(ClientboundPackets1_18.TITLE_TEXT, packetHandler);
        this.registerClientbound(ClientboundPackets1_18.TITLE_SUBTITLE, packetHandler);
        this.registerClientbound(ClientboundPackets1_18.ACTIONBAR, packetHandler);
        this.registerClientbound(ClientboundPackets1_18.SCOREBOARD_OBJECTIVE, Protocol1_19To1_18_2::lambda$registerPackets$1);
        this.registerClientbound(ClientboundPackets1_18.TEAMS, Protocol1_19To1_18_2::lambda$registerPackets$2);
        CommandRewriter<ClientboundPackets1_18> commandRewriter = new CommandRewriter<ClientboundPackets1_18>(this);
        this.registerClientbound(ClientboundPackets1_18.DECLARE_COMMANDS, arg_0 -> Protocol1_19To1_18_2.lambda$registerPackets$3(commandRewriter, arg_0));
        this.registerClientbound(ClientboundPackets1_18.CHAT_MESSAGE, ClientboundPackets1_19.SYSTEM_CHAT, new PacketHandlers(this){
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
            }

            @Override
            public void register() {
                this.map(Type.COMPONENT);
                this.handler(4::lambda$register$0);
                this.read(Type.UUID);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte by = packetWrapper.read(Type.BYTE);
                packetWrapper.write(Type.VAR_INT, Integer.valueOf(by == 0 ? (byte)1 : by));
            }
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.read(Type.LONG);
                this.read(Type.LONG);
                this.read(Type.BYTE_ARRAY_PRIMITIVE);
                this.read(Type.BOOLEAN);
            }
        });
        this.registerServerbound(ServerboundPackets1_19.CHAT_COMMAND, ServerboundPackets1_17.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.read(Type.LONG);
                this.read(Type.LONG);
                this.handler(6::lambda$register$0);
                this.read(Type.BOOLEAN);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                packetWrapper.set(Type.STRING, 0, "/" + string);
                int n = packetWrapper.read(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    packetWrapper.read(Type.STRING);
                    packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                }
            }
        });
        this.cancelServerbound(ServerboundPackets1_19.CHAT_PREVIEW);
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), new PacketHandlers(this){
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
            }

            @Override
            public void register() {
                this.map(Type.UUID);
                this.map(Type.STRING);
                this.create(Type.VAR_INT, 0);
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(8::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                byte[] byArray = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                byte[] byArray2 = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                packetWrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(byArray, byArray2)));
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.read(Type.OPTIONAL_PROFILE_KEY);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(this){
            final Protocol1_19To1_18_2 this$0;
            {
                this.this$0 = protocol1_19To1_18_2;
            }

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.handler(10::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                    packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                } else {
                    NonceStorage nonceStorage = packetWrapper.user().remove(NonceStorage.class);
                    if (nonceStorage == null) {
                        throw new IllegalArgumentException("Server sent nonce is missing");
                    }
                    packetWrapper.read(Type.LONG);
                    packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce());
                }
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
    public void register(ViaProviders viaProviders) {
        viaProviders.register(AckSequenceProvider.class, new AckSequenceProvider());
    }

    @Override
    public void init(UserConnection userConnection) {
        if (!userConnection.has(DimensionRegistryStorage.class)) {
            userConnection.put(new DimensionRegistryStorage());
        }
        userConnection.put(new SequenceStorage());
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_19Types.PLAYER));
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

    @Override
    public ItemRewriter getItemRewriter() {
        return this.getItemRewriter();
    }

    @Override
    public EntityRewriter getEntityRewriter() {
        return this.getEntityRewriter();
    }

    @Override
    public com.viaversion.viaversion.api.data.MappingData getMappingData() {
        return this.getMappingData();
    }

    private static void lambda$registerPackets$3(CommandRewriter commandRewriter, PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            int n2;
            byte by = packetWrapper.passthrough(Type.BYTE);
            packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            if ((by & 8) != 0) {
                packetWrapper.passthrough(Type.VAR_INT);
            }
            if ((n2 = by & 3) == 1 || n2 == 2) {
                packetWrapper.passthrough(Type.STRING);
            }
            if (n2 != 2) continue;
            String string = packetWrapper.read(Type.STRING);
            int n3 = MAPPINGS.getArgumentTypeMappings().mappedId(string);
            if (n3 == -1) {
                Via.getPlatform().getLogger().warning("Unknown command argument type: " + string);
            }
            packetWrapper.write(Type.VAR_INT, n3);
            commandRewriter.handleArgument(packetWrapper, string);
            if ((by & 0x10) == 0) continue;
            packetWrapper.passthrough(Type.STRING);
        }
        packetWrapper.passthrough(Type.VAR_INT);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        byte by = packetWrapper.passthrough(Type.BYTE);
        if (by == 0 || by == 2) {
            packetWrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(packetWrapper.read(Type.COMPONENT)));
            packetWrapper.passthrough(Type.BYTE);
            packetWrapper.passthrough(Type.STRING);
            packetWrapper.passthrough(Type.STRING);
            packetWrapper.passthrough(Type.VAR_INT);
            packetWrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(packetWrapper.read(Type.COMPONENT)));
            packetWrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(packetWrapper.read(Type.COMPONENT)));
        }
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.STRING);
        byte by = packetWrapper.passthrough(Type.BYTE);
        if (by == 0 || by == 2) {
            packetWrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(packetWrapper.read(Type.COMPONENT)));
        }
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.write(Type.COMPONENT, Protocol1_19To1_18_2.mapTextComponentIfNull(packetWrapper.read(Type.COMPONENT)));
    }

    static long access$000() {
        return Protocol1_19To1_18_2.randomLong();
    }
}

