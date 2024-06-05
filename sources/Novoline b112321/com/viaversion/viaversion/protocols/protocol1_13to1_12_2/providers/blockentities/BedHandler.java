package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import net.aYj;
import net.aq_;
import net.bgR;
import net.cX;

public class BedHandler implements aq_ {
   public int a(bgR var1, CompoundTag var2) {
      aYj.b();
      cX var4 = (cX)var1.b(cX.class);
      Position var5 = new Position((int)this.a(var2.get("x")), (short)((int)this.a(var2.get("y"))), (int)this.a(var2.get("z")));
      if(!var4.c(var5)) {
         Via.d().getLogger().warning("Received an bed color update packet, but there is no bed! O_o " + var2);
         return -1;
      } else {
         int var6 = var4.b((Position)var5).getOriginal() - 972 + 748;
         Tag var7 = var2.get("color");
         var6 = var6 + ((Number)var7.getValue()).intValue() * 16;
         return var6;
      }
   }

   private long a(Tag var1) {
      return ((Integer)var1.getValue()).longValue();
   }
}
