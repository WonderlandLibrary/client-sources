package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.Protocol1_13_1To1_13;
import net.HR;
import net.aFA;
import net.aKS;
import net.aKu;
import net.aMz;
import net.aQU;
import net.amt;
import net.h0;
import net.q1;
import net.uN;
import net.y7;

public class xN {
   public static void a(Protocol1_13_1To1_13 var0) {
      HR.b();
      aQU var2 = new aQU(var0, xN::a, xN::b);
      var2.a((y7)q1.SET_SLOT, (Type)Type.FLAT_ITEM);
      var2.b((y7)q1.WINDOW_ITEMS, (Type)Type.FLAT_ITEM_ARRAY);
      var2.c(q1.ADVANCEMENTS, Type.FLAT_ITEM);
      var2.a((y7)q1.COOLDOWN);
      var0.a(q1.PLUGIN_MESSAGE, new aKu());
      var2.f(q1.ENTITY_EQUIPMENT, Type.FLAT_ITEM);
      amt var3 = new amt(var0, xN::a);
      var0.a(q1.DECLARE_RECIPES, new aKS(var3));
      var2.a((h0)uN.CLICK_WINDOW, (Type)Type.FLAT_ITEM);
      var2.b((h0)uN.CREATIVE_INVENTORY_ACTION, (Type)Type.FLAT_ITEM);
      var2.a(q1.SPAWN_PARTICLE, Type.FLAT_ITEM, Type.FLOAT);
   }

   public static void a(aMz var0) {
      PacketRemapper[] var1 = HR.b();
      if(var0 != null) {
         var0.a(Protocol1_13_1To1_13.j.getNewItemId(var0.e()));
      }
   }

   public static void b(aMz var0) {
      PacketRemapper[] var1 = HR.b();
      if(var0 != null) {
         var0.a(Protocol1_13_1To1_13.j.d(var0.e()));
      }
   }
}
