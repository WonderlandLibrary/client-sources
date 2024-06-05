package net;

import net.acE;
import net.amt;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.Protocol;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.rewriters.RecipeRewriter$RecipeConsumer;
import viaversion.viaversion.api.type.Type;

public class amx extends amt {
   private static boolean e;

   public amx(Protocol var1, ItemRewriter$RewriteFunction var2) {
      d();
      super(var1, var2);
      this.recipeHandlers.put("stonecutting", this::d);
      this.recipeHandlers.put("blasting", this::a);
      this.recipeHandlers.put("smoking", this::a);
      this.recipeHandlers.put("campfire_cooking", this::a);
      if(acE.b() == null) {
         a(false);
      }

   }

   public void d(PacketWrapper var1) throws Exception {
      d();
      var1.passthrough(Type.STRING);
      Item[] var3 = (Item[])var1.passthrough(Type.FLAT_VAR_INT_ITEM_ARRAY_VAR_INT);
      int var5 = var3.length;
      int var6 = 0;
      if(var6 < var5) {
         Item var7 = var3[var6];
         this.rewriter.rewrite(var7);
         ++var6;
      }

      this.rewriter.rewrite((Item)var1.passthrough(Type.FLAT_VAR_INT_ITEM));
   }

   public static void a(boolean var0) {
      e = var0;
   }

   public static boolean d() {
      return e;
   }

   public static boolean a() {
      boolean var0 = d();
      return true;
   }

   private static Exception c(Exception var0) {
      return var0;
   }

   static {
      a(false);
   }
}
