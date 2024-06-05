package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping$ParticleHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.Particle$ParticleData;
import com.viaversion.viaversion.protocol.packet.PacketWrapperImpl;
import java.util.List;
import net.aMz;
import net.ayk;

final class ParticleMapping$3 implements ParticleMapping$ParticleHandler {
   public int[] a(ayk var1, PacketWrapperImpl var2) throws Exception {
      return this.a(var1, (aMz)var2.b(Type.FLAT_ITEM));
   }

   public int[] a(ayk var1, List var2) {
      return this.a(var1, (aMz)((Particle$ParticleData)var2.get(0)).getValue());
   }

   private int[] a(ayk var1, aMz var2) {
      aMz var3 = var1.a().a((aMz)var2);
      return new int[]{var3.e(), var3.c()};
   }
}
