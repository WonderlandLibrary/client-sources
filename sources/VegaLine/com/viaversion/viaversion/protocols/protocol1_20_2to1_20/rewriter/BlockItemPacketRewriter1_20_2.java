/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter;

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
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.RecipeRewriter1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.Protocol1_20_2To1_20;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ServerboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.type.ChunkType1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.util.PotionEffects;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.MathUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPacketRewriter1_20_2
extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_20_2, Protocol1_20_2To1_20> {
    public BlockItemPacketRewriter1_20_2(Protocol1_20_2To1_20 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter<ClientboundPackets1_19_4>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_19_4.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19_4.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange1_20(ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_19_4.EFFECT, 1010, 2001);
        ((Protocol1_20_2To1_20)this.protocol).registerServerbound(ServerboundPackets1_20_2.SET_BEACON_EFFECT, wrapper -> {
            if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT) + 1);
            }
            if (wrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT) + 1);
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.UNLOAD_CHUNK, wrapper -> {
            int x = wrapper.read(Type.INT);
            int z = wrapper.read(Type.INT);
            wrapper.write(Type.CHUNK_POSITION, new ChunkPosition(x, z));
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.NBT_QUERY, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.NAMELESS_NBT, wrapper.read(Type.NBT));
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, wrapper -> {
            wrapper.passthrough(Type.POSITION1_14);
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.NAMELESS_NBT, this.handleBlockEntity(wrapper.read(Type.NBT)));
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.CHUNK_DATA, wrapper -> {
            Object tracker = ((Protocol1_20_2To1_20)this.protocol).getEntityRewriter().tracker(wrapper.user());
            Chunk1_18Type chunkType = new Chunk1_18Type(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_20_2To1_20)this.protocol).getMappingData().getBlockStateMappings().size()), MathUtil.ceilLog2(tracker.biomesSent()));
            Chunk chunk = wrapper.read(chunkType);
            ChunkType1_20_2 newChunkType = new ChunkType1_20_2(tracker.currentWorldSectionHeight(), MathUtil.ceilLog2(((Protocol1_20_2To1_20)this.protocol).getMappingData().getBlockStateMappings().mappedSize()), MathUtil.ceilLog2(tracker.biomesSent()));
            wrapper.write(newChunkType, chunk);
            for (ChunkSection section : chunk.getSections()) {
                DataPalette blockPalette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < blockPalette.size(); ++i) {
                    int id = blockPalette.idByIndex(i);
                    blockPalette.setIdByIndex(i, ((Protocol1_20_2To1_20)this.protocol).getMappingData().getNewBlockStateId(id));
                }
            }
            for (BlockEntity blockEntity : chunk.blockEntities()) {
                this.handleBlockEntity(blockEntity.tag());
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.WINDOW_ITEMS, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    Item[] items;
                    for (Item item : items = wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT)) {
                        BlockItemPacketRewriter1_20_2.this.handleItemToClient(item);
                    }
                    wrapper.write(Type.ITEM1_20_2_VAR_INT_ARRAY, items);
                    wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                });
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.SET_SLOT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.VAR_INT);
                this.map(Type.SHORT);
                this.handler(wrapper -> wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM))));
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.ADVANCEMENTS, wrapper -> {
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
                    wrapper.write(Type.ITEM1_20_2, this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                    wrapper.passthrough(Type.VAR_INT);
                    int flags = wrapper.passthrough(Type.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Type.STRING);
                    }
                    wrapper.passthrough(Type.FLOAT);
                    wrapper.passthrough(Type.FLOAT);
                }
                wrapper.read(Type.STRING_ARRAY);
                int requirements = wrapper.passthrough(Type.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Type.STRING_ARRAY);
                }
                wrapper.passthrough(Type.BOOLEAN);
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.ENTITY_EQUIPMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    byte slot;
                    do {
                        slot = wrapper.passthrough(Type.BYTE);
                        wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                    } while ((slot & 0xFFFFFF80) != 0);
                });
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerServerbound(ServerboundPackets1_20_2.CLICK_WINDOW, new PacketHandlers(){

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
                        wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPacketRewriter1_20_2.this.handleItemToServer(wrapper.read(Type.ITEM1_20_2)));
                    }
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPacketRewriter1_20_2.this.handleItemToServer(wrapper.read(Type.ITEM1_20_2)));
                });
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.TRADE_LIST, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            int size = wrapper.passthrough(Type.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.write(Type.ITEM1_20_2, this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                wrapper.write(Type.ITEM1_20_2, this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                wrapper.write(Type.ITEM1_20_2, this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                wrapper.passthrough(Type.BOOLEAN);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.INT);
                wrapper.passthrough(Type.FLOAT);
                wrapper.passthrough(Type.INT);
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerServerbound(ServerboundPackets1_20_2.CREATIVE_INVENTORY_ACTION, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.SHORT);
                this.handler(wrapper -> wrapper.write(Type.FLAT_VAR_INT_ITEM, BlockItemPacketRewriter1_20_2.this.handleItemToServer(wrapper.read(Type.ITEM1_20_2))));
            }
        });
        ((Protocol1_20_2To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.SPAWN_PARTICLE, new PacketHandlers(){

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
                    ParticleMappings mappings = Protocol1_20_2To1_20.MAPPINGS.getParticleMappings();
                    if (mappings.isBlockParticle(id)) {
                        int data = wrapper.read(Type.VAR_INT);
                        wrapper.write(Type.VAR_INT, ((Protocol1_20_2To1_20)BlockItemPacketRewriter1_20_2.this.protocol).getMappingData().getNewBlockStateId(data));
                    } else if (mappings.isItemParticle(id)) {
                        wrapper.write(Type.ITEM1_20_2, BlockItemPacketRewriter1_20_2.this.handleItemToClient(wrapper.read(Type.FLAT_VAR_INT_ITEM)));
                    }
                });
            }
        });
        new RecipeRewriter1_19_4<ClientboundPackets1_19_4>(this.protocol){

            @Override
            public void handleCraftingShapeless(PacketWrapper wrapper) throws Exception {
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.VAR_INT);
                this.handleIngredients(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.ITEM1_20_2, result);
            }

            @Override
            public void handleSmelting(PacketWrapper wrapper) throws Exception {
                wrapper.passthrough(Type.STRING);
                wrapper.passthrough(Type.VAR_INT);
                this.handleIngredient(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.ITEM1_20_2, result);
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
                wrapper.write(Type.ITEM1_20_2, result);
                wrapper.passthrough(Type.BOOLEAN);
            }

            @Override
            public void handleStonecutting(PacketWrapper wrapper) throws Exception {
                wrapper.passthrough(Type.STRING);
                this.handleIngredient(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.ITEM1_20_2, result);
            }

            @Override
            public void handleSmithing(PacketWrapper wrapper) throws Exception {
                this.handleIngredient(wrapper);
                this.handleIngredient(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.ITEM1_20_2, result);
            }

            @Override
            public void handleSmithingTransform(PacketWrapper wrapper) throws Exception {
                this.handleIngredient(wrapper);
                this.handleIngredient(wrapper);
                this.handleIngredient(wrapper);
                Item result = wrapper.read(this.itemType());
                this.rewrite(result);
                wrapper.write(Type.ITEM1_20_2, result);
            }

            @Override
            protected void handleIngredient(PacketWrapper wrapper) throws Exception {
                Item[] items = wrapper.read(this.itemArrayType());
                wrapper.write(Type.ITEM1_20_2_VAR_INT_ARRAY, items);
                for (Item item : items) {
                    this.rewrite(item);
                }
            }
        }.register(ClientboundPackets1_19_4.DECLARE_RECIPES);
    }

    @Override
    public @Nullable Item handleItemToClient(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.tag() != null) {
            BlockItemPacketRewriter1_20_2.to1_20_2Effects(item);
        }
        return super.handleItemToClient(item);
    }

    @Override
    public @Nullable Item handleItemToServer(@Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (item.tag() != null) {
            BlockItemPacketRewriter1_20_2.to1_20_1Effects(item);
        }
        return super.handleItemToServer(item);
    }

    public static void to1_20_2Effects(Item item) {
        Object customPotionEffectsTag = item.tag().remove("CustomPotionEffects");
        if (customPotionEffectsTag instanceof ListTag) {
            ListTag effectsTag = (ListTag)customPotionEffectsTag;
            item.tag().put("custom_potion_effects", customPotionEffectsTag);
            for (Tag tag : effectsTag) {
                String key;
                if (!(tag instanceof CompoundTag)) continue;
                CompoundTag effectTag = (CompoundTag)tag;
                Object idTag = effectTag.remove("Id");
                if (idTag instanceof NumberTag && (key = PotionEffects.idToKey(((NumberTag)idTag).asInt())) != null) {
                    effectTag.put("id", new StringTag(key));
                }
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "Amplifier", "amplifier");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "Duration", "duration");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "Ambient", "ambient");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "ShowParticles", "show_particles");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "ShowIcon", "show_icon");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "HiddenEffect", "hidden_effect");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "FactorCalculationData", "factor_calculation_data");
            }
        }
    }

    public static void to1_20_1Effects(Item item) {
        Object customPotionEffectsTag = item.tag().remove("custom_potion_effects");
        if (customPotionEffectsTag instanceof ListTag) {
            ListTag effectsTag = (ListTag)customPotionEffectsTag;
            item.tag().put("CustomPotionEffects", effectsTag);
            for (Tag tag : effectsTag) {
                if (!(tag instanceof CompoundTag)) continue;
                CompoundTag effectTag = (CompoundTag)tag;
                Object idTag = effectTag.remove("id");
                if (idTag instanceof StringTag) {
                    int id = PotionEffects.keyToId(((StringTag)idTag).getValue());
                    effectTag.put("Id", new IntTag(id));
                }
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "amplifier", "Amplifier");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "duration", "Duration");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "ambient", "Ambient");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "show_particles", "ShowParticles");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "show_icon", "ShowIcon");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "hidden_effect", "HiddenEffect");
                BlockItemPacketRewriter1_20_2.renameTag(effectTag, "factor_calculation_data", "FactorCalculationData");
            }
        }
    }

    private static void renameTag(CompoundTag tag, String entryName, String toEntryName) {
        Object entry = tag.remove(entryName);
        if (entry != null) {
            tag.put(toEntryName, entry);
        }
    }

    private @Nullable CompoundTag handleBlockEntity(@Nullable CompoundTag tag) {
        IntTag secondaryEffect;
        if (tag == null) {
            return null;
        }
        IntTag primaryEffect = (IntTag)tag.remove("Primary");
        if (primaryEffect != null && primaryEffect.asInt() != 0) {
            tag.put("primary_effect", new StringTag(PotionEffects.idToKeyOrLuck(primaryEffect.asInt())));
        }
        if ((secondaryEffect = (IntTag)tag.remove("Secondary")) != null && secondaryEffect.asInt() != 0) {
            tag.put("secondary_effect", new StringTag(PotionEffects.idToKeyOrLuck(secondaryEffect.asInt())));
        }
        return tag;
    }
}

