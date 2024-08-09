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
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.ProfileKey;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19_3Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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
        this.registerClientbound(ClientboundPackets1_19_3.SOUND, Protocol1_19_1To1_19_3::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_19_3.ENTITY_SOUND, Protocol1_19_1To1_19_3::lambda$registerPackets$1);
        TagRewriter<ClientboundPackets1_19_3> tagRewriter = new TagRewriter<ClientboundPackets1_19_3>(this);
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:non_flammable_wood");
        tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:overworld_natural_logs");
        tagRewriter.registerGeneric(ClientboundPackets1_19_3.TAGS);
        new StatisticsRewriter<ClientboundPackets1_19_3>(this).register(ClientboundPackets1_19_3.STATISTICS);
        CommandRewriter<ClientboundPackets1_19_3> commandRewriter = new CommandRewriter<ClientboundPackets1_19_3>(this);
        this.registerClientbound(ClientboundPackets1_19_3.DECLARE_COMMANDS, arg_0 -> Protocol1_19_1To1_19_3.lambda$registerPackets$2(commandRewriter, arg_0));
        this.registerClientbound(ClientboundPackets1_19_3.SERVER_DATA, new PacketHandlers(this){
            final Protocol1_19_1To1_19_3 this$0;
            {
                this.this$0 = protocol1_19_1To1_19_3;
            }

            @Override
            public void register() {
                this.map(Type.OPTIONAL_COMPONENT);
                this.map(Type.OPTIONAL_STRING);
                this.create(Type.BOOLEAN, false);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_19_1To1_19_3 this$0;
            {
                this.this$0 = protocol1_19_1To1_19_3;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(2::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ProfileKey profileKey = packetWrapper.read(Type.OPTIONAL_PROFILE_KEY);
                if (profileKey == null) {
                    packetWrapper.user().put(new NonceStorage(null));
                }
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.HELLO.getId(), ClientboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_19_1To1_19_3 this$0;
            {
                this.this$0 = protocol1_19_1To1_19_3;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(3::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (packetWrapper.user().has(NonceStorage.class)) {
                    return;
                }
                byte[] byArray = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                byte[] byArray2 = packetWrapper.passthrough(Type.BYTE_ARRAY_PRIMITIVE);
                packetWrapper.user().put(new NonceStorage(CipherUtil.encryptNonce(byArray, byArray2)));
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(this){
            final Protocol1_19_1To1_19_3 this$0;
            {
                this.this$0 = protocol1_19_1To1_19_3;
            }

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.handler(4::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                NonceStorage nonceStorage = packetWrapper.user().remove(NonceStorage.class);
                boolean bl = packetWrapper.read(Type.BOOLEAN);
                if (!bl) {
                    packetWrapper.read(Type.LONG);
                    packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, nonceStorage.nonce() != null ? nonceStorage.nonce() : new byte[]{});
                }
            }
        });
        this.registerServerbound(ServerboundPackets1_19_1.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_19_1To1_19_3 this$0;
            {
                this.this$0 = protocol1_19_1To1_19_3;
            }

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
                this.handler(5::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                boolean bl = false;
                BitSet bitSet = new BitSet(20);
                packetWrapper.write(Type.VAR_INT, 0);
                packetWrapper.write(new BitSetType(20), bitSet);
            }
        });
        this.registerServerbound(ServerboundPackets1_19_1.CHAT_COMMAND, new PacketHandlers(this){
            final Protocol1_19_1To1_19_3 this$0;
            {
                this.this$0 = protocol1_19_1To1_19_3;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.LONG);
                this.map(Type.LONG);
                this.handler(6::lambda$register$0);
                this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
                this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = packetWrapper.read(Type.VAR_INT);
                packetWrapper.write(Type.VAR_INT, 0);
                for (n = 0; n < n2; ++n) {
                    packetWrapper.read(Type.STRING);
                    packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                }
                packetWrapper.read(Type.BOOLEAN);
                n = 0;
                BitSet bitSet = new BitSet(20);
                packetWrapper.write(Type.VAR_INT, 0);
                packetWrapper.write(new BitSetType(20), bitSet);
            }
        });
        this.registerClientbound(ClientboundPackets1_19_3.PLAYER_CHAT, ClientboundPackets1_19_1.SYSTEM_CHAT, new PacketHandlers(this){
            final Protocol1_19_1To1_19_3 this$0;
            {
                this.this$0 = protocol1_19_1To1_19_3;
            }

            @Override
            public void register() {
                this.read(Type.UUID);
                this.read(Type.VAR_INT);
                this.read(OPTIONAL_SIGNATURE_BYTES_TYPE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.read(Type.STRING);
                packetWrapper.read(Type.LONG);
                packetWrapper.read(Type.LONG);
                int n = packetWrapper.read(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    int n2 = packetWrapper.read(Type.VAR_INT);
                    if (n2 != 0) continue;
                    packetWrapper.read(SIGNATURE_BYTES_TYPE);
                }
                JsonElement jsonElement = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                JsonElement jsonElement2 = jsonElement != null ? jsonElement : GsonComponentSerializer.gson().serializeToTree(Component.text(string));
                Protocol1_19_1To1_19_3.access$000(this.this$0).processText(jsonElement2);
                int n3 = packetWrapper.read(Type.VAR_INT);
                if (n3 == 2) {
                    packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                }
                int n4 = packetWrapper.read(Type.VAR_INT);
                JsonElement jsonElement3 = packetWrapper.read(Type.COMPONENT);
                JsonElement jsonElement4 = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                JsonElement jsonElement5 = Protocol1_19To1_19_1.decorateChatMessage(packetWrapper.user().get(ChatTypeStorage1_19_3.class), n4, jsonElement3, jsonElement4, jsonElement2);
                if (jsonElement5 == null) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.write(Type.COMPONENT, jsonElement5);
                packetWrapper.write(Type.BOOLEAN, false);
            }
        });
        this.registerClientbound(ClientboundPackets1_19_3.DISGUISED_CHAT, ClientboundPackets1_19_1.SYSTEM_CHAT, this::lambda$registerPackets$3);
        this.cancelClientbound(ClientboundPackets1_19_3.UPDATE_ENABLED_FEATURES);
        this.cancelServerbound(ServerboundPackets1_19_1.CHAT_PREVIEW);
        this.cancelServerbound(ServerboundPackets1_19_1.CHAT_ACK);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new ChatSessionStorage());
        userConnection.put(new ChatTypeStorage1_19_3());
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_19_3Types.PLAYER));
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

    @Override
    public com.viaversion.viabackwards.api.data.BackwardsMappings getMappingData() {
        return this.getMappingData();
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
    public MappingData getMappingData() {
        return this.getMappingData();
    }

    private void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
        this.translatableRewriter.processText(jsonElement);
        int n = packetWrapper.read(Type.VAR_INT);
        JsonElement jsonElement2 = packetWrapper.read(Type.COMPONENT);
        JsonElement jsonElement3 = packetWrapper.read(Type.OPTIONAL_COMPONENT);
        JsonElement jsonElement4 = Protocol1_19To1_19_1.decorateChatMessage(packetWrapper.user().get(ChatTypeStorage1_19_3.class), n, jsonElement2, jsonElement3, jsonElement);
        if (jsonElement4 == null) {
            packetWrapper.cancel();
            return;
        }
        packetWrapper.write(Type.COMPONENT, jsonElement4);
        packetWrapper.write(Type.BOOLEAN, false);
    }

    private static void lambda$registerPackets$2(CommandRewriter commandRewriter, PacketWrapper packetWrapper) throws Exception {
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
            int n3 = packetWrapper.read(Type.VAR_INT);
            int n4 = MAPPINGS.getArgumentTypeMappings().mappings().getNewId(n3);
            Preconditions.checkArgument(n4 != -1, "Unknown command argument type id: " + n3);
            packetWrapper.write(Type.VAR_INT, n4);
            String string = MAPPINGS.getArgumentTypeMappings().identifier(n3);
            commandRewriter.handleArgument(packetWrapper, string);
            if (string.equals("minecraft:gamemode")) {
                packetWrapper.write(Type.VAR_INT, 0);
            }
            if ((by & 0x10) == 0) continue;
            packetWrapper.passthrough(Type.STRING);
        }
        packetWrapper.passthrough(Type.VAR_INT);
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = packetWrapper.read(Type.VAR_INT) - 1;
        if (n2 != -1) {
            int n3 = MAPPINGS.getSoundMappings().getNewId(n2);
            if (n3 == -1) {
                packetWrapper.cancel();
                return;
            }
            packetWrapper.write(Type.VAR_INT, n3);
            return;
        }
        String string = packetWrapper.read(Type.STRING);
        packetWrapper.read(Type.OPTIONAL_FLOAT);
        String string2 = MAPPINGS.getMappedNamedSound(string);
        if (string2 != null) {
            if (string2.isEmpty()) {
                packetWrapper.cancel();
                return;
            }
            string = string2;
        }
        if ((n = MAPPINGS.mappedSound(string)) == -1) {
            packetWrapper.cancel();
            return;
        }
        packetWrapper.write(Type.VAR_INT, n);
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT) - 1;
        if (n != -1) {
            int n2 = MAPPINGS.getSoundMappings().getNewId(n);
            if (n2 == -1) {
                packetWrapper.cancel();
                return;
            }
            packetWrapper.write(Type.VAR_INT, n2);
            return;
        }
        String string = packetWrapper.read(Type.STRING);
        packetWrapper.read(Type.OPTIONAL_FLOAT);
        String string2 = MAPPINGS.getMappedNamedSound(string);
        if (string2 != null) {
            if (string2.isEmpty()) {
                packetWrapper.cancel();
                return;
            }
            string = string2;
        }
        packetWrapper.write(Type.STRING, string);
        packetWrapper.setPacketType(ClientboundPackets1_19_1.NAMED_SOUND);
    }

    static TranslatableRewriter access$000(Protocol1_19_1To1_19_3 protocol1_19_1To1_19_3) {
        return protocol1_19_1To1_19_3.translatableRewriter;
    }
}

