package net;

import com.google.gson.JsonElement;
import net.aK7;
import net.aRY;
import net.aRp;
import net.acE;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.Via;
import viaversion.viaversion.api.remapper.PacketHandler;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_12to1_11_1.ChatItemRewriter;
import viaversion.viaversion.protocols.protocol1_12to1_11_1.TranslateRewriter;

class s7 implements PacketHandler {
   final aK7 a;

   s7(aK7 var1) {
      this.a = var1;
   }

   public void handle(PacketWrapper var1) throws Exception {
      acE[] var2 = aRp.a();
      if(Via.getConfig().is1_12NBTArrayFix()) {
         try {
            JsonElement var3 = (JsonElement)aRY.i.transform((PacketWrapper)null, ((JsonElement)var1.passthrough(Type.COMPONENT)).toString());
            TranslateRewriter.toClient(var3, var1.user());
            ChatItemRewriter.toClient(var3, var1.user());
            var1.set(Type.COMPONENT, 0, var3);
         } catch (Exception var4) {
            var4.printStackTrace();
         }

      }
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
