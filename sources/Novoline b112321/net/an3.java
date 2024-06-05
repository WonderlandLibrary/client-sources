package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import net.acE;
import net.aqG;
import net.w_;
import net.wj;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.Position;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.remapper.PacketHandler;

class an3 extends acE {
   final aqG c;

   an3(aqG var1) {
      this.c = var1;
   }

   public void registerMap() {
      this.a(this::lambda$registerMap$0);
   }

   private void lambda$registerMap$0(PacketWrapper var1) throws Exception {
      aqG.d();
      Chunk var3 = (Chunk)var1.read(new w_());
      var1.write(new wj(), var3);
      var3.setIgnoreOldLightData(true);
      int var4 = 0;
      if(var4 < var3.getSections().length) {
         ChunkSection var5 = var3.getSections()[var4];
         ++var4;
      }

      for(CompoundTag var11 : var3.getBlockEntities()) {
         if(var11 != null) {
            IntTag var6 = (IntTag)var11.get("x");
            IntTag var7 = (IntTag)var11.get("y");
            IntTag var8 = (IntTag)var11.get("z");
            if(var6 != null && var7 != null && var8 != null) {
               aqG.a(this.c, var11, new Position(var6.getValue().intValue(), var7.getValue().shortValue(), var8.getValue().intValue()));
            }
            break;
         }
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
