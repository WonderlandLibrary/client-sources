/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.minecraft.metadata.ChunkPosition;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter.RecipeRewriter1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.type.ChunkType1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.util.PotionEffects;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.MathUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPacketRewriter1_20_2
extends ItemRewriter<ClientboundPackets1_20_2, ServerboundPackets1_19_4, Protocol1_20To1_20_2> {
    public BlockItemPacketRewriter1_20_2(Protocol1_20To1_20_2 protocol) {
        super(protocol, Type.ITEM1_20_2, Type.ITEM1_20_2_VAR_INT_ARRAY);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_20_2> blockRewriter = new BlockRewriter<ClientboundPackets1_20_2>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_20_2.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_20_2.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange1_20(ClientboundPackets1_20_2.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_20_2.EFFECT, 1010, 2001);
        ((Protocol1_20To1_20_2)this.protocol).cancelClientbound(ClientboundPackets1_20_2.CHUNK_BATCH_START);
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.CHUNK_BATCH_FINISHED, null, wrapper -> {
            wrapper.cancel();
            PacketWrapper receivedPacket = wrapper.create(ServerboundPackets1_20_2.CHUNK_BATCH_RECEIVED);
            receivedPacket.write(Type.FLOAT, Float.valueOf(500.0f));
            receivedPacket.sendToServer(Protocol1_20To1_20_2.class);
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.UNLOAD_CHUNK, wrapper -> {
            ChunkPosition chunkPosition = wrapper.read(Type.CHUNK_POSITION);
            wrapper.write(Type.INT, chunkPosition.chunkX());
            wrapper.write(Type.INT, chunkPosition.chunkZ());
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.NBT_QUERY, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.NBT, wrapper.read(Type.NAMELESS_NBT));
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Type.POSITION1_14);
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.NBT, this.handleBlockEntity(wrapper.read(Type.NAMELESS_NBT)));
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.CHUNK_DATA, wrapper -> {
            Object tracker = ((Protocol1_20To1_20_2)this.protocol).getEntityRewriter().tracker(wrapper.user());
            ChunkType1_20_2 chunkType = new ChunkType1_20_2(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_20To1_20_2)this.protocol).getMappingData().getBlockStateMappings().size()), MathUtil.ceilLog2(tracker.biomesSent()));
            Chunk chunk = wrapper.read(chunkType);
            Chunk1_18Type newChunkType = new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_20To1_20_2)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
            wrapper.write(newChunkType, chunk);
            for (ChunkSection section : chunk.getSections()) {
                DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < blockPalette.size(); ++i) {
                    int id = blockPalette.idByIndex(i);
                    blockPalette.setIdByIndex(i, ((Protocol1_20To1_20_2)this.protocol).getMappingData().getNewBlockStateId(id));
                }
            }
            for (BlockEntity blockEntity : chunk.blockEntities()) {
                this.handleBlockEntity(blockEntity.tag());
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerServerbound(ServerboundPackets1_19_4.SET_BEACON_EFFECT, wrapper -> {
            if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT) - 1);
            }
            if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT) - 1);
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.WINDOW_ITEMS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    Item[] items;
                    for (Item item : items = wrapper.read(Type.ITEM1_20_2_VAR_INT_ARRAY)) {
                        BlockItemPacketRewriter1_20_2.this.handleItemToClient(item);
                    }
                    wrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, items);
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
                });
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.handler(wrapper -> wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.ITEM1_20_2))));
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.ADVANCEMENTS, wrapper -> {
            wrapper.passthrough(Type.BOOLEAN);
            int size = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Type.STRING);
                if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Type.STRING);
                }
                if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                    wrapper.passthrough(Type.COMPONENT);
                    wrapper.passthrough(Type.COMPONENT);
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
                    wrapper.passthrough(Type.VAR_INT);
                    int flags = wrapper.passthrough(Type.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Type.STRING);
                    }
                    wrapper.passthrough(Type.FLOAT);
                    wrapper.passthrough(Type.FLOAT);
                }
                wrapper.write(Type.STRING_ARRAY, new String[0]);
                int requirements = wrapper.passthrough(Type.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Type.STRING_ARRAY);
                }
                wrapper.passthrough(Type.BOOLEAN);
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.ENTITY_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    byte slot;
                    do {
                        slot = wrapper.passthrough(Type.BYTE);
                        wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
                    } while ((slot & 0xFFFFFF80) != 0);
                });
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerServerbound(ServerboundPackets1_19_4.CLICK_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int length = wrapper.passthrough(Type.VAR_INT);
                    for (int i = 0; i < length; ++i) {
                        wrapper.passthrough(Type.SHORT);
                        wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToServer(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                    }
                    wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToServer(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                });
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.TRADE_LIST, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            int size = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.write(Type.FLAT_VAR_INT_ITEM, this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
                wrapper.write(Type.FLAT_VAR_INT_ITEM, this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
                wrapper.write(Type.FLAT_VAR_INT_ITEM, this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.FLOAT);
                wrapper.passthrough(Type.INT);
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerServerbound(ServerboundPackets1_19_4.CREATIVE_INVENTORY_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.SHORT);
                this.handler(wrapper -> wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToServer(wrapper.read(Type.FLAT_VAR_INT_ITEM))));
            }
        });
        ((Protocol1_20To1_20_2)this.protocol).registerClientbound(ClientboundPackets1_20_2.SPAWN_PARTICLE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.BOOLEAN);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.DOUBLE);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.INT);
                this.handler(wrapper -> {
                    int id = wrapper.get(Type.VAR_INT, 0);
                    ParticleMappings mappings = Protocol1_20To1_20_2.MAPPINGS.getParticleMappings();
                    if (mappings.isBlockParticle(id)) {
                        int data = wrapper.read(Type.VAR_INT);
                        wrapper.write(Type.VAR_INT, ((Protocol1_20To1_20_2)BlockItemPacketRewriter1_20_2.this.protocol).getMappingData().getNewBlockStateId(data));
                    } else if (mappings.isItemParticle(id)) {
                        wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.ITEM1_20_2)));
                    }
                });
            }
        });
        new RecipeRewriter1_20_2<ClientboundPackets1_20_2>(this.protocol){

            @Override
            public void handleCraftingShapeless(PacketWrapper wrapper) throws Exception {
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.VAR_INT);
                this.handleIngredients(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.FLAT_VAR_INT_ITEM, result);
            }

            @Override
            public void handleSmelting(PacketWrapper wrapper) throws Exception {
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.VAR_INT);
                this.handleIngredient(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.FLAT_VAR_INT_ITEM, result);
                wrapper.passthrough(Type.FLOAT);
                wrapper.passthrough(Type.VAR_INT);
            }

            @Override
            public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
                int ingredients = wrapper.passthrough(Type.VAR_INT) * wrapper.passthrough(Type.VAR_INT);
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < ingredients; ++i) {
                    this.handleIngredient(wrapper);
                }
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.FLAT_VAR_INT_ITEM, result);
                wrapper.passthrough(Type.BOOLEAN);
            }

            @Override
            public void handleStonecutting(PacketWrapper wrapper) throws Exception {
                wrapper.passthrough(Type.STRING);
                this.handleIngredient(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.FLAT_VAR_INT_ITEM, result);
            }

            @Override
            public void handleSmithing(PacketWrapper wrapper) throws Exception {
                this.handleIngredient(wrapper);
                this.handleIngredient(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.FLAT_VAR_INT_ITEM, result);
            }

            @Override
            public void handleSmithingTransform(PacketWrapper wrapper) throws Exception {
                this.handleIngredient(wrapper);
                this.handleIngredient(wrapper);
                this.handleIngredient(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.FLAT_VAR_INT_ITEM, result);
            }

            @Override
            protected void handleIngredient(PacketWrapper wrapper) throws Exception {
                Item[] items = wrapper.read(this.itemArrayType());
                wrapper.write(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT, items);
                for (Item item : items) {
                    this.rewrite(item);
                }
            }
        }.register(ClientboundPackets1_20_2.DECLARE_RECIPES);
    }

    @Override
    public @Nullable Item handleItemToClient(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.tag() != null) {
            com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter.BlockItemPacketRewriter1_20_2.to1_20_1Effects(item);
        }
        return super.handleItemToClient(item);
    }

    @Override
    public @Nullable Item handleItemToServer(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.tag() != null) {
            com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter.BlockItemPacketRewriter1_20_2.to1_20_2Effects(item);
        }
        return super.handleItemToServer(item);
    }

    private @Nullable CompoundTag handleBlockEntity(@Nullable CompoundTag tag) {
        StringTag secondaryEffect;
        if (tag == null) {
            return null;
        }
        StringTag primaryEffect = (StringTag)tag.remove("primary_effect");
        if (primaryEffect != null) {
            String effectKey = Key.stripMinecraftNamespace(primaryEffect.getValue());
            tag.put("Primary", new IntTag(PotionEffects.keyToId(effectKey)));
        }
        if ((secondaryEffect = (StringTag)tag.remove("secondary_effect")) != null) {
            String effectKey = Key.stripMinecraftNamespace(secondaryEffect.getValue());
            tag.put("Secondary", new IntTag(PotionEffects.keyToId(effectKey)));
        }
        return tag;
    }
}

