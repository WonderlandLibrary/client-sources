package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.Position;
import net.aYj;
import net.aq_;
import net.bgR;
import net.cX;

public class SkullHandler implements aq_ {
   private static final int SKULL_WALL_START = 5447;
   private static final int SKULL_END = 5566;

   public int a(bgR var1, CompoundTag var2) {
      cX var4 = (cX)var1.b(cX.class);
      aYj.a();
      Position var5 = new Position((int)this.a(var2.get("x")), (short)((int)this.a(var2.get("y"))), (int)this.a(var2.get("z")));
      if(!var4.c(var5)) {
         Via.d().getLogger().warning("Received an head update packet, but there is no head! O_o " + var2);
         return -1;
      } else {
         int var6 = var4.b((Position)var5).getOriginal();
         if(var6 >= 5447 && var6 <= 5566) {
            Tag var7 = var2.get("SkullType");
            var6 = var6 + ((Number)var2.get("SkullType").getValue()).intValue() * 20;
            if(var2.contains("Rot")) {
               int var10000 = var6 + ((Number)var2.get("Rot").getValue()).intValue();
            }
         }

         Via.d().getLogger().warning("Why does this block have the skull block entity? " + var2);
         return -1;
      }
   }

   private long a(Tag var1) {
      return ((Integer)var1.getValue()).longValue();
   }
}
