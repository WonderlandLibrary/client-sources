package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$ParticleHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle$ParticleData;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.List;
import net.ayk;

final class ParticleMapping$1 implements ParticleMapping$ParticleHandler {
   public int[] a(ayk var1, PacketWrapperImpl var2) throws Exception {
      return this.rewrite(((Integer)var2.b((Type)Type.VAR_INT)).intValue());
   }

   public int[] a(ayk var1, List var2) {
      return this.rewrite(((Integer)((Particle$ParticleData)var2.get(0)).getValue()).intValue());
   }

   private int[] rewrite(int var1) {
      int var2 = ayk.k.c(var1);
      int var3 = var2 >> 4;
      int var4 = var2 & 15;
      return new int[]{var3 + (var4 << 12)};
   }

   public boolean isBlockHandler() {
      return true;
   }
}
