/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.api.rewriters.MapColorRewriter;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.data.MapColorRewrites;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PingRequests;
import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.storage.PlayerLastCursorItem;
import com.viaversion.viaversion.api.data.entity.EntityTracker;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.LongArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.types.Chunk1_16_2Type;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.CompactArrayUtil;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public final class BlockItemPackets1_17
extends ItemRewriter<ClientboundPackets1_17, ServerboundPackets1_16_2, Protocol1_16_4To1_17> {
    public BlockItemPackets1_17(Protocol1_16_4To1_17 protocol1_16_4To1_17) {
        super(protocol1_16_4To1_17);
    }

    @Override
    protected void registerPackets() {
        BlockRewriter<ClientboundPackets1_17> blockRewriter = new BlockRewriter<ClientboundPackets1_17>(this.protocol, Type.POSITION1_14);
        new RecipeRewriter<ClientboundPackets1_17>(this.protocol).register(ClientboundPackets1_17.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_17.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_17.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerEntityEquipmentArray(ClientboundPackets1_17.ENTITY_EQUIPMENT);
        this.registerTradeList(ClientboundPackets1_17.TRADE_LIST);
        this.registerAdvancements(ClientboundPackets1_17.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        blockRewriter.registerAcknowledgePlayerDigging(ClientboundPackets1_17.ACKNOWLEDGE_PLAYER_DIGGING);
        blockRewriter.registerBlockAction(ClientboundPackets1_17.BLOCK_ACTION);
        blockRewriter.registerEffect(ClientboundPackets1_17.EFFECT, 1010, 2001);
        this.registerCreativeInvAction(ServerboundPackets1_16_2.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_16_2.EDIT_BOOK, this::lambda$registerPackets$0);
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_16_2.CLICK_WINDOW, new PacketHandlers(this){
            final BlockItemPackets1_17 this$0;
            {
                this.this$0 = blockItemPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                short s = packetWrapper.passthrough(Type.SHORT);
                byte by = packetWrapper.passthrough(Type.BYTE);
                packetWrapper.read(Type.SHORT);
                int n = packetWrapper.passthrough(Type.VAR_INT);
                Item item = this.this$0.handleItemToServer(packetWrapper.read(Type.FLAT_VAR_INT_ITEM));
                packetWrapper.write(Type.VAR_INT, 0);
                PlayerLastCursorItem playerLastCursorItem = packetWrapper.user().get(PlayerLastCursorItem.class);
                if (n == 0 && by == 0 && item != null) {
                    playerLastCursorItem.setLastCursorItem(item);
                } else if (n == 0 && by == 1 && item != null) {
                    if (playerLastCursorItem.isSet()) {
                        playerLastCursorItem.setLastCursorItem(item);
                    } else {
                        playerLastCursorItem.setLastCursorItem(item, (item.amount() + 1) / 2);
                    }
                } else if (n != 5 || s != -999 || by != 0 && by != 4) {
                    playerLastCursorItem.setLastCursorItem(null);
                }
                Item item2 = playerLastCursorItem.getLastCursorItem();
                if (item2 == null) {
                    packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item);
                } else {
                    packetWrapper.write(Type.FLAT_VAR_INT_ITEM, item2);
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.SET_SLOT, this::lambda$registerPackets$1);
        ((Protocol1_16_4To1_17)this.protocol).registerServerbound(ServerboundPackets1_16_2.WINDOW_CONFIRMATION, null, BlockItemPackets1_17::lambda$registerPackets$2);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.SPAWN_PARTICLE, new PacketHandlers(this){
            final BlockItemPackets1_17 this$0;
            {
                this.this$0 = blockItemPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.BOOLEAN);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(2::lambda$register$0);
                this.handler(this.this$0.getSpawnParticleHandler(Type.FLAT_VAR_INT_ITEM));
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                if (n == 16) {
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.read(Type.FLOAT);
                    packetWrapper.read(Type.FLOAT);
                    packetWrapper.read(Type.FLOAT);
                } else if (n == 37) {
                    packetWrapper.set(Type.INT, 0, -1);
                    packetWrapper.cancel();
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 0);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_LERP_SIZE, ClientboundPackets1_16_2.WORLD_BORDER, 1);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_CENTER, ClientboundPackets1_16_2.WORLD_BORDER, 2);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_INIT, ClientboundPackets1_16_2.WORLD_BORDER, 3);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DELAY, ClientboundPackets1_16_2.WORLD_BORDER, 4);
        ((Protocol1_16_4To1_17)this.protocol).mergePacket(ClientboundPackets1_17.WORLD_BORDER_WARNING_DISTANCE, ClientboundPackets1_16_2.WORLD_BORDER, 5);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.UPDATE_LIGHT, new PacketHandlers(this){
            final BlockItemPackets1_17 this$0;
            {
                this.this$0 = blockItemPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$register$0);
            }

            private void writeLightArrays(PacketWrapper packetWrapper, BitSet bitSet, int n, int n2, int n3) throws Exception {
                int n4;
                packetWrapper.read(Type.VAR_INT);
                ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
                for (n4 = 0; n4 < n2; ++n4) {
                    if (!bitSet.get(n4)) continue;
                    packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                }
                for (n4 = 0; n4 < 18; ++n4) {
                    if (!this.isSet(n, n4)) continue;
                    arrayList.add(packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
                }
                for (n4 = n2 + 18; n4 < n3 + 2; ++n4) {
                    if (!bitSet.get(n4)) continue;
                    packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                }
                for (byte[] byArray : arrayList) {
                    packetWrapper.write(Type.BYTE_ARRAY_PRIMITIVE, byArray);
                }
            }

            private boolean isSet(int n, int n2) {
                return (n & 1 << n2) != 0;
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                Object t = packetWrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
                int n = Math.max(0, -(t.currentMinY() >> 4));
                long[] lArray = packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                long[] lArray2 = packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                int n2 = BlockItemPackets1_17.access$000(this.this$0, lArray, n);
                int n3 = BlockItemPackets1_17.access$000(this.this$0, lArray2, n);
                packetWrapper.write(Type.VAR_INT, n2);
                packetWrapper.write(Type.VAR_INT, n3);
                long[] lArray3 = packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                long[] lArray4 = packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE);
                packetWrapper.write(Type.VAR_INT, BlockItemPackets1_17.access$000(this.this$0, lArray3, n));
                packetWrapper.write(Type.VAR_INT, BlockItemPackets1_17.access$000(this.this$0, lArray4, n));
                this.writeLightArrays(packetWrapper, BitSet.valueOf(lArray), n2, n, t.currentWorldSectionHeight());
                this.writeLightArrays(packetWrapper, BitSet.valueOf(lArray2), n3, n, t.currentWorldSectionHeight());
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.MULTI_BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_17 this$0;
            {
                this.this$0 = blockItemPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.LONG);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                BlockChangeRecord[] blockChangeRecordArray;
                long l = packetWrapper.get(Type.LONG, 0);
                int n = (int)(l << 44 >> 44);
                if (n < 0 || n > 15) {
                    packetWrapper.cancel();
                    return;
                }
                for (BlockChangeRecord blockChangeRecord : blockChangeRecordArray = packetWrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                    blockChangeRecord.setBlockId(((Protocol1_16_4To1_17)BlockItemPackets1_17.access$100(this.this$0)).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_17 this$0;
            {
                this.this$0 = blockItemPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.POSITION1_14, 0).y();
                if (n < 0 || n > 255) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_16_4To1_17)BlockItemPackets1_17.access$200(this.this$0)).getMappingData().getNewBlockStateId(packetWrapper.get(Type.VAR_INT, 0)));
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.CHUNK_DATA, this::lambda$registerPackets$6);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_ENTITY_DATA, BlockItemPackets1_17::lambda$registerPackets$7);
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.BLOCK_BREAK_ANIMATION, new PacketHandlers(this){
            final BlockItemPackets1_17 this$0;
            {
                this.this$0 = blockItemPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(6::lambda$register$0);
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.POSITION1_14).y();
                if (n < 0 || n > 255) {
                    packetWrapper.cancel();
                }
            }
        });
        ((Protocol1_16_4To1_17)this.protocol).registerClientbound(ClientboundPackets1_17.MAP_DATA, new PacketHandlers(this){
            final BlockItemPackets1_17 this$0;
            {
                this.this$0 = blockItemPackets1_17;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BYTE);
                this.handler(7::lambda$register$0);
                this.map(Type.BOOLEAN);
                this.handler(7::lambda$register$1);
            }

            private static void lambda$register$1(PacketWrapper packetWrapper) throws Exception {
                boolean bl = packetWrapper.read(Type.BOOLEAN);
                if (!bl) {
                    packetWrapper.write(Type.VAR_INT, 0);
                } else {
                    MapColorRewriter.getRewriteHandler(MapColorRewrites::getMappedColor).handle(packetWrapper);
                }
            }

            private static void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.write(Type.BOOLEAN, true);
            }
        });
    }

    private int cutLightMask(long[] lArray, int n) {
        if (lArray.length == 0) {
            return 1;
        }
        return this.cutMask(BitSet.valueOf(lArray), n, true);
    }

    private int cutMask(BitSet bitSet, int n, boolean bl) {
        int n2 = 0;
        int n3 = n + (bl ? 18 : 16);
        int n4 = n;
        int n5 = 0;
        while (n4 < n3) {
            if (bitSet.get(n4)) {
                n2 |= 1 << n5;
            }
            ++n4;
            ++n5;
        }
        return n2;
    }

    private static void lambda$registerPackets$7(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.passthrough(Type.POSITION1_14).y();
        if (n < 0 || n > 255) {
            packetWrapper.cancel();
        }
    }

    private void lambda$registerPackets$6(PacketWrapper packetWrapper) throws Exception {
        int n;
        Object object;
        Object t = packetWrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
        int n2 = t.currentWorldSectionHeight();
        Chunk chunk = packetWrapper.read(new Chunk1_17Type(n2));
        packetWrapper.write(new Chunk1_16_2Type(), chunk);
        int n3 = Math.max(0, -(t.currentMinY() >> 4));
        chunk.setBiomeData(Arrays.copyOfRange(chunk.getBiomeData(), n3 * 64, n3 * 64 + 1024));
        chunk.setBitmask(this.cutMask(chunk.getChunkMask(), n3, false));
        chunk.setChunkMask(null);
        ChunkSection[] chunkSectionArray = Arrays.copyOfRange(chunk.getSections(), n3, n3 + 16);
        chunk.setSections(chunkSectionArray);
        CompoundTag compoundTag = chunk.getHeightMap();
        for (Tag object2 : compoundTag.values()) {
            object = (LongArrayTag)object2;
            int[] nArray = new int[256];
            n = MathUtil.ceilLog2((n2 << 4) + 1);
            CompactArrayUtil.iterateCompactArrayWithPadding(n, nArray.length, ((LongArrayTag)object).getValue(), (arg_0, arg_1) -> BlockItemPackets1_17.lambda$null$3(nArray, t, arg_0, arg_1));
            ((LongArrayTag)object).setValue(CompactArrayUtil.createCompactArrayWithPadding(9, nArray.length, arg_0 -> BlockItemPackets1_17.lambda$null$4(nArray, arg_0)));
        }
        for (int i = 0; i < 16; ++i) {
            ChunkSection chunkSection = chunkSectionArray[i];
            if (chunkSection == null) continue;
            object = chunkSection.palette(PaletteType.BLOCKS);
            for (int j = 0; j < object.size(); ++j) {
                n = ((Protocol1_16_4To1_17)this.protocol).getMappingData().getNewBlockStateId(object.idByIndex(j));
                object.setIdByIndex(j, n);
            }
        }
        chunk.getBlockEntities().removeIf(BlockItemPackets1_17::lambda$null$5);
    }

    private static boolean lambda$null$5(CompoundTag compoundTag) {
        NumberTag numberTag = (NumberTag)compoundTag.get("y");
        return numberTag != null && (numberTag.asInt() < 0 || numberTag.asInt() > 255);
    }

    private static long lambda$null$4(int[] nArray, int n) {
        return nArray[n];
    }

    private static void lambda$null$3(int[] nArray, EntityTracker entityTracker, int n, int n2) {
        nArray[n] = MathUtil.clamp(n2 + entityTracker.currentMinY(), 0, 255);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.cancel();
        if (!ViaBackwards.getConfig().handlePingsAsInvAcknowledgements()) {
            return;
        }
        short s = packetWrapper.read(Type.UNSIGNED_BYTE);
        short s2 = packetWrapper.read(Type.SHORT);
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        if (s == 0 && bl && packetWrapper.user().get(PingRequests.class).removeId(s2)) {
            PacketWrapper packetWrapper2 = packetWrapper.create(ServerboundPackets1_17.PONG);
            packetWrapper2.write(Type.INT, Integer.valueOf(s2));
            packetWrapper2.sendToServer(Protocol1_16_4To1_17.class);
        }
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        short s = packetWrapper.passthrough(Type.UNSIGNED_BYTE);
        short s2 = packetWrapper.passthrough(Type.SHORT);
        Item item = packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
        if (item != null && s == -1 && s2 == -1) {
            packetWrapper.user().get(PlayerLastCursorItem.class).setLastCursorItem(item);
        }
        packetWrapper.write(Type.FLAT_VAR_INT_ITEM, this.handleItemToClient(item));
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        this.handleItemToServer(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
    }

    static int access$000(BlockItemPackets1_17 blockItemPackets1_17, long[] lArray, int n) {
        return blockItemPackets1_17.cutLightMask(lArray, n);
    }

    static Protocol access$100(BlockItemPackets1_17 blockItemPackets1_17) {
        return blockItemPackets1_17.protocol;
    }

    static Protocol access$200(BlockItemPackets1_17 blockItemPackets1_17) {
        return blockItemPackets1_17.protocol;
    }
}

