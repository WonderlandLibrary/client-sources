/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.SoundRewriter;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.data.CommandRewriter1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.BlockItemPackets1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets.EntityPackets1_16_2;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_16_2Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.Protocol1_16_2To1_16_1;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ServerboundPackets1_16;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_16_1To1_16_2
extends BackwardsProtocol<ClientboundPackets1_16_2, ClientboundPackets1_16, ServerboundPackets1_16_2, ServerboundPackets1_16> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.16.2", "1.16", Protocol1_16_2To1_16_1.class);
    private final EntityPackets1_16_2 entityRewriter = new EntityPackets1_16_2(this);
    private final BlockItemPackets1_16_2 blockItemPackets = new BlockItemPackets1_16_2(this);
    private final TranslatableRewriter<ClientboundPackets1_16_2> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_16_2>(this);

    public Protocol1_16_1To1_16_2() {
        super(ClientboundPackets1_16_2.class, ClientboundPackets1_16.class, ServerboundPackets1_16_2.class, ServerboundPackets1_16.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossBar(ClientboundPackets1_16_2.BOSSBAR);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_16_2.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_16_2.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_16_2.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_16_2.TITLE);
        this.translatableRewriter.registerOpenWindow(ClientboundPackets1_16_2.OPEN_WINDOW);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_16_2(this).registerDeclareCommands(ClientboundPackets1_16_2.DECLARE_COMMANDS);
        SoundRewriter<ClientboundPackets1_16_2> soundRewriter = new SoundRewriter<ClientboundPackets1_16_2>(this);
        soundRewriter.registerSound(ClientboundPackets1_16_2.SOUND);
        soundRewriter.registerSound(ClientboundPackets1_16_2.ENTITY_SOUND);
        soundRewriter.registerNamedSound(ClientboundPackets1_16_2.NAMED_SOUND);
        soundRewriter.registerStopSound(ClientboundPackets1_16_2.STOP_SOUND);
        this.registerClientbound(ClientboundPackets1_16_2.CHAT_MESSAGE, this::lambda$registerPackets$0);
        this.registerServerbound(ServerboundPackets1_16.RECIPE_BOOK_DATA, Protocol1_16_1To1_16_2::lambda$registerPackets$1);
        new TagRewriter<ClientboundPackets1_16_2>(this).register(ClientboundPackets1_16_2.TAGS, RegistryType.ENTITY);
        new StatisticsRewriter<ClientboundPackets1_16_2>(this).register(ClientboundPackets1_16_2.STATISTICS);
    }

    private static void sendSeenRecipePacket(int n, PacketWrapper packetWrapper) throws Exception {
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        boolean bl2 = packetWrapper.read(Type.BOOLEAN);
        PacketWrapper packetWrapper2 = packetWrapper.create(ServerboundPackets1_16_2.RECIPE_BOOK_DATA);
        packetWrapper2.write(Type.VAR_INT, n);
        packetWrapper2.write(Type.BOOLEAN, bl);
        packetWrapper2.write(Type.BOOLEAN, bl2);
        packetWrapper2.sendToServer(Protocol1_16_1To1_16_2.class);
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new BiomeStorage());
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_16_2Types.PLAYER));
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_16_2> getTranslatableRewriter() {
        return this.translatableRewriter;
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_16_2 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_16_2 getItemRewriter() {
        return this.blockItemPackets;
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

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        if (n == 0) {
            packetWrapper.passthrough(Type.STRING);
            packetWrapper.setPacketType(ServerboundPackets1_16_2.SEEN_RECIPE);
        } else {
            packetWrapper.cancel();
            for (int i = 0; i < 3; ++i) {
                Protocol1_16_1To1_16_2.sendSeenRecipePacket(i, packetWrapper);
            }
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        JsonElement jsonElement = packetWrapper.passthrough(Type.COMPONENT);
        this.translatableRewriter.processText(jsonElement);
        byte by = packetWrapper.passthrough(Type.BYTE);
        if (by == 2) {
            packetWrapper.clearPacket();
            packetWrapper.setPacketType(ClientboundPackets1_16.TITLE);
            packetWrapper.write(Type.VAR_INT, 2);
            packetWrapper.write(Type.COMPONENT, jsonElement);
        }
    }
}

