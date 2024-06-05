package net;

import com.google.gson.JsonElement;
import java.util.UUID;
import net.S3;
import net.acE;
import viaversion.viarewind.protocol.protocol1_8to1_9.storage.BossBarStorage;
import viaversion.viarewind.utils.ChatUtil;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;

final class a9b extends acE {
   public void registerMap() {
      this.a(a9b::lambda$registerMap$0);
   }

   private static void lambda$registerMap$0(PacketWrapper var0) throws Exception {
      var0.cancel();
      S3.b();
      UUID var2 = (UUID)var0.read(Type.UUID);
      int var3 = ((Integer)var0.read(Type.VAR_INT)).intValue();
      BossBarStorage var4 = (BossBarStorage)var0.user().b(BossBarStorage.class);
      if(var3 == 0) {
         var4.add(var2, ChatUtil.jsonToLegacy((JsonElement)var0.read(Type.COMPONENT)), ((Float)var0.read(Type.FLOAT)).floatValue());
         var0.read(Type.VAR_INT);
         var0.read(Type.VAR_INT);
         var0.read(Type.UNSIGNED_BYTE);
      }

      if(var3 == 1) {
         var4.remove(var2);
      }

      if(var3 == 2) {
         var4.updateHealth(var2, ((Float)var0.read(Type.FLOAT)).floatValue());
      }

      if(var3 == 3) {
         String var5 = ChatUtil.jsonToLegacy((JsonElement)var0.read(Type.COMPONENT));
         var4.updateTitle(var2, var5);
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
