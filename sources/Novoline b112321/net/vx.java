package net;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.StringType;
import io.netty.buffer.ByteBuf;
import net.tp;

public class vx extends Type {
   private static final StringType ah = new StringType(262144);

   public vx() {
      super("JsonElement", JsonElement.class);
   }

   public JsonElement a(ByteBuf var1) throws Exception {
      String var2 = ah.read(var1);

      try {
         return tp.b().parse(var2);
      } catch (JsonSyntaxException var4) {
         Via.d().getLogger().severe("Error when trying to parse json: " + var2);
         throw var4;
      }
   }

   public void a(ByteBuf var1, JsonElement var2) throws Exception {
      ah.write(var1, var2.toString());
   }
}
