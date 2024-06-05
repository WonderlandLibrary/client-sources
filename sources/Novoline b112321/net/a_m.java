package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import net.aKQ;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_11to1_10.BlockEntityRewriter;
import viaversion.viaversion.protocols.protocol1_11to1_10.EntityIdRewriter;

class a_m implements PacketHandler {
   final aKQ a;

   a_m(aKQ var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      EntityIdRewriter.b();
      CompoundTag var3 = (CompoundTag)var1.get(Type.NBT, 0);
      if(((Short)var1.get(Type.UNSIGNED_BYTE, 0)).shortValue() == 1) {
         EntityIdRewriter.toClientSpawner(var3);
      }

      if(var3.contains("id")) {
         ((StringTag)var3.get("id")).setValue(BlockEntityRewriter.toNewIdentifier((String)var3.get("id").getValue()));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
