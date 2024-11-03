package com.viaversion.viarewind.protocol.protocol1_8to1_9.storage;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.serializer.TextComponentSerializer;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.HashMap;

public class Windows extends StoredObject {
   private final HashMap<Short, String> types = new HashMap<>();
   private final HashMap<Short, Item[]> brewingItems = new HashMap<>();

   public Windows(UserConnection user) {
      super(user);
   }

   public String get(short windowId) {
      return this.types.get(windowId);
   }

   public void put(short windowId, String type) {
      this.types.put(windowId, type);
   }

   public void remove(short windowId) {
      this.types.remove(windowId);
      this.brewingItems.remove(windowId);
   }

   public Item[] getBrewingItems(short windowId) {
      return this.brewingItems.computeIfAbsent(windowId, key -> new Item[]{new DataItem(), new DataItem(), new DataItem(), new DataItem()});
   }

   public static void updateBrewingStand(UserConnection user, Item blazePowder, short windowId) {
      if (blazePowder == null || blazePowder.identifier() == 377) {
         int amount = blazePowder == null ? 0 : blazePowder.amount();
         PacketWrapper openWindow = PacketWrapper.create(ClientboundPackets1_8.OPEN_WINDOW, user);
         openWindow.write(Type.UNSIGNED_BYTE, windowId);
         openWindow.write(Type.STRING, "minecraft:brewing_stand");
         ATextComponent title = new StringComponent()
            .append(new TranslationComponent("container.brewing"))
            .append(new StringComponent(": " + TextFormatting.DARK_GRAY))
            .append(new StringComponent(amount + " " + TextFormatting.DARK_RED))
            .append(new TranslationComponent("item.blazePowder.name", TextFormatting.DARK_RED));
         openWindow.write(Type.COMPONENT, TextComponentSerializer.V1_8.serializeJson(title));
         openWindow.write(Type.UNSIGNED_BYTE, (short)420);
         PacketUtil.sendPacket(openWindow, Protocol1_8To1_9.class);
         Item[] items = user.get(Windows.class).getBrewingItems(windowId);

         for (int i = 0; i < items.length; i++) {
            PacketWrapper setSlot = PacketWrapper.create(ClientboundPackets1_8.SET_SLOT, user);
            setSlot.write(Type.UNSIGNED_BYTE, windowId);
            setSlot.write(Type.SHORT, (short)i);
            setSlot.write(Type.ITEM1_8, items[i]);
            PacketUtil.sendPacket(setSlot, Protocol1_8To1_9.class);
         }
      }
   }
}
