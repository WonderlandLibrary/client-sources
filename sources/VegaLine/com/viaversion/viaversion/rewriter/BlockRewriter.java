/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.util.MathUtil;
import java.util.List;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BlockRewriter<C extends ClientboundPacketType> {
    private final Protocol<C, ?, ?, ?> protocol;
    private final Type<Position> positionType;
    private final Type<CompoundTag> nbtType;

    public BlockRewriter(Protocol<C, ?, ?, ?> protocol, Type<Position> positionType) {
        this(protocol, positionType, Type.NBT);
    }

    public BlockRewriter(Protocol<C, ?, ?, ?> protocol, Type<Position> positionType, Type<CompoundTag> nbtType) {
        this.protocol = protocol;
        this.positionType = positionType;
        this.nbtType = nbtType;
    }

    public void registerBlockAction(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(BlockRewriter.this.positionType);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    if (BlockRewriter.this.protocol.getMappingData().getBlockMappings() == null) {
                        return;
                    }
                    int id = wrapper.get(Type.VAR_INT, 0);
                    int mappedId = BlockRewriter.this.protocol.getMappingData().getNewBlockId(id);
                    if (mappedId == -1) {
                        wrapper.cancel();
                        return;
                    }
                    wrapper.set(Type.VAR_INT, 0, mappedId);
                });
            }
        });
    }

    public void registerBlockChange(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(BlockRewriter.this.positionType);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> wrapper.set(Type.VAR_INT, 0, BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(wrapper.get(Type.VAR_INT, 0))));
            }
        });
    }

    public void registerMultiBlockChange(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Type.BLOCK_CHANGE_RECORD_ARRAY)) {
                        record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
    }

    public void registerVarLongMultiBlockChange(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.LONG);
                this.map(Type.BOOLEAN);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                        record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
    }

    public void registerVarLongMultiBlockChange1_20(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.LONG);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                        record.setBlockId(BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
    }

    public void registerAcknowledgePlayerDigging(C packetType) {
        this.registerBlockChange(packetType);
    }

    public void registerEffect(C packetType, final int playRecordId, final int blockBreakId) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(BlockRewriter.this.positionType);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Type.INT, 0);
                    int data = wrapper.get(Type.INT, 1);
                    if (id == playRecordId && BlockRewriter.this.protocol.getMappingData().getItemMappings() != null) {
                        wrapper.set(Type.INT, 1, BlockRewriter.this.protocol.getMappingData().getNewItemId(data));
                    } else if (id == blockBreakId && BlockRewriter.this.protocol.getMappingData().getBlockStateMappings() != null) {
                        wrapper.set(Type.INT, 1, BlockRewriter.this.protocol.getMappingData().getNewBlockStateId(data));
                    }
                });
            }
        });
    }

    public void registerChunkData1_19(C packetType, ChunkTypeSupplier chunkTypeSupplier) {
        this.registerChunkData1_19(packetType, chunkTypeSupplier, null);
    }

    public void registerChunkData1_19(C packetType, ChunkTypeSupplier chunkTypeSupplier, @Nullable Consumer<BlockEntity> blockEntityHandler) {
        this.protocol.registerClientbound(packetType, this.chunkDataHandler1_19(chunkTypeSupplier, blockEntityHandler));
    }

    public PacketHandler chunkDataHandler1_19(ChunkTypeSupplier chunkTypeSupplier, @Nullable Consumer<BlockEntity> blockEntityHandler) {
        return wrapper -> {
            Object tracker = this.protocol.getEntityRewriter().tracker(wrapper.user());
            Preconditions.checkArgument(tracker.biomesSent() != -1, "Biome count not set");
            Preconditions.checkArgument(tracker.currentWorldSectionHeight() != -1, "Section height not set");
            Type<Chunk> chunkType = chunkTypeSupplier.supply(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(this.protocol.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
            Chunk chunk = wrapper.passthrough(chunkType);
            for (ChunkSection section : chunk.getSections()) {
                DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < blockPalette.size(); ++i) {
                    int id = blockPalette.idByIndex(i);
                    blockPalette.setIdByIndex(i, this.protocol.getMappingData().getNewBlockStateId(id));
                }
            }
            Mappings blockEntityMappings = this.protocol.getMappingData().getBlockEntityMappings();
            if (blockEntityMappings != null || blockEntityHandler != null) {
                List<BlockEntity> blockEntities = chunk.blockEntities();
                for (int i = 0; i < blockEntities.size(); ++i) {
                    BlockEntity blockEntity = blockEntities.get(i);
                    if (blockEntityMappings != null) {
                        blockEntities.set(i, blockEntity.withTypeId(blockEntityMappings.getNewIdOrDefault(blockEntity.typeId(), blockEntity.typeId())));
                    }
                    if (blockEntityHandler == null || blockEntity.tag() == null) continue;
                    blockEntityHandler.accept(blockEntity);
                }
            }
        };
    }

    public void registerBlockEntityData(C packetType) {
        this.registerBlockEntityData(packetType, null);
    }

    public void registerBlockEntityData(C packetType, @Nullable Consumer<BlockEntity> blockEntityHandler) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            CompoundTag tag;
            Position position = wrapper.passthrough(this.positionType);
            int blockEntityId = wrapper.read(Type.VAR_INT);
            Mappings mappings = this.protocol.getMappingData().getBlockEntityMappings();
            if (mappings != null) {
                wrapper.write(Type.VAR_INT, mappings.getNewIdOrDefault(blockEntityId, blockEntityId));
            } else {
                wrapper.write(Type.VAR_INT, blockEntityId);
            }
            if (blockEntityHandler != null && (tag = wrapper.passthrough(this.nbtType)) != null) {
                BlockEntityImpl blockEntity = new BlockEntityImpl(BlockEntity.pack(position.x(), position.z()), (short)position.y(), blockEntityId, tag);
                blockEntityHandler.accept(blockEntity);
            }
        });
    }

    @FunctionalInterface
    public static interface ChunkTypeSupplier {
        public Type<Chunk> supply(int var1, int var2, int var3);
    }
}

