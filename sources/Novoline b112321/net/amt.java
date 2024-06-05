package net;

import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.rewriters.RecipeRewriter;
import viaversion.viaversion.api.rewriters.RecipeRewriter$RecipeConsumer;
import viaversion.viaversion.api.type.Type;

public class amt extends RecipeRewriter {
   private static boolean d;

   public amt(Protocol var1, ItemRewriter$RewriteFunction var2) {
      super(var1, var2);
      this.recipeHandlers.put("crafting_shapeless", this::c);
      this.recipeHandlers.put("crafting_shaped", this::b);
      this.recipeHandlers.put("smelting", this::a);
   }

   public void a(PacketWrapper var1) throws Exception {
      var1.passthrough(Type.STRING);
      c();
      Item[] var3 = (Item[])var1.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
      int var5 = var3.length;
      int var6 = 0;
      if(var6 < var5) {
         Item var7 = var3[var6];
         this.rewriter.rewrite(var7);
         ++var6;
      }

      this.rewriter.rewrite((Item)var1.passthrough(Type.FLAT_VAR_INT_ITEM));
      var1.passthrough(Type.FLOAT);
      var1.passthrough(Type.VAR_INT);
      if(acE.b() == null) {
         b(false);
      }

   }

   public void b(PacketWrapper var1) throws Exception {
      b();
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue() * ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      var1.passthrough(Type.STRING);
      int var4 = 0;
      if(var4 < var3) {
         Item[] var5 = (Item[])var1.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
         int var7 = var5.length;
         int var8 = 0;
         if(var8 < var7) {
            Item var9 = var5[var8];
            this.rewriter.rewrite(var9);
            ++var8;
         }

         ++var4;
      }

      this.rewriter.rewrite((Item)var1.passthrough(Type.FLAT_VAR_INT_ITEM));
   }

   public void c(PacketWrapper var1) throws Exception {
      b();
      var1.passthrough(Type.STRING);
      int var3 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var4 = 0;
      if(var4 < var3) {
         Item[] var5 = (Item[])var1.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
         int var7 = var5.length;
         int var8 = 0;
         if(var8 < var7) {
            Item var9 = var5[var8];
            this.rewriter.rewrite(var9);
            ++var8;
         }

         ++var4;
      }

      this.rewriter.rewrite((Item)var1.passthrough(Type.FLAT_VAR_INT_ITEM));
   }

   public static void b(boolean var0) {
      d = var0;
   }

   public static boolean b() {
      return d;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   private static Exception d(Exception var0) {
      return var0;
   }

   static {
      b(false);
   }
}
