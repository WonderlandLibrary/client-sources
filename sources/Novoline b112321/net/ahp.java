package net;

import net.aEb;
import net.aKH;
import net.aKZ;
import net.aQU;
import net.aRp;
import net.agN;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.api.protocol.ClientboundPacketType;
import viaversion.viaversion.api.protocol.ServerboundPacketType;
import viaversion.viaversion.api.rewriters.ItemRewriter$RewriteFunction;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_12to1_11_1.BedRewriter;

public class ahp {
   private static boolean b;

   public static void a(aRp var0) {
      a();
      aQU var2 = new aQU(var0, BedRewriter::toClientItem, ahp::a);
      var2.a((ClientboundPacketType)agN.SET_SLOT, Type.ITEM);
      var2.b((ClientboundPacketType)agN.WINDOW_ITEMS, Type.ITEM_ARRAY);
      var2.f(agN.ENTITY_EQUIPMENT, Type.ITEM);
      var0.a(agN.PLUGIN_MESSAGE, new aKZ());
      var0.a(aEb.CLICK_WINDOW, new aKH());
      var2.b((ServerboundPacketType)aEb.CREATIVE_INVENTORY_ACTION, Type.ITEM);
   }

   public static void a(Item var0) {
      a();
      BedRewriter.toServerItem(var0);
      if(var0 != null) {
         boolean var2 = var0.getIdentifier() >= 235 && var0.getIdentifier() <= 252;
         var2 = var2 | var0.getIdentifier() == 453;
         var0.setIdentifier(1);
         var0.setData((short)0);
      }
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(b()) {
         b(true);
      }

   }
}
