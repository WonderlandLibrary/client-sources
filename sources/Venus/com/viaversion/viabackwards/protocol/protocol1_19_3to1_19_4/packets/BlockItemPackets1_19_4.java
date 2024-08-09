/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.packets;

import com.viaversion.viabackwards.api.rewriters.ItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4.Protocol1_19_3To1_19_4;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types.Chunk1_18Type;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.ServerboundPackets1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.rewriter.RecipeRewriter1_19_3;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import com.viaversion.viaversion.util.Key;

public final class BlockItemPackets1_19_4
extends ItemRewriter<ClientboundPackets1_19_4, ServerboundPackets1_19_3, Protocol1_19_3To1_19_4> {
    public BlockItemPackets1_19_4(Protocol1_19_3To1_19_4 protocol1_19_3To1_19_4) {
        super(protocol1_19_3To1_19_4);
    }

    @Override
    public void registerPackets() {
        BlockRewriter<ClientboundPackets1_19_4> blockRewriter = new BlockRewriter<ClientboundPackets1_19_4>(this.protocol, Type.POSITION1_14);
        blockRewriter.registerBlockAction(ClientboundPackets1_19_4.BLOCK_ACTION);
        blockRewriter.registerBlockChange(ClientboundPackets1_19_4.BLOCK_CHANGE);
        blockRewriter.registerVarLongMultiBlockChange(ClientboundPackets1_19_4.MULTI_BLOCK_CHANGE);
        blockRewriter.registerEffect(ClientboundPackets1_19_4.EFFECT, 1010, 2001);
        blockRewriter.registerChunkData1_19(ClientboundPackets1_19_4.CHUNK_DATA, Chunk1_18Type::new);
        blockRewriter.registerBlockEntityData(ClientboundPackets1_19_4.BLOCK_ENTITY_DATA);
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.OPEN_WINDOW, new PacketHandlers(this){
            final BlockItemPackets1_19_4 this$0;
            {
                this.this$0 = blockItemPackets1_19_4;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.VAR_INT);
                this.map(Type.COMPONENT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.get(Type.VAR_INT, 1);
                if (n == 21) {
                    packetWrapper.cancel();
                } else if (n > 21) {
                    packetWrapper.set(Type.VAR_INT, 1, n - 1);
                }
                ((Protocol1_19_3To1_19_4)BlockItemPackets1_19_4.access$000(this.this$0)).getTranslatableRewriter().processText(packetWrapper.get(Type.COMPONENT, 0));
            }
        });
        this.registerSetCooldown(ClientboundPackets1_19_4.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_19_4.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_19_4.SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_19_4.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_19_4.ENTITY_EQUIPMENT);
        this.registerClickWindow1_17_1(ServerboundPackets1_19_3.CLICK_WINDOW);
        this.registerTradeList1_19(ClientboundPackets1_19_4.TRADE_LIST);
        this.registerCreativeInvAction(ServerboundPackets1_19_3.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_19_4.WINDOW_PROPERTY);
        this.registerSpawnParticle1_19(ClientboundPackets1_19_4.SPAWN_PARTICLE);
        RecipeRewriter1_19_3<ClientboundPackets1_19_4> recipeRewriter1_19_3 = new RecipeRewriter1_19_3<ClientboundPackets1_19_4>(this, this.protocol){
            final BlockItemPackets1_19_4 this$0;
            {
                this.this$0 = blockItemPackets1_19_4;
                super(protocol);
            }

            @Override
            public void handleCraftingShaped(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.passthrough(Type.VAR_INT) * packetWrapper.passthrough(Type.VAR_INT);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < n; ++i) {
                    this.handleIngredient(packetWrapper);
                }
                this.rewrite(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                packetWrapper.read(Type.BOOLEAN);
            }
        };
        ((Protocol1_19_3To1_19_4)this.protocol).registerClientbound(ClientboundPackets1_19_4.DECLARE_RECIPES, arg_0 -> BlockItemPackets1_19_4.lambda$registerPackets$0(recipeRewriter1_19_3, arg_0));
    }

    private static void lambda$registerPackets$0(RecipeRewriter1_19_3 recipeRewriter1_19_3, PacketWrapper packetWrapper) throws Exception {
        int n;
        int n2 = n = packetWrapper.passthrough(Type.VAR_INT).intValue();
        for (int i = 0; i < n; ++i) {
            String string = packetWrapper.read(Type.STRING);
            String string2 = Key.stripMinecraftNamespace(string);
            if (string2.equals("smithing_transform") || string2.equals("smithing_trim")) {
                --n2;
                packetWrapper.read(Type.STRING);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
                if (!string2.equals("smithing_transform")) continue;
                packetWrapper.read(Type.FLAT_VAR_INT_ITEM);
                continue;
            }
            if (string2.equals("crafting_decorated_pot")) {
                --n2;
                packetWrapper.read(Type.STRING);
                packetWrapper.read(Type.VAR_INT);
                continue;
            }
            packetWrapper.write(Type.STRING, string);
            packetWrapper.passthrough(Type.STRING);
            recipeRewriter1_19_3.handleRecipeType(packetWrapper, string2);
        }
        packetWrapper.set(Type.VAR_INT, 0, n2);
    }

    static Protocol access$000(BlockItemPackets1_19_4 blockItemPackets1_19_4) {
        return blockItemPackets1_19_4.protocol;
    }
}

