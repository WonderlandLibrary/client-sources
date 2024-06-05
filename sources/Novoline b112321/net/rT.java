package net;

import java.util.List;
import net.iX;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.SetVisibility;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;

public class rT {
   public static iX a(CompiledChunk var0) {
      return var0.b();
   }

   public static boolean b(CompiledChunk var0, EnumWorldBlockLayer var1) {
      return var0.isLayerEmpty(var1);
   }

   public static void a(CompiledChunk var0, TileEntity var1) {
      var0.addTileEntity(var1);
   }

   public static boolean c(CompiledChunk var0, EnumWorldBlockLayer var1) {
      return var0.isLayerStarted(var1);
   }

   public static void a(CompiledChunk var0, EnumWorldBlockLayer var1) {
      var0.setLayerStarted(var1);
   }

   public static void a(CompiledChunk var0, SetVisibility var1) {
      var0.setVisibility(var1);
   }

   public static void a(CompiledChunk var0, iX var1) {
      var0.a(var1);
   }

   public static boolean c(CompiledChunk var0) {
      return var0.isEmpty();
   }

   public static List b(CompiledChunk var0) {
      return var0.getTileEntities();
   }

   public static boolean a(CompiledChunk var0, EnumFacing var1, EnumFacing var2) {
      return var0.isVisible(var1, var2);
   }
}
