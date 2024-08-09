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
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
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

    public BlockRewriter(Protocol<C, ?, ?, ?> protocol, Type<Position> type) {
        this.protocol = protocol;
        this.positionType = type;
    }

    public void registerBlockAction(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final BlockRewriter this$0;
            {
                this.this$0 = blockRewriter;
            }

            @Override
            public void register() {
                this.map(BlockRewriter.access$000(this.this$0));
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                if (BlockRewriter.access$100(this.this$0).getMappingData().getBlockMappings() == null) {
                    return;
                }
                int n = packetWrapper.get(Type.VAR_INT, 0);
                int n2 = BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockId(n);
                if (n2 == -1) {
                    packetWrapper.cancel();
                    return;
                }
                packetWrapper.set(Type.VAR_INT, 0, n2);
            }
        });
    }

    public void registerBlockChange(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final BlockRewriter this$0;
            {
                this.this$0 = blockRewriter;
            }

            @Override
            public void register() {
                this.map(BlockRewriter.access$000(this.this$0));
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                packetWrapper.set(Type.VAR_INT, 0, BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(packetWrapper.get(Type.VAR_INT, 0)));
            }
        });
    }

    public void registerMultiBlockChange(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final BlockRewriter this$0;
            {
                this.this$0 = blockRewriter;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(Type.INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (BlockChangeRecord blockChangeRecord : packetWrapper.passthrough(Type.BLOCK_CHANGE_RECORD_ARRAY)) {
                    blockChangeRecord.setBlockId(BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                }
            }
        });
    }

    public void registerVarLongMultiBlockChange(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final BlockRewriter this$0;
            {
                this.this$0 = blockRewriter;
            }

            @Override
            public void register() {
                this.map(Type.LONG);
                this.map(Type.BOOLEAN);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (BlockChangeRecord blockChangeRecord : packetWrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                    blockChangeRecord.setBlockId(BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                }
            }
        });
    }

    public void registerVarLongMultiBlockChange1_20(C c) {
        this.protocol.registerClientbound(c, new PacketHandlers(this){
            final BlockRewriter this$0;
            {
                this.this$0 = blockRewriter;
            }

            @Override
            public void register() {
                this.map(Type.LONG);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (BlockChangeRecord blockChangeRecord : packetWrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                    blockChangeRecord.setBlockId(BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                }
            }
        });
    }

    public void registerAcknowledgePlayerDigging(C c) {
        this.registerBlockChange(c);
    }

    public void registerEffect(C c, int n, int n2) {
        this.protocol.registerClientbound(c, new PacketHandlers(this, n, n2){
            final int val$playRecordId;
            final int val$blockBreakId;
            final BlockRewriter this$0;
            {
                this.this$0 = blockRewriter;
                this.val$playRecordId = n;
                this.val$blockBreakId = n2;
            }

            @Override
            public void register() {
                this.map(Type.INT);
                this.map(BlockRewriter.access$000(this.this$0));
                this.map(Type.INT);
                this.handler(arg_0 -> this.lambda$register$0(this.val$playRecordId, this.val$blockBreakId, arg_0));
            }

            private void lambda$register$0(int n, int n2, PacketWrapper packetWrapper) throws Exception {
                int n3 = packetWrapper.get(Type.INT, 0);
                int n4 = packetWrapper.get(Type.INT, 1);
                if (n3 == n) {
                    packetWrapper.set(Type.INT, 1, BlockRewriter.access$100(this.this$0).getMappingData().getNewItemId(n4));
                } else if (n3 == n2) {
                    packetWrapper.set(Type.INT, 1, BlockRewriter.access$100(this.this$0).getMappingData().getNewBlockStateId(n4));
                }
            }
        });
    }

    public void registerChunkData1_19(C c, ChunkTypeSupplier chunkTypeSupplier) {
        this.registerChunkData1_19(c, chunkTypeSupplier, null);
    }

    public void registerChunkData1_19(C c, ChunkTypeSupplier chunkTypeSupplier, @Nullable Consumer<BlockEntity> consumer) {
        this.protocol.registerClientbound(c, this.chunkDataHandler1_19(chunkTypeSupplier, consumer));
    }

    public PacketHandler chunkDataHandler1_19(ChunkTypeSupplier chunkTypeSupplier, @Nullable Consumer<BlockEntity> consumer) {
        return arg_0 -> this.lambda$chunkDataHandler1_19$0(chunkTypeSupplier, consumer, arg_0);
    }

    public void registerBlockEntityData(C c) {
        this.registerBlockEntityData(c, null);
    }

    public void registerBlockEntityData(C c, @Nullable Consumer<BlockEntity> consumer) {
        this.protocol.registerClientbound(c, arg_0 -> this.lambda$registerBlockEntityData$1(consumer, arg_0));
    }

    private void lambda$registerBlockEntityData$1(Consumer consumer, PacketWrapper packetWrapper) throws Exception {
        CompoundTag compoundTag;
        Position position = packetWrapper.passthrough(Type.POSITION1_14);
        int n = packetWrapper.read(Type.VAR_INT);
        Mappings mappings = this.protocol.getMappingData().getBlockEntityMappings();
        if (mappings != null) {
            packetWrapper.write(Type.VAR_INT, mappings.getNewIdOrDefault(n, n));
        } else {
            packetWrapper.write(Type.VAR_INT, n);
        }
        if (consumer != null && (compoundTag = packetWrapper.passthrough(Type.NBT)) != null) {
            BlockEntityImpl blockEntityImpl = new BlockEntityImpl(BlockEntity.pack(position.x(), position.z()), (short)position.y(), n, compoundTag);
            consumer.accept(blockEntityImpl);
        }
    }

    private void lambda$chunkDataHandler1_19$0(ChunkTypeSupplier chunkTypeSupplier, Consumer consumer, PacketWrapper packetWrapper) throws Exception {
        Object e = this.protocol.getEntityRewriter().tracker(packetWrapper.user());
        Preconditions.checkArgument(e.biomesSent() != 0, "Biome count not set");
        Preconditions.checkArgument(e.currentWorldSectionHeight() != 0, "Section height not set");
        Type<Chunk> type = chunkTypeSupplier.supply(e.currentWorldSectionHeight(), MathUtil.ceilLog2(this.protocol.getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(e.biomesSent()));
        Chunk chunk = packetWrapper.passthrough(type);
        for (ChunkSection object : chunk.getSections()) {
            DataPalette dataPalette = object.palette(PaletteType.BLOCKS);
            for (int i = 0; i < dataPalette.size(); ++i) {
                int n = dataPalette.idByIndex(i);
                dataPalette.setIdByIndex(i, this.protocol.getMappingData().getNewBlockStateId(n));
            }
        }
        Mappings mappings = this.protocol.getMappingData().getBlockEntityMappings();
        if (mappings != null || consumer != null) {
            List<BlockEntity> list = chunk.blockEntities();
            for (int i = 0; i < list.size(); ++i) {
                BlockEntity blockEntity = list.get(i);
                if (mappings != null) {
                    list.set(i, blockEntity.withTypeId(mappings.getNewIdOrDefault(blockEntity.typeId(), blockEntity.typeId())));
                }
                if (consumer == null || blockEntity.tag() == null) continue;
                consumer.accept(blockEntity);
            }
        }
    }

    static Type access$000(BlockRewriter blockRewriter) {
        return blockRewriter.positionType;
    }

    static Protocol access$100(BlockRewriter blockRewriter) {
        return blockRewriter.protocol;
    }

    @FunctionalInterface
    public static interface ChunkTypeSupplier {
        public Type<Chunk> supply(int var1, int var2, int var3);
    }
}

