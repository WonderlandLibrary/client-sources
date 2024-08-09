/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.BlockEntityIds;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types.Chunk1_17Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.MathUtil;
import java.util.ArrayList;
import java.util.BitSet;

public final class BlockItemPackets1_18
extends ItemRewriter<ClientboundPackets1_18, ServerboundPackets1_17, Protocol1_17_1To1_18> {
    public BlockItemPackets1_18(Protocol1_17_1To1_18 protocol1_17_1To1_18) {
        super(protocol1_17_1To1_18);
    }

    @Override
    protected void registerPackets() {
        new RecipeRewriter<ClientboundPackets1_18>(this.protocol).register(ClientboundPackets1_18.DECLARE_RECIPES);
        this.registerSetCooldown(ClientboundPackets1_18.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_18.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_18.SET_SLOT);
        this.registerEntityEquipmentArray(ClientboundPackets1_18.ENTITY_EQUIPMENT);
        this.registerTradeList(ClientboundPackets1_18.TRADE_LIST);
        this.registerAdvancements(ClientboundPackets1_18.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW);
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.EFFECT, new PacketHandlers(this){
            final BlockItemPackets1_18 this$0;
            {
                this.this$0 = blockItemPackets1_18;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.POSITION1_14);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.INT, 0);
                int n2 = packetWrapper.get(Type.INT, 1);
                if (n == 1010) {
                    packetWrapper.set(Type.INT, 1, ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$000(this.this$0)).getMappingData().getNewItemId(n2));
                }
            }
        });
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_PARTICLE, new PacketHandlers(this){
            final BlockItemPackets1_18 this$0;
            {
                this.this$0 = blockItemPackets1_18;
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
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n;
                int n2 = packetWrapper.get(Type.INT, 0);
                if (n2 == 3) {
                    int n3 = packetWrapper.read(Type.VAR_INT);
                    if (n3 == 7786) {
                        packetWrapper.set(Type.INT, 0, 3);
                    } else {
                        packetWrapper.set(Type.INT, 0, 2);
                    }
                    return;
                }
                ParticleMappings particleMappings = ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$100(this.this$0)).getMappingData().getParticleMappings();
                if (particleMappings.isBlockParticle(n2)) {
                    n = packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$200(this.this$0)).getMappingData().getNewBlockStateId(n));
                } else if (particleMappings.isItemParticle(n2)) {
                    this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                }
                n = ((Protocol1_17_1To1_18)BlockItemPackets1_18.access$300(this.this$0)).getMappingData().getNewParticleId(n2);
                if (n != n2) {
                    packetWrapper.set(Type.INT, 0, n);
                }
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.BLOCK_ENTITY_DATA, new PacketHandlers(this){
            final BlockItemPackets1_18 this$0;
            {
                this.this$0 = blockItemPackets1_18;
            }

            @Override
            public void register() {
                this.map(Type.POSITION1_14);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.VAR_INT);
                CompoundTag compoundTag = packetWrapper.read(Type.NBT);
                int n2 = BlockEntityIds.mappedId(n);
                if (n2 == -1) {
                    packetWrapper.cancel();
                    return;
                }
                String string = (String)((Protocol1_17_1To1_18)BlockItemPackets1_18.access$400(this.this$0)).getMappingData().blockEntities().get(n);
                if (string == null) {
                    packetWrapper.cancel();
                    return;
                }
                CompoundTag compoundTag2 = compoundTag == null ? new CompoundTag() : compoundTag;
                Position position = packetWrapper.get(Type.POSITION1_14, 0);
                compoundTag2.put("id", new StringTag(Key.namespaced(string)));
                compoundTag2.put("x", new IntTag(position.x()));
                compoundTag2.put("y", new IntTag(position.y()));
                compoundTag2.put("z", new IntTag(position.z()));
                BlockItemPackets1_18.access$500(this.this$0, n, compoundTag2);
                packetWrapper.write(Type.UNSIGNED_BYTE, (short)n2);
                packetWrapper.write(Type.NBT, compoundTag2);
            }
        });
        ((Protocol1_17_1To1_18)this.protocol).registerClientbound(ClientboundPackets1_18.CHUNK_DATA, this::lambda$registerPackets$0);
        ((Protocol1_17_1To1_18)this.protocol).cancelClientbound(ClientboundPackets1_18.SET_SIMULATION_DISTANCE);
    }

    private void handleSpawner(int n, CompoundTag compoundTag) {
        CompoundTag compoundTag2;
        CompoundTag compoundTag3;
        if (n == 8 && (compoundTag3 = (CompoundTag)compoundTag.get("SpawnData")) != null && (compoundTag2 = (CompoundTag)compoundTag3.get("entity")) != null) {
            compoundTag.put("SpawnData", compoundTag2);
        }
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        int n;
        Object object2;
        Object e = ((Protocol1_17_1To1_18)this.protocol).getEntityRewriter().tracker(packetWrapper.user());
        Chunk1_18Type chunk1_18Type = new Chunk1_18Type(e.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_17_1To1_18)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(e.biomesSent()));
        Chunk chunk = packetWrapper.read(chunk1_18Type);
        ChunkSection[] chunkSectionArray = chunk.getSections();
        BitSet bitSet = new BitSet(chunk.getSections().length);
        int[] nArray = new int[chunkSectionArray.length * 64];
        int n2 = 0;
        for (int i = 0; i < chunkSectionArray.length; ++i) {
            object2 = chunkSectionArray[i];
            DataPalette object3 = object2.palette(PaletteType.BIOMES);
            for (int n3 = 0; n3 < 64; ++n3) {
                nArray[n2++] = object3.idAt(n3);
            }
            if (object2.getNonAirBlocksCount() == 0) {
                chunkSectionArray[i] = null;
                continue;
            }
            bitSet.set(i);
        }
        ArrayList<CompoundTag> arrayList = new ArrayList<CompoundTag>(chunk.blockEntities().size());
        for (BlockEntity blockEntity : chunk.blockEntities()) {
            CompoundTag compoundTag;
            String string = (String)((Protocol1_17_1To1_18)this.protocol).getMappingData().blockEntities().get(blockEntity.typeId());
            if (string == null) continue;
            if (blockEntity.tag() != null) {
                compoundTag = blockEntity.tag();
                this.handleSpawner(blockEntity.typeId(), compoundTag);
            } else {
                compoundTag = new CompoundTag();
            }
            arrayList.add(compoundTag);
            compoundTag.put("x", new IntTag((chunk.getX() << 4) + blockEntity.sectionX()));
            compoundTag.put("y", new IntTag(blockEntity.y()));
            compoundTag.put("z", new IntTag((chunk.getZ() << 4) + blockEntity.sectionZ()));
            compoundTag.put("id", new StringTag(Key.namespaced(string)));
        }
        object2 = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, bitSet, chunk.getSections(), nArray, chunk.getHeightMap(), arrayList);
        packetWrapper.write(new Chunk1_17Type(e.currentWorldSectionHeight()), object2);
        PacketWrapper packetWrapper2 = packetWrapper.create(ClientboundPackets1_17_1.UPDATE_LIGHT);
        packetWrapper2.write(Type.VAR_INT, object2.getX());
        packetWrapper2.write(Type.VAR_INT, object2.getZ());
        packetWrapper2.write(Type.BOOLEAN, packetWrapper.read(Type.BOOLEAN));
        packetWrapper2.write(Type.LONG_ARRAY_PRIMITIVE, packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE));
        packetWrapper2.write(Type.LONG_ARRAY_PRIMITIVE, packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE));
        packetWrapper2.write(Type.LONG_ARRAY_PRIMITIVE, packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE));
        packetWrapper2.write(Type.LONG_ARRAY_PRIMITIVE, packetWrapper.read(Type.LONG_ARRAY_PRIMITIVE));
        int n3 = packetWrapper.read(Type.VAR_INT);
        packetWrapper2.write(Type.VAR_INT, n3);
        for (n = 0; n < n3; ++n) {
            packetWrapper2.write(Type.BYTE_ARRAY_PRIMITIVE, packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
        }
        n = packetWrapper.read(Type.VAR_INT);
        packetWrapper2.write(Type.VAR_INT, n);
        for (int i = 0; i < n; ++i) {
            packetWrapper2.write(Type.BYTE_ARRAY_PRIMITIVE, packetWrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
        }
        packetWrapper2.send(Protocol1_17_1To1_18.class);
    }

    static Protocol access$000(BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }

    static Protocol access$100(BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }

    static Protocol access$200(BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }

    static Protocol access$300(BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }

    static Protocol access$400(BlockItemPackets1_18 blockItemPackets1_18) {
        return blockItemPackets1_18.protocol;
    }

    static void access$500(BlockItemPackets1_18 blockItemPackets1_18, int n, CompoundTag compoundTag) {
        blockItemPackets1_18.handleSpawner(n, compoundTag);
    }
}

