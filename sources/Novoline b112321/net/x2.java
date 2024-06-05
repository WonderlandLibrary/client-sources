package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import net.Gh;
import net.amt;
import viaversion.viaversion.api.minecraft.item.Item;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.data.ParticleRewriter$ParticleDataHandler;

final class x2 implements ParticleRewriter$ParticleDataHandler {
   public Gh a(Gh var1, Integer[] var2) {
      boolean var3 = amt.b();
      if(var2.length == 1) {
         new Item(var2[0].intValue(), (byte)1, (short)0, (CompoundTag)null);
      }

      if(var2.length == 2) {
         new Item(var2[0].intValue(), (byte)1, var2[1].shortValue(), (CompoundTag)null);
      }

      return var1;
   }
}
