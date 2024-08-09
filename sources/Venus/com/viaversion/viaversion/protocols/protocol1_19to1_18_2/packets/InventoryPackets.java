/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.ClientboundPackets1_18;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.Protocol1_19To1_18_2;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;
import com.viaversion.viaversion.util.Key;

public final class InventoryPackets
extends ItemRewriter<ClientboundPackets1_18, ServerboundPackets1_19, Protocol1_19To1_18_2> {
    public InventoryPackets(Protocol1_19To1_18_2 protocol1_19To1_18_2) {
        super(protocol1_19To1_18_2);
    }

    @Override
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_18.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_18.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_18.SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_18.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_18.ENTITY_EQUIPMENT);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_PARTICLE, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map((Type)Type.INT, Type.VAR_INT);
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
                this.handler(this.this$0.getSpawnParticleHandler(Type.VAR_INT, Type.FLAT_VAR_INT_ITEM));
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                ParticleMappings particleMappings;
                int n = packetWrapper.get(Type.VAR_INT, 0);
                if (n == (particleMappings = ((Protocol1_19To1_18_2)InventoryPackets.access$000(this.this$0)).getMappingData().getParticleMappings()).id("vibration")) {
                    packetWrapper.read(Type.POSITION1_14);
                    String string = Key.stripMinecraftNamespace(packetWrapper.passthrough(Type.STRING));
                    if (string.equals("entity")) {
                        packetWrapper.passthrough(Type.VAR_INT);
                        packetWrapper.write(Type.FLOAT, Float.valueOf(0.0f));
                    }
                }
            }
        });
        this.registerClickWindow1_17_1(ServerboundPackets1_19.CLICK_WINDOW);
        this.registerCreativeInvAction(ServerboundPackets1_19.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_18.WINDOW_PROPERTY);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.TRADE_LIST, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(this::lambda$register$0);
            }

            private void lambda$register$0(PacketWrapper packetWrapper) throws Exception {
                int n = packetWrapper.read(Type.UNSIGNED_BYTE).shortValue();
                packetWrapper.write(Type.VAR_INT, n);
                for (int i = 0; i < n; ++i) {
                    this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    if (packetWrapper.read(Type.BOOLEAN).booleanValue()) {
                        this.this$0.handleItemToClient(packetWrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                    } else {
                        packetWrapper.write(Type.FLAT_VAR_INT_ITEM, null);
                    }
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.FLOAT);
                    packetWrapper.passthrough(Type.INT);
                }
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_19.PLAYER_DIGGING, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(InventoryPackets.access$100(this.this$0));
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_19.PLAYER_BLOCK_PLACEMENT, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(InventoryPackets.access$100(this.this$0));
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_19.USE_ITEM, new PacketHandlers(this){
            final InventoryPackets this$0;
            {
                this.this$0 = inventoryPackets;
            }

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(InventoryPackets.access$100(this.this$0));
            }
        });
        new RecipeRewriter<ClientboundPackets1_18>(this.protocol).register(ClientboundPackets1_18.DECLARE_RECIPES);
    }

    private PacketHandler sequenceHandler() {
        return InventoryPackets::lambda$sequenceHandler$0;
    }

    private static void lambda$sequenceHandler$0(PacketWrapper packetWrapper) throws Exception {
        int n = packetWrapper.read(Type.VAR_INT);
        AckSequenceProvider ackSequenceProvider = Via.getManager().getProviders().get(AckSequenceProvider.class);
        ackSequenceProvider.handleSequence(packetWrapper.user(), n);
    }

    static Protocol access$000(InventoryPackets inventoryPackets) {
        return inventoryPackets.protocol;
    }

    static PacketHandler access$100(InventoryPackets inventoryPackets) {
        return inventoryPackets.sequenceHandler();
    }
}

