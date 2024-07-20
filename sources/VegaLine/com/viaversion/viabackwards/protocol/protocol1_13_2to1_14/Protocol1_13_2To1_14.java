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
import com.viaversion.viaversion.api.minecraft.entities.Entity1_14Types;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ClientboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.Protocol1_14To1_13_2;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;

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
        this.registerClientbound(ClientboundPackets1_14.TAGS, (PacketWrapper wrapper) -> {
            int blockTagsSize = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < blockTagsSize; ++i) {
                wrapper.passthrough(Type.STRING);
                int[] blockIds = wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                for (int j = 0; j < blockIds.length; ++j) {
                    int blockId;
                    int id = blockIds[j];
                    blockIds[j] = blockId = MAPPINGS.getNewBlockId(id);
                }
            }
            int itemTagsSize = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < itemTagsSize; ++i) {
                wrapper.passthrough(Type.STRING);
                int[] itemIds = wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
                for (int j = 0; j < itemIds.length; ++j) {
                    int oldId;
                    int itemId = itemIds[j];
                    itemIds[j] = oldId = MAPPINGS.getItemMappings().getNewId(itemId);
                }
            }
            int fluidTagsSize = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < fluidTagsSize; ++i) {
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.VAR_INT_ARRAY_PRIMITIVE);
            }
            int entityTagsSize = wrapper.read(Type.VAR_INT);
            for (int i = 0; i < entityTagsSize; ++i) {
                wrapper.read(Type.STRING);
                wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
            }
        });
        this.registerClientbound(ClientboundPackets1_14.UPDATE_LIGHT, null, (PacketWrapper wrapper) -> {
            int x = wrapper.read(Type.VAR_INT);
            int z = wrapper.read(Type.VAR_INT);
            int skyLightMask = wrapper.read(Type.VAR_INT);
            int blockLightMask = wrapper.read(Type.VAR_INT);
            int emptySkyLightMask = wrapper.read(Type.VAR_INT);
            int emptyBlockLightMask = wrapper.read(Type.VAR_INT);
            byte[][] skyLight = new byte[16][];
            if (Protocol1_13_2To1_14.isSet(skyLightMask, 0)) {
                wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
            }
            for (int i = 0; i < 16; ++i) {
                if (Protocol1_13_2To1_14.isSet(skyLightMask, i + 1)) {
                    skyLight[i] = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    continue;
                }
                if (!Protocol1_13_2To1_14.isSet(emptySkyLightMask, i + 1)) continue;
                skyLight[i] = ChunkLightStorage.EMPTY_LIGHT;
            }
            if (Protocol1_13_2To1_14.isSet(skyLightMask, 17)) {
                wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
            }
            byte[][] blockLight = new byte[16][];
            if (Protocol1_13_2To1_14.isSet(blockLightMask, 0)) {
                wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
            }
            for (int i = 0; i < 16; ++i) {
                if (Protocol1_13_2To1_14.isSet(blockLightMask, i + 1)) {
                    blockLight[i] = wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    continue;
                }
                if (!Protocol1_13_2To1_14.isSet(emptyBlockLightMask, i + 1)) continue;
                blockLight[i] = ChunkLightStorage.EMPTY_LIGHT;
            }
            if (Protocol1_13_2To1_14.isSet(blockLightMask, 17)) {
                wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
            }
            wrapper.user().get(ChunkLightStorage.class).setStoredLight(skyLight, blockLight, x, z);
            wrapper.cancel();
        });
    }

    private static boolean isSet(int mask, int i) {
        return (mask & 1 << i) != 0;
    }

    @Override
    public void init(UserConnection user) {
        if (!user.has(ClientWorld.class)) {
            user.put(new ClientWorld(user));
        }
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, Entity1_14Types.PLAYER));
        if (!user.has(ChunkLightStorage.class)) {
            user.put(new ChunkLightStorage(user));
        }
        user.put(new DifficultyStorage(user));
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
}

