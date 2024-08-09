/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public final class InventoryPackets
extends ItemRewriter<ClientboundPackets1_17_1, ServerboundPackets1_17, Protocol1_18To1_17_1> {
    public InventoryPackets(Protocol1_18To1_17_1 protocol1_18To1_17_1) {
        super(protocol1_18To1_17_1);
    }

    @Override
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_17_1.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_17_1.WINDOW_ITEMS);
        this.registerTradeList(ClientboundPackets1_17_1.TRADE_LIST);
        this.registerSetSlot1_17_1(ClientboundPackets1_17_1.SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_17_1.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_17_1.ENTITY_EQUIPMENT);
        ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_17_1.EFFECT, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
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
                    packetWrapper.set(Type.INT, 1, ((Protocol1_18To1_17_1)InventoryPackets.access$000(this.this$0)).getMappingData().getNewItemId(n2));
                }
            }
        });
        ((Protocol1_18To1_17_1)this.protocol).registerClientbound(ClientboundPackets1_17_1.SPAWN_PARTICLE, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
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
                if (n2 == 2) {
                    packetWrapper.set(Type.INT, 0, 3);
                    packetWrapper.write(Type.VAR_INT, 7754);
                    return;
                }
                if (n2 == 3) {
                    packetWrapper.write(Type.VAR_INT, 7786);
                    return;
                }
                ParticleMappings particleMappings = ((Protocol1_18To1_17_1)InventoryPackets.access$100(this.this$0)).getMappingData().getParticleMappings();
                if (particleMappings.isBlockParticle(n2)) {
                    n = packetWrapper.passthrough(Type.VAR_INT);
                    packetWrapper.set(Type.VAR_INT, 0, ((Protocol1_18To1_17_1)InventoryPackets.access$200(this.this$0)).getMappingData().getNewBlockStateId(n));
                } else if (particleMappings.isItemParticle(n2)) {
                    this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                }
                n = ((Protocol1_18To1_17_1)InventoryPackets.access$300(this.this$0)).getMappingData().getNewParticleId(n2);
                if (n != n2) {
                    packetWrapper.set(Type.INT, 0, n);
                }
            }
        });
        new RecipeRewriter<ClientboundPackets1_17_1>(this.protocol).register(ClientboundPackets1_17_1.DECLARE_RECIPES);
        this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW);
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
    }

    static Protocol access$000(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }

    static Protocol access$100(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }

    static Protocol access$200(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }

    static Protocol access$300(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }
}

