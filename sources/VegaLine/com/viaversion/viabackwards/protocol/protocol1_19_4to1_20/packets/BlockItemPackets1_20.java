/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.Protocol1_19_4To1_20;
import com.viaversion.viabackwards.protocol.protocol1_19_4to1_20.storage.BackSignEditStorage;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.RecipeRewriter1_19_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.Key;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BlockItemPackets1_20
extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_4, Protocol1_19_4To1_20> {
    private static final Set<String> NEW_TRIM_PATTERNS = new HashSet<String>(Arrays.asList("host", "raiser", "shaper", "silence", "wayfinder"));

    public BlockItemPackets1_20(Protocol1_19_4To1_20 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        final BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter<ClientboundPackets1_19_4>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_19_4.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19_4.BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_19_4.EFFECT, 1010, 2001);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, this::handleBlockEntity);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.CHUNK_DATA, new PacketHandlers(){

            @Override
            protected void register() {
                this.handler(blockRewriter.chunkDataHandler1_19(Chunk1_18Type::new, x$0 -> BlockItemPackets1_20.this.handleBlockEntity(x$0)));
                this.create(Type.BOOLEAN, true);
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_LIGHT, wrapper -> {
            wrapper.passthrough(Type.VAR_INT);
            wrapper.passthrough(Type.VAR_INT);
            wrapper.write(Type.BOOLEAN, true);
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.LONG);
                this.create(Type.BOOLEAN, false);
                this.handler(wrapper -> {
                    for (BlockChangeRecord record : wrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                        record.setBlockId(((Protocol1_19_4To1_20)BlockItemPackets1_20.this.protocol).getMappingData().getNewBlockStateId(record.getBlockId()));
                    }
                });
            }
        });
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
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.ADVANCEMENTS, wrapper -> {
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
                int arrayLength = wrapper.passthrough(Type.VAR_INT);
                for (int array = 0; array < arrayLength; ++array) {
                    wrapper.passthrough(Type.STRING_ARRAY);
                }
                wrapper.read(Type.BOOLEAN);
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, wrapper -> {
            Position position = wrapper.passthrough(Type.POSITION1_14);
            boolean frontSide = wrapper.read(Type.BOOLEAN);
            if (frontSide) {
                wrapper.user().remove(BackSignEditStorage.class);
            } else {
                wrapper.user().put(new BackSignEditStorage(position));
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerServerbound(ServerboundPackets1_19_4.UPDATE_SIGN, wrapper -> {
            Position position = wrapper.passthrough(Type.POSITION1_14);
            BackSignEditStorage backSignEditStorage = wrapper.user().remove(BackSignEditStorage.class);
            boolean frontSide = backSignEditStorage == null || !backSignEditStorage.position().equals(position);
            wrapper.write(Type.BOOLEAN, frontSide);
        });
        new RecipeRewriter1_19_4<ClientboundPackets1_19_4>(this.protocol).register(ClientboundPackets1_19_4.DECLARE_RECIPES);
    }

    @Override
    public @Nullable Item handleItemToClient(@Nullable Item item) {
        StringTag patternStringTag;
        String pattern;
        Object patternTag;
        Object trimTag;
        if (item == null) {
            return null;
        }
        super.handleItemToClient(item);
        if (item.tag() != null && (trimTag = item.tag().get("Trim")) instanceof CompoundTag && (patternTag = ((CompoundTag)trimTag).get("pattern")) instanceof StringTag && NEW_TRIM_PATTERNS.contains(pattern = Key.stripMinecraftNamespace((patternStringTag = (StringTag)patternTag).getValue()))) {
            item.tag().remove("Trim");
            item.tag().put(this.nbtTagName + "|Trim", trimTag);
        }
        return item;
    }

    @Override
    public @Nullable Item handleItemToServer(@Nullable Item item) {
        Object trimTag;
        if (item == null) {
            return null;
        }
        super.handleItemToServer(item);
        if (item.tag() != null && (trimTag = item.tag().remove(this.nbtTagName + "|Trim")) != null) {
            item.tag().put("Trim", trimTag);
        }
        return item;
    }

    private void handleBlockEntity(BlockEntity blockEntity) {
        if (blockEntity.typeId() != 7 && blockEntity.typeId() != 8) {
            return;
        }
        CompoundTag tag = blockEntity.tag();
        CompoundTag frontText = (CompoundTag)tag.remove("front_text");
        tag.remove("back_text");
        if (frontText != null) {
            Object glowing;
            this.writeMessages(frontText, tag, false);
            this.writeMessages(frontText, tag, true);
            Object color = frontText.remove("color");
            if (color != null) {
                tag.put("Color", color);
            }
            if ((glowing = frontText.remove("has_glowing_text")) != null) {
                tag.put("GlowingText", glowing);
            }
        }
    }

    private void writeMessages(CompoundTag frontText, CompoundTag tag, boolean filtered) {
        ListTag messages = (ListTag)frontText.get(filtered ? "filtered_messages" : "messages");
        if (messages == null) {
            return;
        }
        int i = 0;
        for (Tag message : messages) {
            tag.put((filtered ? "FilteredText" : "Text") + ++i, message);
        }
    }
}

