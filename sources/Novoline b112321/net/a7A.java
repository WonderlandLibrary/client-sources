package net;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import net.S3;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a7A extends acE {
   public void registerMap() {
      this.a(Type.POSITION);
      this.a(Type.UNSIGNED_BYTE);
      this.a(Type.NBT);
      this.a(a7A::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      S3.b();
      CompoundTag var2 = (CompoundTag)var0.get(Type.NBT, 0);
      if(var2 != null && var2.contains("SpawnData")) {
         String var3 = (String)((CompoundTag)var2.get("SpawnData")).get("id").getValue();
         var2.remove("SpawnData");
         var2.put(new StringTag("entityId", var3));
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
