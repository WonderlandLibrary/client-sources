package net;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.items.ItemRewriter;
import net.EN;
import net.aMz;
import net.afz;
import net.axZ;

final class aDb extends PacketRemapper {
   public void registerMap() {
      this.a(aDb::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      int var2 = ((Integer)var0.b(Type.I)).intValue();
      short var3 = ((Short)var0.b(Type.M)).shortValue();
      int var4 = ((Integer)var0.b(Type.I)).intValue();
      var0.a(Type.POSITION, new Position(var2, var3, var4));
      afz.b();
      var0.a(Type.k);
      aMz var5 = (aMz)var0.b(axZ.c);
      var5 = ItemRewriter.b(var5);
      var0.a(Type.ITEM, var5);
      int var6 = 0;
      if(var6 < 3) {
         var0.a(Type.k);
         ++var6;
      }

   }
}
