package net;

import cc.novoline.utils.messages.TextMessage;
import net.Uj;
import net.Ux;
import org.jetbrains.annotations.NotNull;

public class U_ extends TextMessage {
   private U_(@NotNull String var1, @NotNull String var2, @NotNull Uj... var3) {
      super(var1);
      this.append("\n\n");
      int var5 = 0;
      Ux.b();
      int var6 = var3.length;
      if(var5 < var6) {
         Uj var7 = var3[var5];
         this.append(var7.a(var2 + " " + Uj.a(var7)));
         if(var5 + 1 != var6) {
            this.newLine();
         }

         ++var5;
      }

   }

   @NotNull
   public static U_ a(@NotNull String var0, @NotNull String var1, @NotNull Uj... var2) {
      return new U_(var0, var1, var2);
   }
}
