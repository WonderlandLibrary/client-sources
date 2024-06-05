package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections;

import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData$ConnectorInitAction;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.WrappedBlockData;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.abi;
import net.af3;
import net.bgR;

public class FireConnectionHandler extends abi {
   private static final String[] WOOD_TYPES = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "dark_oak"};
   private static final Map connectedBlocks = new HashMap();
   private static final Set flammableBlocks = new HashSet();

   private static void addWoodTypes(Set var0, String var1) {
      String[] var3 = WOOD_TYPES;
      abi.b();
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         String var6 = var3[var5];
         var0.add("minecraft:" + var6 + var1);
         ++var5;
      }

   }

   static ConnectionData$ConnectorInitAction init() {
      HashSet var0 = new HashSet();
      var0.add("minecraft:tnt");
      var0.add("minecraft:vine");
      var0.add("minecraft:bookshelf");
      var0.add("minecraft:hay_block");
      var0.add("minecraft:deadbush");
      addWoodTypes(var0, "_slab");
      addWoodTypes(var0, "_log");
      addWoodTypes(var0, "_planks");
      addWoodTypes(var0, "_leaves");
      addWoodTypes(var0, "_fence");
      addWoodTypes(var0, "_fence_gate");
      addWoodTypes(var0, "_stairs");
      FireConnectionHandler var1 = new FireConnectionHandler();
      return FireConnectionHandler::lambda$init$0;
   }

   private static byte getStates(WrappedBlockData var0) {
      abi.b();
      byte var2 = 0;
      if(var0.getValue("east").equals("true")) {
         var2 = (byte)(var2 | 1);
      }

      if(var0.getValue("north").equals("true")) {
         var2 = (byte)(var2 | 2);
      }

      if(var0.getValue("south").equals("true")) {
         var2 = (byte)(var2 | 4);
      }

      if(var0.getValue("up").equals("true")) {
         var2 = (byte)(var2 | 8);
      }

      if(var0.getValue("west").equals("true")) {
         var2 = (byte)(var2 | 16);
      }

      return var2;
   }

   public int a(bgR var1, Position var2, int var3) {
      abi.b();
      byte var5 = 0;
      if(flammableBlocks.contains(Integer.valueOf(this.a(var1, var2.getRelative(BlockFace.EAST))))) {
         var5 = (byte)(var5 | 1);
      }

      if(flammableBlocks.contains(Integer.valueOf(this.a(var1, var2.getRelative(BlockFace.NORTH))))) {
         var5 = (byte)(var5 | 2);
      }

      if(flammableBlocks.contains(Integer.valueOf(this.a(var1, var2.getRelative(BlockFace.SOUTH))))) {
         var5 = (byte)(var5 | 4);
      }

      if(flammableBlocks.contains(Integer.valueOf(this.a(var1, var2.getRelative(BlockFace.TOP))))) {
         var5 = (byte)(var5 | 8);
      }

      if(flammableBlocks.contains(Integer.valueOf(this.a(var1, var2.getRelative(BlockFace.WEST))))) {
         var5 = (byte)(var5 | 16);
      }

      return ((Integer)connectedBlocks.get(Byte.valueOf(var5))).intValue();
   }

   private static void lambda$init$0(Set var0, FireConnectionHandler var1, WrappedBlockData var2) {
      abi.b();
      String var4 = var2.getMinecraftKey();
      if(var4.contains("_wool") || var4.contains("_carpet") || var0.contains(var4)) {
         flammableBlocks.add(Integer.valueOf(var2.getSavedBlockStateId()));
      }

      if(var4.equals("minecraft:fire")) {
         int var5 = var2.getSavedBlockStateId();
         connectedBlocks.put(Byte.valueOf(getStates(var2)), Integer.valueOf(var5));
         af3.h.put(var5, var1);
      }

   }
}
