package ninja.leaping.configurate.transformation;

import java.util.Arrays;
import net.s;
import ninja.leaping.configurate.ConfigurationNode;

class ChainedConfigurationTransformation extends s {
   private final s[] a;

   ChainedConfigurationTransformation(s[] var1) {
      this.a = (s[])Arrays.copyOf(var1, var1.length);
   }

   public void apply(ConfigurationNode var1) {
      s[] var3 = this.a;
      s.b();
      int var4 = var3.length;
      int var5 = 0;
      if(var5 < var4) {
         s var6 = var3[var5];
         var6.apply(var1);
         ++var5;
      }

   }
}
