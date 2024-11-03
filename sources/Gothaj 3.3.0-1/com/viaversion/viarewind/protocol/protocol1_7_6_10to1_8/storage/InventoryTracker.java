package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.FurnaceData;
import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.HashMap;
import java.util.Map;

public class InventoryTracker extends StoredObject {
   public static final Map<String, Integer> WINDOW_TYPE_REGISTRY = new HashMap<>();
   private final HashMap<Short, Short> windowTypeMap = new HashMap<>();
   private final HashMap<Short, FurnaceData> furnaceData = new HashMap<>();
   public short levelCost = 0;
   public short anvilId = -1;

   public InventoryTracker(UserConnection user) {
      super(user);
   }

   public short get(short windowId) {
      return this.windowTypeMap.getOrDefault(windowId, Short.valueOf((short)-1));
   }

   public void remove(short windowId) {
      this.windowTypeMap.remove(windowId);
      this.furnaceData.remove(windowId);
   }

   public static short getInventoryType(String name) {
      return WINDOW_TYPE_REGISTRY.getOrDefault(name, -1).shortValue();
   }

   public HashMap<Short, Short> getWindowTypeMap() {
      return this.windowTypeMap;
   }

   public HashMap<Short, FurnaceData> getFurnaceData() {
      return this.furnaceData;
   }

   static {
      WINDOW_TYPE_REGISTRY.put("minecraft:container", 0);
      WINDOW_TYPE_REGISTRY.put("minecraft:chest", 0);
      WINDOW_TYPE_REGISTRY.put("minecraft:crafting_table", 1);
      WINDOW_TYPE_REGISTRY.put("minecraft:furnace", 2);
      WINDOW_TYPE_REGISTRY.put("minecraft:dispenser", 3);
      WINDOW_TYPE_REGISTRY.put("minecraft:enchanting_table", 4);
      WINDOW_TYPE_REGISTRY.put("minecraft:brewing_stand", 5);
      WINDOW_TYPE_REGISTRY.put("minecraft:villager", 6);
      WINDOW_TYPE_REGISTRY.put("minecraft:beacon", 7);
      WINDOW_TYPE_REGISTRY.put("minecraft:anvil", 8);
      WINDOW_TYPE_REGISTRY.put("minecraft:hopper", 9);
      WINDOW_TYPE_REGISTRY.put("minecraft:dropper", 10);
      WINDOW_TYPE_REGISTRY.put("EntityHorse", 11);
   }
}
