/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.packets;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ClientboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_16_2to1_16_1.ServerboundPackets1_16_2;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ClientboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.Protocol1_17To1_16_4;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.storage.InventoryAcknowledgements;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public final class InventoryPackets
extends ItemRewriter<ClientboundPackets1_16_2, ServerboundPackets1_17, Protocol1_17To1_16_4> {
    public InventoryPackets(Protocol1_17To1_16_4 protocol) {
        super(protocol);
    }

    @Override
    public void registerPackets() {
        this.registerSetCooldown(ClientboundPackets1_16_2.COOLDOWN);
        this.registerWindowItems(ClientboundPackets1_16_2.WINDOW_ITEMS, Type.FLAT_VAR_INT_ITEM_ARRAY);
        this.registerTradeList(ClientboundPackets1_16_2.TRADE_LIST);
        this.registerSetSlot(ClientboundPackets1_16_2.SET_SLOT, Type.FLAT_VAR_INT_ITEM);
        this.registerAdvancements(ClientboundPackets1_16_2.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
        this.registerEntityEquipmentArray(ClientboundPackets1_16_2.ENTITY_EQUIPMENT);
        this.registerSpawnParticle(ClientboundPackets1_16_2.SPAWN_PARTICLE, Type.FLAT_VAR_INT_ITEM, Type.DOUBLE);
        new RecipeRewriter<ClientboundPackets1_16_2>(this.protocol).register(ClientboundPackets1_16_2.DECLARE_RECIPES);
        this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.FLAT_VAR_INT_ITEM);
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.EDIT_BOOK, wrapper -> this.handleItemToServer(wrapper.passthrough(Type.FLAT_VAR_INT_ITEM)));
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.CLICK_WINDOW, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.handler(wrapper -> wrapper.write(Type.SHORT, (short)0));
                this.map(Type.VAR_INT);
                this.handler(wrapper -> {
                    int length = wrapper.read(Type.VAR_INT);
                    for (int i = 0; i < length; ++i) {
                        wrapper.read(Type.SHORT);
                        wrapper.read(Type.FLAT_VAR_INT_ITEM);
                    }
                    Item item = wrapper.read(Type.FLAT_VAR_INT_ITEM);
                    int action = wrapper.get(Type.VAR_INT, 0);
                    if (action == 5 || action == 1) {
                        item = null;
                    } else {
                        InventoryPackets.this.handleItemToServer(item);
                    }
                    wrapper.write(Type.FLAT_VAR_INT_ITEM, item);
                });
            }
        });
        ((Protocol1_17To1_16_4)this.protocol).registerClientbound(ClientboundPackets1_16_2.WINDOW_CONFIRMATION, null, wrapper -> {
            short inventoryId = wrapper.read(Type.UNSIGNED_BYTE);
            short confirmationId = wrapper.read(Type.SHORT);
            boolean accepted = wrapper.read(Type.BOOLEAN);
            if (!accepted) {
                int id = 0x40000000 | inventoryId << 16 | confirmationId & 0xFFFF;
                wrapper.user().get(InventoryAcknowledgements.class).addId(id);
                PacketWrapper pingPacket = wrapper.create(ClientboundPackets1_17.PING);
                pingPacket.write(Type.INT, id);
                pingPacket.send(Protocol1_17To1_16_4.class);
            }
            wrapper.cancel();
        });
        ((Protocol1_17To1_16_4)this.protocol).registerServerbound(ServerboundPackets1_17.PONG, null, wrapper -> {
            int id = wrapper.read(Type.INT);
            if ((id & 0x40000000) != 0 && wrapper.user().get(InventoryAcknowledgements.class).removeId(id)) {
                short inventoryId = (short)(id >> 16 & 0xFF);
                short confirmationId = (short)(id & 0xFFFF);
                PacketWrapper packet = wrapper.create(ServerboundPackets1_16_2.WINDOW_CONFIRMATION);
                packet.write(Type.UNSIGNED_BYTE, inventoryId);
                packet.write(Type.SHORT, confirmationId);
                packet.write(Type.BOOLEAN, true);
                packet.sendToServer(Protocol1_17To1_16_4.class);
            }
            wrapper.cancel();
        });
    }

    @Override
    public Item handleItemToClient(Item item) {
        if (item == null) {
            return null;
        }
        CompoundTag tag = item.tag();
        if (item.identifier() == 733) {
            if (tag == null) {
                tag = new CompoundTag();
                item.setTag(tag);
            }
            if (!(tag.get("map") instanceof NumberTag)) {
                tag.put("map", new IntTag(0));
            }
        }
        item.setIdentifier(((Protocol1_17To1_16_4)this.protocol).getMappingData().getNewItemId(item.identifier()));
        return item;
    }
}

