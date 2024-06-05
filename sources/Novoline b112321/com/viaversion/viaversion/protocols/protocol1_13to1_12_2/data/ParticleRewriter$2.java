package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter$ParticleDataHandler;
import net.Gh;
import net.aMz;
import net.amt;

final class ParticleRewriter$2 implements ParticleRewriter$ParticleDataHandler {
   public Gh a(Gh var1, Integer[] var2) {
      boolean var3 = amt.b();
      if(var2.length == 1) {
         new aMz(var2[0].intValue(), (byte)1, (short)0, (CompoundTag)null);
      }

      if(var2.length == 2) {
         new aMz(var2[0].intValue(), (byte)1, var2[1].shortValue(), (CompoundTag)null);
      }

      return var1;
   }
}
