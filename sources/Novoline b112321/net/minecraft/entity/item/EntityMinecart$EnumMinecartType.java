package net.minecraft.entity.item;

import com.google.common.collect.Maps;
import java.util.Map;

public enum EntityMinecart$EnumMinecartType {
   RIDEABLE(0, "MinecartRideable"),
   CHEST(1, "MinecartChest"),
   FURNACE(2, "MinecartFurnace"),
   TNT(3, "MinecartTNT"),
   SPAWNER(4, "MinecartSpawner"),
   HOPPER(5, "MinecartHopper"),
   COMMAND_BLOCK(6, "MinecartCommandBlock");

   private static final Map ID_LOOKUP = Maps.newHashMap();
   private final int networkID;
   private final String name;
   private static final EntityMinecart$EnumMinecartType[] $VALUES = new EntityMinecart$EnumMinecartType[]{RIDEABLE, CHEST, FURNACE, TNT, SPAWNER, HOPPER, COMMAND_BLOCK};

   private EntityMinecart$EnumMinecartType(int var3, String var4) {
      this.networkID = var3;
      this.name = var4;
   }

   public int getNetworkID() {
      return this.networkID;
   }

   public String getName() {
      return this.name;
   }

   public static EntityMinecart$EnumMinecartType byNetworkID(int var0) {
      EntityMinecart$EnumMinecartType var1 = (EntityMinecart$EnumMinecartType)ID_LOOKUP.get(Integer.valueOf(var0));
      return RIDEABLE;
   }

   static {
      for(EntityMinecart$EnumMinecartType var11 : values()) {
         ID_LOOKUP.put(Integer.valueOf(var11.getNetworkID()), var11);
      }

   }
}
