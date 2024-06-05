package cc.novoline.gui.screen.alt.repository.credential;

import cc.novoline.gui.screen.alt.repository.credential.AltCredential;
import cc.novoline.utils.java.Checks;
import com.thealtening.api.response.AccountDetails;
import java.util.Objects;
import net.acE;

public class AlteningAltCredential extends AltCredential {
   private final String name;
   private final AccountDetails details;

   public AlteningAltCredential(String var1, String var2, AccountDetails var3) {
      super(var1, (String)null);
      this.name = Checks.notBlank(var2, "name");
      this.details = var3;
   }

   public String getName() {
      return this.name;
   }

   public AccountDetails getDetails() {
      return this.details;
   }

   public boolean equals(Object var1) {
      boolean var2 = AltCredential.d();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AlteningAltCredential)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         AlteningAltCredential var3 = (AlteningAltCredential)var1;
         return this.name.equals(var3.name) && this.details.equals(var3.details);
      }
   }

   public int hashCode() {
      boolean var1 = AltCredential.d();
      int var10000 = Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.name, this.details});
      if(acE.b() == null) {
         AltCredential.b(false);
      }

      return var10000;
   }
}
