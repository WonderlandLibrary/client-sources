/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_20to1_19_4.packets;

import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
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
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class InventoryPackets
extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_4, Protocol1_20To1_19_4> {
    public InventoryPackets(Protocol1_20To1_19_4 protocol1_20To1_19_4) {
        super(protocol1_20To1_19_4);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter<ClientboundPackets1_19_4>(this.protocol, Type.POSITION1_14);
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
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.ADVANCEMENTS, this::lambda$registerPackets$0);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, InventoryPackets::lambda$registerPackets$1);
        ((Protocol1_20To1_19_4)this.protocol).registerServerbound(ServerboundPackets1_19_4.UPDATE_SIGN, InventoryPackets::lambda$registerPackets$2);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.CHUNK_DATA, new PacketHandlers(this, blockRewriter){
            final BlockRewriter val$blockRewriter;
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
                this.val$blockRewriter = blockRewriter;
            }

            @Override
            protected void register() {
                this.handler(this.val$blockRewriter.chunkDataHandler1_19(Chunk1_18Type::new, arg_0 -> 1.lambda$register$0(this.this$0, arg_0)));
                this.read(Type.BOOLEAN);
            }

            private static void lambda$register$0(InventoryPackets inventoryPackets, BlockEntity blockEntity) {
                InventoryPackets.access$000(inventoryPackets, blockEntity);
            }
        });
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_LIGHT, InventoryPackets::lambda$registerPackets$3);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.LONG);
                this.read(Type.BOOLEAN);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (BlockChangeRecord blockChangeRecord : packetWrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                    blockChangeRecord.setBlockId(((Protocol1_20To1_19_4)InventoryPackets.access$100(this.this$0)).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                }
            }
        });
        RecipeRewriter1_19_4 recipeRewriter1_19_4 = new RecipeRewriter1_19_4(this.protocol);
        ((Protocol1_20To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.DECLARE_RECIPES, arg_0 -> InventoryPackets.lambda$registerPackets$4(recipeRewriter1_19_4, arg_0));
    }

    private void handleBlockEntity(BlockEntity blockEntity) {
        Object t;
        if (blockEntity.typeId() != 7 && blockEntity.typeId() != 8) {
            return;
        }
        CompoundTag compoundTag = blockEntity.tag();
        CompoundTag compoundTag2 = new CompoundTag();
        compoundTag.put("front_text", compoundTag2);
        ListTag listTag = new ListTag(StringTag.class);
        for (int i = 1; i < 5; ++i) {
            Object t2 = compoundTag.get("Text" + i);
            listTag.add((Tag)(t2 != null ? t2 : new StringTag(ChatRewriter.emptyComponentString())));
        }
        compoundTag2.put("messages", listTag);
        ListTag listTag2 = new ListTag(StringTag.class);
        for (int i = 1; i < 5; ++i) {
            t = compoundTag.get("FilteredText" + i);
            listTag2.add((Tag)(t != null ? t : new StringTag(ChatRewriter.emptyComponentString())));
        }
        compoundTag2.put("filtered_messages", listTag2);
        Object t3 = compoundTag.remove("Color");
        if (t3 != null) {
            compoundTag2.put("color", t3);
        }
        if ((t = compoundTag.remove("GlowingText")) != null) {
            compoundTag2.put("has_glowing_text", t);
        }
    }

    private static void lambda$registerPackets$4(RecipeRewriter recipeRewriter, PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = n = packetWrapper.passthrough(Type.VAR_INT).intValue();
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.read(Type.STRING);
            String string2 = Key.stripMinecraftNamespace(string);
            if (string2.equals("smithing")) {
                --n2;
                packetWrapper.read(Type.STRING);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                continue;
            }
            packetWrapper.write(Type.STRING, string);
            packetWrapper.passthrough(Type.STRING);
            recipeRewriter.handleRecipeType(packetWrapper, string2);
        }
        packetWrapper.set(Type.VAR_INT, 0, n2);
    }

    private static void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.read(Type.BOOLEAN);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.POSITION1_14);
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        if (!bl) {
            packetWrapper.cancel();
        }
    }

    private static void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.POSITION1_14);
        packetWrapper.write(Type.BOOLEAN, true);
    }

    private void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.BOOLEAN);
        int n = packetWrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < n; ++i) {
            int n2;
            packetWrapper.passthrough(Type.STRING);
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.STRING);
            }
            if (packetWrapper.passthrough(Type.BOOLEAN).booleanValue()) {
                packetWrapper.passthrough(Type.COMPONENT);
                packetWrapper.passthrough(Type.COMPONENT);
                this.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                packetWrapper.passthrough(Type.VAR_INT);
                n2 = packetWrapper.passthrough(Type.INT);
                if ((n2 & 1) != 0) {
                    packetWrapper.passthrough(Type.STRING);
                }
                packetWrapper.passthrough(Type.FLOAT);
                packetWrapper.passthrough(Type.FLOAT);
            }
            packetWrapper.passthrough(Type.STRING_ARRAY);
            n2 = packetWrapper.passthrough(Type.VAR_INT);
            for (int j = 0; j < n2; ++j) {
                packetWrapper.passthrough(Type.STRING_ARRAY);
            }
            packetWrapper.write(Type.BOOLEAN, false);
        }
    }

    static void access$000(InventoryPackets inventoryPackets, BlockEntity blockEntity) {
        inventoryPackets.handleBlockEntity(blockEntity);
    }

    static Protocol access$100(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
}

