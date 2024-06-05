package net;

import com.google.gson.JsonElement;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import net.nP;

public class we extends Type {
   public we() {
      super("JsonElement", JsonElement.class);
   }

   public JsonElement a(ByteBuf var1) throws Exception {
      boolean var2 = var1.readBoolean();
      return (JsonElement)Type.p.read(var1);
   }

   public void a(ByteBuf var1, JsonElement var2) throws Exception {
      String var3 = nP.b();
      var1.writeBoolean(false);
      var1.writeBoolean(true);
      Type.p.write(var1, var2);
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
