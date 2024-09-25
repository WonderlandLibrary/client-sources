/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_12to1_11_1.packets;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.rewriters.ItemRewriter;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.BedRewriter;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.Protocol1_12To1_11_1;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.ServerboundPackets1_12;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;

public class InventoryPackets {
    public static void register(Protocol1_12To1_11_1 protocol) {
        ItemRewriter itemRewriter = new ItemRewriter(protocol, BedRewriter::toClientItem, BedRewriter::toServerItem);
        itemRewriter.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM);
        itemRewriter.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM_ARRAY);
        itemRewriter.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM);
        protocol.registerOutgoing(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        if (wrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                            wrapper.passthrough(Type.INT);
                            int size = wrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                            for (int i = 0; i < size; ++i) {
                                BedRewriter.toClientItem(wrapper.passthrough(Type.ITEM));
                                BedRewriter.toClientItem(wrapper.passthrough(Type.ITEM));
                                boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                                if (secondItem) {
                                    BedRewriter.toClientItem(wrapper.passthrough(Type.ITEM));
                                }
                                wrapper.passthrough(Type.BOOLEAN);
                                wrapper.passthrough(Type.INT);
                                wrapper.passthrough(Type.INT);
                            }
                        }
                    }
                });
            }
        });
        protocol.registerIncoming(ServerboundPackets1_12.CLICK_WINDOW, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.UNSIGNED_BYTE);
                this.map(Type.SHORT);
                this.map(Type.BYTE);
                this.map(Type.SHORT);
                this.map(Type.VAR_INT);
                this.map(Type.ITEM);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        Item item = wrapper.get(Type.ITEM, 0);
                        if (!Via.getConfig().is1_12QuickMoveActionFix()) {
                            BedRewriter.toServerItem(item);
                            return;
                        }
                        byte button = wrapper.get(Type.BYTE, 0);
                        int mode = wrapper.get(Type.VAR_INT, 0);
                        if (mode == 1 && button == 0 && item == null) {
                            short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
                            short slotId = wrapper.get(Type.SHORT, 0);
                            short actionId = wrapper.get(Type.SHORT, 1);
                            InventoryQuickMoveProvider provider = Via.getManager().getProviders().get(InventoryQuickMoveProvider.class);
                            boolean succeed = provider.registerQuickMoveAction(windowId, slotId, actionId, wrapper.user());
                            if (succeed) {
                                wrapper.cancel();
                            }
                        } else {
                            BedRewriter.toServerItem(item);
                        }
                    }
                });
            }
        });
        itemRewriter.registerCreativeInvAction(ServerboundPackets1_12.CREATIVE_INVENTORY_ACTION, Type.ITEM);
    }
}

