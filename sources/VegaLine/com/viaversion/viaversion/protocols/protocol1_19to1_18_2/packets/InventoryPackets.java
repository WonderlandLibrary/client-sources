/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.packets;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.ParticleMappings;
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
    public InventoryPackets(Protocol1_19To1_18_2 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_18.COOLDOWN);
        this.registerWindowItems1_17_1(ClientboundPackets1_18.WINDOW_ITEMS);
        this.registerSetSlot1_17_1(ClientboundPackets1_18.SET_SLOT);
        this.registerAdvancements(ClientboundPackets1_18.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_18.ENTITY_EQUIPMENT);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.SPAWN_PARTICLE, new PacketHandlers(){

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
                this.handler(wrapper -> {
                    ParticleMappings particleMappings;
                    int id = wrapper.get(Type.VAR_INT, 0);
                    if (id == (particleMappings = ((Protocol1_19To1_18_2)InventoryPackets.this.protocol).getMappingData().getParticleMappings()).id("vibration")) {
                        wrapper.read(Type.POSITION1_14);
                        String resourceLocation = Key.stripMinecraftNamespace(wrapper.passthrough(Type.STRING));
                        if (resourceLocation.equals("entity")) {
                            wrapper.passthrough(Type.VAR_INT);
                            wrapper.write(Type.FLOAT, Float.valueOf(0.0f));
                        }
                    }
                });
                this.handler(InventoryPackets.this.getSpawnParticleHandler(Type.VAR_INT));
            }
        });
        this.registerClickWindow1_17_1(ServerboundPackets1_19.CLICK_WINDOW);
        this.registerCreativeInvAction(ServerboundPackets1_19.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        this.registerWindowPropertyEnchantmentHandler(ClientboundPackets1_18.WINDOW_PROPERTY);
        ((Protocol1_19To1_18_2)this.protocol).registerClientbound(ClientboundPackets1_18.TRADE_LIST, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int size = wrapper.read(Type.UNSIGNED_BYTE).shortValue();
                    wrapper.write(Type.VAR_INT, size);
                    for (int i = 0; i < size; ++i) {
                        InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                            InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM));
                        } else {
                            wrapper.write(Type.FLAT_VAR_INT_ITEM, null);
                        }
                        wrapper.passthrough(Type.BOOLEAN);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.INT);
                        wrapper.passthrough(Type.FLOAT);
                        wrapper.passthrough(Type.INT);
                    }
                });
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_19.PLAYER_DIGGING, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14);
                this.map(Type.UNSIGNED_BYTE);
                this.handler(InventoryPackets.this.sequenceHandler());
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_19.PLAYER_BLOCK_PLACEMENT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.map(Type.POSITION1_14);
                this.map(Type.VAR_INT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.FLOAT);
                this.map(Type.BOOLEAN);
                this.handler(InventoryPackets.this.sequenceHandler());
            }
        });
        ((Protocol1_19To1_18_2)this.protocol).registerServerbound(ServerboundPackets1_19.USE_ITEM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.VAR_INT);
                this.handler(InventoryPackets.this.sequenceHandler());
            }
        });
        new RecipeRewriter<ClientboundPackets1_18>(this.protocol).register(ClientboundPackets1_18.DECLARE_RECIPES);
    }

    private PacketHandler sequenceHandler() {
        return wrapper -> {
            int sequence = wrapper.read(Type.VAR_INT);
            AckSequenceProvider provider = Via.getManager().getProviders().get(AckSequenceProvider.class);
            provider.handleSequence(wrapper.user(), sequence);
        };
    }
}

