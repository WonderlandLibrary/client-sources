/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.data.CommandRewriter1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets.BlockItemPackets1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.packets.EntityPackets1_19;
import com.viaversion.viabackwards.protocol.protocol1_18_2to1_19.storage.DimensionRegistryStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_19Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
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
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import java.time.Instant;
import java.util.UUID;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class Protocol1_18_2To1_19
extends BackwardsProtocol<ClientboundPackets1_19, ClientboundPackets1_18, ServerboundPackets1_19, ServerboundPackets1_17> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    private static final UUID ZERO_UUID = new UUID(0L, 0L);
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final EntityPackets1_19 entityRewriter = new EntityPackets1_19(this);
    private final BlockItemPackets1_19 blockItemPackets = new BlockItemPackets1_19(this);
    private final TranslatableRewriter<ClientboundPackets1_19> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_19>(this);

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
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_19.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_19.TAB_LIST);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_19.OPEN_WINDOW);
        this.translatableRewriter.registerCombatKill(ClientboundPackets1_19.COMBAT_KILL);
        this.translatableRewriter.registerPing();
        SoundRewriter<ClientboundPackets1_19> soundRewriter = new SoundRewriter<ClientboundPackets1_19>(this);
        soundRewriter.registerStopSound(ClientboundPackets1_19.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_19.SOUND, new PacketHandlers(this, soundRewriter){
            final SoundRewriter val$soundRewriter;
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
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
                this.read(Type.LONG);
                this.handler(this.val$soundRewriter.getSoundHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_19.ENTITY_SOUND, new PacketHandlers(this, soundRewriter){
            final SoundRewriter val$soundRewriter;
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
                this.val$soundRewriter = soundRewriter;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.read(Type.LONG);
                this.handler(this.val$soundRewriter.getSoundHandler());
            }
        });
        this.registerClientbound(ClientboundPackets1_19.NAMED_SOUND, new PacketHandlers(this, soundRewriter){
            final SoundRewriter val$soundRewriter;
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
                this.val$soundRewriter = soundRewriter;
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
                this.read(Type.LONG);
                this.handler(this.val$soundRewriter.getNamedSoundHandler());
            }
        });
        TagRewriter<ClientboundPackets1_19> tagRewriter = new TagRewriter<ClientboundPackets1_19>(this);
        tagRewriter.removeTags("minecraft:banner_pattern");
        tagRewriter.removeTags("minecraft:instrument");
        tagRewriter.removeTags("minecraft:cat_variant");
        tagRewriter.removeTags("minecraft:painting_variant");
        tagRewriter.addEmptyTag(RegistryType.BLOCK, "minecraft:polar_bears_spawnable_on_in_frozen_ocean");
        tagRewriter.renameTag(RegistryType.BLOCK, "minecraft:wool_carpets", "minecraft:carpets");
        tagRewriter.renameTag(RegistryType.ITEM, "minecraft:wool_carpets", "minecraft:carpets");
        tagRewriter.addEmptyTag(RegistryType.ITEM, "minecraft:occludes_vibration_signals");
        tagRewriter.registerGeneric(ClientboundPackets1_19.TAGS);
        new StatisticsRewriter<ClientboundPackets1_19>(this).register(ClientboundPackets1_19.STATISTICS);
        CommandRewriter1_19 commandRewriter1_19 = new CommandRewriter1_19(this);
        this.registerClientbound(ClientboundPackets1_19.DECLARE_COMMANDS, arg_0 -> Protocol1_18_2To1_19.lambda$registerPackets$0(commandRewriter1_19, arg_0));
        this.cancelClientbound(ClientboundPackets1_19.SERVER_DATA);
        this.cancelClientbound(ClientboundPackets1_19.CHAT_PREVIEW);
        this.cancelClientbound(ClientboundPackets1_19.SET_DISPLAY_CHAT_PREVIEW);
        this.registerClientbound(ClientboundPackets1_19.PLAYER_CHAT, ClientboundPackets1_18.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
            }

            @Override
            public void register() {
                this.handler(this::lambda$register$0);
                this.read(Type.LONG);
                this.read(Type.LONG);
                this.read(Type.BYTE_ARRAY_PRIMITIVE);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                JsonElement jsonElement = packetWrapper.read(Type.COMPONENT);
                JsonElement jsonElement2 = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                int n = packetWrapper.read(Type.VAR_INT);
                UUID uUID = packetWrapper.read(Type.UUID);
                JsonElement jsonElement3 = packetWrapper.read(Type.COMPONENT);
                JsonElement jsonElement4 = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                CompoundTag compoundTag = packetWrapper.user().get(DimensionRegistryStorage.class).chatType(n);
                ChatDecorationResult chatDecorationResult = Protocol1_19_1To1_19.decorateChatMessage(compoundTag, n, jsonElement3, jsonElement4, jsonElement2 != null ? jsonElement2 : jsonElement);
                if (chatDecorationResult == null) {
                    packetWrapper.cancel();
                    return;
                }
                Protocol1_18_2To1_19.access$000(this.this$0).processText(chatDecorationResult.content());
                packetWrapper.write(Type.COMPONENT, chatDecorationResult.content());
                packetWrapper.write(Type.BYTE, chatDecorationResult.overlay() ? (byte)2 : 1);
                packetWrapper.write(Type.UUID, uUID);
            }
        });
        this.registerClientbound(ClientboundPackets1_19.SYSTEM_CHAT, ClientboundPackets1_18.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
            }

            @Override
            public void register() {
                this.handler(this::lambda$register$0);
                this.create(Type.UUID, Protocol1_18_2To1_19.access$100());
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                JsonElement jsonElement = packetWrapper.passthrough(Type.COMPONENT);
                Protocol1_18_2To1_19.access$000(this.this$0).processText(jsonElement);
                int n = packetWrapper.read(Type.VAR_INT);
                packetWrapper.write(Type.BYTE, n == 2 ? (byte)2 : 0);
            }
        });
        this.registerServerbound(ServerboundPackets1_17.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.handler(6::lambda$register$0);
                this.create(Type.LONG, 0L);
                this.handler(6::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                String string = packetWrapper.get(Type.STRING, 0);
                if (!string.isEmpty() && string.charAt(0) == '/') {
                    packetWrapper.setPacketType(ServerboundPackets1_19.CHAT_COMMAND);
                    packetWrapper.set(Type.STRING, 0, string.substring(1));
                    packetWrapper.write(Type.VAR_INT, 0);
                } else {
                    packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, Protocol1_18_2To1_19.access$200());
                }
                packetWrapper.write(Type.BOOLEAN, false);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.LONG, Instant.now().toEpochMilli());
            }
        });
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE.getId(), ClientboundLoginPackets.GAME_PROFILE.getId(), new PacketHandlers(this){
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
            }

            @Override
            public void register() {
                this.map(Type.UUID);
                this.map(Type.STRING);
                this.handler(7::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    packetWrapper.read(Type.STRING);
                    packetWrapper.read(Type.STRING);
                    if (!packetWrapper.read(Type.BOOLEAN).booleanValue()) continue;
                    packetWrapper.read(Type.STRING);
                }
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.HELLO.getId(), ServerboundLoginPackets.HELLO.getId(), new PacketHandlers(this){
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
            }

            @Override
            public void register() {
                this.map(Type.STRING);
                this.create(Type.OPTIONAL_PROFILE_KEY, null);
            }
        });
        this.registerServerbound(State.LOGIN, ServerboundLoginPackets.ENCRYPTION_KEY.getId(), ServerboundLoginPackets.ENCRYPTION_KEY.getId(), new PacketHandlers(this){
            final Protocol1_18_2To1_19 this$0;
            {
                this.this$0 = protocol1_18_2To1_19;
            }

            @Override
            public void register() {
                this.map(Type.BYTE_ARRAY_PRIMITIVE);
                this.create(Type.BOOLEAN, true);
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new DimensionRegistryStorage());
        this.addEntityTracker(userConnection, new EntityTrackerBase(userConnection, Entity1_19Types.PLAYER));
    }

    @Override
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

    private static void lambda$registerPackets$0(CommandRewriter commandRewriter, PacketWrapper packetWrapper) throws Exception {
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
            String string = MAPPINGS.getArgumentTypeMappings().identifier(n3);
            if (string == null) {
                ViaBackwards.getPlatform().getLogger().warning("Unknown command argument type id: " + n3);
                string = "minecraft:no";
            }
            packetWrapper.write(Type.STRING, commandRewriter.handleArgumentType(string));
            commandRewriter.handleArgument(packetWrapper, string);
            if ((by & 0x10) == 0) continue;
            packetWrapper.passthrough(Type.STRING);
        }
        packetWrapper.passthrough(Type.VAR_INT);
    }

    static TranslatableRewriter access$000(Protocol1_18_2To1_19 protocol1_18_2To1_19) {
        return protocol1_18_2To1_19.translatableRewriter;
    }

    static UUID access$100() {
        return ZERO_UUID;
    }

    static byte[] access$200() {
        return EMPTY_BYTES;
    }
}

