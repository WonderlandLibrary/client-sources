package net;

import com.google.common.reflect.TypeToken;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.Ea;
import net.Ug;
import net.X9;
import net.ZV;
import net.akH;

class q7 implements ZV {
   private q7() {
   }

   public Pattern a(TypeToken var1, akH var2) throws X9 {
      akH var10000 = var2;

      try {
         return Pattern.compile(var10000.d());
      } catch (PatternSyntaxException var4) {
         throw new X9(var4);
      }
   }

   public void a(TypeToken var1, Pattern var2, akH var3) {
      PacketRemapper[] var4 = Ea.b();
      var3.a((Object)(var2 != null?var2.pattern():null));
   }

   q7(Ug var1) {
      this();
   }

   private static PatternSyntaxException a(PatternSyntaxException var0) {
      return var0;
   }
}
