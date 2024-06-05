package net;

import net.amx;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.rewriters.RecipeRewriter$RecipeConsumer;
import viaversion.viaversion.api.type.Type;

public class amd extends amx {
   private static int[] f;

   public amd(Protocol var1, ItemRewriter$RewriteFunction var2) {
      super(var1, var2);
      this.recipeHandlers.put("smithing", this::e);
   }

   public void e(PacketWrapper var1) throws Exception {
      Item[] var3 = (Item[])var1.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
      int[] var2 = a();
      int var5 = var3.length;
      int var6 = 0;
      if(var6 < var5) {
         Item var7 = var3[var6];
         this.rewriter.rewrite(var7);
         ++var6;
      }

      Item[] var4 = (Item[])var1.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
      var6 = var4.length;
      int var11 = 0;
      if(var11 < var6) {
         Item var8 = var4[var11];
         this.rewriter.rewrite(var8);
         ++var11;
      }

      this.rewriter.rewrite((Item)var1.passthrough(Type.FLAT_VAR_INT_ITEM));
   }

   public static void b(int[] var0) {
      f = var0;
   }

   public static int[] a() {
      return f;
   }

   private static Exception b(Exception var0) {
      return var0;
   }

   static {
      if(a() != null) {
         b(new int[5]);
      }

   }
}
