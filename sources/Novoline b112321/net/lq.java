package net;

import net.aW7;
import net.q1;
import viaversion.viaversion.api.PacketWrapper;
import viaversion.viaversion.api.remapper.ValueCreator;
import viaversion.viaversion.api.type.Type;
import viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.TabCompleteTracker;

class lq implements ValueCreator {
   final aW7 a;

   lq(aW7 var1) {
      this.a = var1;
   }

   public void write(PacketWrapper var1) throws Exception {
      q1.b();
      var1.write(Type.VAR_INT, Integer.valueOf(((TabCompleteTracker)var1.user().b(TabCompleteTracker.class)).getTransactionId()));
      String var3 = ((TabCompleteTracker)var1.user().b(TabCompleteTracker.class)).getInput();
      if(var3.endsWith(" ") || var3.isEmpty()) {
         int var4 = var3.length();
         boolean var5 = false;
      }

      int var6 = var3.lastIndexOf(32) + 1;
      int var9 = var3.length() - var6;
      var1.write(Type.VAR_INT, Integer.valueOf(var6));
      var1.write(Type.VAR_INT, Integer.valueOf(var9));
      var6 = ((Integer)var1.passthrough(Type.VAR_INT)).intValue();
      int var7 = 0;
      if(var7 < var6) {
         String var8 = (String)var1.read(Type.STRING);
         if(var8.startsWith("/")) {
            var8 = var8.substring(1);
         }

         var1.write(Type.STRING, var8);
         var1.write(Type.BOOLEAN, Boolean.valueOf(false));
         ++var7;
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
