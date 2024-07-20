/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_20to1_19_4.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.RecipeRewriter1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20to1_19_4.Protocol1_20To1_19_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.util.Key;

public final class InventoryPackets
extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_4, Protocol1_20To1_19_4> {
    public InventoryPackets(Protocol1_20To1_19_4 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        final BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter<ClientboundPackets1_19_4>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_19_4.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19_4.BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_19_4.EFFECT, 1010, 2001);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, this::handleBlockEntity);
        this.registerOpenWindow(ClientboundPackets1_19_4.OPEN_WINDOW);
        this.registerSetCooldown(ClientboundPackets1_19_4.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_19_4.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_4.SET_SLOT);
        this.registerEntityEquipmentArray(ClientboundPackets1_19_4.ENTITY_EQUIPMENT);
        this.registerClickWindow1_17_1(ServerboundPackets1_19_4.CLICK_WINDOW);
        this.registerTradeList1_19(ClientboundPackets1_19_4.TRADE_LIST);
        this.registerCreativeInvAction(ServerboundPackets1_19_4.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19_4.WINDOW_PROPERTY);
        this.registerSpawnParticle1_19(ClientboundPackets1_19_4.SPAWN_PARTICLE);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.ADVANCEMENTS, wrapper -> {
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
                    this.handleItemToClient(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    wrapper.passthrough(Type.VAR_INT);
                    int flags = wrapper.passthrough(Type.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Type.STRING);
                    }
                    wrapper.passthrough(Type.FLOAT);
                    wrapper.passthrough(Type.FLOAT);
                }
                wrapper.passthrough(Type.STRING_ARRAY);
                int requirements = wrapper.passthrough(Type.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Type.STRING_ARRAY);
                }
                wrapper.write(Type.BOOLEAN, false);
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, wrapper -> {
            wrapper.passthrough(Type.POSITION1_14);
            wrapper.write(Type.BOOLEAN, true);
        });
        ((Protocol1_20To1_19_4)this.protocol).registerServerbound(ServerboundPackets1_19_4.UPDATE_SIGN, wrapper -> {
            wrapper.passthrough(Type.POSITION1_14);
            boolean frontText = wrapper.read(Type.BOOLEAN);
            if (!frontText) {
                wrapper.cancel();
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.CHUNK_DATA, new PacketHandlers(){

            @Override
            protected void register() {
                this.handler(blockRewriter.chunkDataHandler1_19(Chunk1_18Type::new, x$0 -> InventoryPackets.this.handleBlockEntity(x$0)));
                this.read(Type.BOOLEAN);
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_LIGHT, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.passthrough(Type.VAR_INT);
            wrapper.read(Type.BOOLEAN);
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.LONG);
                this.read(Type.BOOLEAN);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                        record.setBlockId(((Protocol1_20To1_19_4)InventoryPackets.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
        RecipeRewriter1_19_4 recipeRewriter = new RecipeRewriter1_19_4(this.protocol);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.DECLARE_RECIPES, wrapper -> {
            int size;
            int newSize = size = wrapper.passthrough(Type.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                String type2 = wrapper.read(Type.STRING);
                String cutType = Key.stripMinecraftNamespace(type2);
                if (cutType.equals("smithing")) {
                    --newSize;
                    wrapper.read(Type.STRING);
                    wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                    wrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                    wrapper.read(Type.FLAT_VAR_INT_ITEM);
                    continue;
                }
                wrapper.write(Type.STRING, type2);
                wrapper.passthrough(Type.STRING);
                recipeRewriter.handleRecipeType(wrapper, cutType);
            }
            wrapper.set(Type.VAR_INT, 0, newSize);
        });
    }

    private void handleBlockEntity(BlockEntity blockEntity) {
        Object glowing;
        Object color;
        if (blockEntity.typeId() != 7 && blockEntity.typeId() != 8) {
            return;
        }
        CompoundTag tag = blockEntity.tag();
        CompoundTag frontText = new CompoundTag();
        tag.put("front_text", frontText);
        ListTag messages = new ListTag(StringTag.class);
        for (int i = 1; i < 5; ++i) {
            Object text = tag.remove("Text" + i);
            messages.add((Tag)(text != null ? text : new StringTag(ChatRewriter.emptyComponentString())));
        }
        frontText.put("messages", messages);
        ListTag filteredMessages = new ListTag(StringTag.class);
        for (int i = 1; i < 5; ++i) {
            Object text = tag.remove("FilteredText" + i);
            filteredMessages.add((Tag)(text != null ? text : messages.get(i - 1)));
        }
        if (!filteredMessages.equals(messages)) {
            frontText.put("filtered_messages", filteredMessages);
        }
        if ((color = tag.remove("Color")) != null) {
            frontText.put("color", color);
        }
        if ((glowing = tag.remove("GlowingText")) != null) {
            frontText.put("has_glowing_text", glowing);
        }
    }
}

