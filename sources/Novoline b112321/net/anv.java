package net;

import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.Protocol1_16_1To1_16_2;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import net.EN;
import net.aqG;

class anv extends PacketRemapper {
   final aqG c;

   anv(aqG var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapperImpl var1) throws Exception {
      long var3 = ((Long)var1.b(Type.g)).longValue();
      var1.b(Type.c);
      int var5 = (int)(var3 >> 42);
      int var6 = (int)(var3 << 44 >> 44);
      int var7 = (int)(var3 << 22 >> 42);
      var1.a(Type.I, Integer.valueOf(var5));
      aqG.c();
      var1.a(Type.I, Integer.valueOf(var7));
      BlockChangeRecord[] var8 = (BlockChangeRecord[])var1.b(Type.VAR_LONG_BLOCK_CHANGE_RECORD_ARRAY);
      var1.a(Type.BLOCK_CHANGE_RECORD_ARRAY, var8);
      int var9 = 0;
      if(var9 < var8.length) {
         BlockChangeRecord var10 = var8[var9];
         int var11 = ((Protocol1_16_1To1_16_2)aqG.a(this.c)).getMappingData().c(var10.getBlockId());
         var8[var9] = new BlockChangeRecord1_8(var10.getSectionX(), var10.getY(var6), var10.getSectionZ(), var11);
         ++var9;
      }

   }
}
