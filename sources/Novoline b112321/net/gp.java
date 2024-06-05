package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import java.util.List;
import net.aVy;
import net.acE;
import net.agN;
import net.cT;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks.FakeTileEntity;
import viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types.Chunk1_9_1_2Type;

class gp implements PacketHandler {
   final aVy a;

   gp(aVy var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      agN.b();
      cT var3 = (cT)var1.user().b(cT.class);
      Chunk1_9_1_2Type var4 = new Chunk1_9_1_2Type(var3);
      Chunk var5 = (Chunk)var1.passthrough(var4);
      List var6 = var5.getBlockEntities();
      int var7 = 0;
      if(var7 < var5.getSections().length) {
         ChunkSection var8 = var5.getSections()[var7];
         int var9 = 0;
         if(var9 < 16) {
            int var10 = 0;
            if(var10 < 16) {
               int var11 = 0;
               if(var11 < 16) {
                  int var12 = var8.getBlockId(var11, var9, var10);
                  if(FakeTileEntity.hasBlock(var12)) {
                     var6.add(FakeTileEntity.getFromBlock(var11 + (var5.getX() << 4), var9 + (var7 << 4), var10 + (var5.getZ() << 4), var12));
                  }

                  ++var11;
               }

               ++var10;
            }

            ++var9;
         }

         ++var7;
      }

      var1.write(Type.NBT_ARRAY, var5.getBlockEntities().toArray(new CompoundTag[0]));
      if(acE.b() == null) {
         agN.b(false);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
