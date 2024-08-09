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
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ServerboundPackets1_19_4;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.RecipeRewriter1_19_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;

public final class BlockItemPackets1_20
extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_4, Protocol1_19_4To1_20> {
    public BlockItemPackets1_20(Protocol1_19_4To1_20 protocol1_19_4To1_20) {
        super(protocol1_19_4To1_20);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter<ClientboundPackets1_19_4>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_19_4.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19_4.BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_19_4.EFFECT, 1010, 2001);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA, this::handleBlockEntity);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.CHUNK_DATA, new PacketHandlers(this, blockRewriter){
            final BlockRewriter val$blockRewriter;
            final BlockItemPackets1_20 this$0;
            {
                this.this$0 = blockItemPackets1_20;
                this.val$blockRewriter = blockRewriter;
            }

            @Override
            protected void register() {
                this.handler(this.val$blockRewriter.chunkDataHandler1_19(Chunk1_18Type::new, arg_0 -> 1.lambda$register$0(this.this$0, arg_0)));
                this.create(Type.BOOLEAN, true);
            }

            private static void lambda$register$0(BlockItemPackets1_20 blockItemPackets1_20, BlockEntity blockEntity) {
                BlockItemPackets1_20.access$000(blockItemPackets1_20, blockEntity);
            }
        });
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.UPDATE_LIGHT, BlockItemPackets1_20::lambda$registerPackets$0);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE, new PacketHandlers(this){
            final BlockItemPackets1_20 this$0;
            {
                this.this$0 = blockItemPackets1_20;
            }

            @Override
            public void register() {
                this.map(Type.LONG);
                this.create(Type.BOOLEAN, false);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                for (BlockChangeRecord blockChangeRecord : packetWrapper.passthrough(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY)) {
                    blockChangeRecord.setBlockId(((Protocol1_19_4To1_20)BlockItemPackets1_20.access$100(this.this$0)).getMappingData().getNewBlockStateId(blockChangeRecord.getBlockId()));
                }
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
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.ADVANCEMENTS, this::lambda$registerPackets$1);
        ((Protocol1_19_4To1_20)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_SIGN_EDITOR, BlockItemPackets1_20::lambda$registerPackets$2);
        ((Protocol1_19_4To1_20)this.protocol).registerServerbound(ServerboundPackets1_19_4.UPDATE_SIGN, BlockItemPackets1_20::lambda$registerPackets$3);
        new RecipeRewriter1_19_4<ClientboundPackets1_19_4>(this.protocol).register(ClientboundPackets1_19_4.DECLARE_RECIPES);
    }

    private void handleBlockEntity(BlockEntity blockEntity) {
        if (blockEntity.typeId() != 7 && blockEntity.typeId() != 8) {
            return;
        }
        CompoundTag compoundTag = blockEntity.tag();
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.remove("front_text");
        compoundTag.remove("back_text");
        if (compoundTag2 != null) {
            Object t;
            this.writeMessages(compoundTag2, compoundTag, false);
            this.writeMessages(compoundTag2, compoundTag, true);
            Object t2 = compoundTag2.remove("color");
            if (t2 != null) {
                compoundTag.put("Color", t2);
            }
            if ((t = compoundTag2.remove("has_glowing_text")) != null) {
                compoundTag.put("GlowingText", t);
            }
        }
    }

    private void writeMessages(CompoundTag compoundTag, CompoundTag compoundTag2, boolean bl) {
        ListTag listTag = (ListTag)compoundTag.get(bl ? "filtered_messages" : "messages");
        if (listTag == null) {
            return;
        }
        int n = 0;
        for (Tag tag : listTag) {
            compoundTag2.put((bl ? "FilteredText" : "Text") + ++n, tag);
        }
    }

    private static void lambda$registerPackets$3(PacketWrapper packetWrapper) throws Exception {
        Position position = packetWrapper.passthrough(Type.POSITION1_14);
        BackSignEditStorage backSignEditStorage = packetWrapper.user().remove(BackSignEditStorage.class);
        boolean bl = backSignEditStorage == null || !backSignEditStorage.position().equals(position);
        packetWrapper.write(Type.BOOLEAN, bl);
    }

    private static void lambda$registerPackets$2(PacketWrapper packetWrapper) throws Exception {
        Position position = packetWrapper.passthrough(Type.POSITION1_14);
        boolean bl = packetWrapper.read(Type.BOOLEAN);
        if (bl) {
            packetWrapper.user().remove(BackSignEditStorage.class);
        } else {
            packetWrapper.user().put(new BackSignEditStorage(position));
        }
    }

    private void lambda$registerPackets$1(PacketWrapper packetWrapper) throws Exception {
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
            packetWrapper.read(Type.BOOLEAN);
        }
    }

    private static void lambda$registerPackets$0(PacketWrapper packetWrapper) throws Exception {
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.passthrough(Type.VAR_INT);
        packetWrapper.write(Type.BOOLEAN, true);
    }

    static void access$000(BlockItemPackets1_20 blockItemPackets1_20, BlockEntity blockEntity) {
        blockItemPackets1_20.handleBlockEntity(blockEntity);
    }

    static Protocol access$100(BlockItemPackets1_20 blockItemPackets1_20) {
        return blockItemPackets1_20.protocol;
    }
}

