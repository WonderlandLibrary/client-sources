package cc.novoline.gui.screen.alt.repository.credential;

import cc.novoline.utils.java.Checks;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AltCredential {
   private final String login;
   private final String password;
   private static boolean b;

   public AltCredential(@NotNull String var1, @Nullable String var2) {
      b();
      Checks.notBlank(var1, "login");
      this.login = var1.trim();
      this.password = StringUtils.isNotBlank(var2)?var2:null;
   }

   @NotNull
   public String getLogin() {
      return this.login;
   }

   @Nullable
   public String getPassword() {
      return this.password;
   }

   public boolean equals(Object var1) {
      boolean var2 = b();
      if(this == var1) {
         return true;
      } else if(var1 != null && this.getClass() == var1.getClass()) {
         AltCredential var3 = (AltCredential)var1;
         return this.login.equals(var3.login) && Objects.equals(this.password, var3.password);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.login, this.password});
   }

   public String toString() {
      boolean var1 = b();
      return this.login + (this.password != null?":" + this.password:"");
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean d() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!d()) {
         b(true);
      }

   }
}
