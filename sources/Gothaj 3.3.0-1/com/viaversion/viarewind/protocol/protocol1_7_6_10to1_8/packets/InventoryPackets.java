package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ServerboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.FurnaceData;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.InventoryTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.UUID;

public class InventoryPackets {
   public static void register(final Protocol1_7_6_10To1_8 protocol) {
      protocol.registerClientbound(ClientboundPackets1_8.OPEN_WINDOW, wrapper -> {
         InventoryTracker windowTracker = wrapper.user().get(InventoryTracker.class);
         short windowId = wrapper.passthrough(Type.UNSIGNED_BYTE);
         short windowTypeId = InventoryTracker.getInventoryType(wrapper.read(Type.STRING));
         windowTracker.getWindowTypeMap().put(windowId, windowTypeId);
         wrapper.write(Type.UNSIGNED_BYTE, windowTypeId);
         JsonElement titleComponent = wrapper.read(Type.COMPONENT);
         String title = ChatUtil.jsonToLegacy(titleComponent);
         title = ChatUtil.removeUnusedColor(title, '8');
         if (title.length() > 32) {
            title = title.substring(0, 32);
         }

         wrapper.write(Type.STRING, title);
         wrapper.passthrough(Type.UNSIGNED_BYTE);
         wrapper.write(Type.BOOLEAN, true);
         if (windowTypeId == 11) {
            wrapper.passthrough(Type.INT);
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.CLOSE_WINDOW, wrapper -> {
         short windowId = wrapper.passthrough(Type.UNSIGNED_BYTE);
         wrapper.user().get(InventoryTracker.class).remove(windowId);
      });
      protocol.registerClientbound(ClientboundPackets1_8.SET_SLOT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.handler(wrapper -> {
               short windowType = wrapper.user().get(InventoryTracker.class).get(wrapper.get(Type.UNSIGNED_BYTE, 0));
               short slot = wrapper.get(Type.SHORT, 0);
               if (windowType == 4) {
                  if (slot == 1) {
                     wrapper.cancel();
                  } else if (slot >= 2) {
                     wrapper.set(Type.SHORT, 0, (short)(slot - 1));
                  }
               }
            });
            this.map(Type.ITEM1_8, Types1_7_6_10.COMPRESSED_NBT_ITEM);
            this.handler(wrapper -> {
               Item item = wrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
               protocol.getItemRewriter().handleItemToClient(item);
               wrapper.set(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0, item);
            });
            this.handler(wrapper -> {
               short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
               if (windowId == 0) {
                  short slot = wrapper.get(Type.SHORT, 0);
                  if (slot >= 5 && slot <= 8) {
                     PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
                     Item item = wrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM, 0);
                     playerSession.setPlayerEquipment(wrapper.user().getProtocolInfo().getUuid(), item, 8 - slot);
                     if (playerSession.isSpectator()) {
                        wrapper.cancel();
                     }
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.WINDOW_ITEMS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.handler(wrapper -> {
               short windowType = wrapper.user().get(InventoryTracker.class).get(wrapper.get(Type.UNSIGNED_BYTE, 0));
               Item[] items = wrapper.read(Type.ITEM1_8_SHORT_ARRAY);
               if (windowType == 4) {
                  Item[] old = items;
                  items = new Item[items.length - 1];
                  items[0] = old[0];
                  System.arraycopy(old, 2, items, 1, old.length - 3);
               }

               for (int i = 0; i < items.length; i++) {
                  items[i] = protocol.getItemRewriter().handleItemToClient(items[i]);
               }

               wrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, items);
            });
            this.handler(wrapper -> {
               short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
               if (windowId == 0) {
                  UUID userId = wrapper.user().getProtocolInfo().getUuid();
                  PlayerSessionStorage playerSession = wrapper.user().get(PlayerSessionStorage.class);
                  Item[] items = wrapper.get(Types1_7_6_10.COMPRESSED_NBT_ITEM_ARRAY, 0);

                  for (int i = 5; i < 9; i++) {
                     playerSession.setPlayerEquipment(userId, items[i], 8 - i);
                     if (playerSession.isSpectator()) {
                        items[i] = null;
                     }
                  }

                  if (playerSession.isSpectator()) {
                     GameProfileStorage.GameProfile profile = wrapper.user().get(GameProfileStorage.class).get(userId);
                     if (profile != null) {
                        items[5] = profile.getSkull();
                     }
                  }
               }
            });
         }
      });
      protocol.registerClientbound(ClientboundPackets1_8.WINDOW_PROPERTY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.handler(wrapper -> {
               InventoryTracker windowTracker = wrapper.user().get(InventoryTracker.class);
               short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
               short windowType = windowTracker.get(windowId);
               short progressBarId = wrapper.get(Type.SHORT, 0);
               short progress = wrapper.get(Type.SHORT, 1);
               if (windowType == 2) {
                  FurnaceData furnace = windowTracker.getFurnaceData().computeIfAbsent(windowId, x -> new FurnaceData());
                  if (progressBarId != 0 && progressBarId != 1) {
                     if (progressBarId == 2 || progressBarId == 3) {
                        if (progressBarId == 2) {
                           furnace.progress = progress;
                        } else {
                           furnace.maxProgress = progress;
                        }

                        if (furnace.maxProgress == 0) {
                           wrapper.cancel();
                           return;
                        }

                        progress = (short)(200 * furnace.progress / furnace.maxProgress);
                        wrapper.set(Type.SHORT, 0, (short)0);
                        wrapper.set(Type.SHORT, 1, progress);
                     }
                  } else {
                     if (progressBarId == 0) {
                        furnace.fuelLeft = progress;
                     } else {
                        furnace.maxFuel = progress;
                     }

                     if (furnace.maxFuel == 0) {
                        wrapper.cancel();
                        return;
                     }

                     progress = (short)(200 * furnace.fuelLeft / furnace.maxFuel);
                     wrapper.set(Type.SHORT, 0, (short)1);
                     wrapper.set(Type.SHORT, 1, progress);
                  }
               } else if (windowType == 4 && progressBarId > 2) {
                  wrapper.cancel();
               } else if (windowType == 8) {
                  windowTracker.levelCost = progress;
                  windowTracker.anvilId = windowId;
               }
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.CLOSE_WINDOW, wrapper -> {
         short windowId = wrapper.passthrough(Type.UNSIGNED_BYTE);
         wrapper.user().get(InventoryTracker.class).remove(windowId);
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.CLICK_WINDOW, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.BYTE, Type.UNSIGNED_BYTE);
            this.map(Type.SHORT);
            this.handler(wrapper -> {
               short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
               short slot = wrapper.get(Type.SHORT, 0);
               short windowType = wrapper.user().get(InventoryTracker.class).get(windowId);
               if (windowType == 4 && slot > 0) {
                  wrapper.set(Type.SHORT, 0, (short)(slot + 1));
               }
            });
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.BYTE);
            this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM1_8);
            this.handler(wrapper -> {
               Item item = wrapper.get(Type.ITEM1_8, 0);
               protocol.getItemRewriter().handleItemToServer(item);
               wrapper.set(Type.ITEM1_8, 0, item);
            });
         }
      });
      protocol.registerServerbound(ServerboundPackets1_7_2_5.CREATIVE_INVENTORY_ACTION, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.SHORT);
            this.map(Types1_7_6_10.COMPRESSED_NBT_ITEM, Type.ITEM1_8);
            this.handler(wrapper -> {
               Item item = wrapper.get(Type.ITEM1_8, 0);
               protocol.getItemRewriter().handleItemToServer(item);
               wrapper.set(Type.ITEM1_8, 0, item);
            });
         }
      });
   }
}
