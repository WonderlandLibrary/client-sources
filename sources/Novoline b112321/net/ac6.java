package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import net.acE;
import net.aqT;
import net.aqp;
import net.cT;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;

class ac6 extends acE {
   final aqT c;

   ac6(aqT var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      cT var3 = (cT)var1.user().b(cT.class);
      Chunk1_9_3_4Type var4 = new Chunk1_9_3_4Type(var3);
      Chunk var5 = (Chunk)var1.passthrough(var4);
      aqp.d();
      aqT.a(this.c, var5);

      for(CompoundTag var7 : var5.getBlockEntities()) {
         Tag var8 = var7.get("id");
         if(var8 instanceof StringTag) {
            String var9 = (String)var8.getValue();
            if(var9.equals("minecraft:sign")) {
               ((StringTag)var8).setValue("Sign");
            }
            break;
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
