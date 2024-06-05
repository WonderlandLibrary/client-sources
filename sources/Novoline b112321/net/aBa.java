package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import java.util.Iterator;
import net.aKc;
import net.cT;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.minecraft.chunks.Chunk;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.protocols.protocol1_11to1_10.BlockEntityRewriter;
import viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;
import viaversion.viaversion.protocols.protocol1_9_1_2to1_9_3_4.types.Chunk1_9_3_4Type;

class aBa implements PacketHandler {
   final aKc a;

   aBa(aKc var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      cT var3 = (cT)var1.user().b(cT.class);
      EntityIdRewriter.b();
      Chunk1_9_3_4Type var4 = new Chunk1_9_3_4Type(var3);
      Chunk var5 = (Chunk)var1.passthrough(var4);
      var1.clearInputBuffer();
      if(var5.getBlockEntities() != null) {
         Iterator var6 = var5.getBlockEntities().iterator();
         if(var6.hasNext()) {
            CompoundTag var7 = (CompoundTag)var6.next();
            if(var7.contains("id")) {
               String var8 = ((StringTag)var7.get("id")).getValue();
               if(var8.equals("MobSpawner")) {
                  EntityIdRewriter.toClientSpawner(var7);
               }

               ((StringTag)var7.get("id")).setValue(BlockEntityRewriter.toNewIdentifier(var8));
            }
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
