package net;

import java.io.InputStream;
import java.util.Map;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.optifine.MatchBlock;
import net.optifine.Reflector;

public class ahc {
   public static void a(ResourceLocation var0, String var1) {
      acE[] var2 = MatchBlock.b();
      if(!Reflector.aC.d()) {
         Object var3 = Reflector.f(Reflector.bQ, new Object[0]);
         Reflector.f(var3, Reflector.aC, new Object[]{var0, var1});
      }

   }

   public static void a(ResourceLocation var0) {
      acE[] var1 = MatchBlock.b();
      if(!Reflector.z.d()) {
         Object var2 = Reflector.f(Reflector.bQ, new Object[0]);
         Reflector.f(var2, Reflector.z, new Object[]{var0});
      }

   }

   public static void a(String var0, Object var1) {
      MatchBlock.b();
      Map var3 = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);
      if(var3 != null) {
         var3.put(var0, var1);
      }

   }

   public static boolean a(RenderGlobal var0, float var1, int var2) {
      acE[] var3 = MatchBlock.b();
      return !Reflector.bv.d()?false:Reflector.b(Reflector.bv, new Object[]{var0, Float.valueOf(var1), Integer.valueOf(var2)});
   }

   public static InputStream a(String var0) {
      acE[] var1 = MatchBlock.b();
      if(!Reflector.OptiFineClassTransformer_instance.exists()) {
         return null;
      } else {
         Object var2 = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
         return null;
      }
   }

   public static boolean a(IBlockState var0) {
      MatchBlock.b();
      Block var2 = var0.getBlock();
      return !Reflector.bm.d()?var2.hasTileEntity():Reflector.d(var2, Reflector.bm, new Object[]{var0});
   }

   public static boolean a(ItemStack var0) {
      acE[] var1 = MatchBlock.b();
      return !Reflector.ce.d()?var0.isItemDamaged():Reflector.d(var0.getItem(), Reflector.ce, new Object[]{var0});
   }

   public static boolean a(ItemArmor var0, ItemStack var1) {
      MatchBlock.b();
      int var3 = var0.getColor(var1);
      return var3 != 16777215;
   }
}
