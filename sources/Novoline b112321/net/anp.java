package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;
import net.EN;
import net.aqG;
import net.aqv;
import net.awD;

class anp extends PacketRemapper {
   final aqv c;

   anp(aqv var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(Type.I);
      this.a(anp::lambda$registerMap$0);
      this.a(Type.k);
      this.a(Type.STRING_ARRAY);
      this.a(this::lambda$registerMap$1);
      this.a(Type.STRING);
      this.a(Type.g);
      this.a(anp::lambda$registerMap$2);
      this.a(aqv.a(this.c, awD.PLAYER, Type.I));
   }

   private static void lambda$registerMap$2(PacketWrapperImpl var0) throws Exception {
      int var1 = ((Integer)var0.b((Type)Type.VAR_INT)).intValue();
      var0.a(Type.M, Short.valueOf((short)Math.max(var1, 255)));
   }

   private void lambda$registerMap$1(PacketWrapperImpl var1) throws Exception {
      var1.b(Type.ac);
      var1.a(Type.ac, EntityPackets.b);
      CompoundTag var2 = (CompoundTag)var1.b(Type.ac);
      var1.a(Type.STRING, aqv.a(this.c, var2));
   }

   private static void lambda$registerMap$0(PacketWrapperImpl var0) throws Exception {
      aqG.d();
      boolean var2 = ((Boolean)var0.b(Type.c)).booleanValue();
      short var3 = ((Short)var0.b(Type.M)).shortValue();
      var3 = (short)(var3 | 8);
      var0.a(Type.M, Short.valueOf(var3));
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
