/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.BackwardsMappings;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.data.CommandRewriter1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.BlockItemPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.EntityPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.PlayerPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.packets.SoundPackets1_14;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.ChunkLightStorage;
import com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage.DifficultyStorage;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Protocol1_13_2To1_14
extends BackwardsProtocol<ClientboundPackets1_14, ClientboundPackets1_13, ServerboundPackets1_14, ServerboundPackets1_13> {
    public static final BackwardsMappings MAPPINGS = new BackwardsMappings("1.14", "1.13.2", Protocol1_14To1_13_2.class);
    private final EntityPackets1_14 entityRewriter = new EntityPackets1_14(this);
    private final BlockItemPackets1_14 blockItemPackets = new BlockItemPackets1_14(this);
    private final TranslatableRewriter<ClientboundPackets1_14> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_14>(this);

    public Protocol1_13_2To1_14() {
        super(ClientboundPackets1_14.class, ClientboundPackets1_13.class, ServerboundPackets1_14.class, ServerboundPackets1_13.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.translatableRewriter.registerBossBar(ClientboundPackets1_14.BOSSBAR);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_14.CHAT_MESSAGE);
        this.translatableRewriter.registerCombatEvent(ClientboundPackets1_14.COMBAT_EVENT);
        this.translatableRewriter.registerDisconnect(ClientboundPackets1_14.DISCONNECT);
        this.translatableRewriter.registerTabList(ClientboundPackets1_14.TAB_LIST);
        this.translatableRewriter.registerTitle(ClientboundPackets1_14.TITLE);
        this.translatableRewriter.registerPing();
        new CommandRewriter1_14(this).registerDeclareCommands(ClientboundPackets1_14.DECLARE_COMMANDS);
        new PlayerPackets1_14(this).register();
        new SoundPackets1_14(this).register();
        new StatisticsRewriter<ClientboundPackets1_14>(this).register(ClientboundPackets1_14.STATISTICS);
        this.cancelClientbound(ClientboundPackets1_14.UPDATE_VIEW_POSITION);
        this.cancelClientbound(ClientboundPackets1_14.UPDATE_VIEW_DISTANCE);
        this.registerClientbound(ClientboundPackets1_14.TAGS, Protocol1_13_2To1_14::lambda$registerPackets$0);
        this.registerClientbound(ClientboundPackets1_14.UPDATE_LIGHT, null, Protocol1_13_2To1_14::lambda$registerPackets$1);
    }

    private static boolean isSet(int n, int n2) {
        return (n & 1 << n2) != 0;
    }

    @Override
    public void init(UserConnection userConnection) {
        if (!userConnection.has(ClientWorld.class)) {
            userConnection.put(new ClientWorld(userConnection));
        }
        userConnection.addEntityTracker(this.getClass(), new EntityTrackerBase(userConnection, Entity1_14Types.PLAYER));
        if (!userConnection.has(ChunkLightStorage.class)) {
            userConnection.put(new ChunkLightStorage(userConnection));
        }
        userConnection.put(new DifficultyStorage(userConnection));
    }

    @Override
    public BackwardsMappings getMappingData() {
        return MAPPINGS;
    }

    public EntityPackets1_14 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPackets1_14 getItemRewriter() {
        return this.blockItemPackets;
    }

    @Override
    public TranslatableRewriter<ClientboundPackets1_14> getTranslatableRewriter() {
        return this.translatableRewriter;
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
        int n2 = packetWrapper.read(Type.VAR_INT);
        int n3 = packetWrapper.read(Type.VAR_INT);
        int n4 = packetWrapper.read(Type.VAR_INT);
        int n5 = packetWrapper.read(Type.VAR_INT);
        int n6 = packetWrapper.read(Type.VAR_INT);
        byte[][] byArrayArray = new byte[16][];
        if (Protocol1_13_2To1_14.isSet(n3, 0)) {
            packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
        }
        for (int i = 0; i < 16; ++i) {
            if (Protocol1_13_2To1_14.isSet(n3, i + 1)) {
                byArrayArray[i] = packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                continue;
            }
            if (!Protocol1_13_2To1_14.isSet(n5, i + 1)) continue;
            byArrayArray[i] = ChunkLightStorage.EMPTY_LIGHT;
        }
        if (Protocol1_13_2To1_14.isSet(n3, 17)) {
            packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
        }
        byte[][] byArrayArray2 = new byte[16][];
        if (Protocol1_13_2To1_14.isSet(n4, 0)) {
            packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
        }
        for (int i = 0; i < 16; ++i) {
            if (Protocol1_13_2To1_14.isSet(n4, i + 1)) {
                byArrayArray2[i] = packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                continue;
            }
            if (!Protocol1_13_2To1_14.isSet(n6, i + 1)) continue;
            byArrayArray2[i] = ChunkLightStorage.EMPTY_LIGHT;
        }
        if (Protocol1_13_2To1_14.isSet(n4, 17)) {
            packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
        }
        packetWrapper.user().get(ChunkLightStorage.class).setStoredLight(byArrayArray, byArrayArray2, n, n2);
        packetWrapper.cancel();
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6 = packetWrapper.passthrough(Type.VAR_INT);
        for (n5 = 0; n5 < n6; ++n5) {
            packetWrapper.passthrough(Type.STRING);
            int[] nArray = packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            for (int i = 0; i < nArray.length; ++i) {
                n4 = nArray[i];
                nArray[i] = n3 = MAPPINGS.getNewBlockId(n4);
            }
        }
        n5 = packetWrapper.passthrough(Type.VAR_INT);
        for (n2 = 0; n2 < n5; ++n2) {
            packetWrapper.passthrough(Type.STRING);
            int[] nArray = packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            for (n4 = 0; n4 < nArray.length; ++n4) {
                int n7;
                n3 = nArray[n4];
                nArray[n4] = n7 = MAPPINGS.getItemMappings().getNewId(n3);
            }
        }
        n2 = packetWrapper.passthrough(Type.VAR_INT);
        for (n = 0; n < n2; ++n) {
            packetWrapper.passthrough(Type.STRING);
            packetWrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
        }
        n = packetWrapper.read(Type.VAR_INT);
        for (n4 = 0; n4 < n; ++n4) {
            packetWrapper.read(Type.STRING);
            packetWrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
        }
    }
}

