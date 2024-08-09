/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat.TranslatableRewriter1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.BackwardsMappings;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.CommandRewriter1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.BlockItemPackets1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets.EntityPackets1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.storage.PlayerSneakStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.UUID;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_15_2To1_16
extends BackwardsProtocol<ClientboundPackets1_16, ClientboundPackets1_15, ServerboundPackets1_16, ServerboundPackets1_14> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings();
    private final EntityPackets1_16 entityRewriter = new EntityPackets1_16(this);
    private final BlockItemPackets1_16 blockItemPackets = new BlockItemPackets1_16(this);
    private final TranslatableRewriter1_16 translatableRewriter = new TranslatableRewriter1_16(this);

    public Protocol1_15_2To1_16() {
        super(ClientboundPackets1_16.class, ClientboundPackets1_15.class, ServerboundPackets1_16.class, ServerboundPackets1_14.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossBar(ClientboundPackets1_16.BOSSBAR);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_16.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_16.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_16.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_16.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_16(this).registerDeclareCommands(ClientboundPackets1_16.DECLARE_COMMANDS);
        this.registerClientbound(State.STATUS, 0, 0, this::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_16.CHAT_MESSAGE, new PacketHandlers(this){
            final Protocol1_15_2To1_16 this$0;
            {
                this.this$0 = protocol1_15_2To1_16;
            }

            @Override
            public void register() {
                this.handler(this::lambda$register$0);
                this.map(Type.BYTE);
                this.map(Type.UUID, Type.NOTHING);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Protocol1_15_2To1_16.access$000(this.this$0).processText(packetWrapper.passthrough(Type.COMPONENT));
            }
        });
        this.registerClientbound(ClientboundPackets1_16.OPEN_WINDOW, new PacketHandlers(this){
            final Protocol1_15_2To1_16 this$0;
            {
                this.this$0 = protocol1_15_2To1_16;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
                this.handler(2::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                if (n == 20) {
                    packetWrapper.set(Type.VAR_INT, 1, 7);
                } else if (n > 20) {
                    packetWrapper.set(Type.VAR_INT, 1, --n);
                }
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Protocol1_15_2To1_16.access$000(this.this$0).processText(packetWrapper.passthrough(Type.COMPONENT));
            }
        });
        SoundRewriter<ClientboundPackets1_16> soundRewriter = new SoundRewriter<ClientboundPackets1_16>(this);
        soundRewriter.registerSound(ClientboundPackets1_16.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_16.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_16.STOP_SOUND);
        this.registerClientbound(State.LOGIN, 2, 2, Protocol1_15_2To1_16::lambda$registerPackets$1);
        new TagRewriter<ClientboundPackets1_16>(this).register(ClientboundPackets1_16.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_16>(this).register(ClientboundPackets1_16.STATISTICS);
        this.registerServerbound(ServerboundPackets1_14.ENTITY_ACTION, Protocol1_15_2To1_16::lambda$registerPackets$2);
        this.registerServerbound(ServerboundPackets1_14.INTERACT_ENTITY, Protocol1_15_2To1_16::lambda$registerPackets$3);
        this.registerServerbound(ServerboundPackets1_14.PLAYER_ABILITIES, Protocol1_15_2To1_16::lambda$registerPackets$4);
        this.cancelServerbound(ServerboundPackets1_14.UPDATE_JIGSAW_BLOCK);
    }

    @Override
    public void init(UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.put(new PlayerSneakStorage());
        userConnection.put(new WorldNameTracker());
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16Types.PLAYER));
    }

    public TranslatableRewriter1_16 getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_16 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_16 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override
    public TranslatableRewriter getTranslatableRewriter() {
        return this.getTranslatableRewriter();
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

    private static void lambda$registerPackets$4(PacketWrapper packetWrapper) throws Exception {
        byte by = packetWrapper.read(Type.BYTE);
        by = (byte)(by & 2);
        packetWrapper.write(Type.BYTE, by);
        packetWrapper.read(Type.FLOAT);
        packetWrapper.read(Type.FLOAT);
    }

    private static void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        if (n == 0 || n == 2) {
            if (n == 2) {
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
            }
            packetWrapper.passthrough(Type.VAR_INT);
        }
        packetWrapper.write(Type.BOOLEAN, packetWrapper.user().get(PlayerSneakStorage.class).isSneaking());
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        if (n == 0) {
            packetWrapper.user().get(PlayerSneakStorage.class).setSneaking(false);
        } else if (n == 1) {
            packetWrapper.user().get(PlayerSneakStorage.class).setSneaking(true);
        }
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        UUID uUID = packetWrapper.read(Type.UUID);
        packetWrapper.write(Type.STRING, uUID.toString());
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        String string = packetWrapper.passthrough(Type.STRING);
        JsonObject jsonObject = GsonUtil.getGson().fromJson(string, JsonObject.class);
        JsonElement jsonElement = jsonObject.get("description");
        if (jsonElement == null) {
            return;
        }
        this.translatableRewriter.processText(jsonElement);
        packetWrapper.set(Type.STRING, 0, jsonObject.toString());
    }

    static TranslatableRewriter1_16 access$000(Protocol1_15_2To1_16 protocol1_15_2To1_16) {
        return protocol1_15_2To1_16.translatableRewriter;
    }
}

