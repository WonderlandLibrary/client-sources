package net;

import net.Wx;
import net.aQU;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4;

public class axe {
   private static String[] b;

   public static void a(Protocol1_10To1_9_3_4 var0) {
      b();
      aQU var2 = new aQU(var0, axe::lambda$register$0, axe::a);
      var2.b((ServerboundPacketType)Wx.CREATIVE_INVENTORY_ACTION, Type.ITEM);
   }

   public static void a(Item var0) {
      String[] var1 = b();
      if(var0 != null) {
         if(var0.getIdentifier() >= 213 && var0.getIdentifier() <= 217) {
            boolean var3 = true;
         } else {
            boolean var10000 = false;
         }

         var0.setIdentifier(1);
         var0.setData((short)0);
      }
   }

   private static void lambda$register$0(Item var0) {
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[2]);
      }

   }
}
