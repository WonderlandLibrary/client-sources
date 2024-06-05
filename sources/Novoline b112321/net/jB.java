package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import net.aFA;
import net.aMz;
import net.aQU;
import net.aRQ;
import net.aoy;
import net.aqN;
import net.ayx;
import net.h0;
import net.q1;
import net.uN;
import net.y7;

public class jB {
   public static void a(ayx var0) {
      aQU var1 = new aQU(var0, jB::a, jB::b);
      var1.a((y7)q1.COOLDOWN);
      var1.b((y7)q1.WINDOW_ITEMS, (Type)Type.FLAT_ITEM_ARRAY);
      var1.a((y7)q1.SET_SLOT, (Type)Type.FLAT_ITEM);
      var0.a((y7)q1.PLUGIN_MESSAGE, (PacketRemapper)(new aoy()));
      var1.f(q1.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
      var1.a((h0)uN.CLICK_WINDOW, (Type)Type.FLAT_ITEM);
      var1.b((h0)uN.CREATIVE_INVENTORY_ACTION, (Type)Type.FLAT_ITEM);
      var1.a(q1.SPAWN_PARTICLE, Type.FLAT_ITEM, Type.FLOAT);
   }

   public static void a(aMz var0) {
      String[] var1 = aqN.a();
      if(var0 != null) {
         var0.a(aRQ.j.getNewItemId(var0.e()));
      }
   }

   public static void b(aMz var0) {
      String[] var1 = aqN.a();
      if(var0 != null) {
         var0.a(aRQ.j.d(var0.e()));
      }
   }
}
